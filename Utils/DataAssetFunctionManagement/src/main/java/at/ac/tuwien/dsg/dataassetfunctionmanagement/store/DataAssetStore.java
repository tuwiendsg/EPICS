/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetfunctionmanagement.store;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.taskservice.Query;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.util.JAXBUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
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

    private MySqlConnectionManager connectionManager;

    public DataAssetStore() {
        String ip = Configuration.getConfig("DATA.SOURCE.IP");
        String port = Configuration.getConfig("DATA.SOURCE.PORT");
        String db = Configuration.getConfig("DATA.SOURCE.DATABASE");
        String user = Configuration.getConfig("DATA.SOURCE.USERNAME");
        String pass = Configuration.getConfig("DATA.SOURCE.PASSWORD");

        connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);
    }

    public void storeDataAsset(DataAsset da) {

        try {
            String daXML = JAXBUtils.marshal(da, DataAsset.class);

            Logger.getLogger(DataAssetStore.class.getName()).log(Level.INFO, "Prepare to Store: " + daXML);
            InputStream daStream = new ByteArrayInputStream(daXML.getBytes(StandardCharsets.UTF_8));
            List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
            listOfInputStreams.add(daStream);

            String sql = "INSERT INTO DataAsset (dataAssetID, dataPartitionID, data) VALUES ('" + da.getName() + "'," + da.getPartition() + ",?)";

            connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);

        } catch (Exception ex) {
            Logger.getLogger(DataAssetStore.class.getName()).log(Level.SEVERE, ex.toString());
        }

    }

    public void updateDataAsset(String dataAssetID, String dataParitionID, String data) {
        InputStream dataStream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
        listOfInputStreams.add(dataStream);

        String sql = "UPDATE DataAsset SET data=? WHERE dataAssetID='" + dataAssetID + "' AND dataPartitionID='" + dataParitionID + "'";
        connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);

    }

    public ResultSet getDataAssetByID(String dawID) {

        String sql = "SELECT * FROM DataAsset WHERE dataAssetID='" + dawID + "'";
        ResultSet rs = connectionManager.ExecuteQuery(sql);
      
        return rs;
    }
    
    public int getNumberOfPartitionsByDataAssetID(String dataAssetID){
        
        String sql ="Select COUNT(dataPartitionID) as counter FROM DataAsset WHERE dataAssetID='"+dataAssetID+"'";
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        int counter=0;
        
        try {
            while (rs.next()){
                counter = rs.getInt("counter");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return counter;
    }
    
    public String getDataPartition(String dataAssetID, String dataPartitionID){
        
        String daXML="";
        String sql ="SELECT * FROM DataAsset WHERE dataAssetID='"+dataAssetID+"' AND dataPartitionID='"+dataPartitionID+"'";
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        
        try {
            while (rs.next()){
                
                InputStream inputStream = rs.getBinaryStream("data");

                StringWriter writer = new StringWriter();
                String encoding = StandardCharsets.UTF_8.name();
                org.apache.commons.io.IOUtils.copy(inputStream, writer, encoding);
                daXML = writer.toString();
            }
            
            rs.close();
        } catch (Exception ex) {
            Logger.getLogger(DataAssetStore.class.getName()).log(Level.SEVERE, ex.toString());
        }
        
        return daXML;
    }
    
    
}
