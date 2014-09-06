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
import at.ac.tuwien.dsg.depictool.entity.MetricRange;
import at.ac.tuwien.dsg.depictool.entity.MonitorAction;
import at.ac.tuwien.dsg.depictool.entity.MonitorProcess;
import at.ac.tuwien.dsg.depictool.entity.Range;
import at.ac.tuwien.dsg.depictool.entity.TriggerValues;
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
        
        
        List<ControlProcess> listOfControlProcesses = new ArrayList<>();
        
        List<ElasticState> listOfElasticStates = elasticDataObject.getListOfElasticStates();
         
        for (ElasticState eState_i : listOfElasticStates) {
            for (ElasticState eState_j : listOfElasticStates) {

                if (!eState_i.equals(eState_j)) {

                    ControlProcess controlProcess = findControlProcess(eState_i, eState_j);
                    if (controlProcess!=null)
                    listOfControlProcesses.add(controlProcess);
                }
            }
        }
        
        
        return listOfControlProcesses;
    }

    private ControlProcess findControlProcess(ElasticState eStatei, ElasticState eStatej) {

        ControlProcess controlProcess=null;
        
        List<ControlAction> listOfControlActions = new ArrayList<>();
        
        List<MetricRange> listOfMetricRanges_i = eStatei.getListOfMetricRanges();
        List<MetricRange> listOfMetricRanges_j = eStatej.getListOfMetricRanges();
        
        for (MetricRange metricRange_i : listOfMetricRanges_i) {
            String metricName_i = metricRange_i.getMetricName();
            String rangeVal_i = metricRange_i.getRange();        
            String rangeVal_j = findRangeValueFromMetricName(metricName_i, listOfMetricRanges_j);
            ControlAction controlAction = findControlAction(metricName_i,rangeVal_i,rangeVal_j);
            
            
             
            if (controlAction==null && !rangeVal_i.equals(rangeVal_j)){
                listOfControlActions.clear();
                
                break;
            } 
                
            if (controlAction!=null)    
            listOfControlActions.add(controlAction);
            
            
            
            
        }
        
        
        
        if (listOfControlActions.size()!=0)
        controlProcess = new ControlProcess(eStatei.geteStateID(), eStatej.geteStateID(), listOfControlActions);
       // System.out.println("from eState i: " + eStatei.geteStateID() + " --- to eState j: " + eStatei.geteStateID());
        return controlProcess;
    }
    
    private ControlAction findControlAction(String metricName,String rangeVal_i,String rangeVal_j){
        ControlAction returnControlAction = null;
        
        List<MetricElasticityProcess> listOfMetricElasticityProcesses = elasticityProcessConfiguration.getListOfMetricElasticityProcesses();
        
        for (MetricElasticityProcess elasticityProcess : listOfMetricElasticityProcesses) {
            if (elasticityProcess.getMetricName().equals(metricName)){
                
                List<ControlAction> listOfControlActions = elasticityProcess.getListOfControlActions();
                
                for(ControlAction controlAction : listOfControlActions) {
                    
                    TriggerValues triggerValues= controlAction.getTriggerValues();
                    
                    if(triggerValues.getFromRange().equals(rangeVal_i) && triggerValues.getToRange().equals(rangeVal_j)) {
                        returnControlAction = controlAction;
                    }
                    
                }
                
                
            }
            
        }
        
        
        return returnControlAction;
    }
    
    private String findRangeValueFromMetricName(String metricName,List<MetricRange> listOfMetricRanges){
        
        String rangeVal="";
        
        for (MetricRange metricRange : listOfMetricRanges) {
            if (metricRange.getMetricName().equals(metricName)) {
                rangeVal = metricRange.getRange();
            }
        }
        
        
        return  rangeVal;
    }
    
    private void log(DataElasticityProcess dataElasticityProcess){
        
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
            for (ControlAction controlAction : listOfControlActions){
                TriggerValues triggerValues = controlAction.getTriggerValues();
                
                System.out.println("     - Control Action: "+ controlAction.getActionID() +" --- from: " + triggerValues.getFromRange() 
                        + " - to: " + triggerValues.getToRange());
                
                System.out.println("               Param: " + controlAction.getListOfParameters().get(0).getParaName()
                        + " Value: " + controlAction.getListOfParameters().get(0).getValue());
                
                
                
                
            }
            
        }
        
        
    }

}
