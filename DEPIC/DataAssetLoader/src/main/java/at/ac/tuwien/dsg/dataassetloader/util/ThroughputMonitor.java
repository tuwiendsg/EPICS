/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class ThroughputMonitor {
    private static final List<Operation> objList= new LinkedList<Operation>();
    private static final int maxBuffer = 5000;
    private static final int timeInterval = 1;

    
    public static void trackingLoad(String dataAssetRequestStr){
      
        long timeStamp = System.currentTimeMillis();
        Operation operation = new Operation(timeStamp, dataAssetRequestStr);
        if (objList.size() < maxBuffer) {
            objList.add(operation);
        } else {
            objList.remove(0);
            objList.add(operation);
        }
    }
    
    public static double calculateThroughput(String dataAssetRequestStr){
        
        int counter=0;
        long currentTimeStamp = System.currentTimeMillis();
        
        for (int i=objList.size()-1;i>=0;i--){
            
            Operation operation = objList.get(i);
            
            long timeStamp = operation.getTimeStamp();         
            long interval = currentTimeStamp-timeStamp;

            String daRequest = operation.getDataAssetRequest();
        
            if (interval < timeInterval * 1000) {
                if (dataAssetRequestStr.equals(daRequest)) {
                    counter++;
                }
            } else {
                
                break;
            }
        }
      
        double throughput = (counter*1.0)/(timeInterval*1.0);
       
        
        return throughput;
    }
    
    
    
    
}
