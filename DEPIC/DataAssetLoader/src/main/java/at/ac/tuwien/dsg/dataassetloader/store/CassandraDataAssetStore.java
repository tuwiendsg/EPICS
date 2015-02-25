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
import java.util.UUID;
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
public class CassandraDataAssetStore implements DataStore {

    static final org.slf4j.Logger log = LoggerFactory.getLogger(CassandraDataAssetStore.class);
    private Session session;
    private String ip;
    private int port;
    private String keyspace;

    public CassandraDataAssetStore() {

        Configuration cfg = new Configuration();
        ip = cfg.getConfig("CASSANDRA.DB.IP");
        port = Integer.parseInt(cfg.getConfig("CASSANDRA.DB.PORT"));
        keyspace = cfg.getConfig("CASSANDRA.DB.KEY");
    }

    public CassandraDataAssetStore(String ip, int port, String keyspace) {
        this.ip = ip;
        this.port = port;
        this.keyspace = keyspace;
    }

    public void openConnection() {
        log.info("Connecting to Cassandra at " + ip + ":" + port);
        if (session == null) {
            Cluster cluster = Cluster.builder()
                    .withPort(port)
                    .addContactPoint(ip).build();
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

    // Cassandra test
    public void createDataAssetTable() {

        String sql = "CREATE TABLE " + keyspace + ".DataAsset ("
                + " id varchar,"
                + " dataAssetID varchar,"
                + " dataPartitionID int,"
                + " data text,"
                + " PRIMARY KEY (id, dataAssetID, dataPartitionID));";

        String sql2 = "CREATE TABLE " + keyspace + ".SENSOR ("
                + " id varchar,"
                + " sensor_name varchar,"
                + " data text,"
                + " PRIMARY KEY (id, sensor_name));";

        session.execute(sql2);
    }

    public void createKeySpace() {

        String sql = "CREATE KEYSPACE " + keyspace
                + "  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };";

        session.execute(sql);

    }

    public void insertDataAsset(DataAsset dataAsset) {

        UUID uuid = UUID.randomUUID();

        String uuid_str = uuid.toString();

        String xml = "";

        try {
            xml = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(CassandraDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "INSERT INTO " + keyspace + ".DataAsset (id , dataAssetID, dataPartitionID, data) "
                + "VALUES ( '" + uuid_str + "', '" + dataAsset.getName() + "'," + dataAsset.getPartition() + ",'" + xml + "' )";

        String sql2 = "INSERT INTO " + keyspace + ".SENSOR (id , sensor_name, data) "
                + "VALUES ( '" + uuid_str + "', '" + dataAsset.getName() + "','" + xml + "' )";

        session.execute(sql2);

    }

    public void getDataAsset(DataPartitionRequest dataPartitionRequest) {
        String dataAssetID = dataPartitionRequest.getDataAssetID();
        String dataPartitionID = dataPartitionRequest.getPartitionID();

        String sql2 = "SELECT * FROM " + keyspace + ".DataAsset "
                + "WHERE dataAssetID='" + dataAssetID + "' AND dataPartitionID=" + dataPartitionID + " ALLOW FILTERING;";

        String sql = "SELECT * FROM " + keyspace + ".SENSOR "
                + "WHERE sensor_name='" + dataAssetID + "' ALLOW FILTERING;";

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
                String id = row.getString("id");
                String data = row.getString("data");

                System.out.println("ID: " + id + " - data: " + data);
            }

        }

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

    //////////////////////
    // end cassandra test
    //////////////////////
    @Override
    public void saveDataAsset(String daXML, String dafName, String partitionID) {

        UUID uuid = UUID.randomUUID();
        String uuid_str = uuid.toString();

        String sql = "INSERT INTO " + keyspace + ".DataAsset (id , dataAssetID, dataPartitionID, data) "
                + "VALUES ( '" + uuid_str + "', '" + dafName + "'," + partitionID + ",'" + daXML + "' )";

        session.execute(sql);

    }

    @Override
    public void removeDataAsset(String dafName) {

        String sql = "DElETE FROM " + keyspace + ".DataAsset WHERE dataAssetID='" + dafName + "'";
        session.execute(sql);

    }

    @Override
    public String getDataAssetXML(String dafName, String partitionID) {

        String data = "";
        String sql = "SELECT * FROM " + keyspace + ".DataAsset "
                + "WHERE dataAssetID='" + dafName + "' AND dataPartitionID=" + partitionID + " ALLOW FILTERING;";

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
                String id = row.getString("id");
                data = row.getString("data");

                System.out.println("ID: " + id + " - data: " + data);
            }

        }

        return data;

    }

    @Override
    public String copyDataAssetRepo(DataPartitionRequest request) {

        System.out.println("Staring Copying Data ...");

        String sql = "SELECT * FROM " + keyspace + ".DataAsset "
                + "WHERE dataAssetID='" + request.getDataAssetID() + "' ALLOW FILTERING;";

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
                String id = row.getString("id");
                String daPartitionStr = row.getString("data");

                String name = request.getEdaas() + ";" + request.getCustomerID() + ";" + request.getDataAssetID();

                DataAsset dataAssetPartition = null;
                try {
                    dataAssetPartition = JAXBUtils.unmarshal(daPartitionStr, DataAsset.class);
                } catch (JAXBException ex) {
                    Logger.getLogger(CassandraDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
                }
                dataAssetPartition.setName(name);
                insertDataPartitionRepo(dataAssetPartition);
                noOfPartitions++;

                ThroughputMonitor.trackingLoad(request);
            }

        }

        return String.valueOf(noOfPartitions);
    }

