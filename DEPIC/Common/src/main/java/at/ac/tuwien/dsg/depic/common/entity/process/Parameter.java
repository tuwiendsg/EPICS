/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.common.entity.process;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "Parameter")
@XmlAccessorType(XmlAccessType.FIELD)
public class Parameter {
    
    @XmlElement(name = "paraName", required = true)
    String paraName;
    
    @XmlElement(name = "type", required = true)
    String type;
    
    @XmlElement(name = "value", required = true)
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
