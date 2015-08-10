/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.runtime;

import java.util.List;

/**
 *
 * @author Jun
 */
public class ExecutionStep {
    List<String> listOfPrerequisiteActions;
    List<String> listOfExecutionActions;
    List<String> waitingActions;
    List<String> executingActions;
    List<String> finishedActions;

    public ExecutionStep() {
    }

    public List<String> getListOfPrerequisiteActions() {
        return listOfPrerequisiteActions;
    }

    public void setListOfPrerequisiteActions(List<String> listOfPrerequisiteActions) {
        this.listOfPrerequisiteActions = listOfPrerequisiteActions;
    }

    public List<String> getListOfExecutionActions() {
        return listOfExecutionActions;
    }

    public void setListOfExecutionActions(List<String> listOfExecutionActions) {
        this.listOfExecutionActions = listOfExecutionActions;
    }

    public List<String> getWaitingActions() {
        return waitingActions;
    }

    public void setWaitingActions(List<String> waitingActions) {
        this.waitingActions = waitingActions;
    }

    public List<String> getExecutingActions() {
        return executingActions;
    }

    public void setExecutingActions(List<String> executingActions) {
        this.executingActions = executingActions;
    }

    public List<String> getFinishedActions() {
        return finishedActions;
    }

    public void setFinishedActions(List<String> finishedActions) {
        this.finishedActions = finishedActions;
    }

    

    
    
    
}