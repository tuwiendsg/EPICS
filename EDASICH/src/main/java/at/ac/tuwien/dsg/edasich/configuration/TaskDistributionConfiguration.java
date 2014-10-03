/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "MOMConfiguration")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskDistributionConfiguration {
    
    @XmlElement(name = "ip", required = true)
    String ip;
    
    @XmlElement(name = "port", required = true)
    String port;
    
    @XmlElement(name = "api", required = true)
    String api;

    public TaskDistributionConfiguration() {
    }

    public TaskDistributionConfiguration(String ip, String port, String api) {
        this.ip = ip;
        this.port = port;
        this.api = api;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
    
    
    
}
