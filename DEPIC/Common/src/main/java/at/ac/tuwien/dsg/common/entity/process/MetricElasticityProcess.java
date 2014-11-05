/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.process;

import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.qor.TriggerActions;
import java.util.List;

/**
 *
 * @author Jun
 */
public class MetricElasticityProcess {
    String metricName;
    MonitorAction monitorAction;
    List<TriggerActions> listOfTriggerActions;

    public MetricElasticityProcess() {
    }

    public MetricElasticityProcess(String metricName, MonitorAction monitorAction, List<TriggerActions> listOfTriggerActions) {
        this.metricName = metricName;
        this.monitorAction = monitorAction;
        this.listOfTriggerActions = listOfTriggerActions;
    }

  
    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public MonitorAction getMonitorAction() {
        return monitorAction;
    }

    public void setMonitorAction(MonitorAction monitorAction) {
        this.monitorAction = monitorAction;
    }

    public List<TriggerActions> getListOfTriggerActions() {
        return listOfTriggerActions;
    }

    public void setListOfTriggerActions(List<TriggerActions> listOfTriggerActions) {
        this.listOfTriggerActions = listOfTriggerActions;
    }

    
    
    
}
