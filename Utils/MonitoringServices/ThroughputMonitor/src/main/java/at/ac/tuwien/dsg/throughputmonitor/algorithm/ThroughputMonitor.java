/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.throughputmonitor.algorithm;

import at.ac.tuwien.dsg.externalserviceutils.IOUtils;


/**
 *
 * @author Jun
 */
public class ThroughputMonitor {
    private final double avgInterval=3;

    public ThroughputMonitor() {
     
    }
    
    
    public double monitorThroughput(String daID){
        
        String[] timelines  = getTime(daID);
        
        long currentTime = System.currentTimeMillis();
        long timeMark = currentTime - 3*1000;
        
        int timePointCounter = 0;
        double sumOfSentData = 0;
        for (int i=1;i<timelines.length;i++){
           
            
            String[] strs = timelines[i].split(",");
            long timepoint = Long.parseLong(strs[0]);
            double sentdata = Double.parseDouble(strs[1]);
            
            if (timepoint>timeMark) {
                timePointCounter++;
                sumOfSentData += sentdata; 
            }    
        }
        
        double throughput = timePointCounter/avgInterval;
        
        return throughput;
    }
 
    
    public String[] getTime(String daID){
        IOUtils iou = new IOUtils();
        String timeData = iou.readData("tp_time_" + daID);
        
        String[] times = timeData.split(";");
       
        return times;
    }
}
