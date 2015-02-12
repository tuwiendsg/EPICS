/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataaccuracymonitor.service;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataPartitionRequest;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depic.dataaccuracymonitor.algorithm.DataAccuracyMonitor;
import at.ac.tuwien.dsg.depic.dataaccuracymonitor.util.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class DataAccuracyMonitorService {
    

    public double requestAccuracyMonitorService(String dataAssetRequest){
        
         
        DataPartitionRequest daRequest = null;
        
        try {
            daRequest = JAXBUtils.unmarshal(dataAssetRequest, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAccuracyMonitorService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        int noOfPartitions = getDataAssetNoOfPartition(dataAssetRequest);
        
        double avgAccuracy =0;
        
        for (int i=0;i<noOfPartitions;i++){
           
            daRequest.setPartitionID(String.valueOf(i));
            
            String requestXML="";
            try {
                requestXML = JAXBUtils.marshal(daRequest, DataPartitionRequest.class);
            } catch (JAXBException ex) {
                Logger.getLogger(DataAccuracyMonitorService.class.getName()).log(Level.SEVERE, null, ex);
            }
            DataAsset daPartition= loadDataAssetPartition(requestXML);
            
            DataAccuracyMonitor monitor = new DataAccuracyMonitor(daPartition);
            double accuracy = monitor.measureDataAccuracy();
            avgAccuracy+=accuracy;
        }
        
        avgAccuracy /= noOfPartitions;
        
        
        return avgAccuracy;
      
    }
    
    
    private int getDataAssetNoOfPartition(String darequestXML) {

        Configuration cfg = new Configuration();

        String ip = cfg.getConfig("DATA.ASSET.LOADER.IP");
        String port = cfg.getConfig("DATA.ASSET.LOADER.PORT");
        String resource = cfg.getConfig("DATA.ASSET.LOADER.RESOURCE.NO.PARTITION");

        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);

        String noOfPartition = ws.callPutMethod(darequestXML);
        
        Logger.getLogger(DataAccuracyMonitorService.class.getName()).log(Level.INFO, "NoOfPartition:" + noOfPartition);
        System.out.println("No Of Partitions Str: " + noOfPartition);
        
        return Integer.parseInt(noOfPartition);

    }
    
    private DataAsset loadDataAssetPartition(String requestXML){
        
         Configuration cfg = new Configuration();

        String ip = cfg.getConfig("DATA.ASSET.LOADER.IP");
        String port = cfg.getConfig("DATA.ASSET.LOADER.PORT");
        String resource = cfg.getConfig("DATA.ASSET.LOADER.RESOURCE.GET.PARTITION");
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        String rs = ws.callPutMethod(requestXML);
        
        DataAsset da=null;
        try {
            da = JAXBUtils.unmarshal(rs, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAccuracyMonitorService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Logger.getLogger(DataAccuracyMonitorService.class.getName()).log(Level.INFO, "DataPartition:" + da.getPartition());
        
        return da;
    }
    
}
