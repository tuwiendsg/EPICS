/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.dataelasticitycapabilities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement
public class NumericalElasticityValue {
    double fromValue;
    double toValue;
    double cost;

    public NumericalElasticityValue() {
    }

    
    
    public NumericalElasticityValue(double fromValue, double toValue, double cost) {
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.cost = cost;
    }

    
    
    
    public double getFromValue() {
        return fromValue;
    }

    @XmlElement
    public void setFromValue(double fromValue) {
        this.fromValue = fromValue;
    }

    public double getToValue() {
        return toValue;
    }

    @XmlElement 
    public void setToValue(double toValue) {
        this.toValue = toValue;
    }

    public double getCost() {
        return cost;
    }

    @XmlElement (name = "Cost") 
    public void setCost(double cost) {
        this.cost = cost;
    }
    
    
    
}
