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
public class MOMConfiguration {
    
    @XmlElement(name = "ip", required = true)
    String ip;
    
    @XmlElement(name = "port", required = true)
    String port;
    
    @XmlElement(name = "queue", required = true)
    String queue;
    
    @XmlElement(name = "limit", required = true)
    int limit;

    public MOMConfiguration() {
    }

    public MOMConfiguration(String ip, String port, String queue, int limit) {
        this.ip = ip;
        this.port = port;
        this.queue = queue;
        this.limit = limit;
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

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    
    
    
}
