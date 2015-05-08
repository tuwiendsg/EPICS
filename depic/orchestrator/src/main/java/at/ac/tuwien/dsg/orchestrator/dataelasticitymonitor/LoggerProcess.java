/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.dataelasticitymonitor;

import at.ac.tuwien.dsg.depic.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticState;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticStateSet;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MetricCondition;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.AdjustmentProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.MonitoringProcess;
import at.ac.tuwien.dsg.depic.common.entity.runtime.MonitoringSession;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ParallelGateway;
import at.ac.tuwien.dsg.depic.common.entity.runtime.MonitoringMetric;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
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
    MonitoringProcess monitorProcess;
    List<AdjustmentProcess> listOfControlProcesses;

    
    public void config(){
        ElasticityProcessesStore elasticityProcessesStore = new ElasticityProcessesStore(); 
        ElasticDataAsset eda = elasticityProcessesStore.getElasticDataAsset("daf2");
   //     ElasticProcess elasticityProcess= eda.getElasticityProcess();
     
       listOfFinalState = eda.getListOfFinalElasticState();
      //  monitorProcess = elasticityProcess.getMonitorProcess(); 
      //  listOfControlProcesses = elasticityProcess.getListOfControlProcesses();
//    
//        
//        Logger.logInfo("INITIAL ESTATE SET ------- \n");
//        for (ElasticState  estate : listOfInitialState){
//           
//            logElasticState(estate);
//        }
//        
        
        Logger.logInfo("FINAL ESTATE SET ------- \n");
        for (ElasticState  estate : listOfFinalState){
           
            logElasticState(estate);
        }
        
        
        
        Logger.logInfo("CONTROL PROCESSES LIST ------ \n");
        for (AdjustmentProcess cp : listOfControlProcesses){
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
    
     
     public void logControlProcesses(AdjustmentProcess controlProcess) {

        Logger.logInfo("\nLOG CONTROL PROCESS");

        Logger.logInfo("\n***");
        List<AdjustmentAction> listOfControlActions = controlProcess.getListOfAdjustmentActions();

        Logger.logInfo("PROCESS ----------- ");
   
        Logger.logInfo("eState fi: " + controlProcess.getFinalEState().geteStateID());
        for (AdjustmentAction controlAction : listOfControlActions) {
            Logger.logInfo("control action: " + controlAction.getActionName());
        }

        Logger.logInfo(".............");

        List<MetricCondition> conditions_in = controlProcess.getFinalEState().getListOfConditions();
        for (MetricCondition c : conditions_in) {
            Logger.logInfo("   id: " + c.getConditionID());
            Logger.logInfo("   metric: " + c.getMetricName());
            Logger.logInfo("   lower: " + c.getLowerBound());
            Logger.logInfo("   upper: " + c.getUpperBound());

        }

        Logger.logInfo("eState fi: " + controlProcess.getFinalEState().geteStateID());
        List<MetricCondition> conditions_fi = controlProcess.getFinalEState().getListOfConditions();
        for (MetricCondition c : conditions_fi) {
            Logger.logInfo("   id: " + c.getConditionID());
            Logger.logInfo("   metric: " + c.getMetricName());
            Logger.logInfo("   lower: " + c.getLowerBound());
            Logger.logInfo("   upper: " + c.getUpperBound());

        }

        for (AdjustmentAction controlAction : listOfControlActions) {
            Logger.logInfo("control action: " + controlAction.getActionID());
            Logger.logInfo("control action: " + controlAction.getActionName());
//            Logger.logInfo("  incomming: " + controlAction.ge());
//            Logger.logInfo("  outgoing: " + controlAction.getOutgoing());
//            List<Parameter> listOfParams = controlAction.getListOfTransitions().get(0).getListOfParameters();
//
//            for (Parameter param : listOfParams) {
//                Logger.logInfo("    parameter: " + param.getParameterName());
//                Logger.logInfo("    value: " + param.getValue());
//            }

        }
//
//        List<ParallelGateway> listOfParallelGateways = controlProcess.getListOfParallelGateways();
//
//        if (listOfParallelGateways!=null){
//        for (ParallelGateway parallelGateway : listOfParallelGateways) {
//            Logger.logInfo("parallel gateway: " + parallelGateway.getGatewayID());
//            Logger.logInfo("  incoming: " + parallelGateway.getIncomming());
//            Logger.logInfo("  outgoing: " + parallelGateway.getOutgoing());
//        }
//        }
    }
    
}
