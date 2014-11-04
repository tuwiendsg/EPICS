/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetfunctionmanagement.taskservice;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAttribute;
import at.ac.tuwien.dsg.common.entity.eda.da.DataItem;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.util.IOUtils;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.util.MySqlConnectionManager;

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
      
         
        DataAsset da = executeQueryStatement(param);
        try {
            String daXML = JAXBUtils.marshal(da, DataAsset.class);
            
            System.out.println(daXML);
            IOUtils ioUtils = new IOUtils();
            ioUtils.writeData(daXML, dawID);
        
        } catch (JAXBException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        

    }
    
    
    public DataAsset executeQueryStatement(String sql) {

        String ip = Configuration.getConfig("DATA.SOURCE.IP");
        String port = Configuration.getConfig("DATA.SOURCE.PORT");
        String db = Configuration.getConfig("DATA.SOURCE.DATABASE");
        String user = Configuration.getConfig("DATA.SOURCE.USERNAME");
        String pass = Configuration.getConfig("DATA.SOURCE.PASSWORD");
        
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);
        
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        
        /*
        List<DataItem> listOfDataItems = new ArrayList<DataItem>();
        String vals = "";
        
        try {
            while (rs.next()) {
                
                int id = rs.getInt("id");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String global_active_power = rs.getString("global_active_power");
                double global_reactive_power = rs.getDouble("global_reactive_power");
                double voltage = rs.getDouble("voltage");
                double global_intensity = rs.getDouble("global_intensity");
                double sub_metering_1 = rs.getDouble("sub_metering_1");
                double sub_metering_2 = rs.getDouble("sub_metering_2");
                double sub_metering_3 = rs.getDouble("sub_metering_3");
                
                vals = vals + String.valueOf(id) +"\t" 
                        + date +"\t" 
                        + time +"\t" 
                        + String.valueOf(global_active_power) +"\t" 
                        + String.valueOf(global_reactive_power)  +"\t" 
                        + String.valueOf(voltage) +"\t" 
                        + String.valueOf(global_intensity)+"\t" 
                        + String.valueOf(sub_metering_1)+"\t" 
                        + String.valueOf(sub_metering_2)+"\t" 
                        + String.valueOf(sub_metering_3);
                
                UUID keyGen = UUID.randomUUID();
                
                DataAttribute attributeKey = new DataAttribute("key", keyGen.toString());
                DataAttribute attributeValue = new DataAttribute("value", vals);
                
                List<DataAttribute> listOfAttributes = new ArrayList<DataAttribute>();
                listOfAttributes.add(attributeKey);
                listOfAttributes.add(attributeValue);
                
                DataItem dataItem = new DataItem(listOfAttributes);
                
                listOfDataItems.add(dataItem);
                
                System.out.println(vals);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        DataAsset da = new DataAsset(listOfDataItems);
       */
        
        DataAsset da = getDataAsset(rs);
        
        
        return da;
    }
    
    
     public DataAsset getDataAsset(ResultSet rs) {
  
        List<DataItem>  dataItemList = new ArrayList<DataItem>();
         
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
            while (rs.next()){
                
                List<DataAttribute> attsList = new ArrayList<DataAttribute>();
                for (String colName : colsList) {
                    
                    
                    
                    
                    String colVal = rs.getString(colName);
                    DataAttribute dataAttribute = new DataAttribute(colName, colVal);
                    attsList.add(dataAttribute);
                }
                
                DataItem dataItem = new DataItem(attsList);
                dataItemList.add(dataItem);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Query.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        DataAsset da = new DataAsset(dataItemList);
        
        
        return da;
    }
    
    
    
    
}
