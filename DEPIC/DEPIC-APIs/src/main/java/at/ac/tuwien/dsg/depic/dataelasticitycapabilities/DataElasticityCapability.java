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
public class DataElasticityCapability {

    private String elasticityCapabilityName;
    DataElasticityConfiguration dataElasticityConfiguration;
    int priorityOrder;

    public DataElasticityCapability() {
        
        
    }

    public DataElasticityConfiguration getDataElasticityConfiguration() {
        return dataElasticityConfiguration;
    }

    @XmlElement
    public void setDataElasticityConfiguration(DataElasticityConfiguration dataElasticityConfiguration) {
        this.dataElasticityConfiguration = dataElasticityConfiguration;
    }

    public int getPriorityOrder() {
        return priorityOrder;
    }

    @XmlElement
    public void setPriorityOrder(int priorityOrder) {
        this.priorityOrder = priorityOrder;
    }

    
    


    public String getElasticityCapabilityName() {
        return elasticityCapabilityName;
    }

    @XmlElement
    public void setElasticityCapabilityName(String elasticityCapabilityName) {
        this.elasticityCapabilityName = elasticityCapabilityName;
    }

    
    
    



    
    

    
}
