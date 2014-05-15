/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.dataelasticitycapabilities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author Jun
 */
@XmlSeeAlso({DataElasticityCapability.class})
@XmlRootElement
public class NumericalElasticityCapability extends DataElasticityCapability {
    
    private NumericalElasticityBoundary elastictyBoundary;
    private List elasticityValues;
    private double lessThanValue;
    private double greaterThanValue;

    
    
    
    public NumericalElasticityCapability() {
        elasticityValues = new ArrayList();
    }

    public NumericalElasticityCapability lessThan(double lessThanValue) {
        this.lessThanValue = lessThanValue;
        return this;
    }
    
    
    public NumericalElasticityCapability greaterThan(double greaterThanValue) {
        this.greaterThanValue = greaterThanValue;
        return this;
    }
    
    public NumericalElasticityCapability withPriorityOrder(int priorityOrder) {
        this.priorityOrder = priorityOrder;
        return this;
    }
    
    public NumericalElasticityCapability withDataElasticityConfiguration(DataElasticityConfiguration dataElasticityConfiguration) {
        this.dataElasticityConfiguration = dataElasticityConfiguration;
        return this;
    }

    public NumericalElasticityBoundary getElastictyBoundary() {
        return elastictyBoundary;
    }

    @XmlElement
    public void setElastictyBoundary(NumericalElasticityBoundary elastictyBoundary) {
        this.elastictyBoundary = elastictyBoundary;
    }

    
    public List<NumericalElasticityValue> getElasticityValues() {
        return elasticityValues;
    }

    @XmlElement
    public void setElasticityValues(List<NumericalElasticityValue> elasticityValues) {
        this.elasticityValues = elasticityValues;
    }

    public double getLessThanValue() {
        return lessThanValue;
    }

    @XmlElement
    public void setLessThanValue(double lessThanValue) {
        this.lessThanValue = lessThanValue;
    }

    public double getGreaterThanValue() {
        return greaterThanValue;
    }

    @XmlElement
    public void setGreaterThanValue(double greaterThanValue) {
        this.greaterThanValue = greaterThanValue;
    }

  
    
    
    
    
    


}
