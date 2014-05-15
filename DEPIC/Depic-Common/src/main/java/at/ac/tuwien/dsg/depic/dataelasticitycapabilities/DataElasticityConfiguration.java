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
public class DataElasticityConfiguration {
    String costURL;
    String currentValueURL;
    int monitoringInterval;

    public DataElasticityConfiguration() {
    }
    
    public DataElasticityConfiguration withCostURL(String priceRestURL) {
        this.costURL = priceRestURL;
        return this;
    }
    
    public DataElasticityConfiguration withCurrentValueURL(String currentValueRestURL) {
        this.currentValueURL = currentValueRestURL;
        return this;
    }
    
    public DataElasticityConfiguration withMonitoringInterval(int monitoringInterval) {
        this.monitoringInterval = monitoringInterval;
        return this;
    }

    public String getPriceURL() {
        return costURL;
    }

    @XmlElement
    public void setPriceURL(String priceURL) {
        this.costURL = priceURL;
    }

    public String getCurrentValueURL() {
        return currentValueURL;
    }

    @XmlElement
    public void setCurrentValueURL(String currentValueURL) {
        this.currentValueURL = currentValueURL;
    }

    public int getMonitoringInterval() {
        return monitoringInterval;
    }

    @XmlElement
    public void setMonitoringInterval(int monitoringInterval) {
        this.monitoringInterval = monitoringInterval;
    }
    
    
    
}
