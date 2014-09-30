/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement (name = "QoR")
public class QoR {
    double dataCompleteness;
    double throughPut;
    double cost;

    public QoR() {
    }

    public QoR(double dataCompleteness, double throughPut, double cost) {
        this.dataCompleteness = dataCompleteness;
        this.throughPut = throughPut;
        this.cost = cost;
    }

    public double getDataCompleteness() {
        return dataCompleteness;
    }

    @XmlElement (name ="dataCompleteness")
    public void setDataCompleteness(double dataCompleteness) {
        this.dataCompleteness = dataCompleteness;
    }

    public double getThroughPut() {
        return throughPut;
    }

    @XmlElement (name ="throughPut")
    public void setThroughPut(double throughPut) {
        this.throughPut = throughPut;
    }

    public double getCost() {
        return cost;
    }

    @XmlElement (name ="cost")
    public void setCost(double cost) {
        this.cost = cost;
    }

    
    
    
    
}
