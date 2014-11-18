/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depictool.util;

import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorProcess;
import java.util.List;

/**
 *
 * @author Jun
 */
public class Logger {
    
    
    public void logMonitorProcesses(MonitorProcess monitorProcess){
        List<MonitorAction> listOfMonitorActions  = monitorProcess.getListOfMonitorActions();
        System.out.println("\nLOG MONITOR PROCESS");
        for (MonitorAction monitorAction : listOfMonitorActions){
            System.out.println("monitor action ID: " + monitorAction.getMonitorActionID());
        }
        
    }
    
    
    public void logControlProcesses(List<ControlProcess> listOfControlProcesses){
        
        System.out.println("\nLOG CONTROL PROCESS");
        
        
        for (ControlProcess controlProcess : listOfControlProcesses) {
        
        System.out.println("\n***");    
        List<ControlAction> listOfControlActions =  controlProcess.getListOfControlActions();
        
        for (ControlAction controlAction : listOfControlActions) {
            System.out.println("control action ID: " + controlAction.getControlActionID());
            
        }
        }
    }
    
}
