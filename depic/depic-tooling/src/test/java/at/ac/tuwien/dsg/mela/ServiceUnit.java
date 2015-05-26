/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.mela;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "ServiceUnit")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceUnit {
    
    @XmlAttribute (name = "id", required = true)
    String id;
   
    @XmlElement(name = "SYBLDirective", required = false)
    SYBLDirective sybld;

    public ServiceUnit() {
    }

    public ServiceUnit(String id) {
        this.id = id;
    }

    public ServiceUnit(String id, SYBLDirective sybld) {
        this.id = id;
        this.sybld = sybld;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SYBLDirective getSybld() {
        return sybld;
    }

    public void setSybld(SYBLDirective sybld) {
        this.sybld = sybld;
    }
    
    
}
