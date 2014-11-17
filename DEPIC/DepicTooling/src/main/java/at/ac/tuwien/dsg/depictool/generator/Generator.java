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

import at.ac.tuwien.dsg.depictool.elstore.ElasticityProcessStore;
import at.ac.tuwien.dsg.depictool.util.SALSAConnector;
import java.util.ArrayList;
import java.util.List;

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

    public void generateAPIs() {

        System.out.println("Start generate APIs ...");

        DaaSGenerator daaSGenerator = new DaaSGenerator(elasticDataObject);
        daaSGenerator.generateDaaS();

    }

    public void generateElasticityProcesses() {
        System.out.println("Start generate Elasticity Processes");
        System.out.println("eDaaS: " + eDaaSName);
        System.out.println("qor metrics: " + qorModel.getListOfMetrics().get(0).getName());
        System.out.println("metric process: " + elasticityProcessConfiguration.getListOfMetricElasticityProcesses().get(0).getMetricName());
        
        

        ElasticityProcessesGenerator elasticityProcessGenerator = new ElasticityProcessesGenerator(null, elasticityProcessConfiguration);
        MonitorProcess monitorProcess = elasticityProcessGenerator.generateMonitorProcess();
        List<ControlProcess> listOfControlProcesses = elasticityProcessGenerator.generateControlProcesses();
        ElasticityProcess elasticityProcesses = new ElasticityProcess(monitorProcess, listOfControlProcesses);

    }
    
    

    public String generateDeploymentDesciptionForElasticityProcesses(ElasticityProcess elasticityProcesses) {

        String deployementDescriptionXml = "";

        List<DeployAction> listOfDeployActions = new ArrayList<DeployAction>();

        ElasticityProcessStore epStore = new ElasticityProcessStore();
        MonitorProcess monitorProcess = elasticityProcesses.getMonitorProcess();
        List<ControlProcess> listOfControlProcesses = elasticityProcesses.getListOfControlProcesses();

        List<MonitorAction> monitorActions = monitorProcess.getListOfMonitorActions();

        for (MonitorAction ma : monitorActions) {
            DeployAction deployAction = epStore.getPrimitiveAction(ma.getMonitorActionID());
            listOfDeployActions.add(deployAction);
        }

        for (ControlProcess cp : listOfControlProcesses) {
            List<ControlAction> controlActions = cp.getListOfControlActions();
            for (ControlAction ca : controlActions) {
                DeployAction deployAction = epStore.getPrimitiveAction(ca.getControlActionID());
                listOfDeployActions.add(deployAction);
            }
        }
        
        DeploymentDescription deploymentDescription = new DeploymentDescription(listOfDeployActions);
        SALSAConnector salsaCon = new SALSAConnector(deploymentDescription);
        deployementDescriptionXml = salsaCon.toToscaString();
         
        return deployementDescriptionXml;
    }
    
    public void storeSpecifications(ElasticityProcess elasticityProcesses, String deploymentDescription){
        ElasticityProcessStore epStore = new ElasticityProcessStore();
        epStore.storeElasticityProcesses(elasticityProcesses, deploymentDescription);
    }

}
