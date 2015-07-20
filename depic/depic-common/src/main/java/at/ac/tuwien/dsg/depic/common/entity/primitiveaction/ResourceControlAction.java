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

@XmlRootElement(name = "ResourceControlAction")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResourceControlAction implements PrimitiveAction{
    
    @XmlElement(name = "associatedQoRMetric", required = true)
    String associatedQoRMetric; 
    
    @XmlElement(name = "listOfResourceControlStrategies", required = true)
    List<ResourceControlCase> listOfResourceControlStrategies;

    public ResourceControlAction() {
    }

    public ResourceControlAction(String associatedQoRMetric, List<ResourceControlCase> listOfResourceControlStrategies) {
        this.associatedQoRMetric = associatedQoRMetric;
        this.listOfResourceControlStrategies = listOfResourceControlStrategies;
    }

    public String getAssociatedQoRMetric() {
        return associatedQoRMetric;
    }

    public void setAssociatedQoRMetric(String associatedQoRMetric) {
        this.associatedQoRMetric = associatedQoRMetric;
    }

    public List<ResourceControlCase> getListOfResourceControlStrategies() {
        return listOfResourceControlStrategies;
    }

    public void setListOfResourceControlStrategies(List<ResourceControlCase> listOfResourceControlStrategies) {
        this.listOfResourceControlStrategies = listOfResourceControlStrategies;
    }

    
    
    
}
