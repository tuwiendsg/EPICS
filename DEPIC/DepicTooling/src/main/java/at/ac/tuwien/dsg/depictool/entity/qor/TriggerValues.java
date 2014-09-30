/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity.qor;

/**
 *
 * @author Jun
 */
public class TriggerValues {
    String fromRange;
    String toRange;

   

    public TriggerValues() {
    }
    
     public TriggerValues(String fromRange, String toRange) {
        this.fromRange = fromRange;
        this.toRange = toRange;
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
    
}
