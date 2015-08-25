
/**
 * Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup
 E184.  This work was partially supported by the European Commission in terms
 * of the CELAR FP7 project (FP7-ICT-2011-8 #317790).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller;

import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ExecutionSession;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ExecutionStep;
import java.util.ArrayList;
import java.util.List;


public class DEPExecutionEngine {

    private static List<ExecutionSession> listOfExecutionSessions;

    public static void addExecutionSession(ExecutionSession executionSession) {

        if (listOfExecutionSessions == null) {
            listOfExecutionSessions = new ArrayList<ExecutionSession>();

        }

        for (ExecutionStep executionStep : executionSession.getListOfExecutionSteps()) {
            List<String> listOfExecutionActions = executionStep.getListOfExecutionActions();
            List<String> listOfWaitingActions = new ArrayList<String>();
            List<String> listOfExecutingActions = new ArrayList<String>();
            List<String> listOfFinishedActions = new ArrayList<String>();

            for (String exeAction : listOfExecutionActions) {
                System.out.println("Executiong STEP: Adding to waiting: " + exeAction);
                String waitingAction = new String(exeAction);
                listOfWaitingActions.add(waitingAction);
            }

            executionStep.setExecutingActions(listOfExecutingActions);
            executionStep.setWaitingActions(listOfWaitingActions);
            executionStep.setFinishedActions(listOfFinishedActions);

        }

        listOfExecutionSessions.add(executionSession);

    }

    public static boolean isFinished(String sessionID) {
        
        boolean isFinished = true;
        
        if (listOfExecutionSessions == null) {
            listOfExecutionSessions = new ArrayList<ExecutionSession>();

        }

       

        for (ExecutionSession executionSession : listOfExecutionSessions) {

            if (executionSession.getMonitoringSession().getSessionID().equals(sessionID)) {

                List<ExecutionStep> listOfExecutionSteps = executionSession.getListOfExecutionSteps();

                for (ExecutionStep executionStep : listOfExecutionSteps) {

                    if (!(executionStep.getExecutingActions().isEmpty() && executionStep.getWaitingActions().isEmpty())) {

                        isFinished = false;
                    }

                }

            }

        }

        return isFinished;
    }
    
    public static boolean isExisted(String sessionID) {
        if (listOfExecutionSessions == null) {
            listOfExecutionSessions = new ArrayList<ExecutionSession>();

        }

        boolean isExisted = false;

        for (ExecutionSession executionSession : listOfExecutionSessions) {

            if (executionSession.getMonitoringSession().getSessionID().equals(sessionID)) {

                isExisted = true;

            }

        }

        return isExisted;
    }
    
    
    public static ExecutionSession getExecutionSessionFromID (String sessionID) {
        
        
        if (listOfExecutionSessions == null) {
            listOfExecutionSessions = new ArrayList<ExecutionSession>();

        }
        ExecutionSession foundExecutionSession = null;
        for (ExecutionSession executionSession : listOfExecutionSessions) {
            if (executionSession.getMonitoringSession().getSessionID().equals(sessionID)) {
                foundExecutionSession = executionSession;
                break;

            }

        }

        return foundExecutionSession;
    }

//    public static boolean executionSeesionRemoved(String sessionID) {
//
//        boolean isRemoved = true;
//        
//        System.out.println("No of Execution Session: " + listOfExecutionSessions.size());
//
//        for (ExecutionSession executionSession : listOfExecutionSessions) {
//
//            if (executionSession.getMonitoringSession().getSessionID().equals(sessionID)) {
//                isRemoved = false;
//            }
//
//        }
//        
//        if (isRemoved) {
//            System.out.println("REMOVED EXECUTION SESSION CONFIM");
//            
//        } else {
//           System.out.println("EXISTING EXECUTION SESSION CONFIM");     
//        }
//
//        return isRemoved;
//    }

    public static void checkExecution(String sessionID, String dataAssetIndex) {
        
        if (listOfExecutionSessions == null) {
            listOfExecutionSessions = new ArrayList<ExecutionSession>();

        }

        boolean isBreak = false;
        System.out.println("CHECK_EXECUTION : 1");

        for (ExecutionSession executionSession : listOfExecutionSessions) {

            System.out.println("CHECK_EXECUTION : 2");
            if (executionSession.getMonitoringSession().getSessionID().equals(sessionID)) {

                System.out.println("CHECK_EXECUTION : 3");
                List<ExecutionStep> listOfExecutionSteps = executionSession.getListOfExecutionSteps();

                System.out.println("CHECK_EXECUTION : 4");
                for (ExecutionStep executionStep : listOfExecutionSteps) {
                    System.out.println("CHECK_EXECUTION : 5");
                
                    System.out.println("START - Execution Step ----------------------");
                    System.out.println("No of Executing Actions: " + executionStep.getExecutingActions().size());
                    
                    System.out.println("CHECK_EXECUTION : 6");
                    for (String acc : executionStep.getExecutingActions()){
                        System.out.println(acc);
                    }
                    
                    System.out.println("CHECK_EXECUTION : 7");
                    
                    System.out.println("No of Finished Actions: " + executionStep.getFinishedActions().size());
                    
                    for (String acc : executionStep.getFinishedActions()){
                        System.out.println(acc);
                    }
                    
                    System.out.println("CHECK_EXECUTION : 8");
                    
                    System.out.println("No of Waiting Actions: " + executionStep.getWaitingActions().size());
                    
                    
                    for (String acc : executionStep.getWaitingActions()){
                        System.out.println(acc);
                    }
                    System.out.println("CHECK_EXECUTION : 9");
                    
                     System.out.println("END - Execution Step ----------------------");

                    if (executionStep.getExecutingActions().isEmpty() && executionStep.getWaitingActions().isEmpty()) {
                        // skip

                    } else if (!executionStep.getExecutingActions().isEmpty() && executionStep.getWaitingActions().isEmpty()) {
                        // skip

                    } else if (executionStep.getExecutingActions().isEmpty() && !executionStep.getWaitingActions().isEmpty()) {
                        for (String nextExeAction : executionStep.getWaitingActions()) {
                            System.out.println("STARTING ACTION: " + nextExeAction);
                            ActionExecutor actionExecutor = new ActionExecutor(executionSession, nextExeAction, dataAssetIndex);
                            actionExecutor.start();
                            isBreak = true;
                        }

                        if (isBreak) {
                            break;
                        }

                    } else if (!executionStep.getExecutingActions().isEmpty() && !executionStep.getWaitingActions().isEmpty()) {
                        //skip
                    }

                    if (isBreak) {
                        break;
                    }

                }

                if (isBreak) {
                    break;
                }

            }

            if (isBreak) {
                break;
            }

        }
        System.out.println("CHECK_EXECUTION : 10");

    }

    public static void actionExecuting(String sessionID, String actionID) {
        
        if (listOfExecutionSessions == null) {
            listOfExecutionSessions = new ArrayList<ExecutionSession>();

        }
        for (ExecutionSession executionSession : listOfExecutionSessions) {

            if (executionSession.getMonitoringSession().getSessionID().equals(sessionID)) {

                List<ExecutionStep> listOfExecutionSteps = executionSession.getListOfExecutionSteps();

                for (ExecutionStep executionStep : listOfExecutionSteps) {
                    executionStep.getWaitingActions().remove(actionID);
                    executionStep.getExecutingActions().add(actionID);

                }
            }

        }
    }

    public static void actionExecutionFinished(String sessionID, String actionID) {
        
        if (listOfExecutionSessions == null) {
            listOfExecutionSessions = new ArrayList<ExecutionSession>();

        }

        for (ExecutionSession executionSession : listOfExecutionSessions) {

            if (executionSession.getMonitoringSession().getSessionID().equals(sessionID)) {

                List<ExecutionStep> listOfExecutionSteps = executionSession.getListOfExecutionSteps();

                for (ExecutionStep executionStep : listOfExecutionSteps) {
                    executionStep.getExecutingActions().remove(actionID);
                    executionStep.getFinishedActions().add(actionID);

                }
            }

        }

    }
    
    public static void removeExecutionSession(String sessionID){
        
        int removedIndex = -1;
        
        if (listOfExecutionSessions == null) {
            listOfExecutionSessions = new ArrayList<ExecutionSession>();

        }
        
        for (ExecutionSession executionSession : listOfExecutionSessions) {

            if (executionSession.getMonitoringSession().getSessionID().equals(sessionID)) {
                removedIndex = listOfExecutionSessions.indexOf(executionSession);
                
            }

        }
        
        if (removedIndex!=-1){
            System.out.println("REMOVE EXECUTION SESSION !!!!!");
            listOfExecutionSessions.remove(removedIndex);
        }
        
        
    }
    

}
