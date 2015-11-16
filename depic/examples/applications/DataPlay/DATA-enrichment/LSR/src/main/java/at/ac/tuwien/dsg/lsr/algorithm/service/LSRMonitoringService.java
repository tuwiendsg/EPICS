/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.lsr.algorithm.service;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.lsr.algorithm.AccuracyMonitoringBasedMultiLinearRegression;
import at.ac.tuwien.dsg.lsr.util.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class LSRMonitoringService {
    
    
    public double requestMonitorAccuracyOfTrend(String dataAssetRequest){
        
        DataPartitionRequest daRequest = null;
        
        try {
            daRequest = JAXBUtils.unmarshal(dataAssetRequest, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(LSRMonitoringService.class.getName()).log(Level.SEVERE, null, ex);
        }
       // RegistrationService registrationService = new RegistrationService(daRequest.getDataAssetID());
       // registrationService.start();
        
            
           
            DataAsset daPartition= loadDataAssetPartition(daRequest);
            
            AccuracyMonitoringBasedMultiLinearRegression linearRegression = new AccuracyMonitoringBasedMultiLinearRegression();
            double avgAccuracy = linearRegression.monitor(daPartition);
            
            
  //      registrationService.stop();
   
        return avgAccuracy;
        
        
    }
   
    private DataAsset loadDataAssetPartition(DataPartitionRequest daRequest) {

//        Configuration cfg = new Configuration();

//        String ip = cfg.getConfig("DATA.ASSET.LOADER.IP");
//        String port = cfg.getConfig("DATA.ASSET.LOADER.PORT");
//        String resource = cfg.getConfig("DATA.ASSET.LOADER.RESOURCE.GET.PARTITION");
//        String daf = cfg.getConfig("DATA.ANALYTICS.FUNCTION");
        
        String ip = "localhost";
        String port = "8080";
        String resource = "/depic-orchestrator/rest/dataasset/repo/getpartition";
        String daf = "SELECT * FROM \"jobs\";";
        
        
        
        

        daRequest.setDataAssetID(daf);
        String requestXML = "";
        try {
            requestXML = JAXBUtils.marshal(daRequest, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(LSRMonitoringService.class.getName()).log(Level.SEVERE, null, ex);
        }
  
            
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        DataAsset da = ws.callPutMethod(requestXML);
        
     
        
        Logger.getLogger(LSRMonitoringService.class.getName()).log(Level.INFO, "DataPartition:" + da.getPartition());
        
        return da;
    }
    
    
    
    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
