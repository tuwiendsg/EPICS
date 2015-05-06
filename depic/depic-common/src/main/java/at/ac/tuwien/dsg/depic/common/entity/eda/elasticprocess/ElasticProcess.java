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

@XmlRootElement(name = "ElasticProcess")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElasticProcess {

    @XmlElement(name = "monitoringProcess", required = true)
    MonitoringProcess monitoringProcess;
    
    @XmlElement(name = "listOfControlProcesses", required = true)
    List<AdjustmentProcess> listOfControlProcesses;

    public ElasticProcess() {
    }

    public ElasticProcess(MonitoringProcess monitoringProcess, List<AdjustmentProcess> listOfControlProcesses) {
        this.monitoringProcess = monitoringProcess;
        this.listOfControlProcesses = listOfControlProcesses;
    }

    public MonitoringProcess getMonitoringProcess() {
        return monitoringProcess;
    }

    public void setMonitoringProcess(MonitoringProcess monitoringProcess) {
        this.monitoringProcess = monitoringProcess;
    }

    public List<AdjustmentProcess> getListOfControlProcesses() {
        return listOfControlProcesses;
    }

    public void setListOfControlProcesses(List<AdjustmentProcess> listOfControlProcesses) {
        this.listOfControlProcesses = listOfControlProcesses;
    }

    
    
    
    
    
}
