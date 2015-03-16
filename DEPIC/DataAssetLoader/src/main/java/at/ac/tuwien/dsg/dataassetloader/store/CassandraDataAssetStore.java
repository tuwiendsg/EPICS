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

import static com.datastax.driver.core.DataType.Name.ASCII;
import static com.datastax.driver.core.DataType.Name.BOOLEAN;
import static com.datastax.driver.core.DataType.Name.COUNTER;
import static com.datastax.driver.core.DataType.Name.DECIMAL;
import static com.datastax.driver.core.DataType.Name.DOUBLE;
import static com.datastax.driver.core.DataType.Name.FLOAT;
import static com.datastax.driver.core.DataType.Name.INET;
import static com.datastax.driver.core.DataType.Name.INT;
import static com.datastax.driver.core.DataType.Name.TEXT;
import static com.datastax.driver.core.DataType.Name.TIMESTAMP;
import static com.datastax.driver.core.DataType.Name.TIMEUUID;
import static com.datastax.driver.core.DataType.Name.VARCHAR;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

import org.slf4j.LoggerFactory;

/**
 *
 * @author Jun
 */
public class CassandraDataAssetStore  {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CassandraDataAssetStore.class);
    private static Session session;
    private static String ip;
    private static int port;
    private static String keyspace;
  


    
    private static void doConfig() {
        if (ip == null) {
            Configuration cfg = new Configuration();
            ip = cfg.getConfig("EDA.REPOSITORY.CASSANDRA.IP");
            port = Integer.parseInt(cfg.getConfig("EDA.REPOSITORY.CASSANDRA.PORT"));
            keyspace = cfg.getConfig("EDA.REPOSITORY.CASSANDRA.KEY");
        }
    }

   

    public static void openConnection() {
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

    public static void closeConnection() {
        session.close();
        session = null;
    }
    
    public static void createKeySpace() {

        String sql = "CREATE KEYSPACE " + keyspace
                + "  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };";

        session.execute(sql);

    }
    
    public static void createTableDataAsset() {

        String sql = "CREATE TABLE " + keyspace + ".DataAsset ("
                + " dataAssetID varchar,"
                + " dataPartitionID int,"
                + " data text,"
                + " PRIMARY KEY ( dataAssetID, dataPartitionID));";

        session.execute(sql);
        
    }
    
    public static void createTableProcessingDataAsset() {

        String sql = "CREATE TABLE " + keyspace + ".ProcessingDataAsset ("
                + " id varchar,"
                + " dataPartitionID int,"
                + " data text,"
                + " PRIMARY KEY (id, dataPartitionID));";

        session.execute(sql);
        
        
       
    }


    public static void saveDataAsset(DataAsset dataAsset) {

    
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


    public static void removeDataAsset(String dafName) {

        String sql = "DElETE FROM " + keyspace + ".DataAsset WHERE dataAssetID='" + dafName + "'";
        session.execute(sql);

    }

    
    public static String getDataAssetXML(String dafName, String partitionID) {

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
    
    public static void truncateDataAssetTable(){
          
         String sql = "TRUNCATE " + keyspace + ".DataAsset;";
         System.out.println("SQL: " + sql);


        session.execute(sql);
        
    }
    
    public static void truncateProcessingDataAssetTable(){
          
         String sql = "TRUNCATE " + keyspace + ".ProcessingDataAsset;";
         System.out.println("SQL: " + sql);


        session.execute(sql);
        
    }

   
    public static String copyDataAssetRepo(DataPartitionRequest request) {

        truncateProcessingDataAssetTable();
        
        System.out.println("Staring Copying Data ...");

        
        String sql = "SELECT * FROM " + keyspace + ".DataAsset "
                + "WHERE dataAssetID='" + request.getDataAssetID() + "' ALLOW FILTERING;";

        System.out.println("sql:" + sql);
        
        Configuration cfg = new Configuration();
        System.out.println("ENLARGE.COEFFICIENT = " + cfg.getConfig("ENLARGE.COEFFICIENT"));
        int enlargeCoefficient = Integer.parseInt(cfg.getConfig("ENLARGE.COEFFICIENT"));
        System.out.println("ENLARGE.COEFFICIENT = " +enlargeCoefficient);
        
        
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


    public static String getDataPartitionRepo(DataPartitionRequest request) {

        String edaas = request.getEdaas();
        String customerID = request.getCustomerID();
        String dawID = request.getDataAssetID();
        String paritionID = request.getPartitionID();
        
        String id = edaas+";" + customerID+";" + dawID;
        
        String data = "";

        String sql = "SELECT * FROM " + keyspace + ".ProcessingDataAsset "
                + "WHERE id='" + id +  "' AND dataPartitionID=" + paritionID + " ALLOW FILTERING;";

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
    
    public static String getNoOfPartitionDataAssetTable(){
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

 
    public static String getNoOfPartitionRepo(DataPartitionRequest request) {
    
          String edaas = request.getEdaas();
        String customerID = request.getCustomerID();
        String dawID = request.getDataAssetID();
        String id = edaas+";" + customerID+";" + dawID;
        String noOfPartition="";
        

     String sql = "SELECT COUNT(*) FROM " + keyspace + ".ProcessingDataAsset "
                + "WHERE id='" + id + "' ALLOW FILTERING;";

     
     
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

    
    public static void saveDataPartitionRepo(DataAsset dataAssetPartition) {
   
        String daXML="";
        try {
            daXML = JAXBUtils.marshal(dataAssetPartition, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(MySqlDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
 
        int partitionID = dataAssetPartition.getPartition();
        
     
        String sql = "UPDATE " + keyspace + ".ProcessingDataAsset SET data='"+daXML+"' "
                + "WHERE id='"+dataAssetPartition.getName()+"' AND dataPartitionID="+partitionID+" ; ";
        
        
        session.execute(sql);
        
    }

  
    public static void insertDataPartitionRepo(DataAsset dataAssetPartition) {
         String daXML="";
        try {
            daXML = JAXBUtils.marshal(dataAssetPartition, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(MySqlDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        int partitionID = dataAssetPartition.getPartition();
  
        String sql = "INSERT INTO " + keyspace + ".ProcessingDataAsset (id, dataPartitionID, data) "
                + "VALUES ('" + dataAssetPartition.getName() + "', "+partitionID+", '"+daXML+"' );";
        
        session.execute(sql);
    
    }

}
