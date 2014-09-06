/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.tooling.edo.metric;

import java.util.List;

/**
 *
 * @author Jun
 */
public class ControlAction {
    String actionURI;
    List listOfParameters;

    public ControlAction() {
    }

    public ControlAction(String actionURI, List listOfParameters) {
        this.actionURI = actionURI;
        this.listOfParameters = listOfParameters;
    }

    public String getActionURI() {
        return actionURI;
    }

    public void setActionURI(String actionURI) {
        this.actionURI = actionURI;
    }

    public List getListOfParameters() {
        return listOfParameters;
    }

    public void setListOfParameters(List listOfParameters) {
        this.listOfParameters = listOfParameters;
    }
    
    
    
}
