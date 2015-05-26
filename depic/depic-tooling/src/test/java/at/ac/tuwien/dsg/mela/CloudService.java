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
@XmlRootElement(name = "CloudService")
@XmlAccessorType(XmlAccessType.FIELD)
public class CloudService {
    
    @XmlAttribute (name = "id", required = true)
    String id;
    
    @XmlElement(name = "ServiceTopology", required = true)
    List<ServiceTopology> listOfServiceTopologies;

    public CloudService() {
    }

    public CloudService(String id, List<ServiceTopology> listOfServiceTopologies) {
        this.id = id;
        this.listOfServiceTopologies = listOfServiceTopologies;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ServiceTopology> getListOfServiceTopologies() {
        return listOfServiceTopologies;
    }

    public void setListOfServiceTopologies(List<ServiceTopology> listOfServiceTopologies) {
        this.listOfServiceTopologies = listOfServiceTopologies;
    }
    
    
    
    
}
