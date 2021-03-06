/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.store;


import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.configuration.Configuration;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.util.JAXBUtils;
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
//import java.util.UUID;
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
public class CassandraDataAssetStore {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CassandraDataAssetStore.class);
    private static Session session;
    private static String ip;
    private static int port;
    private static String keyspace;

    private static void doConfig() {
        if (ip == null) {

            ip = Configuration.getConfig("CASSANDRA.DB.IP");
            port = Integer.parseInt(Configuration.getConfig("CASSANDRA.DB.PORT"));
            keyspace = Configuration.getConfig("CASSANDRA.DB.KEY");
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



// Cassandra test
    
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
                + " PRIMARY KEY (dataAssetID, dataPartitionID));";

        session.execute(sql);
        
    }
    
    public static void createTableSensor(){
        
        String sql = "CREATE TABLE " + keyspace + ".SENSOR ("
                + " id int,"
                + " sensor_name varchar,"
                + " position varchar,"
                + " data text,"
                + " PRIMARY KEY (id, sensor_name));";
        
        session.execute(sql);
    }
    
    public static void createTableKDD(){
        
        String sql = "CREATE TABLE " + keyspace + ".KDD ("
                + " id int,"
                + " adID int,"
                + " userID varchar,"
                + " click varchar,"
                + " data text,"
       //         + " PRIMARY KEY (id, adID, userID, click));";
        + " PRIMARY KEY (id, adID));";
        
        
      
        
        session.execute(sql);
    }
    
    
    public static void insertKDD(DataAsset dataAsset){
        
        List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();
        
        for (DataItem dataItem : listOfDataItems){
            
            int id = Integer.parseInt(getDataAttributeValue(dataItem, "id"));
            int adID = Integer.parseInt(getDataAttributeValue(dataItem, "adID"));
            String userID = getDataAttributeValue(dataItem, "userID");
            String click = getDataAttributeValue(dataItem, "click");
            String data = getDataAttributeValue(dataItem, "data");
         
            
            
         String sql = "INSERT INTO " + keyspace + ".KDD (id, adID, userID, click, data) "
                + "VALUES ( " + id + ", " + adID + ", '" + userID + "', '" + click + "', '" + data + "' )";

            System.out.println("SQL: " + sql);
        session.execute(sql);
            
            
        }
        
        
        
    }
    
    
    
    
    public static DataAsset getDataKDD(String sql) {
      
        List<DataItem> listOfDataItems = new ArrayList<DataItem>();
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

               
                List<DataAttribute> listOfDataAttributes = new ArrayList<DataAttribute>();
               
                    
                    DataAttribute id = new DataAttribute("id", row.getString("id"));
                    DataAttribute adID = new DataAttribute("adID", String.valueOf(row.getInt("adID")));
                    DataAttribute userID = new DataAttribute("userID", row.getString("userID"));
                    DataAttribute click = new DataAttribute("click", row.getString("click"));
                    DataAttribute data = new DataAttribute("data", row.getString("data"));
                    
                    
                    listOfDataAttributes.add(id);
                    listOfDataAttributes.add(adID);
                    listOfDataAttributes.add(userID);
                    listOfDataAttributes.add(click);
                    listOfDataAttributes.add(data);

                
                
                DataItem dataItem = new DataItem(listOfDataAttributes);
                listOfDataItems.add(dataItem);
                
                
            }

        }
        
        DataAsset da = new DataAsset("", 0, listOfDataItems);
        
        
        return da;
        

    }

    

    public static void insertDataAsset(DataAsset dataAsset) {

//        UUID uuid = UUID.randomUUID();
//
//        String uuid_str = uuid.toString();

        String xml = "";

        try {
            xml = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(CassandraDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "INSERT INTO " + keyspace + ".DataAsset ( dataAssetID, dataPartitionID, data) "
                + "VALUES ( '" + dataAsset.getDataAssetID()+ "'," + dataAsset.getPartition() + ",'" + xml + "' )";


        session.execute(sql);

    }
    public static void insertSensor(DataAsset dataAsset){
        
//        UUID uuid = UUID.randomUUID();
//
//        String uuid_str = uuid.toString();
//
//        String xml = "";
//
//        try {
//            xml = JAXBUtils.marshal(dataAsset, DataAsset.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(CassandraDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        String sql = "INSERT INTO " + keyspace + ".SENSOR (id , sensor_name, position, data) "
//                + "VALUES ( '" + uuid_str + "', '" + dataAsset.getName() + "','" + xml + "' )";
//
//        session.execute(sql);
    }
    
    
    
    
    public static ResultSet executeCQLStatement(String cql){
        
        ResultSet resultSet = null;
        try {
            resultSet = session.execute(cql);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("Exception cause: " + cql);
        }
        
        return resultSet;
    }
    
    public static void runCQLStatementKDD(String cql){
        ResultSet resultSet = null;
        try {
            resultSet = session.execute(cql);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("Exception cause: " + cql);
        }
        ExecutionInfo info = resultSet.getExecutionInfo();

        if (!(resultSet == null || resultSet.isExhausted())) {

            List<Row> rows = resultSet.all();

            for (Row row : rows) {

               
                    DataAttribute id = new DataAttribute("id", row.getString("id"));
                    DataAttribute adID = new DataAttribute("adID", String.valueOf(row.getInt("adID")));
                    DataAttribute userID = new DataAttribute("userID", row.getString("userID"));
                    DataAttribute click = new DataAttribute("click", row.getString("click"));
                    DataAttribute data = new DataAttribute("data", row.getString("data"));
                    
                 System.out.println("RS: " +" adID: " + adID.getAttributeValue() + " - userID: " + userID.getAttributeValue());
                
                
            }

        }
        
        
    }
    
    

    public static DataAsset getDataAsset(DataPartitionRequest dataPartitionRequest) {
        String data ="";
        String dataAssetID = dataPartitionRequest.getDataAssetID();
        String dataPartitionID = dataPartitionRequest.getPartitionID();

        String sql = "SELECT * FROM " + keyspace + ".DataAsset "
                + "WHERE dataAssetID='" + dataAssetID + "' AND dataPartitionID=" + dataPartitionID + " ALLOW FILTERING;";
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

                System.out.println(" - data: " + data);
            }

        }
        
        DataAsset dataAsset=null;
    try {
        dataAsset = JAXBUtils.unmarshal(data, DataAsset.class);
    } catch (JAXBException ex) {
        Logger.getLogger(CassandraDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
    }
        
        
        return dataAsset;

    }
    public static int getNoOfParitionDataAsset(String dataAssetID) {
        String counter="";
        

        String sql = "SELECT COUNT(*) FROM " + keyspace + ".DataAsset "
                + "WHERE dataAssetID='" + dataAssetID + "' ALLOW FILTERING;";

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
                 counter = String.valueOf(row.getLong("count"));
              
            }

        }
        
        
        
        
   
        
        
        return Integer.parseInt(counter);

    }
    
    
    public static String getNoOfParitionKDD() {
        long counter=0;
        

        String sql = "SELECT COUNT(*) FROM " + keyspace + ".KDD; ";
             

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
                 counter = row.getLong("count");
              
            }

        }
        
        
        
        
   
        
        
        return String.valueOf(counter);

    }
    
    public static void updateDataAsset(DataAsset dataAssetPartition){
        String daXML="";
        try {
            daXML = JAXBUtils.marshal(dataAssetPartition, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(CassandraDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        String[] strs = dataAssetPartition.getName().split(";");
//        String edaasName = strs[0];
//        String customerID = strs[1];
//        String dawName = strs[2];
//        int partitionID = dataAssetPartition.getPartition();
//        
//        
//   
        
//        //String sql = "INSERT INTO ProcessingDataAsset(customerID,edaas,daw_name,partitionID,da) VALUES ('" + customerID + "','" + edaasName + "','" + dawName + "','"+partitionID+"',?)";
//        String sql= "UPDATE ProcessingDataAsset SET da=? WHERE edaas='"+edaasName+"' AND customerID='"+customerID+"' AND daw_name='"+dawName+"' AND partitionID='"+partitionID+"'";
////        

        String sql = "UPDATE " + keyspace + ".DataAsset "
                + "SET data='"+daXML+"' "
                 + "WHERE dataAssetID='" + dataAssetPartition.getDataAssetID()+ "' AND dataPartitionID=" + dataAssetPartition.getPartition() + " ;";


        session.execute(sql);
    }
    
    
    private static String getDataAttributeValue(DataItem dataItem, String attributeName){
        
        List<DataAttribute> listOfDataAttributes = dataItem.getListOfAttributes();
        
        for (DataAttribute dataAttribute : listOfDataAttributes){
            if (dataAttribute.getAttributeName().equals(attributeName)) {
                return dataAttribute.getAttributeValue();
            }
            
        }
        
        return "";
        
    }

    private static void convertRow(Row row) {

        for (ColumnDefinitions.Definition column : row.getColumnDefinitions()) {
            String columnName = column.getName();
            String value;
            switch (column.getType().getName()) {
                case DOUBLE:
                    value = "" + row.getDouble(columnName);
                    break;
                case DECIMAL:
                case FLOAT:
                    value = "" + row.getFloat(columnName);
                    break;
                case COUNTER:
                    value = "" + row.getLong(columnName);
                    break;
                case INT:
                    value = "" + row.getInt(columnName);
                    break;
                case TEXT:
                case VARCHAR:
                case ASCII:
                    value = row.getString(columnName);
                    break;
                case BOOLEAN:
                    value = "" + row.getBool(columnName);
                    break;
                case INET:
                    value = row.getInet(columnName).toString();
                    break;
                case TIMEUUID:
                case UUID:
                    value = row.getUUID(columnName).toString();
                    break;
                case TIMESTAMP:
                    value = row.getDate(columnName).toString();
                    break;
                default:
                    value = "type " + column.getType().getName();
                    log.error("Currently not processing type " + column.getType().getName());

            }

        }

    }
    
    public static void truncateKDDTable(){
        
         String sql = "TRUNCATE " + keyspace + ".KDD;";
         System.out.println("SQL: " + sql);

        session.execute(sql);
    }
    
    public static void truncateDataAssetTable(){
          
         String sql = "TRUNCATE " + keyspace + ".DataAsset;";
         System.out.println("SQL: " + sql);


        session.execute(sql);
        
    }
    
    
    
    
//    
//    
//    private static String randomUDID(){
//        UUID uuid = UUID.randomUUID();
//
//        String uuid_str = uuid.toString();
//        
//        return uuid_str;
//    }

    //////////////////////
    // end cassandra test
    //////////////////////

    


}
