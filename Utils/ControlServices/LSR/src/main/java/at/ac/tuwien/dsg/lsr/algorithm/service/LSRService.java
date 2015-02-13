/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.lsr.algorithm.service;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataControlRequest;
import at.ac.tuwien.dsg.common.entity.process.Parameter;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depic.dataaccuracymonitor.util.Configuration;

import at.ac.tuwien.dsg.lsr.algorithm.LinearRegression;
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
        
        double datacompleteness = 0;
        String[] attributeIndice = null;        
        
        for (Parameter parameter : listOfParameters) {
            
            if (parameter.getParaName().equals("expectedDataCompleteness")) {
                datacompleteness = Double.parseDouble(parameter.getValue());
            }
            
            if (parameter.getParaName().equals("attributeIndice")){
                String params = parameter.getValue();
                attributeIndice = params.split(";");
                
            }
        }
        
        LinearRegression linearRegression = new LinearRegression();
        DataAsset dataAsset= linearRegression.improveDataCompleteness(da, datacompleteness, attributeIndice);
        das.storeDataAsset(dataAsset);
        
        
        
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
    
    
    private void storeDataAssetPartition(String dataAssetXML){
        
        
    }
    
}
