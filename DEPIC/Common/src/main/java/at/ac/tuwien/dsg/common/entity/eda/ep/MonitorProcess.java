/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.eda.ep;

import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorAction;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "MonitorProcess")
@XmlAccessorType(XmlAccessType.FIELD)
public class MonitorProcess {
    
    @XmlElement(name = "listOfMonitorActions", required = true)
    List<MonitorAction> listOfMonitorActions;

    public MonitorProcess() {
    }

    public MonitorProcess(List<MonitorAction> listOfMonitorActions) {
        this.listOfMonitorActions = listOfMonitorActions;
    }

    public List<MonitorAction> getListOfMonitorActions() {
        return listOfMonitorActions;
    }

    public void setListOfMonitorActions(List<MonitorAction> listOfMonitorActions) {
        this.listOfMonitorActions = listOfMonitorActions;
    }
    
    
    
    
    
}
