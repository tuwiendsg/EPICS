/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depictool.app;

import at.ac.tuwien.dsg.depictool.entity.ControlAction;
import at.ac.tuwien.dsg.depictool.entity.ControlProcess;
import at.ac.tuwien.dsg.depictool.entity.DataElasticityProcess;
import at.ac.tuwien.dsg.depictool.entity.DataElasticityProcessConfiguration;
import at.ac.tuwien.dsg.depictool.entity.ElasticDataObject;
import at.ac.tuwien.dsg.depictool.entity.ElasticState;
import at.ac.tuwien.dsg.depictool.entity.MetricElasticityProcess;
import at.ac.tuwien.dsg.depictool.entity.MonitorAction;
import at.ac.tuwien.dsg.depictool.entity.MonitorProcess;
import java.util.ArrayList;
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
             
        // generate monitor process
        MonitorProcess monitorProcess = generateMonitorProcess();
             
        // generate control processes
        List<ControlProcess> listOfControlProcesses = generateControlProcesses();

        // elasticity process
        DataElasticityProcess dataElasticityProcess = new DataElasticityProcess(monitorProcess, listOfControlProcesses);
        
        log(dataElasticityProcess);
    }
    
    private MonitorProcess generateMonitorProcess(){
        
        List<MonitorAction> listOfMonitorActions = new ArrayList<>();
        
        List<MetricElasticityProcess> listOfMetricElasticityProcesses = elasticityProcessConfiguration.getListOfMetricElasticityProcesses();
        
        for (MetricElasticityProcess metric : listOfMetricElasticityProcesses) {
            MonitorAction monitorAction = metric.getMonitorAction();
            listOfMonitorActions.add(monitorAction);
            
        }
        
        MonitorProcess monitorProcess = new MonitorProcess(listOfMonitorActions);
        
        return monitorProcess;
    }
    
    
    private List<ControlProcess> generateControlProcesses(){
        
         List<ElasticState> listOfElasticStates = elasticDataObject.getListOfElasticStates();
         
        for (ElasticState eState_i : listOfElasticStates) {
            for (ElasticState eState_j : listOfElasticStates) {

                if (!eState_i.equals(eState_j)) {

                    
                    System.out.println();

                }
            }
        }
        
        
        return null;
    }

    private ControlProcess findControlProcess(ElasticState eStatei) {

        List<ControlAction> listOfControlActions = new ArrayList<>();

        ControlProcess controlProcess = new ControlProcess(eStatei.geteStateID(), eStatei.geteStateID(), listOfControlActions);
        System.out.println("from eState i: " + eStatei.geteStateID() + " --- to eState j: " + eStatei.geteStateID());
        return controlProcess;
    }
    
    private void log(DataElasticityProcess dataElasticityProcess){
        
        //print out monitor process;
        
        MonitorProcess monitorProcess = dataElasticityProcess.getMonitorProcess();
        
        List<MonitorAction> listOfMonitorActions = monitorProcess.getListOfMonitorActions();
        
        System.out.println("Monitor Process --- ");
        for (MonitorAction monitorAction : listOfMonitorActions) {
            System.out.println("Monitor Action: " + monitorAction.getMonitorActionID());
            
        }
        
        
    }

}
