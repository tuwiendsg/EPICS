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
public class Range {
    String rangeID;
    double fromValue;
    double toValue;

    public Range() {
    }

    public Range(String rangeID, double fromValue, double toValue) {
        this.rangeID = rangeID;
        this.fromValue = fromValue;
        this.toValue = toValue;
    }

    public String getRangeID() {
        return rangeID;
    }

    public void setRangeID(String rangeID) {
        this.rangeID = rangeID;
    }

    
    
    

    public double getFromValue() {
        return fromValue;
    }

    public void setFromValue(double fromValue) {
        this.fromValue = fromValue;
    }

    public double getToValue() {
        return toValue;
    }

    public void setToValue(double toValue) {
        this.toValue = toValue;
    }
    
    
    
    
}
