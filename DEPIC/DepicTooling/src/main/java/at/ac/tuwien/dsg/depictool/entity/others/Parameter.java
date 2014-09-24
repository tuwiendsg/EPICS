/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity.others;

/**
 *
 * @author Jun
 */
public class Parameter {
    String paraName;
    String type;
    String value;

    public Parameter() {
    }

    public Parameter(String paraName, String type, String value) {
        this.paraName = paraName;
        this.type = type;
        this.value = value;
    }

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    
    
    
    
    
}
