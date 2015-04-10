/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.dataelasticitymonitor;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.ElasticDataAsset;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.ElasticState;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.ElasticStateSet;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.MetricCondition;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ControlAction;
import at.ac.tuwien.dsg.depic.common.entity.eda.ep.ControlProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.ep.ElasticityProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.ep.MonitorProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.ep.MonitoringSession;
import at.ac.tuwien.dsg.depic.common.entity.eda.ep.ParallelGateway;
import at.ac.tuwien.dsg.depic.common.entity.process.MonitoringMetric;
import at.ac.tuwien.dsg.depic.common.entity.process.Parameter;
import at.ac.tuwien.dsg.depic.common.utils.Logger;
import at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore.ElasticityProcessesStore;
import at.ac.tuwien.dsg.orchestrator.registry.ElasticServiceRegistry;
import java.util.List;

/**
 *
 * @author Jun
 */
public class LoggerProcess {
   
    List<ElasticState> listOfInitialState;
    List<ElasticState> listOfFinalState;
    MonitorProcess monitorProcess;
    List<ControlProcess> listOfControlProcesses;

    
    public void config(){
        ElasticityProcessesStore elasticityProcessesStore = new ElasticityProcessesStore(); 
        ElasticDataAsset eda = elasticityProcessesStore.getElasticDataAsset("daf2");
        ElasticityProcess elasticityProcess= eda.getElasticityProcess();
        ElasticStateSet elasticStateSet = eda.getElasticStateSet();
        
        listOfInitialState = elasticStateSet.getInitialElasticStateSet();
       listOfFinalState = elasticStateSet.getFinalElasticStateSet();
        monitorProcess = elasticityProcess.getMonitorProcess(); 
        listOfControlProcesses = elasticityProcess.getListOfControlProcesses();
    
        
        Logger.logInfo("INITIAL ESTATE SET ------- \n");
        for (ElasticState  estate : listOfInitialState){
           
            logElasticState(estate);
        }
        
        
        Logger.logInfo("FINAL ESTATE SET ------- \n");
        for (ElasticState  estate : listOfFinalState){
           
            logElasticState(estate);
        }
        
        
        
        Logger.logInfo("CONTROL PROCESSES LIST ------ \n");
        for (ControlProcess cp : listOfControlProcesses){
            logControlProcesses(cp);
        }
        
        
    
    }
    
    
     private void logElasticState(ElasticState elasticState ){
  
            Logger.logInfo("\n***"); 
            Logger.logInfo("eState ID: " + elasticState.geteStateID());
            List<MetricCondition> conditions = elasticState.getListOfConditions();
            for(MetricCondition condition : conditions){
                Logger.logInfo("\n---"); 
                System.out.print("    metric: " + condition.getMetricName());
                System.out.print("    id: " + condition.getConditionID());
                System.out.print("    lower: " + condition.getLowerBound());
                System.out.print("    uppper: " + condition.getUpperBound());
                
            
            
        }
        
    }
    
     
     public void logControlProcesses(ControlProcess controlProcess) {

        Logger.logInfo("\nLOG CONTROL PROCESS");

        Logger.logInfo("\n***");
        List<ControlAction> listOfControlActions = controlProcess.getListOfControlActions();

        Logger.logInfo("PROCESS ----------- ");
        Logger.logInfo("eState in: " + controlProcess.geteStateID_i().geteStateID());
        Logger.logInfo("eState fi: " + controlProcess.geteStateID_j().geteStateID());
        for (ControlAction controlAction : listOfControlActions) {
            Logger.logInfo("control action: " + controlAction.getControlActionName());
        }

        Logger.logInfo(".............");

        List<MetricCondition> conditions_in = controlProcess.geteStateID_i().getListOfConditions();
        for (MetricCondition c : conditions_in) {
            Logger.logInfo("   id: " + c.getConditionID());
            Logger.logInfo("   metric: " + c.getMetricName());
            Logger.logInfo("   lower: " + c.getLowerBound());
            Logger.logInfo("   upper: " + c.getUpperBound());

        }

        Logger.logInfo("eState fi: " + controlProcess.geteStateID_j().geteStateID());
        List<MetricCondition> conditions_fi = controlProcess.geteStateID_j().getListOfConditions();
        for (MetricCondition c : conditions_fi) {
            Logger.logInfo("   id: " + c.getConditionID());
            Logger.logInfo("   metric: " + c.getMetricName());
            Logger.logInfo("   lower: " + c.getLowerBound());
            Logger.logInfo("   upper: " + c.getUpperBound());

        }

        for (ControlAction controlAction : listOfControlActions) {
            Logger.logInfo("control action: " + controlAction.getControlActionID());
            Logger.logInfo("control action: " + controlAction.getControlActionName());
            Logger.logInfo("  incomming: " + controlAction.getIncomming());
            Logger.logInfo("  outgoing: " + controlAction.getOutgoing());
            List<Parameter> listOfParams = controlAction.getListOfParameters();

            for (Parameter param : listOfParams) {
                Logger.logInfo("    parameter: " + param.getParaName());
                Logger.logInfo("    value: " + param.getValue());
            }

        }

        List<ParallelGateway> listOfParallelGateways = controlProcess.getListOfParallelGateways();

        if (listOfParallelGateways!=null){
        for (ParallelGateway parallelGateway : listOfParallelGateways) {
            Logger.logInfo("parallel gateway: " + parallelGateway.getId());
            Logger.logInfo("  incoming: " + parallelGateway.getIncomming());
            Logger.logInfo("  outgoing: " + parallelGateway.getOutgoing());
        }
        }
    }
    
}
