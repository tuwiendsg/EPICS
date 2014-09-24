/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depictool.entity.eda.ep;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ElasticityProcess {

    MonitorProcess monitorProcess;
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
