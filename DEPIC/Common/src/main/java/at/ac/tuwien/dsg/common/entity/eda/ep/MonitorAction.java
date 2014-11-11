/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.eda.ep;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "MonitorAction")
@XmlAccessorType(XmlAccessType.FIELD)
public class MonitorAction {
    
    @XmlElement(name = "monitorActionID", required = true)
    String monitorActionID;

    public MonitorAction() {
    }
    
    public MonitorAction(String monitorActionID) {
        this.monitorActionID = monitorActionID;
    }

    public String getMonitorActionID() {
        return monitorActionID;
    }

    public void setMonitorActionID(String monitorActionID) {
        this.monitorActionID = monitorActionID;
    }
    
    
    
    
    
}
