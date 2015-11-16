/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.mlr.algorithm.service;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ExternalServiceRequest;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;


import at.ac.tuwien.dsg.mlr.algorithm.VehicleArcAdjustment;
import at.ac.tuwien.dsg.mlr.util.Configuration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;



/**
 *
 * @author Jun
 */
public class ControlService {

    public ControlService() {
    }
    
    
    public void requestControlService(ExternalServiceRequest controlRequest){
             
        List<Parameter> listOfParameters = controlRequest.getListOfParameters();

        int attributeIndex = 3;          

        
        DataPartitionRequest dataRequest = new DataPartitionRequest(
                controlRequest.getEdaas(), 
                controlRequest.getCustomerID(), 
                controlRequest.getDataAssetID(),
                controlRequest.getDataAssetIndex());
        
        
        
        String dataRequestXML="";
        try {
            dataRequestXML = JAXBUtils.marshal(dataRequest, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(ControlService.class.getName()).log(Level.SEVERE, null, ex);
        }

   

       
            
            try {
                dataRequestXML = JAXBUtils.marshal(dataRequest, DataPartitionRequest.class);
            } catch (JAXBException ex) {
                Logger.getLogger(ControlService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DataAsset da = loadDataAssetPartition(dataRequestXML);
            
            
            
            VehicleArcAdjustment aic = new VehicleArcAdjustment(da, attributeIndex);
            DataAsset controlledDA  = aic.applyControl();
            
            String dataAssetId= controlRequest.getEdaas() + ";" + controlRequest.getCustomerID() +";" +controlRequest.getDataAssetID();
            controlledDA.setDataAssetID(dataAssetId);
            
            storeDataAssetPartition(controlledDA);
      
        
     

    }

    
    private int getDataAssetNoOfPartition(String darequestXML) {

       
        return 1;

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
            Logger.getLogger(ControlService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return da;
    }
    
    
    private void storeDataAssetPartition(DataAsset dataAsset){
        
        String dataAssetXML = "";
        try {
            dataAssetXML = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(ControlService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         Configuration cfg = new Configuration();

        String ip = cfg.getConfig("DATA.ASSET.LOADER.IP");
        String port = cfg.getConfig("DATA.ASSET.LOADER.PORT");
        String resource = cfg.getConfig("DATA.ASSET.LOADER.RESOURCE.STORE.PARTITION");
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        ws.callPutMethod(dataAssetXML);
        
    }
    
}
