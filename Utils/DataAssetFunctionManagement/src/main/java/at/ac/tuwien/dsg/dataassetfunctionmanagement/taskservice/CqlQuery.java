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
    private static final CqlQuery INSTANCE = new CqlQuery();
    
    public static CqlQuery getInstance() {
        return INSTANCE;
    }

    public void start(String param, String dawID) {
        System.out.println("CQL query Starting ...");
        //cassandra connection
        System.out.println("CQL: " + param);
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

                String data = row.getString("data");
                String[] strs = data.split("\t");

                List<DataAttribute> listOfDataAttributes = new ArrayList<DataAttribute>();

                for (int i = 0; i < strs.length; i++) {
                    DataAttribute dataAttribute = new DataAttribute("c" + i, strs[i]);
                    listOfDataAttributes.add(dataAttribute);
                }

                DataItem dataItem = new DataItem(listOfDataAttributes);
                dataItemList.add(dataItem);
                
                partitionCounter++;
                
                if (partitionCounter==noOfPartitions-1){
                    partitionCounter=0;
                    DataAsset da = new DataAsset(dawID, partitionIndex, dataItemList);
                    
                    
                    CassandraDataAssetStore.insertDataAsset(da);
                    
                    partitionIndex++;
                    dataItemList = new ArrayList<DataItem>();
                    Logger.getLogger(CqlQuery.class.getName()).log(Level.INFO, "Partition Index: " + partitionIndex);  
                }                 
                    
                    
            }

        }

    }
}
