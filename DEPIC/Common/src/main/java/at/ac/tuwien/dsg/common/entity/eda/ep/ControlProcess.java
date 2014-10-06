/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.eda.ep;

import at.ac.tuwien.dsg.common.entity.eda.ElasticState;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ControlProcess {
    ElasticState eStateID_i;
    ElasticState eStateID_j;
    
    List<ControlAction> listOfControlActions;

    public ControlProcess() {
    }

    public ControlProcess(ElasticState eStateID_i, ElasticState eStateID_j, List<ControlAction> listOfControlActions) {
        this.eStateID_i = eStateID_i;
        this.eStateID_j = eStateID_j;
        this.listOfControlActions = listOfControlActions;
    }

    public ElasticState geteStateID_i() {
        return eStateID_i;
    }

    public void seteStateID_i(ElasticState eStateID_i) {
        this.eStateID_i = eStateID_i;
    }

    public ElasticState geteStateID_j() {
        return eStateID_j;
    }

    public void seteStateID_j(ElasticState eStateID_j) {
        this.eStateID_j = eStateID_j;
    }

    public List<ControlAction> getListOfControlActions() {
        return listOfControlActions;
    }

    public void setListOfControlActions(List<ControlAction> listOfControlActions) {
        this.listOfControlActions = listOfControlActions;
    }

    
}
