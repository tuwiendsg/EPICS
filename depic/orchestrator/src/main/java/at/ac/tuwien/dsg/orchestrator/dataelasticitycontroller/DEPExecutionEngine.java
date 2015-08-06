/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller;

import at.ac.tuwien.dsg.depic.common.entity.runtime.ExecutionSession;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ExecutionStep;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class DEPExecutionEngine {
   
    private static List<ExecutionSession> listOfExecutionSessions;

    
    
    public static void addExecutionSession(ExecutionSession executionSession){
        
        if (listOfExecutionSessions==null){
            listOfExecutionSessions = new ArrayList<ExecutionSession>();
            
        }
        
        
        for (ExecutionStep executionStep : executionSession.getListOfExecutionSteps()){
            List<String> listOfExecutionActions= executionStep.getListOfExecutionActions();
            
            for (String exeAction : listOfExecutionActions){
                
                String waitingAction = new String(exeAction);
                executionStep.getWaitingActions().add(waitingAction); 
            }
            
        }
        
        listOfExecutionSessions.add(executionSession);
        
    }
    
    public static boolean isFinished(String sessionID){
        
        boolean isFinished = true;
        
        
        for (ExecutionSession executionSession : listOfExecutionSessions) {
            
            if (executionSession.getMonitoringSession().getSessionID().equals(sessionID)){
                
                List<ExecutionStep> listOfExecutionSteps  = executionSession.getListOfExecutionSteps();
                
                for (ExecutionStep executionStep : listOfExecutionSteps){
                    
                    if (!(executionStep.getExecutingActions().isEmpty() && executionStep.getWaitingActions().isEmpty())){
                        
                        isFinished=false;
                    }
                    
                    
                }
  
            }
            
        }
        

        return isFinished;
    }
    
    
    
    public static void checkExecution(String sessionID){
        
        
        for (ExecutionSession executionSession : listOfExecutionSessions) {
            
            if (executionSession.getMonitoringSession().getSessionID().equals(sessionID)){
                
                
                
                List<ExecutionStep> listOfExecutionSteps  = executionSession.getListOfExecutionSteps();
                
                
                for (ExecutionStep executionStep : listOfExecutionSteps){
                   
                    if (executionStep.getExecutingActions().isEmpty() && executionStep.getWaitingActions().isEmpty()){
                        // skip
                        
                    } else if (!executionStep.getExecutingActions().isEmpty() && executionStep.getWaitingActions().isEmpty()){
                        // skip

                    } else if (executionStep.getExecutingActions().isEmpty() && !executionStep.getWaitingActions().isEmpty()){
                         for (String nextExeAction : executionStep.getWaitingActions()){
                             ActionExecutor actionExecutor = new ActionExecutor("ip", "port", "resource", sessionID, "xmlStr", nextExeAction);
                             actionExecutor.start();
                         }
                        
                      
                    } else if (!executionStep.getExecutingActions().isEmpty() && !executionStep.getWaitingActions().isEmpty()){
                        //skip
                    }
                    
                    
                   
                    
                    
                }
  
            }
            
        }
        
        
    }
    
    
    
    
    
    
    public static void actionExecutionFinished(String sessionID, String actionID){
        
        for (ExecutionSession executionSession : listOfExecutionSessions){
            
            
            if (executionSession.getMonitoringSession().getSessionID().equals(sessionID)){
                
                List<ExecutionStep> listOfExecutionSteps = executionSession.getListOfExecutionSteps();
                
                for (ExecutionStep executionStep : listOfExecutionSteps){
                    executionStep.getExecutingActions().remove(actionID);
                    executionStep.getFinishedActions().add(actionID);
                    
                }      
            }
   
        }

    }
    
    
    
    
    
    
    
    
    
    
    
}
