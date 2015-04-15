/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess;

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

@XmlRootElement(name = "MonitoringProcess")
@XmlAccessorType(XmlAccessType.FIELD)
public class MonitoringProcess {
    
    @XmlElement(name = "listOfMonitoringActions", required = true)
    List<MonitoringAction> listOfMonitoringActions;

    public MonitoringProcess() {
    }

    public MonitoringProcess(List<MonitoringAction> listOfMonitoringActions) {
        this.listOfMonitoringActions = listOfMonitoringActions;
    }
    

    public List<MonitoringAction> getListOfMonitoringActions() {
        return listOfMonitoringActions;
    }

    public void setListOfMonitoringActions(List<MonitoringAction> listOfMonitoringActions) {
        this.listOfMonitoringActions = listOfMonitoringActions;
    }

    
    
    
    
    
    
}
