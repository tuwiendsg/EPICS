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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public void saveDataAsset(DataAsset da, String dafName) {

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);

        List<DataItem> dataItems = da.getListOfDataItems();

        for (DataItem dataItem : dataItems) {
            String atts = "";
            String vals = "";
            List<DataAttribute> dataAttributes = dataItem.getListOfAttributes();

            for (DataAttribute dataAttribute : dataAttributes) {
                atts += dataAttribute.getAttributeName() + ",";
                vals += dataAttribute.getAttributeValue() + ",";
            }

            atts = atts.substring(0, atts.length() - 1);
            vals = vals.substring(0, vals.length() - 1);

            String sql = "INSERT INTO '" + dafName + "' (" + atts + ") VALUES (" + vals + ") ";
            connectionManager.ExecuteUpdate(sql);

        }
    }

    public DataAsset getDataAsset(String dafName) {
  
        List<DataItem>  dataItemList = new ArrayList<>();
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);
        String sql = "SELECT * FROM " + dafName;
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        
        List<String> colsList = new ArrayList<>();
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int noOfColumn = metaData.getColumnCount();
            
            for (int i=0;i<noOfColumn;i++){
                String colName = metaData.getColumnName(0);
                colsList.add(colName);
            }   
        } catch (SQLException ex) {
            Logger.getLogger(DataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            while (rs.next()){
                
                List<DataAttribute> attsList = new ArrayList<>();
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
        
        
        DataAsset da = new DataAsset(dataItemList);
        
        
        return da;
    }

}
