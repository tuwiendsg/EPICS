
package at.ac.tuwien.dsg.edaas.requirement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "TemplateConstraint")
@XmlAccessorType(XmlAccessType.FIELD)
public class TemplateConstraint {
    
    @XmlElement(name = "constraintName", required = true)
    private String constraintName;
    
    @XmlElement(name = "minValue", required = true)
    private double minValue;
    
    @XmlElement(name = "maxValue", required = true)
    private double maxValue;

    public TemplateConstraint() {
    }

    public TemplateConstraint(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public TemplateConstraint(String constraintName, double minValue, double maxValue) {
        this.constraintName = constraintName;
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
