/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.store;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAttribute;
import at.ac.tuwien.dsg.common.entity.eda.da.DataItem;
import at.ac.tuwien.dsg.common.entity.eda.da.DataPartitionRequest;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetloader.util.ThroughputMonitor;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.ExecutionInfo;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.util.ArrayList;
import java.util.List;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

import org.slf4j.LoggerFactory;

/**
 *
 * @author Jun
 */
public class CassandraDataAssetStore  {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(CassandraDataAssetStore.class);
    private Session session;
    private String ip;
    private int port;
    private String keyspace;
  


    
    private void doConfig() {
        if (ip == null) {
            Configuration cfg = new Configuration();
            ip = cfg.getConfig("EDA.REPOSITORY.CASSANDRA.IP");
            port = Integer.parseInt(cfg.getConfig("EDA.REPOSITORY.CASSANDRA.PORT"));
            keyspace = cfg.getConfig("EDA.REPOSITORY.CASSANDRA.KEY");
        }
    }

   

    public void openConnection() {
        doConfig();
        
        log.info("Connecting to Cassandra at " + ip + ":" + port);
        if (session == null) {
            Cluster cluster = Cluster.builder()
                    .withPort(port)
                    .addContactPoint(ip).build();
            
            cluster.getConfiguration().getSocketOptions().setConnectTimeoutMillis(10000000);
            cluster.getConfiguration().getSocketOptions().setReadTimeoutMillis(10000000);
        
            
            Metadata metadata = cluster.getMetadata();

            // print cassandra cluster status
            System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
            for (Host host : metadata.getAllHosts()) {
                System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(), host.getAddress(), host.getRack());
            }
            session = cluster.connect();

        }
    }

    public void closeConnection() {
        session.close();
        session = null;
    }
    
    public void createKeySpace() {

        String sql = "CREATE KEYSPACE " + keyspace
                + "  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };";

        session.execute(sql);

    }
    
    public void createTableDataAsset() {

        String sql = "CREATE TABLE " + keyspace + ".DataAsset ("
                + " dataAssetID varchar,"
                + " dataPartitionID int,"
                + " data text,"
                + " PRIMARY KEY ( dataAssetID, dataPartitionID));";

        session.execute(sql);
        
    }
    
    public void createTableProcessingDataAsset() {

        String sql = "CREATE TABLE " + keyspace + ".ProcessingDataAsset ("
                + " id varchar,"
    //            + " dataPartitionID int,"
                + " data text,"
                + " PRIMARY KEY (id));";

        session.execute(sql);
        
        
       
    }


    public void saveDataAsset(DataAsset dataAsset) {

    
        String xml = "";

        try {
            xml = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(CassandraDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "INSERT INTO " + keyspace + ".DataAsset ( dataAssetID, dataPartitionID, data) "
                + "VALUES ('" + dataAsset.getName() + "'," + dataAsset.getPartition() + ",'" + xml + "' )";


        session.execute(sql);

    }


    public void removeDataAsset(String dafName) {

        String sql = "DElETE FROM " + keyspace + ".DataAsset WHERE dataAssetID='" + dafName + "'";
        session.execute(sql);

    }

    
    public String getDataAssetXML(String dafName, String partitionID) {

        String data ="";
 

        String sql = "SELECT * FROM " + keyspace + ".DataAsset "
                + "WHERE dataAssetID='" + dafName + "' AND dataPartitionID=" + partitionID + " ALLOW FILTERING;";
//
//        String sql = "SELECT * FROM " + keyspace + ".SENSOR "
//                + "WHERE sensor_name='" + dataAssetID + "' ALLOW FILTERING;";

        ResultSet resultSet = null;
        try {
            resultSet = session.execute(sql);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("Exception cause: " + sql);
        }
        ExecutionInfo info = resultSet.getExecutionInfo();

        if (!(resultSet == null || resultSet.isExhausted())) {

            List<Row> rows = resultSet.all();

            for (Row row : rows) {
                
                data = row.getString("data");

//                System.out.println("ID: " + id + " - data: " + data);
            }

        }
        
//        DataAsset dataAsset=null;
//    try {
//        dataAsset = JAXBUtils.unmarshal(data, DataAsset.class);
//    } catch (JAXBException ex) {
//        Logger.getLogger(CassandraDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
//    }
//        
        
        return data;

    }
    
    public void truncateDataAssetTable(){
          
         String sql = "TRUNCATE " + keyspace + ".DataAsset;";
         System.out.println("SQL: " + sql);


        session.execute(sql);
        
    }
    
    public void truncateProcessingDataAssetTable(){
          
         String sql = "TRUNCATE " + keyspace + ".ProcessingDataAsset;";
         System.out.println("SQL: " + sql);


        session.execute(sql);
        
    }

   
    public String copyDataAssetRepo(DataPartitionRequest request) {

       // truncateProcessingDataAssetTable();
        
        System.out.println("Staring Copying Data ...");

        
        String sql = "SELECT * FROM " + keyspace + ".DataAsset "
                + "WHERE dataAssetID='" + request.getDataAssetID() + "' ALLOW FILTERING;";

        System.out.println("sql:" + sql);
        
        Configuration cfg = new Configuration();
        System.out.println("ENLARGE.COEFFICIENT = " + cfg.getConfig("ENLARGE.COEFFICIENT"));
        int enlargeCoefficient = Integer.parseInt(cfg.getConfig("ENLARGE.COEFFICIENT"));
        
        
        
        ResultSet resultSet = null;
        try {
            resultSet = session.execute(sql);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("Exception cause: " + sql);
        }

        
        
        ExecutionInfo info = resultSet.getExecutionInfo();
        int noOfPartitions = 0;
        if (!(resultSet == null || resultSet.isExhausted())) {

            List<Row> rows = resultSet.all();

            for (Row row : rows) {
                
           
                String daPartitionStr = row.getString("data");
                for (int i=0;i<enlargeCoefficient;i++) {

                

                String name = request.getEdaas() + ";" + request.getCustomerID() + ";" + request.getDataAssetID();

                DataAsset dataAssetPartition = null;
                try {
                    dataAssetPartition = JAXBUtils.unmarshal(daPartitionStr, DataAsset.class);
                } catch (JAXBException ex) {
                    Logger.getLogger(CassandraDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
                }
                dataAssetPartition.setName(name);
                dataAssetPartition.setPartition(noOfPartitions);
                insertDataPartitionRepo(dataAssetPartition);
                noOfPartitions++;

                ThroughputMonitor.trackingLoad(request);
                }
            }

        }

        return String.valueOf(noOfPartitions);
    }


    public String getDataPartitionRepo(DataPartitionRequest request) {

        String edaas = request.getEdaas();
        String customerID = request.getCustomerID();
        String dawID = request.getDataAssetID();
        String paritionID = request.getPartitionID();
        
        String id = edaas+";" + customerID+";" + dawID+";" + paritionID ;
        
        String data = "";

        String sql = "SELECT * FROM " + keyspace + ".ProcessingDataAsset "
                + "WHERE id='" + id +  "' ;";
      
        ResultSet resultSet = null;
        try {
            resultSet = session.execute(sql);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("Exception cause: " + sql);
        }
        ExecutionInfo info = resultSet.getExecutionInfo();

        if (!(resultSet == null || resultSet.isExhausted())) {

            List<Row> rows = resultSet.all();

            for (Row row : rows) {
         
                data = row.getString("data");

              //  System.out.println("data: " + data);
            }

        }

        return data;

    }
    
    public String getNoOfPartitionDataAssetTable(){
        String sql = "SELECT COUNT(*) FROM " + keyspace + ".DataAsset;";
        
         String noOfPartition="";
         ResultSet resultSet = null;
        try {
            resultSet = session.execute(sql);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("Exception cause: " + sql);
        }
        ExecutionInfo info = resultSet.getExecutionInfo();

        if (!(resultSet == null || resultSet.isExhausted())) {

            List<Row> rows = resultSet.all();

            for (Row row : rows) {
        
                noOfPartition = String.valueOf(row.getLong("count"));

            }

        }
        
        
        return noOfPartition;
        
    }

 
    public String getNoOfPartitionRepo(DataPartitionRequest request) {
    
//          String edaas = request.getEdaas();
//        String customerID = request.getCustomerID();
//        String dawID = request.getDataAssetID();
//        String id = edaas+";" + customerID+";" + dawID;
        String noOfPartition="";
        
        
        DataAssetFunctionStore dafs = new DataAssetFunctionStore();
        noOfPartition = String.valueOf(dafs.getNoOfPartitions(request.getDataAssetID()));
        
        
//     String sql = "SELECT COUNT(*) FROM " + keyspace + ".ProcessingDataAsset "
//                + " WHERE id='" + id + "' ALLOW FILTERING;";
//     
//        ResultSet resultSet = null;
//        try {
//            resultSet = session.execute(sql);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            log.error("Exception cause: " + sql);
//        }
//        ExecutionInfo info = resultSet.getExecutionInfo();
//
//        if (!(resultSet == null || resultSet.isExhausted())) {
//
//            List<Row> rows = resultSet.all();
//
//            for (Row row : rows) {
//        
//                noOfPartition = String.valueOf(row.getLong("count"));
//
//            }
//
//        }
        
        return noOfPartition;
    }

    
    public void saveDataPartitionRepo(DataAsset dataAssetPartition) {
   
        String daXML="";
        try {
            daXML = JAXBUtils.marshal(dataAssetPartition, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(MySqlDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
 
        int partitionID = dataAssetPartition.getPartition();
        
     
        String sql = "UPDATE " + keyspace + ".ProcessingDataAsset SET data='"+daXML+"' "
                + "WHERE id='"+dataAssetPartition.getName()+";"+partitionID+"' ; ";
        
        
        session.execute(sql);
        
    }

  
    public void insertDataPartitionRepo(DataAsset dataAssetPartition) {
         String daXML="";
        try {
            daXML = JAXBUtils.marshal(dataAssetPartition, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(MySqlDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        int partitionID = dataAssetPartition.getPartition();
  
        String sql = "INSERT INTO " + keyspace + ".ProcessingDataAsset (id, data) "
                + "VALUES ('" + dataAssetPartition.getName() +";"+ partitionID+"', '"+daXML+"' );";
        
        session.execute(sql);
    
    }

}
