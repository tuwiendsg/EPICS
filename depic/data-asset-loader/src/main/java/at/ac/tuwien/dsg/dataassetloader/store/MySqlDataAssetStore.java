/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.store;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetloader.util.ThroughputMonitor;
import at.ac.tuwien.dsg.depic.common.entity.runtime.MonitoringSession;
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
import javax.xml.bind.JAXBException;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Jun
 */
public class MySqlDataAssetStore implements DataStore {

    String ip;
    String port;
    String db;
    String user;
    String pass;

    public MySqlDataAssetStore() {
        Configuration config = new Configuration();
        ip = config.getConfig("EDA.REPOSITORY.MYSQL.IP");
        port = config.getConfig("EDA.REPOSITORY.MYSQL.PORT");
        db = config.getConfig("EDA.REPOSITORY.MYSQL.DB");
        user = config.getConfig("EDA.REPOSITORY.MYSQL.USER");
        pass = config.getConfig("EDA.REPOSITORY.MYSQL.PASS");

    }

    public void storeDataAsset(String daXML, String dafName, String partitionID) {

        InputStream daStream = new ByteArrayInputStream(daXML.getBytes(StandardCharsets.UTF_8));
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);

        String sql = "INSERT INTO DataAsset(daw_name,partitionID,da) VALUES ('" + dafName + "','" + partitionID + "',?)";

        List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
        listOfInputStreams.add(daStream);

        connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);

    }

    public void removeDataAsset(String dafName) {
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);
        String sql = "DElETE FROM DataAsset WHERE daw_name='" + dafName + "'";

        connectionManager.ExecuteUpdate(sql);
    }

    public String getDataAssetXML(String dafName, String partitionID) {

        String dafStr = "";
        try {
            InputStream inputStream = null;

            MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);

            String sql = "Select * from DataAsset where daw_name='" + dafName + "' and partitionID='" + partitionID + "'";

            ResultSet rs = connectionManager.ExecuteQuery(sql);

            try {
                while (rs.next()) {
                    inputStream = rs.getBinaryStream("da");
                }

                rs.close();
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

    /*

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
     Logger.getLogger(MySqlDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
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
     Logger.getLogger(MySqlDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
     }
        
        
     DataAsset da = new DataAsset(dafName,dataItemList);
        
        
     return da;
     }
     */
    public String copyDataAssetRepo(MonitoringSession monitoringSession, int dataAssetCounter) {

        System.out.println("Staring Copying Data ...");

        InputStream inputStream = null;

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);

        String sql = "Select * from DataAsset where daw_name='" + monitoringSession.getDataAssetID() + "' "
                + "and partitionID='" + dataAssetCounter + "'";

        System.out.println("sql: " + sql);

        ResultSet rs = connectionManager.ExecuteQuery(sql);

        try {
            while (rs.next()) {

                inputStream = rs.getBinaryStream("da");
                StringWriter writer = new StringWriter();
                String encoding = StandardCharsets.UTF_8.name();

                IOUtils.copy(inputStream, writer, encoding);
                String daPartitionStr = writer.toString();

                String name = monitoringSession.getEdaasName() + ";" + monitoringSession.getSessionID() + ";" + monitoringSession.getDataAssetID();

                DataAsset dataAssetPartition = JAXBUtils.unmarshal(daPartitionStr, DataAsset.class);
                dataAssetPartition.setDataAssetID(name);
                insertDataPartitionRepo(dataAssetPartition);

            }

            rs.close();
        } catch (Exception ex) {

        }
        return "1";
    }

    public String getDataPartitionRepo(DataPartitionRequest request) {

        String edaas = request.getEdaas();
        String customerID = request.getCustomerID();
        String dawID = request.getDataAssetID();
        String paritionID = request.getPartitionID();
        String daPartitionStr = "";

//        Configuration cfg = new Configuration();
//        String dataPath = cfg.getConfig("DATA.REPO.PATH");
//
//        String fileName = "kmeans-" + request.getDataAssetID() + ".data";
//
//        at.ac.tuwien.dsg.depic.common.utils.IOUtils iou = new at.ac.tuwien.dsg.depic.common.utils.IOUtils(dataPath);
//        daPartitionStr = iou.readData(fileName);

            System.out.println("GET DATA PARTITION ... ");

            InputStream inputStream = null;

            MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);
            
            String sql = "Select * from ProcessingDataAsset where edaas='"+edaas+"' AND customerID='"+customerID+"'  AND partitionID='"+paritionID+"' ";
            
           System.out.println(sql);
           
           
            ResultSet rs = connectionManager.ExecuteQuery(sql);

            try {
                while (rs.next()) {
                    inputStream = rs.getBinaryStream("da");
                    StringWriter writer = new StringWriter();
                    String encoding = StandardCharsets.UTF_8.name();

                    IOUtils.copy(inputStream, writer, encoding);
                    daPartitionStr = writer.toString();
                    
                 
                    }

                rs.close();
            } catch (Exception ex) {

            }
        return daPartitionStr;
    }

    public String getNoOfPartitionRepo(DataPartitionRequest request) {
//        
//        String edaas = request.getEdaas();
//        String customerID = request.getCustomerID();
//        String dawID = request.getDataAssetID();
//
//        String noOfPartition="";
//        
//
//
//            MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);
//
//            String sql = "Select COUNT(*) as counter from ProcessingDataAsset where edaas='"+edaas+"' AND customerID='"+customerID+"' AND daw_name='"+dawID+"'";
//
//            ResultSet rs = connectionManager.ExecuteQuery(sql);
//
//            try {
//                while (rs.next()) {
//                    
//                    noOfPartition = String.valueOf(rs.getInt("counter"));
//                            
//                    }
//
//                rs.close();
//            } catch (Exception ex) {
//
//            }

        return "1";
    }

    public void saveDataPartitionRepo(DataAsset dataAssetPartition) {

        System.out.println("SAVING DATA ASSET ...");

        String daXML="";
        try {
            daXML = JAXBUtils.marshal(dataAssetPartition, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(MySqlDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String[] strs = dataAssetPartition.getDataAssetID().split(";");
        String edaasName = strs[0];
        String customerID = strs[1];
        String dawName = strs[2];
        int partitionID = dataAssetPartition.getPartition();
        
        
        
        InputStream daStream = new ByteArrayInputStream(daXML.getBytes(StandardCharsets.UTF_8));
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);
        
        //String sql = "INSERT INTO ProcessingDataAsset(customerID,edaas,daw_name,partitionID,da) VALUES ('" + customerID + "','" + edaasName + "','" + dawName + "','"+partitionID+"',?)";
        String sql= "UPDATE ProcessingDataAsset SET da=? WHERE edaas='"+edaasName+"' AND customerID='"+customerID+"' AND daw_name='"+dawName+"' AND partitionID='"+partitionID+"'";
        List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
        listOfInputStreams.add(daStream);
        
        connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);
    }

    public void insertDataPartitionRepo(DataAsset dataAssetPartition) {

        System.out.println("Insert data asset");
        String daXML = "";
        try {
            daXML = JAXBUtils.marshal(dataAssetPartition, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(MySqlDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[] strs = dataAssetPartition.getDataAssetID().split(";");
        String edaasName = strs[0];
        String customerID = strs[1];
        String dawName = strs[2];
        int partitionID = dataAssetPartition.getPartition();

        InputStream daStream = new ByteArrayInputStream(daXML.getBytes(StandardCharsets.UTF_8));
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);

        String sql = "INSERT INTO ProcessingDataAsset(customerID,edaas,daw_name,partitionID,da) VALUES ('" + customerID + "','" + edaasName + "','" + dawName + "','" + partitionID + "',?)";
        //String sql= "UPDATE ProcessingDataAsset SET da=? WHERE edaas='"+edaasName+"' AND customerID='"+customerID+"' AND daw_name='"+dawName+"' AND partitionID='"+partitionID+"'";
        List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
        listOfInputStreams.add(daStream);

        connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);
    }

    @Override
    public void saveDataAsset(String daXML, String dafName, String partitionID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
