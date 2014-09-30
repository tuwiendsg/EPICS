/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity.process;

import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorAction;
import java.util.List;

/**
 *
 * @author Jun
 */
public class MetricElasticityProcess {
    String metricName;
    MonitorAction monitorAction;
    List<ControlAction> listOfControlActions;

    public MetricElasticityProcess() {
    }

    public MetricElasticityProcess(String metricName, MonitorAction monitorAction, List<ControlAction> listOfControlActions) {
        this.metricName = metricName;
        this.monitorAction = monitorAction;
        this.listOfControlActions = listOfControlActions;
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

    public List<ControlAction> getListOfControlActions() {
        return listOfControlActions;
    }

    public void setListOfControlActions(List<ControlAction> listOfControlActions) {
        this.listOfControlActions = listOfControlActions;
    }

    
    
    
}
