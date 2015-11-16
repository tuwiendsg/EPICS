/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.lsr.algorithm.service;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ExternalServiceRequest;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.lsr.algorithm.AccuracyAdjustmentBasedMultiLinearRegression;

import at.ac.tuwien.dsg.lsr.algorithm.AccuracyMonitoringBasedMultiLinearRegression;
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

    public void requestLSRService(ExternalServiceRequest controlRequest) {

        DataPartitionRequest daRequest = new DataPartitionRequest("dataplay", "c01", "", "0");

        DataAsset da = loadDataAssetPartition(daRequest);
        AccuracyAdjustmentBasedMultiLinearRegression linearRegression = new AccuracyAdjustmentBasedMultiLinearRegression();
        DataAsset dataAsset = linearRegression.adjustAccuracy(da);

        
        
        storeDataAssetPartition(dataAsset);
    }

    private DataAsset loadDataAssetPartition(DataPartitionRequest daRequest) {

//        Configuration cfg = new Configuration();
//
//        String ip = cfg.getConfig("DATA.ASSET.LOADER.IP");
//        String port = cfg.getConfig("DATA.ASSET.LOADER.PORT");
//        String resource = cfg.getConfig("DATA.ASSET.LOADER.RESOURCE.GET.PARTITION");
//        String daf = cfg.getConfig("DATA.ANALYTICS.FUNCTION");
        
        
        String ip = "localhost";
        String port = "8080";
        String resource = "/depic-orchestrator/rest/dataasset/repo/getpartition";
        String daf = "SELECT * FROM \"gold-prices\";";

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

    private void storeDataAssetPartition(DataAsset dataAsset) {

        
//
//        Configuration cfg = new Configuration();
//
//        String ip = cfg.getConfig("DATA.ASSET.LOADER.IP");
//        String port = cfg.getConfig("DATA.ASSET.LOADER.PORT");
//        String resource = cfg.getConfig("DATA.ASSET.LOADER.RESOURCE.STORE.PARTITION");
//        String tableName = cfg.getConfig("TABLE.NAME");
        
        
        String ip = "localhost";
        String port = "8080";
        String resource = "/depic-orchestrator/rest/dataasset/repo/savepartition";
        String tableName = "gold-prices";
        
        String dataAssetID =  "dataplay;" + tableName;
        
        dataAsset.setDataAssetID(dataAssetID);
        
        String dataAssetXML = "";
        try {
            dataAssetXML = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(LSRService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        ws.callPutMethod(dataAssetXML);
    }

}
