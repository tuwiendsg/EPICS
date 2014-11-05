/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity;

import java.util.List;

/**
 *
 * @author Jun
 */
public class ControlProcess {
    String eStateID_i;
    String eStateID_j;
    
    List<ControlAction> listOfControlActions;

    public ControlProcess() {
    }

    public ControlProcess(String eStateID_i, String eStateID_j, List<ControlAction> listOfControlActions) {
        this.eStateID_i = eStateID_i;
        this.eStateID_j = eStateID_j;
        this.listOfControlActions = listOfControlActions;
    }

    public String geteStateID_i() {
        return eStateID_i;
    }

    public void seteStateID_i(String eStateID_i) {
        this.eStateID_i = eStateID_i;
    }

    public String geteStateID_j() {
        return eStateID_j;
    }

    public void seteStateID_j(String eStateID_j) {
        this.eStateID_j = eStateID_j;
    }

    public List<ControlAction> getListOfControlActions() {
        return listOfControlActions;
    }

    public void setListOfControlActions(List<ControlAction> listOfControlActions) {
        this.listOfControlActions = listOfControlActions;
    }
    
    
    
    
    
    
}
