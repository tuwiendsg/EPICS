/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.deployment;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement (name = "DeployAction")
public class DeployAction {
    String actionID;
    String actionName;
    String ip;
    String port;
    String artifact;

    public DeployAction() {
    }

    public DeployAction(String actionID, String actionName, String ip, String port, String artifact) {
        this.actionID = actionID;
        this.actionName = actionName;
        this.ip = ip;
        this.port = port;
        this.artifact = artifact;
    }

    public String getActionID() {
        return actionID;
    }

    @XmlElement (name ="actionID")
    public void setActionID(String actionID) {
        this.actionID = actionID;
    }

    public String getActionName() {
        return actionName;
    }

    @XmlElement (name ="actionName")
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getIp() {
        return ip;
    }

    @XmlElement (name ="ip")
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    @XmlElement (name ="port")
    public void setPort(String port) {
        this.port = port;
    }

    public String getArtifact() {
        return artifact;
    }

    @XmlElement (name ="artifact")
    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }
    
    
    
    
}
