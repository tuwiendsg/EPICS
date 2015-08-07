/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller;

import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
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

    public static void checkExecution(String sessionID) {

        boolean isBreak = false;

        for (ExecutionSession executionSession : listOfExecutionSessions) {

            if (executionSession.getMonitoringSession().getSessionID().equals(sessionID)) {

                List<ExecutionStep> listOfExecutionSteps = executionSession.getListOfExecutionSteps();

                for (ExecutionStep executionStep : listOfExecutionSteps) {

                    System.out.println("No of Executing Actions: " + executionStep.getExecutingActions().size());
                    System.out.println("No of Finished Actions: " + executionStep.getFinishedActions().size());
                    System.out.println("No of Waiting Actions: " + executionStep.getWaitingActions().size());

                    if (executionStep.getExecutingActions().isEmpty() && executionStep.getWaitingActions().isEmpty()) {
                        // skip

                    } else if (!executionStep.getExecutingActions().isEmpty() && executionStep.getWaitingActions().isEmpty()) {
                        // skip

                    } else if (executionStep.getExecutingActions().isEmpty() && !executionStep.getWaitingActions().isEmpty()) {
                        for (String nextExeAction : executionStep.getWaitingActions()) {
                            System.out.println("STARTING ACTION: " + nextExeAction);
                            ActionExecutor actionExecutor = new ActionExecutor(executionSession, nextExeAction);
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

    }

    public static void actionExecuting(String sessionID, String actionID) {
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

}
