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

@XmlRootElement(name = "ControlAction")
@XmlAccessorType(XmlAccessType.FIELD)
public class ControlAction implements PrimitiveAction{
    
    @XmlElement(name = "controlActionID", required = true)
    String controlActionID;
    
    @XmlElement(name = "controlActionName", required = true)
    String controlActionName;
    
    @XmlElement(name = "artifact", required = true)
    Artifact artifact;
    
    @XmlElement(name = "costPerHour", required = true)
    double costPerHour;
    
    @XmlElement(name = "associatedQoRMetric", required = true)
    String associatedQoRMetric; 
    
    @XmlElement(name = "listOfPrerequisiteActionIDs", required = true)
    List<String> listOfPrerequisiteActionIDs;
    
    @XmlElement(name = "listOfTransitions", required = true)
    List<Transition> listOfTransitions;
    
    @XmlElement(name = "incomming")
    String incomming;
    
    @XmlElement(name = "outgoing")
    String outgoing;
    
    public ControlAction() {
    }

    public ControlAction(String controlActionID, String controlActionName, Artifact artifact, double costPerHour, String associatedQoRMetric, List<String> listOfPrerequisiteActionIDs, List<Transition> listOfTransitions) {
        this.controlActionID = controlActionID;
        this.controlActionName = controlActionName;
        this.artifact = artifact;
        this.costPerHour = costPerHour;
        this.associatedQoRMetric = associatedQoRMetric;
        this.listOfPrerequisiteActionIDs = listOfPrerequisiteActionIDs;
        this.listOfTransitions = listOfTransitions;
    }

    

    public String getControlActionID() {
        return controlActionID;
    }

    public void setControlActionID(String controlActionID) {
        this.controlActionID = controlActionID;
    }

    public String getControlActionName() {
        return controlActionName;
    }

    public void setControlActionName(String controlActionName) {
        this.controlActionName = controlActionName;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    public double getCostPerHour() {
        return costPerHour;
    }

    public void setCostPerHour(double costPerHour) {
        this.costPerHour = costPerHour;
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

    public List<Transition> getListOfTransitions() {
        return listOfTransitions;
    }

    public void setListOfTransitions(List<Transition> listOfTransitions) {
        this.listOfTransitions = listOfTransitions;
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
