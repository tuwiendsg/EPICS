/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.dastore;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAttribute;
import at.ac.tuwien.dsg.common.entity.eda.da.DataItem;
import at.ac.tuwien.dsg.common.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Jun
 */
public class DataAssetStore {

    String ip;
    String port;
    String db;
    String user;
    String pass;

    public DataAssetStore() {
        Configuration config = new Configuration();
        ip = config.getConfig("EDA.REPOSITORY.IP");
        port = config.getConfig("EDA.REPOSITORY.PORT");
        db = config.getConfig("EDA.REPOSITORY.DB");
        user = config.getConfig("EDA.REPOSITORY.USER");
        pass = config.getConfig("EDA.REPOSITORY.PASS");

    }

    public void saveDataAsset(String daXML, String dafName) {

        InputStream daStream = new ByteArrayInputStream(daXML.getBytes(StandardCharsets.UTF_8));
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);

        String sql = "INSERT INTO DataAsset(daw_name,da) VALUES ('" + dafName + "',?)";
        connectionManager.ExecuteUpdateBlob(sql, daStream);
  
        
                
    }
    
    public void removeDataAsset(String dafName) {
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);
        String sql = "DElETE FROM DataAsset WHERE daw_name='"+dafName+"'";

        connectionManager.ExecuteUpdate(sql);
    }
    
      public String getDataAssetXML(String dafName) {

        String dafStr = "";
        try {
            InputStream inputStream = null;

      
         MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);

            String sql = "Select * from DataAsset where daw_name='" + dafName + "'";

            ResultSet rs = connectionManager.ExecuteQuery(sql);

            try {
                while (rs.next()) {
                    inputStream = rs.getBinaryStream("da");
                }
            } catch (SQLException ex) {
                
            }

            StringWriter writer = new StringWriter();
            String encoding = StandardCharsets.UTF_8.name();

            IOUtils.copy(inputStream, writer, encoding);
            dafStr = writer.toString();
                

        } catch (IOException ex) {
          
        }

        return dafStr;
    }
    
   

    public DataAsset getDataAsset(String dafName) {
  
        List<DataItem>  dataItemList = new ArrayList<DataItem>();
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);
        String sql = "SELECT * FROM " + dafName;
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        
        List<String> colsList = new ArrayList<String>();
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int noOfColumn = metaData.getColumnCount();
            
            for (int i=0;i<noOfColumn;i++){
                String colName = metaData.getColumnName(i+1);
                colsList.add(colName);
            }   
        } catch (SQLException ex) {
            Logger.getLogger(DataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        DataAsset da = new DataAsset(dafName,dataItemList);
        
        
        return da;
    }

}
