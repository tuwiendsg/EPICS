/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.deployment.Hung;

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
    
    String artifact;
    String artifactType;
    
    // call this API, the action is execute. This must be filled after deployment 
    // to have the real IP of the VM which contain the artifact of the action
    String apiEndpoint;	
    
    // call this API, a new instance of the artifact is created.
    // This will be fill after SALSA description is generated.
    String deploymentEndpoint;

    public DeployAction() {
    }
    
    public DeployAction(String actionID, String actionName, String artifactType, String artifactRef) {
        this.actionID = actionID;
        this.actionName = actionName;
        this.artifactType = artifactType;
        this.artifact = artifactRef;
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

    public String getArtifactType() {
		return artifactType;
	}

	public void setArtifactType(String artifactType) {
		this.artifactType = artifactType;
	}

	public String getApiEndpoint() {
		return apiEndpoint;
	}

	public void setApiEndpoint(String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
	}

	public String getArtifact() {
        return artifact;
    }

    @XmlElement (name ="artifact")
    public void setArtifact(String artifact) {
        this.artifact = artifact;
    }

	public String getDeploymentEndpoint() {
		return deploymentEndpoint;
	}

	public void setDeploymentEndpoint(String deploymentEndpoint) {
		this.deploymentEndpoint = deploymentEndpoint;
	}
    
    
    
    
}
