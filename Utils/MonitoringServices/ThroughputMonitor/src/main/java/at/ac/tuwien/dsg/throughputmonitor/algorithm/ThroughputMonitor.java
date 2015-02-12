/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.throughputmonitor.algorithm;

import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.throughputmonitor.util.Configuration;



/**
 *
 * @author Jun
 */
public class ThroughputMonitor {
    private final double avgNoOfPartitions=10000;

    public ThroughputMonitor() {
     
    }
    
    
    public double monitorThroughput(String dataAssetReqest){
        
        Configuration cfg = new Configuration();
        String ip = cfg.getConfig("DATA.ASSET.LOADER.IP");
        String port = cfg.getConfig("DATA.ASSET.LOADER.PORT");
        String resource = cfg.getConfig("DATA.ASSET.LOADER.RESOURCE.THROUGHPUT");
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        String rs = ws.callPutMethod(dataAssetReqest);
        
        double throughput_pps = Double.parseDouble(rs);        
        double throughput_dah = avgNoOfPartitions*throughput_pps/3600.0;
  
        return throughput_dah;
    }
 
    
}
