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
public class CategoricalElasticityValue {
    int elValue;
    double cost;

    public CategoricalElasticityValue() {
    }
    
    
    

    public CategoricalElasticityValue(int elValue, double cost) {
        this.elValue = elValue;
        this.cost = cost;
    }

    public int getElValue() {
        return elValue;
    }

    @XmlElement (name = "Level")
    public void setElValue(int elValue) {
        this.elValue = elValue;
    }

    public double getCost() {
        return cost;
    }

    @XmlElement (name = "Cost")
    public void setCost(double price) {
        this.cost = price;
    }
    
    
    
    
    
}
