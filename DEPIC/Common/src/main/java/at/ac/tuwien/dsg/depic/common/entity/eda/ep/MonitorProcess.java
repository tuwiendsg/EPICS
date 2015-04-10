/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.common.entity.eda.ep;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
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
    List<MonitoringAction> listOfMonitorActions;

    public MonitorProcess() {
    }

    public MonitorProcess(List<MonitoringAction> listOfMonitorActions) {
        this.listOfMonitorActions = listOfMonitorActions;
    }

    public List<MonitoringAction> getListOfMonitorActions() {
        return listOfMonitorActions;
    }

    public void setListOfMonitorActions(List<MonitoringAction> listOfMonitorActions) {
        this.listOfMonitorActions = listOfMonitorActions;
    }
    
    
    
    
    
}
