/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.throughputmonitor.service;

import at.ac.tuwien.dsg.throughputmonitor.algorithm.ThroughputMonitor;

/**
 *
 * @author Jun
 */
public class ThroughputMonitorService {
    
    public String requestThroughputMonitorService(String dataAssetRequest){
        
        ThroughputMonitor throughputMonitor = new ThroughputMonitor();
        double throughput = throughputMonitor.monitorThroughput(dataAssetRequest);
        
        return String.valueOf(throughput);
    }
    
}