    @Override
    public String getDataPartitionRepo(DataPartitionRequest request) {

        String edaas = request.getEdaas();
        String customerID = request.getCustomerID();
        String dawID = request.getDataAssetID();
        String paritionID = request.getPartitionID();
        String data = "";

        String sql = "SELECT * FROM " + keyspace + ".ProcessingDataAsset "
                + "WHERE edaas='" + edaas + "' AND customerID='" + customerID + "' AND dataAssetID='" + dawID + "' AND dataPartitionID=" + paritionID + " ALLOW FILTERING;";

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
                String id = row.getString("id");
                data = row.getString("data");

                System.out.println("ID: " + id + " - data: " + data);
            }

        }

        return data;

    }

    @Override
    public String getNoOfPartitionRepo(DataPartitionRequest request) {
    
          String edaas = request.getEdaas();
        String customerID = request.getCustomerID();
        String dawID = request.getDataAssetID();

        String noOfPartition="";
        

     String sql = "SELECT COUNT(*) as counter FROM " + keyspace + ".ProcessingDataAsset "
                + "WHERE edaas='" + edaas + "' AND customerID='" + customerID + "' AND dataAssetID='" + dawID + "'  ALLOW FILTERING;";

     
     
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
        
                noOfPartition = String.valueOf(row.getInt("counter"));

            }

        }
        
        return noOfPartition;
    }

    @Override
    public void saveDataPartitionRepo(DataAsset dataAssetPartition) {
   
        String daXML="";
        try {
            daXML = JAXBUtils.marshal(dataAssetPartition, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(MySqlDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String[] strs = dataAssetPartition.getName().split(";");
        String edaasName = strs[0];
        String customerID = strs[1];
        String dawName = strs[2];
        int partitionID = dataAssetPartition.getPartition();
        
     
        String sql = "UPDATE " + keyspace + ".ProcessingDataAsset SET data='"+daXML+"' "
                + "WHERE edaas='"+edaasName+"' AND customerID='"+customerID+"' AND dataAssetID='"+dawName+"' AND dataPartitionID='"+partitionID+"'";
        session.execute(sql);
        
    }

    @Override
    public void insertDataPartitionRepo(DataAsset dataAssetPartition) {
         String daXML="";
        try {
            daXML = JAXBUtils.marshal(dataAssetPartition, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(MySqlDataAssetStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String[] strs = dataAssetPartition.getName().split(";");
        String edaasName = strs[0];
        String customerID = strs[1];
        String dawName = strs[2];
        int partitionID = dataAssetPartition.getPartition();
        UUID uuid = UUID.randomUUID();

        String uuid_str = uuid.toString();
     
       
        String sql = "INSERT INTO " + keyspace + ".ProcessingDataAsset (id ,customerID,edaas, dataAssetID, dataPartitionID, data) "
                + "VALUES ( '" + uuid_str + "', '" + customerID + "'," + edaasName + ",'" + dawName + "','"+partitionID+"','"+daXML+"' )";
        
        session.execute(sql);
    
    }

}
