/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.primitiveaction;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "ResourceControl")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResourceControl implements PrimitiveAction{
    
    @XmlElement(name = "associatedQoRMetric", required = true)
    String associatedQoRMetric; 
    
    @XmlElement(name = "controlMetric", required = true)
    String controlMetric;
    
    @XmlElement(name = "listOfResourceControlStrategies", required = true)
    List<ResourceControlStrategy> listOfResourceControlStrategies;

    public ResourceControl() {
    }

    public ResourceControl(String associatedQoRMetric, String controlMetric, List<ResourceControlStrategy> listOfResourceControlStrategies) {
        this.associatedQoRMetric = associatedQoRMetric;
        this.controlMetric = controlMetric;
        this.listOfResourceControlStrategies = listOfResourceControlStrategies;
    }

    

    public String getControlMetric() {
        return controlMetric;
    }

    public void setControlMetric(String controlMetric) {
        this.controlMetric = controlMetric;
    }

    public List<ResourceControlStrategy> getListOfResourceControlStrategies() {
        return listOfResourceControlStrategies;
    }

    public void setListOfResourceControlStrategies(List<ResourceControlStrategy> listOfResourceControlStrategies) {
        this.listOfResourceControlStrategies = listOfResourceControlStrategies;
    }

    public String getAssociatedQoRMetric() {
        return associatedQoRMetric;
    }

    public void setAssociatedQoRMetric(String associatedQoRMetric) {
        this.associatedQoRMetric = associatedQoRMetric;
    }
    
    
    
    
}
