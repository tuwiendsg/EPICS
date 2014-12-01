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
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorProcess;
import at.ac.tuwien.dsg.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.common.utils.IOUtils;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;

import at.ac.tuwien.dsg.depictool.elstore.ElasticityProcessStore;
import at.ac.tuwien.dsg.depictool.util.Configuration;
import at.ac.tuwien.dsg.depictool.util.Logger;
import at.ac.tuwien.dsg.depictool.util.SALSAConnector;
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
        ElasticityProcess elasticityProcesses = generateElasticityProcesses();
        generateElasticDaaS();
        prepareDeployment(elasticityProcesses);
    }

    public void prepareDeployment(ElasticityProcess elasticityProcesses) {

        
        Configuration config = new Configuration();
        String parentPath = config.getCurrentPath();
        
        String edaasPath = parentPath + "/WEB-INF/classes/project/eDaaS";
        String edaasZipFile  = parentPath + "/edaasproject/" + eDaaSName + ".zip";
        
        System.out.println("PARENT PATH: " + parentPath);
         
        
        ZipUtils zipUtils = new ZipUtils();
        zipUtils.zipDir(edaasZipFile, edaasPath);
        
        String elasticityProcessesXML = "";
        try {
            elasticityProcessesXML = JAXBUtils.marshal(elasticityProcesses, ElasticityProcess.class);
        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }

        String deploymenDescription = generateDeploymentDesciptionForElasticityProcesses(elasticityProcesses);

        ElasticityProcessStore elStore = new ElasticityProcessStore();
        elStore.storeElasticityProcesses(eDaaSName, elasticityProcessesXML, deploymenDescription);
        
        
        
        

    }

    private void generateElasticDaaS() {

        System.out.println("Start generate APIs ...");

        DaaSGenerator daaSGenerator = new DaaSGenerator(qorModel);
        daaSGenerator.generateDaaS();

    }

    private ElasticityProcess generateElasticityProcesses() {
        System.out.println("Start generate Elasticity Processes");
        System.out.println("eDaaS: " + eDaaSName);
        System.out.println("qor metrics: " + qorModel.getListOfMetrics().get(0).getName());
        System.out.println("metric process: " + elasticityProcessConfiguration.getListOfMetricElasticityProcesses().get(0).getMetricName());

        ElasticityProcessesGenerator elasticityProcessGenerator = new ElasticityProcessesGenerator(qorModel, elasticityProcessConfiguration);
        MonitorProcess monitorProcess = elasticityProcessGenerator.generateMonitorProcess();
        List<ControlProcess> listOfControlProcesses = elasticityProcessGenerator.generateControlProcesses();
        ElasticityProcess elasticityProcesses = new ElasticityProcess(monitorProcess, listOfControlProcesses);

        //log
        Logger logger = new Logger();
        logger.logMonitorProcesses(monitorProcess);
        logger.logControlProcesses(listOfControlProcesses);

        return elasticityProcesses;

    }

    private String generateDeploymentDesciptionForElasticityProcesses(ElasticityProcess elasticityProcesses) {

        String deployementDescriptionXml = "";

        List<DeployAction> listOfDeployActions = new ArrayList<DeployAction>();

        ElasticityProcessStore epStore = new ElasticityProcessStore();
        MonitorProcess monitorProcess = elasticityProcesses.getMonitorProcess();
        List<ControlProcess> listOfControlProcesses = elasticityProcesses.getListOfControlProcesses();

        List<MonitorAction> monitorActions = monitorProcess.getListOfMonitorActions();

        for (MonitorAction ma : monitorActions) {
            

            if (!isDeployActionExisting(listOfDeployActions, ma.getMonitorActionID())) {
                DeployAction deployAction = epStore.getPrimitiveAction(ma.getMonitorActionID());
                listOfDeployActions.add(deployAction);
            }

        }
        

        for (ControlProcess cp : listOfControlProcesses) {
            List<ControlAction> controlActions = cp.getListOfControlActions();
            for (ControlAction ca : controlActions) {
                

                if (!isDeployActionExisting(listOfDeployActions, ca.getControlActionID())) {
                    DeployAction deployAction = epStore.getPrimitiveAction(ca.getControlActionID());
                    listOfDeployActions.add(deployAction);
                }
            }
        }
        
        

        Configuration config = new Configuration();
        String edaasArtifact  = config.getConfig("EDAAS.URL") + "/edaasproject/" + eDaaSName + ".zip"; 
        DeployAction eDaaSDA = new DeployAction(eDaaSName, eDaaSName, edaasArtifact, "sh");
        listOfDeployActions.add(eDaaSDA);
        
        DeploymentDescription deploymentDescription = new DeploymentDescription(listOfDeployActions);
        SALSAConnector salsaCon = new SALSAConnector(deploymentDescription);
        salsaCon.config(eDaaSName);
        deployementDescriptionXml = salsaCon.toToscaString();
        
        salsaCon.newServicesInstance(deployementDescriptionXml);

        return deployementDescriptionXml;
    }

    private boolean isDeployActionExisting(List<DeployAction> listOfDeployActions, String deployActionID) {

        for (DeployAction da : listOfDeployActions) {
            if (da.getActionID().equals(deployActionID)) {
                return true;
            }
        }
        return false;
    }

}
