/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity;

/**
 *
 * @author Jun
 */
public class Parameter {
    String parameterID;
    String value;

    public Parameter() {
    }

    public Parameter(String parameterID, String value) {
        this.parameterID = parameterID;
        this.value = value;
    }

    public String getParameterID() {
        return parameterID;
    }

    public void setParameterID(String parameterID) {
        this.parameterID = parameterID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
    
    
}
