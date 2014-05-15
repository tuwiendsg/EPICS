/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.sybl.elasticstate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement
public class CategoricalElCapState {
    String name;
    double cost;
    int currentValue;

    public CategoricalElCapState() {
    }

    
    
    public CategoricalElCapState(String name, double cost, int currentValue) {
        this.name = name;
        this.cost = cost;
        this.currentValue = currentValue;
    }

    public String getName() {
        return name;
    }
    
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    @XmlElement
    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    @XmlElement
    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }


    


    
}
