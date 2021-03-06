/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.generator;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;

import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;

import at.ac.tuwien.dsg.depic.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.DataElasticityManagementProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticStateSet;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlAction;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DeployAction;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ElasticService;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ElasticServices;
import at.ac.tuwien.dsg.depic.common.repository.ElasticProcessRepositoryManager;
import at.ac.tuwien.dsg.depic.common.repository.PrimitiveActionMetadataManager;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;


import at.ac.tuwien.dsg.depic.common.utils.Logger;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depic.depictool.connector.ComotConnector;
import at.ac.tuwien.dsg.depic.depictool.connector.ElasticServiceMonitor;

import at.ac.tuwien.dsg.depic.depictool.utils.Configuration;
import at.ac.tuwien.dsg.depic.depictool.utils.ZipUtils;
import at.ac.tuwien.dsg.depic.elastic.process.generator.ElasticProcessesGenerator;
import java.util.List;
import java.util.logging.Level;
import javax.xml.bind.JAXBException;





/**
 *
 * @author Jun
 */
public class Generator {

    DataAnalyticsFunction daf;
    QoRModel qorModel;
    PrimitiveActionMetadata primitiveActionRepository;
    ElasticDataAsset elasticDataAsset;
    String eDaaSName;
    DBType dbType;

    public Generator() {
    }

    public Generator(DataAnalyticsFunction daf, QoRModel qorModel) {
        this.daf = daf;
        this.qorModel = qorModel;
        config();
    }
    
    private void config(){
        eDaaSName = daf.getName();
        this.dbType = daf.getDbType();
        
        loadPrimitiveActionMetadata();
        
    }
    
    private void loadPrimitiveActionMetadata() {

        PrimitiveActionMetadataManager pamm = new PrimitiveActionMetadataManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        List<MonitoringAction> listOfMonitoringActions = pamm.getMonitoringActionList();
        List<AdjustmentAction> listOfAdjustmentActions = pamm.getAdjustmentActionList();
        List<ResourceControlAction> listOfResourceControlActions = pamm.getResourceControlActionList();

        primitiveActionRepository = new PrimitiveActionMetadata(listOfAdjustmentActions, listOfMonitoringActions, listOfResourceControlActions);

    }



    public void startGenerator() {
        long t1 = System.currentTimeMillis();
        
        ElasticProcessesGenerator elasticProcessesGenerator = new ElasticProcessesGenerator(daf, qorModel, primitiveActionRepository);
        
        DataElasticityManagementProcess elasticProcess = elasticProcessesGenerator.generateElasticProcesses();
        
        long t2 = System.currentTimeMillis();
        DaaSGenerator daaSGenerator = new DaaSGenerator(qorModel);
        daaSGenerator.generateDaaS();
        
         
        elasticDataAsset = new ElasticDataAsset(daf.getName(), elasticProcess, elasticProcessesGenerator.getFinalElasticStates());
        
        long t3 = System.currentTimeMillis();
        prepareDeployment(elasticDataAsset);
        long t4 = System.currentTimeMillis();
    
        
        Logger.logInfo("GENERATE_ELASTICITY_PROCESSES_TIME: " + (t2-t1));
        Logger.logInfo("GENERATE_EDAAS_PROCESSES_TIME: " + (t3-t2));
        Logger.logInfo("DEPLOYMENT_TIME: " + (t4-t3));

        
       
    
    }
    
    
    
    

    public void prepareDeployment(ElasticDataAsset elasticDataAsset) {

        ElasticProcessRepositoryManager elStore = new ElasticProcessRepositoryManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
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
            elasticityProcessesXML = JAXBUtils.marshal(elasticDataAsset.getElasticProcess(), DataElasticityManagementProcess.class);
        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }

   
        DeployAction eDaaSDA = new DeployAction(eDaaSName, eDaaSName, "sh", edaasShArtifactURL);
        Logger.logInfo("EDAAS ARTIFACT URL: " + edaasShArtifactURL);
       
        ComotConnector comotConnector = new ComotConnector(elasticDataAsset.getElasticProcess(), eDaaSDA);
  
        //comotConnector.deployCloudSevices2();
        comotConnector.deployCloudSevices();
        
        
        Logger.logInfo("On Deployment Process: ..." + comotConnector.getCloudServiceID() );
        
        List<ElasticService> listOfElasticServices = comotConnector.getCloudServiceInfo();
        ElasticServices elasticServices = new ElasticServices(listOfElasticServices);
        configElasticServices(elasticServices);
        
        ElasticServiceMonitor elasticServiceMonitor = new ElasticServiceMonitor(eDaaSName, comotConnector);
        elasticServiceMonitor.start();
                
        String elasticStateSetXML ="";
        
        ElasticStateSet elasticStateSet = new ElasticStateSet(elasticDataAsset.getListOfFinalElasticState());
        
        try {
            elasticStateSetXML = JAXBUtils.marshal(elasticStateSet, ElasticStateSet.class);
        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
       elStore.storeElasticityProcesses(eDaaSName,elasticStateSetXML, elasticityProcessesXML, "", dbType.getDBType());
        
        
   
       // configureElasticityServices(listOfElasticServices);
        

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
    
    

    private void generateElasticDaaS() {

        Logger.logInfo("Start generate APIs ...");

        DaaSGenerator daaSGenerator = new DaaSGenerator(qorModel);
        daaSGenerator.generateDaaS();

    }

    



}
