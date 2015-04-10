/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.common.entity.process;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.MetricCondition;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ControlAction;
import at.ac.tuwien.dsg.depic.common.entity.qor.MetricControlActions;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "MetricElasticityProcess")
@XmlAccessorType(XmlAccessType.FIELD)
public class MetricElasticityProcess {
    
    @XmlElement(name = "metricName", required = true)
    String metricName;
    
    @XmlElement(name = "listOfConditions", required = true)
    List<MetricCondition> listOfConditions;
    
    @XmlElement(name = "monitorAction", required = true)
    MonitoringAction monitorAction;
    
    @XmlElement(name = "listOfMetricControlActions", required = true)
    List<MetricControlActions> listOfMetricControlActions;

    public MetricElasticityProcess() {
    }

    public MetricElasticityProcess(String metricName, List<MetricCondition> listOfConditions, MonitoringAction monitorAction, List<MetricControlActions> listOfMetricControlActions) {
        this.metricName = metricName;
        this.listOfConditions = listOfConditions;
        this.monitorAction = monitorAction;
        this.listOfMetricControlActions = listOfMetricControlActions;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public List<MetricCondition> getListOfConditions() {
        return listOfConditions;
    }

    public void setListOfConditions(List<MetricCondition> listOfConditions) {
        this.listOfConditions = listOfConditions;
    }

    public MonitoringAction getMonitorAction() {
        return monitorAction;
    }

    public void setMonitorAction(MonitoringAction monitorAction) {
        this.monitorAction = monitorAction;
    }

    public List<MetricControlActions> getListOfMetricControlActions() {
        return listOfMetricControlActions;
    }

    public void setListOfMetricControlActions(List<MetricControlActions> listOfMetricControlActions) {
        this.listOfMetricControlActions = listOfMetricControlActions;
    }

    
   
    
    
    
}
