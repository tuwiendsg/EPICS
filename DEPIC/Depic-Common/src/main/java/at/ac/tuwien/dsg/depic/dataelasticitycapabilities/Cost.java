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
public class Cost {
    
    double lessThan;
    int priorityOrder;

    public Cost() {
    }
    
    
    public Cost lessThan(double lessThan) {
        this.lessThan = lessThan;
        return this;
    }
    

    public Cost withPriorityOrder(int priorityOrder) {
        this.priorityOrder = priorityOrder;
        return this;
    }

    public double getLessThan() {
        return lessThan;
    }

    @XmlElement
    public void setLessThan(double lessThan) {
        this.lessThan = lessThan;
    }

    public int getPriorityOrder() {
        return priorityOrder;
    }

    @XmlElement
    public void setPriorityOrder(int priorityOrder) {
        this.priorityOrder = priorityOrder;
    }
    
    
    
    
    
}
