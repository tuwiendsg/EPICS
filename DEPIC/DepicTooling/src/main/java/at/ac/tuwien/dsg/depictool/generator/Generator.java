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
import at.ac.tuwien.dsg.depictool.util.Logger;
import at.ac.tuwien.dsg.depictool.util.SALSAConnector;
import java.util.ArrayList;
import java.util.List;
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

        String elasticityProcessesXML = "";
        try {
            elasticityProcessesXML = JAXBUtils.marshal(elasticityProcesses, ElasticityProcess.class);
        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }

        String deploymenDescription = generateDeploymentDesciptionForElasticityProcesses(elasticityProcesses);

        ElasticityProcessStore elStore = new ElasticityProcessStore();
        elStore.storeElasticityProcesses(eDaaSName, elasticityProcessesXML, deploymenDescription);
        /*
         IOUtils iou = new IOUtils();
         iou.writeData(deploymenDescription, "tosca.xml");
        
       
         */

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
            DeployAction deployAction = epStore.getPrimitiveAction(ma.getMonitorActionID());

            if (!isDeployActionExisting(listOfDeployActions, deployAction)) {
                listOfDeployActions.add(deployAction);
            }

        }

        for (ControlProcess cp : listOfControlProcesses) {
            List<ControlAction> controlActions = cp.getListOfControlActions();
            for (ControlAction ca : controlActions) {
                DeployAction deployAction = epStore.getPrimitiveAction(ca.getControlActionID());

                if (!isDeployActionExisting(listOfDeployActions, deployAction)) {
                    listOfDeployActions.add(deployAction);
                }
            }
        }

        DeploymentDescription deploymentDescription = new DeploymentDescription(listOfDeployActions);
        SALSAConnector salsaCon = new SALSAConnector(deploymentDescription);
        salsaCon.config(eDaaSName);
        deployementDescriptionXml = salsaCon.toToscaString();
        
        //salsaCon.newServicesInstance(deployementDescriptionXml);

        return deployementDescriptionXml;
    }

    private boolean isDeployActionExisting(List<DeployAction> listOfDeployActions, DeployAction deployAction) {

        for (DeployAction da : listOfDeployActions) {
            if (da.getActionID().equals(deployAction.getActionID())) {
                return true;
            }
        }
        return false;
    }

}
