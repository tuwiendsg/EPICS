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
public class CategoricalElasticityCapability extends DataElasticityCapability{
    private CategoricalElasticityBoundary elastictyBoundary;
    private List<CategoricalElasticityValue> elasticityValues;
    private int lessThanLevel;
    private int greaterThanLevel;
    
    

    public CategoricalElasticityCapability() {
        elasticityValues = new ArrayList<CategoricalElasticityValue>();
    }

    
    public CategoricalElasticityCapability lessThanLevel(int lessThanLevel) {
        this.lessThanLevel = lessThanLevel;
        return this;
    }
    
    
    public CategoricalElasticityCapability greaterThanLevel(int greaterThanLevel) {
        this.greaterThanLevel = greaterThanLevel;
        return this;
    }
    
    public CategoricalElasticityCapability withPriorityOrder(int priorityOrder) {
        this.priorityOrder = priorityOrder;
        return this;
    }
    
    public CategoricalElasticityCapability withDataElasticityConfiguration(DataElasticityConfiguration dataElasticityConfiguration) {
        this.dataElasticityConfiguration = dataElasticityConfiguration;
        return this;
    }
    
    
    
    public CategoricalElasticityBoundary getElastictyBoundary() {
        return elastictyBoundary;
    }

    @XmlElement
    public void setElastictyBoundary(CategoricalElasticityBoundary elastictyBoundary) {
        this.elastictyBoundary = elastictyBoundary;
    }
 
    public boolean addElasticityValue (CategoricalElasticityValue elValue) {
        elasticityValues.add(elValue);
        
        return true;
    }
    

   
    
    

    public int getLessThanLevel() {
        return lessThanLevel;
    }

    @XmlElement
    public void setLessThanLevel(int lessThanLevel) {
        this.lessThanLevel = lessThanLevel;
    }

    public int getGreaterThanLevel() {
        return greaterThanLevel;
    }

    @XmlElement
    public void setGreaterThanLevel(int greaterThanLevel) {
        this.greaterThanLevel = greaterThanLevel;
    }

    public List<CategoricalElasticityValue> getElasticityValues() {
        return elasticityValues;
    }

    @XmlElement
    public void setElasticityValues(List<CategoricalElasticityValue> elasticityValues) {
        this.elasticityValues = elasticityValues;
    }
    
    
    


    
    
}
