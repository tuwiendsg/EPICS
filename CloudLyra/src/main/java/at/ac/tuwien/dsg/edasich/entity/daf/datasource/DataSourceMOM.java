/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.entity.daf.datasource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "dataSourceMom")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataSourceMOM {
    
    @XmlElement(name = "ip", required = true)
    String ip;
    
    @XmlElement(name = "port", required = true)
    String port;
    
    @XmlElement(name = "queue", required = true)
    String queue;

    public DataSourceMOM() {
    }

    public DataSourceMOM(String ip, String port, String queue) {
        this.ip = ip;
        this.port = port;
        this.queue = queue;
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

    
    
    
}
