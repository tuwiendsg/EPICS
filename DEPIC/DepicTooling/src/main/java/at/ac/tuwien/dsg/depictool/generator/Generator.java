/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depictool.generator;

import at.ac.tuwien.dsg.common.deployment.DeployAction;
import at.ac.tuwien.dsg.common.deployment.DeploymentDescription;
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
import at.ac.tuwien.dsg.depic.depictool.connector.ComotConnector;

import at.ac.tuwien.dsg.depictool.elstore.ElasticityProcessStore;
import at.ac.tuwien.dsg.depictool.util.Configuration;
import at.ac.tuwien.dsg.depictool.util.Logger;
import at.ac.tuwien.dsg.mela.SALSAConnector;
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

    public Generator() {
    }

    public Generator(String eDaaSName, QoRModel qorModel, MetricProcess elasticityProcessConfiguration) {
        this.eDaaSName = eDaaSName;
        this.qorModel = qorModel;
        this.elasticityProcessConfiguration = elasticityProcessConfiguration;
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

        
        Configuration config = new Configuration();
        String parentPath = config.getCurrentPath();
        
        String edaasPath = parentPath + "/WEB-INF/classes/project/eDaaS";
        String edaasZipFile  = parentPath + "/edaasproject/" + eDaaSName + ".zip";
        
        System.out.println("PARENT PATH: " + parentPath);
         
        
        ZipUtils zipUtils = new ZipUtils();
        zipUtils.zipDir(edaasZipFile, edaasPath);
        
        
        
        String elasticityProcessesXML = "";
        try {
            elasticityProcessesXML = JAXBUtils.marshal(elasticDataAsset.getElasticityProcess(), ElasticityProcess.class);
        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }

        //String deploymenDescription = generateDeploymentDesciptionForElasticityProcesses(elasticDataAsset.getElasticityProcess());

        //String edaasArtifact  =  "http://128.130.172.215/salsa/upload/files/jun/edaas/install-edaas.sh" ;
        DeployAction eDaaSDA = new DeployAction(eDaaSName, eDaaSName, "sh", "");
        
        ComotConnector comotConnector = new ComotConnector(elasticDataAsset.getElasticityProcess(), eDaaSDA);
        comotConnector.deployCloudSevices();
        
        String elasticStateSetXML ="";
        
        try {
            elasticStateSetXML = JAXBUtils.marshal(elasticDataAsset.getElasticStateSet(), ElasticStateSet.class);
        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        ElasticityProcessStore elStore = new ElasticityProcessStore();
        elStore.storeElasticityProcesses(eDaaSName,elasticStateSetXML, elasticityProcessesXML, "");
        
        
        
        

    }

    private void generateElasticDaaS() {

        System.out.println("Start generate APIs ...");

        DaaSGenerator daaSGenerator = new DaaSGenerator(qorModel);
        daaSGenerator.generateDaaS();

    }

    private ElasticDataAsset generateElasticityProcesses() {
        System.out.println("Start generate Elasticity Processes");
        System.out.println("eDaaS: " + eDaaSName);
        System.out.println("qor metrics: " + qorModel.getListOfMetrics().get(0).getName());
        System.out.println("metric process: " + elasticityProcessConfiguration.getListOfMetricElasticityProcesses().get(0).getMetricName());

        ElasticityProcessesGenerator elasticityProcessGenerator = new ElasticityProcessesGenerator(qorModel, elasticityProcessConfiguration);
        MonitorProcess monitorProcess = elasticityProcessGenerator.generateMonitorProcess();
        List<ElasticState> initialElasticStateSet = elasticityProcessGenerator.generateSetOfInitialElasticState();
        
        Logger logger = new Logger();
        System.out.println("Inital eState Set");
        logger.logElasticState(initialElasticStateSet);
        
        
        List<ElasticState> finalElasticStateSet = elasticityProcessGenerator.generateSetOfFinalElasticState(initialElasticStateSet);
        System.out.println("");
        System.out.println("Final eState Set");
        logger.logElasticState(finalElasticStateSet);
        
        ElasticStateSet elasticStateSet = new ElasticStateSet(initialElasticStateSet, finalElasticStateSet);
        
        List<ControlProcess> listOfControlProcesses = elasticityProcessGenerator.generateControlProcesses(initialElasticStateSet, finalElasticStateSet);
        ElasticityProcess elasticityProcesses = new ElasticityProcess(monitorProcess, listOfControlProcesses);

        ElasticDataAsset elasticDataAsset = new ElasticDataAsset(eDaaSName, elasticityProcesses, elasticStateSet);
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
