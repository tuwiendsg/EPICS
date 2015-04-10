/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.utils;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.ElasticState;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.MetricCondition;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ControlAction;
import at.ac.tuwien.dsg.depic.common.entity.eda.ep.ControlProcess;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.eda.ep.MonitorProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.ep.ParallelGateway;
import at.ac.tuwien.dsg.depic.common.entity.process.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author Jun
 */
public class Logger {
    
    private static List<Double> designComplexity;
    
    public void logMonitorProcesses(MonitorProcess monitorProcess){
        List<MonitoringAction> listOfMonitorActions  = monitorProcess.getListOfMonitorActions();
        logInfo("\nLOG MONITOR PROCESS");
        for (MonitoringAction monitorAction : listOfMonitorActions){
            logInfo("monitor action ID: " + monitorAction.getMonitorActionID());
        }
        
    }
    
    
    public void logControlProcesses(List<ControlProcess> listOfControlProcesses){
        
        logInfo("\nLOG CONTROL PROCESS");
        
        logInfo("Number of Control Processes: " + listOfControlProcesses.size());
        for (ControlProcess controlProcess : listOfControlProcesses) {
       
        logInfo("\n***");    
        List<ControlAction> listOfControlActions =  controlProcess.getListOfControlActions();
        
        
         logInfo("PROCESS ----------- ");
         logInfo("eState in: "+ controlProcess.geteStateID_i().geteStateID());
         logInfo("eState fi: "+ controlProcess.geteStateID_j().geteStateID());
         for (ControlAction controlAction : listOfControlActions) {
            logInfo("control action: " + controlAction.getControlActionName());
        }
         
        logInfo(".............");
         
         
         List<MetricCondition> conditions_in = controlProcess.geteStateID_i().getListOfConditions();
         for (MetricCondition c: conditions_in){
             logInfo("   id: " + c.getConditionID());
             logInfo("   metric: " + c.getMetricName());
             logInfo("   lower: " + c.getLowerBound());
             logInfo("   upper: " + c.getUpperBound());
             
         }
         
         logInfo("eState fi: "+ controlProcess.geteStateID_j().geteStateID());
         List<MetricCondition> conditions_fi = controlProcess.geteStateID_j().getListOfConditions();
         for (MetricCondition c: conditions_fi){
             logInfo("   id: " + c.getConditionID());
             logInfo("   metric: " + c.getMetricName());
             logInfo("   lower: " + c.getLowerBound());
             logInfo("   upper: " + c.getUpperBound());
             
         }
                 
        for (ControlAction controlAction : listOfControlActions) {
            logInfo("control action: " + controlAction.getControlActionID());
            logInfo("control action: " + controlAction.getControlActionName());
            logInfo("  incomming: " + controlAction.getIncomming());
            logInfo("  outgoing: " + controlAction.getOutgoing());
            List<Parameter> listOfParams = controlAction.getListOfParameters();
            
            for (Parameter param : listOfParams){
                logInfo("    parameter: " + param.getParaName());
                logInfo("    value: " + param.getValue());
            }
            
        }
        
        List<ParallelGateway> listOfParallelGateways = controlProcess.getListOfParallelGateways();
        
        for (ParallelGateway parallelGateway : listOfParallelGateways){
            logInfo("parallel gateway: " + parallelGateway.getId());
            logInfo("  incoming: " + parallelGateway.getIncomming());
            logInfo("  outgoing: " + parallelGateway.getOutgoing());
        }
        
        
        
        }
    }
    
    public void logElasticState(List<ElasticState> listOfElasticStates){
        logInfo("Number of Elastic States: " + listOfElasticStates.size());
        
        for (ElasticState elasticState : listOfElasticStates){
            logInfo("\n***"); 
            logInfo("eState ID: " + elasticState.geteStateID());
            List<MetricCondition> conditions = elasticState.getListOfConditions();
            for(MetricCondition condition : conditions){
                logInfo("\n---"); 
                logInfo("    metric: " + condition.getMetricName());
                logInfo("    id: " + condition.getConditionID());
                logInfo("    lower: " + condition.getLowerBound());
                logInfo("    uppper: " + condition.getUpperBound());
                
            }
            
        }
        
    }
    
    
    public static void logInfo(String log) {
        java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.INFO, log);
    }
    
    public static void logDesignComplexity(double val){
        
        if (designComplexity==null) {
        
        designComplexity = new ArrayList<Double>();
        }
        
        
        
        designComplexity.add(val);
 
    }
    
    public static double getMinDesignComplexity (){
        double minVal = Double.MAX_VALUE;
        
        for (Double val : designComplexity){
            if (val<minVal){
                minVal=val;
            }
                    
        }
        return minVal;
    }
    
    public static double getMaxDesignComplexity (){
        double maxVal = Double.MIN_VALUE;
        
        for (Double val : designComplexity){
            if (val>maxVal){
                maxVal=val;
            }
                    
        }
        return maxVal;
        
    }
    
    public static double getStandardDeviation (){
        
        double sum =0;
        for (Double val : designComplexity){
            sum +=val;
                    
        }
        
        double avg= sum/(double)designComplexity.size();
        
        double sd =0;
        
        for (Double val : designComplexity){
            sd = sd +  Math.pow(val-avg,2);          
        }
        sd = sd/(double)designComplexity.size();
        sd = Math.sqrt(sd);
        
        
        return sd;
        
    }
    
    public static void clear (){
        
        designComplexity.clear();
        
    }
    
}
