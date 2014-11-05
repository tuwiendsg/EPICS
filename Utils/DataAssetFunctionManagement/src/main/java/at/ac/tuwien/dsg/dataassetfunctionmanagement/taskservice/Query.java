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
import at.ac.tuwien.dsg.dataassetfunctionmanagement.util.IOUtils;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.util.JAXBUtils;
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
        
        
        DataAsset da = new DataAsset("",dataItemList);
        
        
        return da;
    }
    
    
    
    
}
