/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetfunctionmanagement.taskservice;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAttribute;
import at.ac.tuwien.dsg.common.entity.eda.da.DataItem;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.store.CassandraDataAssetStore;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.store.MySqlDataAssetStore;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class CqlQuery {
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
        
  
        ResultSet rs = CassandraDataAssetStore.executeCQLStatement(sql);
        
        getDataAsset(rs, dawID);
    }
    
    
    public void getDataAsset(ResultSet resultSet, String dawID) {
       
        if (!(resultSet == null || resultSet.isExhausted())) {

            List<Row> rows = resultSet.all();

            
            List<DataItem>  dataItemList = new ArrayList<DataItem>();
            int noOfPartitions = Integer.parseInt(Configuration.getConfig("DATA.PARTITION"));
            int partitionIndex =0;     
            int partitionCounter=0;
            
            
            
            
            for (Row row : rows) {
                List<DataAttribute> listOfDataAttributes = new ArrayList<DataAttribute>();
               
                    
                    DataAttribute id = new DataAttribute("id", row.getString("id"));
                    DataAttribute adID = new DataAttribute("adID", row.getString("adID"));
                    DataAttribute userID = new DataAttribute("userID", row.getString("userID"));
                    DataAttribute click = new DataAttribute("click", row.getString("click"));
                    DataAttribute data = new DataAttribute("data", row.getString("data"));
                    
                    
                    listOfDataAttributes.add(id);
                    listOfDataAttributes.add(adID);
                    listOfDataAttributes.add(userID);
                    listOfDataAttributes.add(click);
                    listOfDataAttributes.add(data);
                    
                    
                    DataItem dataItem = new DataItem(listOfDataAttributes);
                dataItemList.add(dataItem);
                
                partitionCounter++;
                
                if (partitionCounter==noOfPartitions-1){
                    partitionCounter=0;
                    DataAsset da = new DataAsset(dawID, partitionIndex, dataItemList);
                    
                    
                    CassandraDataAssetStore.insertDataAsset(da);
                    
                    partitionIndex++;
                    dataItemList = new ArrayList<DataItem>();
                    Logger.getLogger(Query.class.getName()).log(Level.INFO, "Partition Index: " + partitionIndex);  
                }                 
                    
                    
            }

        }

    }
}
