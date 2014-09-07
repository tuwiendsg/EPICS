/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.deployment;

/**
 *
 * @author Jun
 */
public class DeploymentDescription {
    String actionID;
    String actionName;
    String ip;
    String port;
    String artifact;

    public DeploymentDescription() {
    }

    public DeploymentDescription(String actionID, String actionName, String ip, String port, String artifact) {
        this.actionID = actionID;
        this.actionName = actionName;
        this.ip = ip;
        this.port = port;
        this.artifact = artifact;
    }

    public String getActionID() {
        return actionID;
    }

    public void setActionID(String actionID) {
        this.actionID = actionID;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
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

    public String getArtifact() {
        return artifact;
    }

    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }
    
    
    
}
