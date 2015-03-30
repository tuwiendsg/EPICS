/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.lsr.algorithm.service;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataControlRequest;
import at.ac.tuwien.dsg.common.entity.eda.da.DataPartitionRequest;
import at.ac.tuwien.dsg.common.entity.process.Parameter;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;


import at.ac.tuwien.dsg.lsr.algorithm.LinearRegression;
import at.ac.tuwien.dsg.lsr.util.Configuration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;



/**
 *
 * @author Jun
 */
public class LSRService {

    public LSRService() {
    }
    
    
    public void requestLSRService(DataControlRequest controlRequest){
             
        List<Parameter> listOfParameters = controlRequest.getListOfParameters();

        int attributeIndex = 2;
        
        
//        for (Parameter parameter : listOfParameters) {
//            if (parameter.getParaName().equals("attributeIndex")){     
//                attributeIndex = Integer.parseInt(parameter.getValue());   
//            }
//        }
        
        DataPartitionRequest dataRequest = new DataPartitionRequest(controlRequest.getEdaas(), controlRequest.getCustomerID(), controlRequest.getDataAssetID(), "");
        
        
        
        
        String dataRequestXML="";
        try {
            dataRequestXML = JAXBUtils.marshal(dataRequest, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(LSRService.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        RegistrationService registrationService = new RegistrationService(dataRequest.getCustomerID());
        registrationService.start();
        
        int noOfPartitions = getDataAssetNoOfPartition(dataRequestXML);

        for (int i = 0; i < noOfPartitions; i++) {
            
            dataRequest.setPartitionID(String.valueOf(i));
            
            try {
                dataRequestXML = JAXBUtils.marshal(dataRequest, DataPartitionRequest.class);
            } catch (JAXBException ex) {
                Logger.getLogger(LSRService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            DataAsset da = loadDataAssetPartition(dataRequestXML);
            LinearRegression linearRegression = new LinearRegression();
            DataAsset dataAsset = linearRegression.improveDataCompleteness(da, attributeIndex);
            storeDataAssetPartition(dataAsset);
        }
        
        registrationService.stop();

    }

    
    private int getDataAssetNoOfPartition(String darequestXML) {

        Configuration cfg = new Configuration();

        String ip = cfg.getConfig("DATA.ASSET.LOADER.IP");
        String port = cfg.getConfig("DATA.ASSET.LOADER.PORT");
        String resource = cfg.getConfig("DATA.ASSET.LOADER.RESOURCE.NO.PARTITION");

        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);

        String noOfPartition = ws.callPutMethod(darequestXML);
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
            Logger.getLogger(LSRService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return da;
    }
    
    
    private void storeDataAssetPartition(DataAsset dataAsset){
        
        String dataAssetXML = "";
        try {
            dataAssetXML = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(LSRService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         Configuration cfg = new Configuration();

        String ip = cfg.getConfig("DATA.ASSET.LOADER.IP");
        String port = cfg.getConfig("DATA.ASSET.LOADER.PORT");
        String resource = cfg.getConfig("DATA.ASSET.LOADER.RESOURCE.STORE.PARTITION");
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        ws.callPutMethod(dataAssetXML);
        
    }
    
}
