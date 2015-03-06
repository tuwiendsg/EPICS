/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depictool.generator;

import at.ac.tuwien.dsg.common.deployment.DeployAction;
import at.ac.tuwien.dsg.common.deployment.DeploymentDescription;
import at.ac.tuwien.dsg.common.deployment.ElasticService;
import at.ac.tuwien.dsg.common.deployment.ElasticServices;
import at.ac.tuwien.dsg.common.entity.eda.EDaaSType;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.ElasticityProcess;
import at.ac.tuwien.dsg.common.entity.process.MetricProcess;
import at.ac.tuwien.dsg.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.common.entity.eda.ElasticState;
import at.ac.tuwien.dsg.common.entity.eda.ElasticStateSet;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorProcess;
import at.ac.tuwien.dsg.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.common.utils.IOUtils;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depic.depictool.connector.ComotConnector;
import at.ac.tuwien.dsg.depic.depictool.connector.ElasticServiceMonitor;
import at.ac.tuwien.dsg.depic.depictool.connector.SalsaConnector;

import at.ac.tuwien.dsg.depictool.elstore.ElasticityProcessStore;
import at.ac.tuwien.dsg.depictool.util.Configuration;
import at.ac.tuwien.dsg.common.utils.Logger;


import at.ac.tuwien.dsg.depictool.util.ZipUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class Generator {

    ElasticDataAsset elasticDataObject;
    String eDaaSName;
    QoRModel qorModel;
    MetricProcess elasticityProcessConfiguration;
    EDaaSType eDaaSType;

    public Generator() {
    }


    public Generator(String eDaaSName, QoRModel qorModel, MetricProcess elasticityProcessConfiguration, EDaaSType eDaaSType) {
        this.eDaaSName = eDaaSName;
        this.qorModel = qorModel;
        this.elasticityProcessConfiguration = elasticityProcessConfiguration;
        this.eDaaSType = eDaaSType;
    }
    
    
    

    public Generator(ElasticDataAsset elasticDataObject, MetricProcess elasticityProcessConfiguration) {
        this.elasticDataObject = elasticDataObject;
        this.elasticityProcessConfiguration = elasticityProcessConfiguration;
    }

    public void startGenerator() {
        ElasticDataAsset elasticDataAsset = generateElasticityProcesses();
        generateElasticDaaS();
        prepareDeployment(elasticDataAsset);
    }

    public void prepareDeployment(ElasticDataAsset elasticDataAsset) {

        ElasticityProcessStore elStore = new ElasticityProcessStore();
        Configuration config = new Configuration();
        String parentPath = config.getCurrentPath();
        
        String edaasPath = parentPath + "/WEB-INF/classes/project/eDaaS";
        String edaasZipFile  = parentPath + "/edaasproject/" + eDaaSName + ".zip";
        
        Logger.logInfo("PARENT PATH: " + parentPath);

        ZipUtils zipUtils = new ZipUtils();
        zipUtils.zipDir(edaasZipFile, edaasPath);
        String edaasZipURL = config.getConfig("EDAAS.URL") + "/edaasproject/" + eDaaSName + ".zip";
        
        
        
        IOUtils fileReader = new IOUtils(config.getArtifactPath());
        String edaasArtifactTemplate = fileReader.readData("install-edaas.sh");
        edaasArtifactTemplate = edaasArtifactTemplate.replace("EDAAS.ZIP.URL", edaasZipURL);
        edaasArtifactTemplate = edaasArtifactTemplate.replace("EDAAS.NAME", eDaaSName);
        
        IOUtils fileWriter = new IOUtils(parentPath+"/edaasproject");
        fileWriter.writeData(edaasArtifactTemplate, eDaaSName+".sh");
        String edaasShArtifactURL = config.getConfig("EDAAS.URL") + "/edaasproject/" + eDaaSName + ".sh";
        
        
        String elasticityProcessesXML = "";
        try {
            elasticityProcessesXML = JAXBUtils.marshal(elasticDataAsset.getElasticityProcess(), ElasticityProcess.class);
        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }

   
        DeployAction eDaaSDA = new DeployAction(eDaaSName, eDaaSName, "sh", edaasShArtifactURL);
        Logger.logInfo("EDAAS ARTIFACT URL: " + edaasShArtifactURL);
        
        
        ComotConnector comotConnector = new ComotConnector(elasticDataAsset.getElasticityProcess(), eDaaSDA);
        //String cloudServiceID = comotConnector.deployCloudSevices();
        comotConnector.deployCloudSevices2();
        //comotConnector.deployCloudSevices();
        Logger.logInfo("On Deployment Process: ..." + comotConnector.getCloudServiceID() );
        
        List<ElasticService> listOfElasticServices = comotConnector.getCloudServiceInfo();
        ElasticServices elasticServices = new ElasticServices(listOfElasticServices);
        configElasticServices(elasticServices);
        
        ElasticServiceMonitor elasticServiceMonitor = new ElasticServiceMonitor(eDaaSName, comotConnector);
        elasticServiceMonitor.start();
                
        String elasticStateSetXML ="";
        
        try {
            elasticStateSetXML = JAXBUtils.marshal(elasticDataAsset.getElasticStateSet(), ElasticStateSet.class);
        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        elStore.storeElasticityProcesses(eDaaSName,elasticStateSetXML, elasticityProcessesXML, "", eDaaSType.geteDaaSType());
        
        elStore.storeElasticServices(listOfElasticServices);
        //configure elasticity services
        configureElasticityServices(listOfElasticServices);
        

    }
    
    private void configElasticServices(ElasticServices elasticServices){
        String eSXML="";
        try {
            eSXML = JAXBUtils.marshal(elasticServices, ElasticServices.class);
        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
        Configuration configuration  = new Configuration();
        String ip = configuration.getConfig("ORCHESTRATOR.IP");
        String port = configuration.getConfig("ORCHESTRATOR.PORT");
        String resource = configuration.getConfig("ORCHESTRATOR.ELASTIC.SERVICE.RESOURCE");
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        ws.callPutMethod(eSXML);
        
    }
    
    private void configureElasticityServices(List<ElasticService> listOfElasticServices){
        
        for (ElasticService elasticService : listOfElasticServices){
            
            if (elasticService.getActionID().equals(eDaaSName)){
                String configureDataAssetLoaderUri = elasticService.getUri()+"/eDaaS/rest/dataasset/conf/dataassetloaderip";
                String configureOrchestratorrUri = elasticService.getUri()+"/eDaaS/rest/dataasset/conf/orchestratorip";
                Configuration cfg =new Configuration();
                String daLoaderIp = cfg.getConfig("DATA.ASSET.LOADER.IP.LOCAL");
                String orchestratorIp = cfg.getConfig("ORCHESTRATOR.IP.LOCAL");
                
                RestfulWSClient ws1 = new RestfulWSClient(configureDataAssetLoaderUri);
                ws1.callPutMethod(daLoaderIp);
                
                RestfulWSClient ws2 = new RestfulWSClient(configureOrchestratorrUri);
                ws2.callPutMethod(orchestratorIp);
                
                
            } else {
            
            String configureUri = elasticService.getUri()+"/conf";
            Configuration cfg =new Configuration();
            String daLoaderIp = cfg.getConfig("DATA.ASSET.LOADER.IP.LOCAL");
            RestfulWSClient ws = new RestfulWSClient(configureUri);
            ws.callPutMethod(daLoaderIp);
            
            }
            
        }
        
        
    }

    private void generateElasticDaaS() {

        Logger.logInfo("Start generate APIs ...");

        DaaSGenerator daaSGenerator = new DaaSGenerator(qorModel);
        daaSGenerator.generateDaaS();

    }

    private ElasticDataAsset generateElasticityProcesses() {
        Logger.logInfo("Start generate Elasticity Processes");
        Logger.logInfo("eDaaS: " + eDaaSName);
        Logger.logInfo("qor metrics: " + qorModel.getListOfMetrics().get(0).getName());
        Logger.logInfo("metric process: " + elasticityProcessConfiguration.getListOfMetricElasticityProcesses().get(0).getMetricName());

        ElasticityProcessesGenerator elasticityProcessGenerator = new ElasticityProcessesGenerator(qorModel, elasticityProcessConfiguration);
        MonitorProcess monitorProcess = elasticityProcessGenerator.generateMonitorProcess();
        List<ElasticState> initialElasticStateSet = elasticityProcessGenerator.generateSetOfInitialElasticState();
        
        Logger logger = new Logger();
        Logger.logInfo("Inital eState Set");
        logger.logElasticState(initialElasticStateSet);
        
        
        List<ElasticState> finalElasticStateSet = elasticityProcessGenerator.generateSetOfFinalElasticState(initialElasticStateSet);
        Logger.logInfo("");
        Logger.logInfo("Final eState Set");
        logger.logElasticState(finalElasticStateSet);
        
        ElasticStateSet elasticStateSet = new ElasticStateSet(initialElasticStateSet, finalElasticStateSet);
        
        List<ControlProcess> listOfControlProcesses = elasticityProcessGenerator.generateControlProcesses(initialElasticStateSet, finalElasticStateSet);
        ElasticityProcess elasticityProcesses = new ElasticityProcess(monitorProcess, listOfControlProcesses);

        ElasticDataAsset elasticDataAsset = new ElasticDataAsset(eDaaSName,eDaaSType, elasticityProcesses, elasticStateSet);
        //log
       
        
        logger.logMonitorProcesses(monitorProcess);
        logger.logControlProcesses(listOfControlProcesses);

        return elasticDataAsset;

    }
/*
    private String generateDeploymentDesciptionForElasticityProcesses(ElasticityProcess elasticityProcesses) {

        String deployementDescriptionXml = "";

        List<DeployAction> deployMonitoringServices = new ArrayList<DeployAction>();

        ElasticityProcessStore epStore = new ElasticityProcessStore();
        MonitorProcess monitorProcess = elasticityProcesses.getMonitorProcess();
        List<ControlProcess> listOfControlProcesses = elasticityProcesses.getListOfControlProcesses();

        List<MonitorAction> monitorActions = monitorProcess.getListOfMonitorActions();

        for (MonitorAction ma : monitorActions) {
            

            if (!isDeployActionExisting(deployMonitoringServices, ma.getMonitorActionID())) {
                DeployAction deployAction = epStore.getPrimitiveAction(ma.getMonitorActionID());
                deployMonitoringServices.add(deployAction);
            }

        }
        
       
        
        

        for (ControlProcess cp : listOfControlProcesses) {
            List<ControlAction> controlActions = cp.getListOfControlActions();
            for (ControlAction ca : controlActions) {
                

                if (!isDeployActionExisting(deployMonitoringServices, ca.getControlActionName())) {
                    DeployAction deployAction = epStore.getPrimitiveAction(ca.getControlActionName());
                    deployMonitoringServices.add(deployAction);
                }
            }
        }
        
        

        Configuration config = new Configuration();
        //String edaasArtifact  = config.getConfig("EDAAS.URL") + "/edaasproject/" + eDaaSName + ".zip"; 
        String edaasArtifact  =  "http://128.130.172.215/salsa/upload/files/jun/edaas/install-edaas.sh" ;
        DeployAction eDaaSDA = new DeployAction(eDaaSName, eDaaSName, "sh", edaasArtifact);
      
        
        deployMonitoringServices.add(eDaaSDA);
        
        DeploymentDescription deploymentDescription = new DeploymentDescription(deployMonitoringServices);
        SALSAConnector salsaCon = new SALSAConnector(deploymentDescription);
        salsaCon.config(eDaaSName);
        deployementDescriptionXml = salsaCon.toToscaString();
        
        salsaCon.newServicesInstance(deployementDescriptionXml);

              
        
        return deployementDescriptionXml;
    }
  */


}
