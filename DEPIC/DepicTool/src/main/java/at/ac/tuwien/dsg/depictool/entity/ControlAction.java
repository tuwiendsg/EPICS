/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity;

import java.util.List;

/**
 *
 * @author Jun
 */
public class ControlAction {
    String controlActionID;
    TriggerValues triggerValues;
    List<Parameter> listOfParameters;

    public ControlAction() {
    }

    public ControlAction(String actionID, TriggerValues triggerValues, List<Parameter> listOfParameters) {
        this.controlActionID = actionID;
        this.triggerValues = triggerValues;
        this.listOfParameters = listOfParameters;
    }

    public String getActionID() {
        return controlActionID;
    }

    public void setActionID(String actionID) {
        this.controlActionID = actionID;
    }

    public TriggerValues getTriggerValues() {
        return triggerValues;
    }

    public void setTriggerValues(TriggerValues triggerValues) {
        this.triggerValues = triggerValues;
    }

    public List<Parameter> getListOfParameters() {
        return listOfParameters;
    }

    public void setListOfParameters(List<Parameter> listOfParameters) {
        this.listOfParameters = listOfParameters;
    }

    
    
    
    
    
    
}
