/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depictool.util;

import at.ac.tuwien.dsg.common.entity.eda.ElasticState;
import at.ac.tuwien.dsg.common.entity.eda.MetricCondition;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.ParallelGateway;
import at.ac.tuwien.dsg.common.entity.process.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class Logger {
    
    
    public void logMonitorProcesses(MonitorProcess monitorProcess){
        List<MonitorAction> listOfMonitorActions  = monitorProcess.getListOfMonitorActions();
        System.out.println("\nLOG MONITOR PROCESS");
        for (MonitorAction monitorAction : listOfMonitorActions){
            System.out.println("monitor action ID: " + monitorAction.getMonitorActionID());
        }
        
    }
    
    
    public void logControlProcesses(List<ControlProcess> listOfControlProcesses){
        
        System.out.println("\nLOG CONTROL PROCESS");
        
        System.out.println("Number of Control Processes: " + listOfControlProcesses.size());
        for (ControlProcess controlProcess : listOfControlProcesses) {
       
        System.out.println("\n***");    
        List<ControlAction> listOfControlActions =  controlProcess.getListOfControlActions();
        
        
         System.out.println("PROCESS ----------- ");
         System.out.println("eState in: "+ controlProcess.geteStateID_i().geteStateID());
         System.out.println("eState fi: "+ controlProcess.geteStateID_j().geteStateID());
         for (ControlAction controlAction : listOfControlActions) {
            System.out.println("control action: " + controlAction.getControlActionName());
        }
         
        System.out.println(".............");
         
         
         List<MetricCondition> conditions_in = controlProcess.geteStateID_i().getListOfConditions();
         for (MetricCondition c: conditions_in){
             System.out.println("   id: " + c.getConditionID());
             System.out.println("   metric: " + c.getMetricName());
             System.out.println("   lower: " + c.getLowerBound());
             System.out.println("   upper: " + c.getUpperBound());
             
         }
         
         System.out.println("eState fi: "+ controlProcess.geteStateID_j().geteStateID());
         List<MetricCondition> conditions_fi = controlProcess.geteStateID_j().getListOfConditions();
         for (MetricCondition c: conditions_fi){
             System.out.println("   id: " + c.getConditionID());
             System.out.println("   metric: " + c.getMetricName());
             System.out.println("   lower: " + c.getLowerBound());
             System.out.println("   upper: " + c.getUpperBound());
             
         }
                 
        for (ControlAction controlAction : listOfControlActions) {
            System.out.println("control action: " + controlAction.getControlActionID());
            System.out.println("control action: " + controlAction.getControlActionName());
            System.out.println("  incomming: " + controlAction.getIncomming());
            System.out.println("  outgoing: " + controlAction.getOutgoing());
            List<Parameter> listOfParams = controlAction.getListOfParameters();
            
            for (Parameter param : listOfParams){
                System.out.println("    parameter: " + param.getParaName());
                System.out.println("    value: " + param.getValue());
            }
            
        }
        
        List<ParallelGateway> listOfParallelGateways = controlProcess.getListOfParallelGateways();
        
        for (ParallelGateway parallelGateway : listOfParallelGateways){
            System.out.println("parallel gateway: " + parallelGateway.getId());
            System.out.println("  incoming: " + parallelGateway.getIncomming());
            System.out.println("  outgoing: " + parallelGateway.getOutgoing());
        }
        
        
        
        }
    }
    
    public void logElasticState(List<ElasticState> listOfElasticStates){
        System.out.println("Number of Elastic States: " + listOfElasticStates.size());
        
        for (ElasticState elasticState : listOfElasticStates){
            System.out.println("\n***"); 
            System.out.println("eState ID: " + elasticState.geteStateID());
            List<MetricCondition> conditions = elasticState.getListOfConditions();
            for(MetricCondition condition : conditions){
                System.out.println("\n---"); 
                System.out.print("    metric: " + condition.getMetricName());
                System.out.print("    id: " + condition.getConditionID());
                System.out.print("    lower: " + condition.getLowerBound());
                System.out.print("    uppper: " + condition.getUpperBound());
                
            }
            
        }
        
    }
    
}
