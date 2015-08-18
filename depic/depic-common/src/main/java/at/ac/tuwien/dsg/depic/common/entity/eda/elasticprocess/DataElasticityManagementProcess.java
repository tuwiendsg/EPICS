/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "DataElasticityManagementProcess")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataElasticityManagementProcess {

    @XmlElement(name = "monitoringProcess", required = true)
    MonitoringProcess monitoringProcess;
    
    @XmlElement(name = "listOfAdjustmentProcesses", required = true)
    List<AdjustmentProcess> listOfAdjustmentProcesses;
    
    @XmlElement(name = "listOfResourceControlPlans", required = true)
    List<ResourceControlPlan> listOfResourceControlPlans;

    public DataElasticityManagementProcess() {
    }

    public DataElasticityManagementProcess(MonitoringProcess monitoringProcess, List<AdjustmentProcess> listOfAdjustmentProcesses, List<ResourceControlPlan> listOfResourceControlPlans) {
        this.monitoringProcess = monitoringProcess;
        this.listOfAdjustmentProcesses = listOfAdjustmentProcesses;
        this.listOfResourceControlPlans = listOfResourceControlPlans;
    }

    public MonitoringProcess getMonitoringProcess() {
        return monitoringProcess;
    }

    public void setMonitoringProcess(MonitoringProcess monitoringProcess) {
        this.monitoringProcess = monitoringProcess;
    }

    public List<AdjustmentProcess> getListOfAdjustmentProcesses() {
        return listOfAdjustmentProcesses;
    }

    public void setListOfAdjustmentProcesses(List<AdjustmentProcess> listOfAdjustmentProcesses) {
        this.listOfAdjustmentProcesses = listOfAdjustmentProcesses;
    }

    public List<ResourceControlPlan> getListOfResourceControlPlans() {
        return listOfResourceControlPlans;
    }

    public void setListOfResourceControlPlans(List<ResourceControlPlan> listOfResourceControlPlans) {
        this.listOfResourceControlPlans = listOfResourceControlPlans;
    }

    
}
