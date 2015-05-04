/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.common.entity.primitiveaction;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "AdjustmentAction")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdjustmentAction implements PrimitiveAction{
    
    @XmlElement(name = "controlActionID", required = true)
    String actionID;
    
    @XmlElement(name = "controlActionName", required = true)
    String actionName;
    
    @XmlElement(name = "artifact", required = true)
    Artifact artifact;
    
    @XmlElement(name = "associatedQoRMetric", required = true)
    String associatedQoRMetric; 
    
    @XmlElement(name = "listOfPrerequisiteActionIDs", required = true)
    List<String> listOfPrerequisiteActionIDs;
    
    @XmlElement(name = "listOfAdjustmentCases", required = true)
    List<AdjustmentCase> listOfAdjustmentCases;
    
    @XmlElement(name = "incomming")
    String incomming;
    
    @XmlElement(name = "outgoing")
    String outgoing;
    
    public AdjustmentAction() {
    }

    public AdjustmentAction(String actionID, String actionName, Artifact artifact, String associatedQoRMetric, List<String> listOfPrerequisiteActionIDs, List<AdjustmentCase> listOfAdjustmentCases) {
        this.actionID = actionID;
        this.actionName = actionName;
        this.artifact = artifact;
        this.associatedQoRMetric = associatedQoRMetric;
        this.listOfPrerequisiteActionIDs = listOfPrerequisiteActionIDs;
        this.listOfAdjustmentCases = listOfAdjustmentCases;
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

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public String getAssociatedQoRMetric() {
        return associatedQoRMetric;
    }

    public void setAssociatedQoRMetric(String associatedQoRMetric) {
        this.associatedQoRMetric = associatedQoRMetric;
    }

    public List<String> getListOfPrerequisiteActionIDs() {
        return listOfPrerequisiteActionIDs;
    }

    public void setListOfPrerequisiteActionIDs(List<String> listOfPrerequisiteActionIDs) {
        this.listOfPrerequisiteActionIDs = listOfPrerequisiteActionIDs;
    }

    public List<AdjustmentCase> getListOfAdjustmentCases() {
        return listOfAdjustmentCases;
    }

    public void setListOfAdjustmentCases(List<AdjustmentCase> listOfAdjustmentCases) {
        this.listOfAdjustmentCases = listOfAdjustmentCases;
    }

    public String getIncomming() {
        return incomming;
    }

    public void setIncomming(String incomming) {
        this.incomming = incomming;
    }

    public String getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(String outgoing) {
        this.outgoing = outgoing;
    }

    
    
    
}
