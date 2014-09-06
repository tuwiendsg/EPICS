/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity;

import java.util.List;

/**
 *
 * @author Jun
 */
public class MonitorProcess {
    List<MonitorAction> listOfMonitorActions;

    public MonitorProcess() {
    }

    public MonitorProcess(List<MonitorAction> listOfMonitorActions) {
        this.listOfMonitorActions = listOfMonitorActions;
    }

    public List<MonitorAction> getListOfMonitorActions() {
        return listOfMonitorActions;
    }

    public void setListOfMonitorActions(List<MonitorAction> listOfMonitorActions) {
        this.listOfMonitorActions = listOfMonitorActions;
    }
    
    
    
    
    
}
