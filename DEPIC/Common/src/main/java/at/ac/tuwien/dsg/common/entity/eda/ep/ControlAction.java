/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.eda.ep;

import at.ac.tuwien.dsg.common.entity.process.Parameter;
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
public class ControlAction {
    
    @XmlElement(name = "controlActionID")
    String controlActionID;
    
    @XmlElement(name = "controlActionName", required = true)
    String controlActionName;
    
    @XmlElement(name = "listOfParameters", required = true)
    List<Parameter> listOfParameters;
    
    @XmlElement(name = "incomming")
    String incomming;
    
    @XmlElement(name = "outgoing")
    String outgoing;
    
    public ControlAction() {
    }

    public ControlAction(String controlActionName, List<Parameter> listOfParameters) {
        this.controlActionName = controlActionName;
        this.listOfParameters = listOfParameters;
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

    public List<Parameter> getListOfParameters() {
        return listOfParameters;
    }

    public void setListOfParameters(List<Parameter> listOfParameters) {
        this.listOfParameters = listOfParameters;
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
