/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.tooling.edo.metric;

/**
 *
 * @author Jun
 */
public class MonitorConf {
    String monitorURI;
    int monitorInterval;

    public MonitorConf() {
    }

    public MonitorConf(String monitorURI, int monitorInterval) {
        this.monitorURI = monitorURI;
        this.monitorInterval = monitorInterval;
    }
    
    
    

    public String getMonitorURI() {
        return monitorURI;
    }

    public void setMonitorURI(String monitorURI) {
        this.monitorURI = monitorURI;
    }

    public int getMonitorInterval() {
        return monitorInterval;
    }

    public void setMonitorInterval(int monitorInterval) {
        this.monitorInterval = monitorInterval;
    }

    
   
    
    
}
