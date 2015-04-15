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
@XmlRootElement(name = "ServiceTopology")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceTopology {
    
    @XmlAttribute (name = "id", required = true)
    String id;
   
    @XmlElement(name = "ServiceUnit", required = true)
    List<ServiceUnit> listOfServiceUnits;

    public ServiceTopology() {
    }

    public ServiceTopology(String id, List<ServiceUnit> listOfServiceUnits) {
        this.id = id;
        this.listOfServiceUnits = listOfServiceUnits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ServiceUnit> getListOfServiceUnits() {
        return listOfServiceUnits;
    }

    public void setListOfServiceUnits(List<ServiceUnit> listOfServiceUnits) {
        this.listOfServiceUnits = listOfServiceUnits;
    }
    
    
    
}
