/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.eda.ep;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "ElasticityProcess")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElasticityProcess {

    @XmlElement(name = "monitorProcess", required = true)
    MonitorProcess monitorProcess;
    
    @XmlElement(name = "listOfControlProcesses", required = true)
    List<ControlProcess> listOfControlProcesses;

    public ElasticityProcess() {
    }

    public ElasticityProcess(MonitorProcess monitorProcess, List<ControlProcess> listOfControlProcesses) {
        this.monitorProcess = monitorProcess;
        this.listOfControlProcesses = listOfControlProcesses;
    }

    public MonitorProcess getMonitorProcess() {
        return monitorProcess;
    }

    public void setMonitorProcess(MonitorProcess monitorProcess) {
        this.monitorProcess = monitorProcess;
    }

    public List<ControlProcess> getListOfControlProcesses() {
        return listOfControlProcesses;
    }

    public void setListOfControlProcesses(List<ControlProcess> listOfControlProcesses) {
        this.listOfControlProcesses = listOfControlProcesses;
    }
    
    
    
    
    
    
}
