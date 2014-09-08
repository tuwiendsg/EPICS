/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depictool.app;

import at.ac.tuwien.dsg.common.deployment.DeployAction;
import at.ac.tuwien.dsg.common.deployment.DeploymentDescription;
import at.ac.tuwien.dsg.common.deployment.DeploymentDescriptionJAXB;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depictool.entity.ActionDependency;
import at.ac.tuwien.dsg.depictool.entity.ControlAction;
import at.ac.tuwien.dsg.depictool.entity.ControlProcess;
import at.ac.tuwien.dsg.depictool.entity.DataElasticityProcess;
import at.ac.tuwien.dsg.depictool.entity.DataElasticityProcessConfiguration;
import at.ac.tuwien.dsg.depictool.entity.ElasticDataObject;
import at.ac.tuwien.dsg.depictool.entity.ElasticState;
import at.ac.tuwien.dsg.depictool.entity.MetricElasticityProcess;
import at.ac.tuwien.dsg.depictool.entity.MetricRange;
import at.ac.tuwien.dsg.depictool.entity.MonitorAction;
import at.ac.tuwien.dsg.depictool.entity.MonitorProcess;
import at.ac.tuwien.dsg.depictool.entity.Range;
import at.ac.tuwien.dsg.depictool.entity.TriggerValues;
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

    ElasticDataObject elasticDataObject;
    DataElasticityProcessConfiguration elasticityProcessConfiguration;

    public Generator() {
    }

    public Generator(ElasticDataObject elasticDataObject, DataElasticityProcessConfiguration elasticityProcessConfiguration) {
        this.elasticDataObject = elasticDataObject;
        this.elasticityProcessConfiguration = elasticityProcessConfiguration;
    }

    public void generateAPIs() {

        System.out.println("Start generate APIs ...");
        
        
        APIGenerator apiGenerator = new APIGenerator(elasticDataObject);
        apiGenerator.generateAPI();
        

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

        
        ElasticityProcessGenerator elasticityProcessGenerator = new ElasticityProcessGenerator(elasticDataObject, elasticityProcessConfiguration);
        // generate monitor process
        MonitorProcess monitorProcess = elasticityProcessGenerator.generateMonitorProcess();

        // generate control processes
        List<ControlProcess> listOfControlProcesses = elasticityProcessGenerator.generateControlProcesses();

        // elasticity process
        DataElasticityProcess dataElasticityProcess = new DataElasticityProcess(monitorProcess, listOfControlProcesses);

        log(dataElasticityProcess);
        
        
        // deploy elasticity process
        elasticityProcessGenerator.deployElasticityProcess(dataElasticityProcess);

    }

    private void log(DataElasticityProcess dataElasticityProcess) {

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
