/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depictool.generator;

import at.ac.tuwien.dsg.common.deployment.DeployAction;
import at.ac.tuwien.dsg.common.deployment.DeploymentDescription;
import at.ac.tuwien.dsg.common.deployment.DeploymentDescriptionJAXB;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depictool.entity.others.ActionDependency;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.ElasticityProcess;
import at.ac.tuwien.dsg.common.entity.process.MetricProcess;
import at.ac.tuwien.dsg.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.common.entity.eda.ElasticState;
import at.ac.tuwien.dsg.common.entity.process.MetricElasticityProcess;
import at.ac.tuwien.dsg.common.entity.qor.MetricRange;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorProcess;
import at.ac.tuwien.dsg.common.entity.qor.Range;
import at.ac.tuwien.dsg.common.entity.qor.TriggerValues;
import at.ac.tuwien.dsg.depictool.util.ElasticityProcessRepositorty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jun
 */
public class Generator {

    ElasticDataAsset elasticDataObject;
    MetricProcess elasticityProcessConfiguration;

    public Generator() {
    }

    public Generator(ElasticDataAsset elasticDataObject, MetricProcess elasticityProcessConfiguration) {
        this.elasticDataObject = elasticDataObject;
        this.elasticityProcessConfiguration = elasticityProcessConfiguration;
    }

    public void generateAPIs() {

        System.out.println("Start generate APIs ...");
        
        
        DaaSGenerator daaSGenerator = new DaaSGenerator(elasticDataObject);
        daaSGenerator.generateDaaS();
        
        // algorithm for generate APIs
        //Using Template, customize 
        // output  Java classes of Client Serivce
        // HTTP CLIENT TO call DataObjectLoader
        // Http client to send User Requirement
        // Compile -> war file
        // algorithm to generate => deployment descriptions
        // => deploy
        // Restful API documentation => store in WSO2 API manager
    }

   public void generateElasticityProcesses() {
        System.out.println("Start generate Elasticity Processes");

        
        ElasticityProcessesGenerator elasticityProcessGenerator = new ElasticityProcessesGenerator(elasticDataObject, elasticityProcessConfiguration);
        // generate monitor process
        MonitorProcess monitorProcess = elasticityProcessGenerator.generateMonitorProcess();

        // generate control processes
        List<ControlProcess> listOfControlProcesses = elasticityProcessGenerator.generateControlProcesses();

        // elasticity process
        ElasticityProcess dataElasticityProcess = new ElasticityProcess(monitorProcess, listOfControlProcesses);

        log(dataElasticityProcess);
        
        
        // deploy elasticity process
        elasticityProcessGenerator.deployElasticityProcess(dataElasticityProcess);

    }

    private void log(ElasticityProcess dataElasticityProcess) {

        //print out monitor process;
        MonitorProcess monitorProcess = dataElasticityProcess.getMonitorProcess();

        List<MonitorAction> listOfMonitorActions = monitorProcess.getListOfMonitorActions();

        System.out.println("Monitor Process --- ");
        for (MonitorAction monitorAction : listOfMonitorActions) {
            System.out.println("Monitor Action: " + monitorAction.getMonitorActionID());

        }

        System.out.println("");
        //print out control process;

        System.out.println("List of Control Processes --- ");
        List<ControlProcess> listOfControlProcesses = dataElasticityProcess.getListOfControlProcesses();

        for (ControlProcess controlProcess : listOfControlProcesses) {
            System.out.println("*** Control Process: --- from " + controlProcess.geteStateID_i() + " to " + controlProcess.geteStateID_j());

            List<ControlAction> listOfControlActions = controlProcess.getListOfControlActions();
            for (ControlAction controlAction : listOfControlActions) {
                TriggerValues triggerValues = controlAction.getTriggerValues();

                System.out.println("     - Control Action: " + controlAction.getActionID() + " --- from: " + triggerValues.getFromRange()
                        + " - to: " + triggerValues.getToRange());

                System.out.println("               Param: " + controlAction.getListOfParameters().get(0).getParaName()
                        + " Value: " + controlAction.getListOfParameters().get(0).getValue());

            }

        }

    }

}
