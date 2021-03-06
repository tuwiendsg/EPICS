/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataassetloader.datasource;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetloader.store.MySqlDataAssetStore;
import at.ac.tuwien.dsg.depic.common.entity.runtime.MonitoringSession;
import at.ac.tuwien.dsg.depic.common.utils.YamlUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class MySQLDataLoader implements DataLoader{
    
    
    public String loadDataAsset(DataAnalyticsFunction daf){
        
        String partitionNo="";
        try {
            
            String log = "DAF Name: " + daf.getName();
            Logger.getLogger(MySQLDataLoader.class.getName()).log(Level.INFO,log);
            
            String dafYaml = YamlUtils.marshallYaml(DataAnalyticsFunction.class, daf);
            String returnStr = requestToGetDataAsset(dafYaml);
            
            String[] strs = returnStr.split(";");
            String dataAssetID = "0"; //strs[0];
            
            for (int k=0; k<100;k++) {
            dataAssetID = String.valueOf(k);
            int numberOfPartitions = Integer.parseInt(strs[1]);
            partitionNo = String.valueOf(numberOfPartitions);
            
            for (int i = 0; i < numberOfPartitions; i++) {
                String dataAssetXml = getDataPartition(dataAssetID, String.valueOf(i));
                
                System.out.println("DATA ASSET LOADING ... ");
                DataAsset da = JAXBUtils.unmarshal(dataAssetXml, DataAsset.class);
                
                
                da.setDataAssetID(daf.getName()+"-"+dataAssetID);
                dataAssetXml = JAXBUtils.marshal(da, DataAsset.class);   
                
                MySqlDataAssetStore das = new MySqlDataAssetStore();
              
               // das.saveDataAsset(dataAssetXml, daf.getName()+"-"+dataAssetID, String.valueOf(i));
                
            }
            
            
            
            
            
            }
            
        } catch (JAXBException ex) {
            Logger.getLogger(MySQLDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return partitionNo;
    } 
    
    private String requestToGetDataAsset(String daw){
        
            Configuration config = new Configuration();
            String ip = config.getConfig("DAF.MANAGEMENT.IP");
            String port = config.getConfig("DAF.MANAGEMENT.PORT");
            String resource = config.getConfig("DAF.MANAGEMENT.RESOURCE.DAW")+ "/" + DBType.MYSQL.getDBType();
            
            RestfulWSClient rs = new RestfulWSClient(ip, port, resource);
            String returnStr = rs.callPutMethod(daw);
            
        return returnStr;
    }

    private String getDataPartition(String dataAssetID, String dataPartitionID) {

        Configuration config = new Configuration();
        String ip = config.getConfig("DAF.MANAGEMENT.IP");
        String port = config.getConfig("DAF.MANAGEMENT.PORT");
        String resource = config.getConfig("DAF.MANAGEMENT.RESOURCE.DATAASSET")+ "/" + DBType.MYSQL.getDBType();

      
        
        DataPartitionRequest dataPartitionRequest = new DataPartitionRequest("", "", dataAssetID, dataPartitionID);
        
        String dataPartitionXML = "";
        try {
            dataPartitionXML = JAXBUtils.marshal(dataPartitionRequest, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(MySQLDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        RestfulWSClient rs = new RestfulWSClient(ip, port, resource);
        String dataParstitionXML = rs.callPutMethod(dataPartitionXML);
        
        return dataParstitionXML;
    }
    
    
    
    
    public String copyDataAssetRepo(MonitoringSession monitoringSession, int dataAssetCounter){
        
        MySqlDataAssetStore das = new MySqlDataAssetStore();
        return das.copyDataAssetRepo(monitoringSession, dataAssetCounter);
        
        
    }
    
    public String getNoOfParitionRepo(DataPartitionRequest request){
        MySqlDataAssetStore das = new MySqlDataAssetStore();
        String noOfPartition =das.getNoOfPartitionRepo(request);
        return noOfPartition;
    }
    
    public String getDataPartitionRepo(DataPartitionRequest request){
        MySqlDataAssetStore das = new MySqlDataAssetStore();
        String daXML =das.getDataPartitionRepo(request);
        return daXML;
    }
    
    public void saveDataPartitionRepo(DataAsset dataAsset){
        MySqlDataAssetStore das = new MySqlDataAssetStore();
        das.saveDataPartitionRepo(dataAsset);
        
    }
    
    
    public void storeDataPartitionRepo(DataAsset dataAsset){
        
        String dataAssetXML = "";
        
        try {
            dataAssetXML = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(MySQLDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        MySqlDataAssetStore das = new MySqlDataAssetStore();
        das.storeDataAsset(dataAssetXML, dataAsset.getDataAssetID(), String.valueOf(dataAsset.getPartition()));
    }
 
}
