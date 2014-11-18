/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edaas.requirement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "CostConstraint")
@XmlAccessorType(XmlAccessType.FIELD)
public class CostConstraint {
    
    @XmlElement(name = "constraintName", required = true)
    private String constraintName;
    
    @XmlElement(name = "minValue", required = true)
    private double minValue;
    
    @XmlElement(name = "maxValue", required = true)
    private double maxValue;

    public CostConstraint() {
    }

    public CostConstraint(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    
    
    
    
}
