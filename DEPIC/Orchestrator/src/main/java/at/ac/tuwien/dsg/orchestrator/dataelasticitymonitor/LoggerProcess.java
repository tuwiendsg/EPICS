/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.dataelasticitymonitor;

import at.ac.tuwien.dsg.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.common.entity.eda.ElasticState;
import at.ac.tuwien.dsg.common.entity.eda.ElasticStateSet;
import at.ac.tuwien.dsg.common.entity.eda.MetricCondition;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.ElasticityProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitoringSession;
import at.ac.tuwien.dsg.common.entity.eda.ep.ParallelGateway;
import at.ac.tuwien.dsg.common.entity.process.MonitoringMetric;
import at.ac.tuwien.dsg.common.entity.process.Parameter;
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
    
        
        System.out.println("INITIAL ESTATE SET ------- \n");
        for (ElasticState  estate : listOfInitialState){
           
            logElasticState(estate);
        }
        
        
        System.out.println("FINAL ESTATE SET ------- \n");
        for (ElasticState  estate : listOfFinalState){
           
            logElasticState(estate);
        }
        
        
        
        System.out.println("CONTROL PROCESSES LIST ------ \n");
        for (ControlProcess cp : listOfControlProcesses){
            logControlProcesses(cp);
        }
        
        
    
    }
    
    
     private void logElasticState(ElasticState elasticState ){
  
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
    
     
     public void logControlProcesses(ControlProcess controlProcess) {

        System.out.println("\nLOG CONTROL PROCESS");

        System.out.println("\n***");
        List<ControlAction> listOfControlActions = controlProcess.getListOfControlActions();

        System.out.println("PROCESS ----------- ");
        System.out.println("eState in: " + controlProcess.geteStateID_i().geteStateID());
        System.out.println("eState fi: " + controlProcess.geteStateID_j().geteStateID());
        for (ControlAction controlAction : listOfControlActions) {
            System.out.println("control action: " + controlAction.getControlActionName());
        }

        System.out.println(".............");

        List<MetricCondition> conditions_in = controlProcess.geteStateID_i().getListOfConditions();
        for (MetricCondition c : conditions_in) {
            System.out.println("   id: " + c.getConditionID());
            System.out.println("   metric: " + c.getMetricName());
            System.out.println("   lower: " + c.getLowerBound());
            System.out.println("   upper: " + c.getUpperBound());

        }

        System.out.println("eState fi: " + controlProcess.geteStateID_j().geteStateID());
        List<MetricCondition> conditions_fi = controlProcess.geteStateID_j().getListOfConditions();
        for (MetricCondition c : conditions_fi) {
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

            for (Parameter param : listOfParams) {
                System.out.println("    parameter: " + param.getParaName());
                System.out.println("    value: " + param.getValue());
            }

        }

        List<ParallelGateway> listOfParallelGateways = controlProcess.getListOfParallelGateways();

        if (listOfParallelGateways!=null){
        for (ParallelGateway parallelGateway : listOfParallelGateways) {
            System.out.println("parallel gateway: " + parallelGateway.getId());
            System.out.println("  incoming: " + parallelGateway.getIncomming());
            System.out.println("  outgoing: " + parallelGateway.getOutgoing());
        }
        }
    }
    
}
