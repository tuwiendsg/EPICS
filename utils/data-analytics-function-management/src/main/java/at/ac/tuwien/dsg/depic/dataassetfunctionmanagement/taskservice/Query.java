/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.taskservice;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import at.ac.tuwien.dsg.depic.common.utils.MySqlConnectionManager;

import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.configuration.Configuration;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.store.MySqlDataAssetStore;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.util.IOUtils;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.util.JAXBUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;



/**
 *
 * @author Jun
 */
public class Query {
    private static final Query INSTANCE = new Query();
    
    public static Query getInstance() {
        return INSTANCE;
    }

    public void start(String param, String dawID) {
        System.out.println("Query Starting ...");
        //cassandra connection
        System.out.println("SQL: " + param);
        System.out.println("DAW ID : " + dawID);
               
        executeQueryStatement(param, dawID);

    }
    
    
    public void executeQueryStatement(String sql, String dawID) {

        String ip = Configuration.getConfig("DATA.SOURCE.IP");
        String port = Configuration.getConfig("DATA.SOURCE.PORT");
        String db = Configuration.getConfig("DATA.SOURCE.DATABASE");
        String user = Configuration.getConfig("DATA.SOURCE.USERNAME");
        String pass = Configuration.getConfig("DATA.SOURCE.PASSWORD");

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);  
        getDataAsset(rs, dawID);
 
    }
    
    
     public void getDataAsset(ResultSet rs, String dawID) {
         
        List<String> colsList = new ArrayList<String>();
        
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int noOfColumn = metaData.getColumnCount();
            
            System.out.println("No Of Columns: " + noOfColumn);
            
            
            for (int i=0;i<noOfColumn;i++){
                String colName = metaData.getColumnName(i+1);
                colsList.add(colName);
            }   
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            
            List<DataItem>  dataItemList = new ArrayList<DataItem>();
            int noOfPartitions = Integer.parseInt(Configuration.getConfig("DATA.PARTITION"));
            int partitionIndex =0;     
            int partitionCounter=0;
            
            
                while (rs.next()){
                

                List<DataAttribute> attsList = new ArrayList<DataAttribute>();
                for (String colName : colsList) {
                    
                    String colVal = rs.getString(colName);
                    DataAttribute dataAttribute = new DataAttribute(colName, colVal);
                    attsList.add(dataAttribute);
                }
                
                DataItem dataItem = new DataItem(attsList);
                dataItemList.add(dataItem);
                
                partitionCounter++;
                
                if (partitionCounter==noOfPartitions-1){
                    partitionCounter=0;
                    DataAsset da = new DataAsset(dawID, partitionIndex, dataItemList);
                    MySqlDataAssetStore das = new MySqlDataAssetStore();
                    das.storeDataAsset(da);
                    
                    partitionIndex++;
                    dataItemList = new ArrayList<DataItem>();
                    Logger.getLogger(Query.class.getName()).log(Level.INFO, "Partition Index: " + partitionIndex);  
                }                              
            }
             
            rs.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
     
     
    
  
    
}