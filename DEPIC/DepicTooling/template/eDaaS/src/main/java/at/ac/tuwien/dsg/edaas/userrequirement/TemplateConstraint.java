/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edaas.userrequirement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement (name = "TemplateConstraint")
public class TemplateConstraint {
    String constraintName;
    private double minValue;
    private double maxValue;

    public TemplateConstraint() {
    }

    public TemplateConstraint(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.constraintName = "Template";
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
