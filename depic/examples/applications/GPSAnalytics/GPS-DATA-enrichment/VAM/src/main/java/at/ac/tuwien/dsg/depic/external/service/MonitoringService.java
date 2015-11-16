/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.external.service;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ExternalServiceRequest;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;


import at.ac.tuwien.dsg.depic.external.algorithm.vehicleArcMonitor;
import at.ac.tuwien.dsg.depic.external.util.Configuration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;



/**
 *
 * @author Jun
 */
public class MonitoringService {

    public MonitoringService() {
    }
    
    
    public double requestMonitoringService(String dataAssetRequest){
        
        
       
        DataPartitionRequest daRequest = null;
        
        try {
            daRequest = JAXBUtils.unmarshal(dataAssetRequest, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(MonitoringService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int noOfPartitions = getDataAssetNoOfPartition(dataAssetRequest);
        
        double avgAccuracy =0;
        
        for (int i=0;i<noOfPartitions;i++){
           
   
            
            String requestXML="";
            try {
                requestXML = JAXBUtils.marshal(daRequest, DataPartitionRequest.class);
            } catch (JAXBException ex) {
                Logger.getLogger(MonitoringService.class.getName()).log(Level.SEVERE, null, ex);
            }
            DataAsset daPartition= loadDataAssetPartition(requestXML);
            
            vehicleArcMonitor aim = new vehicleArcMonitor(daPartition);
            double accuracy = aim.monitor();
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
        
        Logger.getLogger(MonitoringService.class.getName()).log(Level.INFO, "NoOfPartition:" + noOfPartition);
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
            Logger.getLogger(MonitoringService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Logger.getLogger(MonitoringService.class.getName()).log(Level.INFO, "DataPartition:" + da.getPartition());
        
        return da;
    }
    
    private void openConnection(){
        Configuration cfg = new Configuration();

        String ip = cfg.getConfig("DATA.ASSET.LOADER.IP");
        String port = cfg.getConfig("DATA.ASSET.LOADER.PORT");
        String resource = cfg.getConfig("DATA.ASSET.LOADER.RESOURCE.OPEN");
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        ws.callPutMethod("");
    }
    
    private void closeConnection(){
        Configuration cfg = new Configuration();

        String ip = cfg.getConfig("DATA.ASSET.LOADER.IP");
        String port = cfg.getConfig("DATA.ASSET.LOADER.PORT");
        String resource = cfg.getConfig("DATA.ASSET.LOADER.RESOURCE.CLOSE");
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        ws.callPutMethod("");
    }
}
