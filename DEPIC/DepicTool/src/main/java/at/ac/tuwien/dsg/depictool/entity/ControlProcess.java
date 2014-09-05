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
public class ControlProcess {
    TriggerValues triggerValues;
    List listOfActions;

    public ControlProcess() {
    }

    public ControlProcess(TriggerValues triggerValues, List listOfActions) {
        this.triggerValues = triggerValues;
        this.listOfActions = listOfActions;
    }

    public TriggerValues getTriggerValues() {
        return triggerValues;
    }

    public void setTriggerValues(TriggerValues triggerValues) {
        this.triggerValues = triggerValues;
    }

    public List getListOfActions() {
        return listOfActions;
    }

    public void setListOfActions(List listOfActions) {
        this.listOfActions = listOfActions;
    }
    
    
    
    
}
