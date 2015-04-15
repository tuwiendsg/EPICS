/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.mela;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "SYBLDirective")
@XmlAccessorType(XmlAccessType.FIELD)
public class SYBLDirective {
    
    @XmlAttribute (name = "Constraints", required = true)
    String constraints;
    
    @XmlAttribute (name = "Monitoring", required = true)
    String monitoring;
    
    @XmlAttribute (name = "Priorities", required = true)
    String priorities;
    
    @XmlAttribute (name = "Strategies", required = true)
    String strategies;

    public SYBLDirective() {
    }

    public SYBLDirective(String constraints, String monitoring, String priorities, String strategies) {
        this.constraints = constraints;
        this.monitoring = monitoring;
        this.priorities = priorities;
        this.strategies = strategies;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    public String getMonitoring() {
        return monitoring;
    }

    public void setMonitoring(String monitoring) {
        this.monitoring = monitoring;
    }

    public String getPriorities() {
        return priorities;
    }

    public void setPriorities(String priorities) {
        this.priorities = priorities;
    }

    public String getStrategies() {
        return strategies;
    }

    public void setStrategies(String strategies) {
        this.strategies = strategies;
    }
    
    
    
}
