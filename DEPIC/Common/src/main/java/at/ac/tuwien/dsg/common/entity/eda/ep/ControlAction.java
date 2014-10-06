/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.eda.ep;

import at.ac.tuwien.dsg.common.entity.process.Parameter;
import at.ac.tuwien.dsg.common.entity.qor.TriggerActions;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ControlAction {
    String controlActionID;
    List<Parameter> listOfParameters;

    public ControlAction() {
    }

    public ControlAction(String controlActionID, List<Parameter> listOfParameters) {
        this.controlActionID = controlActionID;
        this.listOfParameters = listOfParameters;
    }

    public String getControlActionID() {
        return controlActionID;
    }

    public void setControlActionID(String controlActionID) {
        this.controlActionID = controlActionID;
    }

    public List<Parameter> getListOfParameters() {
        return listOfParameters;
    }

    public void setListOfParameters(List<Parameter> listOfParameters) {
        this.listOfParameters = listOfParameters;
    }

    
    
    
    
    
    
}
