/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.common.entity.qor;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ControlAction;
import java.util.List;

/**
 *
 * @author Jun
 */
public class MetricControlActions {
    String fromRange;
    String toRange;
    List<ControlAction> listOfControlActions;

    public MetricControlActions() {
    }

    public MetricControlActions(String fromRange, String toRange, List<ControlAction> listOfControlActions) {
        this.fromRange = fromRange;
        this.toRange = toRange;
        this.listOfControlActions = listOfControlActions;
    }

    public String getFromRange() {
        return fromRange;
    }

    public void setFromRange(String fromRange) {
        this.fromRange = fromRange;
    }

    public String getToRange() {
        return toRange;
    }

    public void setToRange(String toRange) {
        this.toRange = toRange;
    }

    public List<ControlAction> getListOfControlActions() {
        return listOfControlActions;
    }

    public void setListOfControlActions(List<ControlAction> listOfControlActions) {
        this.listOfControlActions = listOfControlActions;
    }
    
    
    

   
    
    
}
