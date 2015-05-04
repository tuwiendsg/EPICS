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

@XmlRootElement(name = "PrimitiveActionMetadata")
@XmlAccessorType(XmlAccessType.FIELD)
public class PrimitiveActionMetadata {
    
    @XmlElement(name = "listOfControlActions", required = true)
    List<AdjustmentAction> listOfControlActions;
    
    @XmlElement(name = "listOfMonitoringActions", required = true)
    List<MonitoringAction> listOfMonitoringActions;

    public PrimitiveActionMetadata() {
    }

    public PrimitiveActionMetadata(List<AdjustmentAction> listOfControlActions, List<MonitoringAction> listOfMonitoringActions) {
        this.listOfControlActions = listOfControlActions;
        this.listOfMonitoringActions = listOfMonitoringActions;
    }

    public List<AdjustmentAction> getListOfControlActions() {
        return listOfControlActions;
    }

    public void setListOfControlActions(List<AdjustmentAction> listOfControlActions) {
        this.listOfControlActions = listOfControlActions;
    }

    public List<MonitoringAction> getListOfMonitoringActions() {
        return listOfMonitoringActions;
    }

    public void setListOfMonitoringActions(List<MonitoringAction> listOfMonitoringActions) {
        this.listOfMonitoringActions = listOfMonitoringActions;
    }
    
    
    
}
