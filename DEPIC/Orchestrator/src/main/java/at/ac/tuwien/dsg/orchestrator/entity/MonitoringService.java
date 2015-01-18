/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "MonitoringService")
@XmlAccessorType(XmlAccessType.FIELD)
public class MonitoringService {
    
    @XmlElement(name = "actionID", required = true)
    String actionID;
    
    @XmlElement(name = "uri", required = true)
    String uri;

    public MonitoringService() {
    }

    public MonitoringService(String actionID, String uri) {
        this.actionID = actionID;
        this.uri = uri;
    }

    public String getActionID() {
        return actionID;
    }

    public void setActionID(String actionID) {
        this.actionID = actionID;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
    
    
}
