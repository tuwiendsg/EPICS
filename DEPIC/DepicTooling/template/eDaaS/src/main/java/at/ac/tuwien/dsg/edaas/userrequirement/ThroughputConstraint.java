/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Throughputs
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edaas.userrequirement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement (name = "ThroughputConstraint")
public class ThroughputConstraint {
    String constraintName;
    private double minValue;
    private double maxValue;

    public ThroughputConstraint() {
    }

    public ThroughputConstraint(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.constraintName = "Throughput";
    }

    public String getConstraintName() {
        return constraintName;
    }

    @XmlElement (name ="constraintName")
    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public double getMinValue() {
        return minValue;
    }

    @XmlElement (name ="minValue")
    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    @XmlElement (name ="maxValue")
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
    
    
    
    
    
    
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Throughputs
 * and open the template in the editor.
 */