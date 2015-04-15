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

@XmlRootElement(name = "Transition")
@XmlAccessorType(XmlAccessType.FIELD)
public class Transition {
    
    @XmlElement(name = "initialCondition", required = true)
    MetricCondition initialCondition;
    
    @XmlElement(name = "finalCondition", required = true)
    MetricCondition finalCondition;
    
    @XmlElement(name = "listOfParameters", required = true)
    List<Parameter> listOfParameters;

    public Transition() {
    }

    public Transition(MetricCondition initialCondition, MetricCondition finalCondition, List<Parameter> listOfParameters) {
        this.initialCondition = initialCondition;
        this.finalCondition = finalCondition;
        this.listOfParameters = listOfParameters;
    }

    public MetricCondition getInitialCondition() {
        return initialCondition;
    }

    public void setInitialCondition(MetricCondition initialCondition) {
        this.initialCondition = initialCondition;
    }

    public MetricCondition getFinalCondition() {
        return finalCondition;
    }

    public void setFinalCondition(MetricCondition finalCondition) {
        this.finalCondition = finalCondition;
    }

    public List<Parameter> getListOfParameters() {
        return listOfParameters;
    }

    public void setListOfParameters(List<Parameter> listOfParameters) {
        this.listOfParameters = listOfParameters;
    }
    
    
    
    
}
