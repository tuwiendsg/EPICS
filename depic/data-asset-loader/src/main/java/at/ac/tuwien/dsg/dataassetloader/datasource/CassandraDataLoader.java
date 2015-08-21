/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.datasource;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetloader.store.CassandraDataAssetStore;
import at.ac.tuwien.dsg.depic.common.entity.runtime.MonitoringSession;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class CassandraDataLoader implements DataLoader {
    
    private static List<CassandraSession> listOfCassandraObj;
    
    
    

    public CassandraDataLoader() {

        if (listOfCassandraObj == null) {
            listOfCassandraObj = new ArrayList<CassandraSession>();
//            String customer1 = "c01";
//            CassandraDataAssetStore cassandraDataAssetStore1 = new CassandraDataAssetStore();
//            CassandraSession cassandraSession1 = new CassandraSession(customer1, cassandraDataAssetStore1);
//            listOfCassandraObj.add(cassandraSession1);
//                     
//            String customer2 = "c02";
//            CassandraDataAssetStore cassandraDataAssetStore2 = new CassandraDataAssetStore();
//            CassandraSession cassandraSession2 = new CassandraSession(customer2, cassandraDataAssetStore2);
//            listOfCassandraObj.add(cassandraSession2);

        }

    }
    
    private CassandraDataAssetStore getCassandraObject(String customerID){
        
        for (CassandraSession cassandraSession : listOfCassandraObj){
            if (cassandraSession.getCustomerID().equals(customerID)){
                return cassandraSession.getCassandraDataAssetStore();
            }
        }     
        
        CassandraDataAssetStore cassandraDataAssetStore = new CassandraDataAssetStore();
        CassandraSession cassandraSession = new CassandraSession(customerID, cassandraDataAssetStore);
        listOfCassandraObj.add(cassandraSession);

        return cassandraDataAssetStore;
        
    }
    
    public void openConnectionEDARepo(DataPartitionRequest request){
        CassandraDataAssetStore cassandraDataAssetStore = getCassandraObject(request.getCustomerID());
        cassandraDataAssetStore.openConnection();
    }
    
    public void closeConnectionEDARepo(DataPartitionRequest request){
        CassandraDataAssetStore cassandraDataAssetStore = getCassandraObject(request.getCustomerID());
        cassandraDataAssetStore.closeConnection();
    }
    
    public void setupEDARepo(DataPartitionRequest request){
        CassandraDataAssetStore cassandraDataAssetStore = getCassandraObject(request.getCustomerID());
        
        cassandraDataAssetStore.createKeySpace();
        cassandraDataAssetStore.createTableDataAsset();
        cassandraDataAssetStore.createTableProcessingDataAsset();
    }
    
    
    
    
    
    @Override
    public String loadDataAsset(DataAnalyticsFunction dataAssetFunction) {
        
        CassandraDataAssetStore cassandraDataAssetStore = getCassandraObject("");
        
        
        openConnectionDAFM();
        
        String partitionNo = "";
        try {

            String log = "DAF Name: " + dataAssetFunction.getName();
            Logger.getLogger(CassandraDataLoader.class.getName()).log(Level.INFO, log);

            String daw = dataAssetFunction.getDaw();
            String returnStr = requestToGetDataAsset(daw);

            String[] strs = returnStr.split(";");
            String dataAssetID = strs[0];
            int numberOfPartitions = Integer.parseInt(strs[1]);
            partitionNo = String.valueOf(numberOfPartitions);
            cassandraDataAssetStore.openConnection();
            for (int i = 0; i < numberOfPartitions; i++) {
                String dataAssetXml = getDataPartition(dataAssetID, String.valueOf(i));
                DataAsset da = JAXBUtils.unmarshal(dataAssetXml, DataAsset.class);
                da.setDataAssetID(dataAssetFunction.getName());
                //dataAssetXml = JAXBUtils.marshal(da, DataAsset.class);

                cassandraDataAssetStore.saveDataAsset(da);

            }
            cassandraDataAssetStore.closeConnection();

        } catch (JAXBException ex) {
            Logger.getLogger(CassandraDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        closeConnectionDAFM();
        return partitionNo;

    }

    public void truncateDataAssetTable(DataPartitionRequest request) {
        CassandraDataAssetStore cassandraDataAssetStore = getCassandraObject(request.getCustomerID());
        cassandraDataAssetStore.truncateDataAssetTable();
    }
    
    public void truncateProcessingDataAssetTable(DataPartitionRequest request) {
        CassandraDataAssetStore cassandraDataAssetStore = getCassandraObject(request.getCustomerID());
        cassandraDataAssetStore.truncateProcessingDataAssetTable();
    }

            
            

    private void openConnectionDAFM() {
        Configuration config = new Configuration();
        String ip = config.getConfig("DAF.MANAGEMENT.IP");
        String port = config.getConfig("DAF.MANAGEMENT.PORT");

        String resourceOpenConnection = config.getConfig("DAF.MANAGEMENT.RESOURCE.DAW") + "/connection/open/" + DBType.CASSANDRA.getDBType();
       
        RestfulWSClient rs_open = new RestfulWSClient(ip, port, resourceOpenConnection);
        rs_open.callPutMethod("");
    }

    private void closeConnectionDAFM() {
        Configuration config = new Configuration();
        String ip = config.getConfig("DAF.MANAGEMENT.IP");
        String port = config.getConfig("DAF.MANAGEMENT.PORT");

        String resourceCloseConnection = config.getConfig("DAF.MANAGEMENT.RESOURCE.DAW") + "/connection/close/" + DBType.CASSANDRA.getDBType();

        RestfulWSClient rs_close = new RestfulWSClient(ip, port, resourceCloseConnection);
        rs_close.callPutMethod("");
    }

   
    public String copyDataAssetRepo(DataPartitionRequest request) {
        
        CassandraDataAssetStore cassandraDataAssetStore = getCassandraObject(request.getCustomerID());
        
        String rs = "";
        //CassandraDataAssetStore.openConnectionDAFM();
        rs = cassandraDataAssetStore.copyDataAssetRepo(request);
       // CassandraDataAssetStore.closeConnectionDAFM();
        return rs;
    }

    @Override
    public String getNoOfParitionRepo(DataPartitionRequest request) {
        CassandraDataAssetStore cassandraDataAssetStore = getCassandraObject(request.getCustomerID());
        String noOfPartition = cassandraDataAssetStore.getNoOfPartitionRepo(request);
        return noOfPartition;
    }

    @Override
    public String getDataPartitionRepo(DataPartitionRequest request) {
        CassandraDataAssetStore cassandraDataAssetStore = getCassandraObject(request.getCustomerID());
        String daXML = cassandraDataAssetStore.getDataPartitionRepo(request);
        return daXML;
    }


    public void saveDataPartitionRepo(DataAsset dataAsset) {
               
        String[] strs = dataAsset.getDataAssetID().split(";");
        String customerID = strs[1];
        CassandraDataAssetStore cassandraDataAssetStore = getCassandraObject(customerID);
        cassandraDataAssetStore.saveDataPartitionRepo(dataAsset);
    }

    private String requestToGetDataAsset(String daw) {

        System.out.println("Request to Get Data Asset ...");
        Configuration config = new Configuration();
        String ip = config.getConfig("DAF.MANAGEMENT.IP");
        String port = config.getConfig("DAF.MANAGEMENT.PORT");
        String resource = config.getConfig("DAF.MANAGEMENT.RESOURCE.DAW") + "/" + DBType.CASSANDRA.getDBType();

        RestfulWSClient rs = new RestfulWSClient(ip, port, resource);
        String returnStr = rs.callPutMethod(daw);

        System.out.println("Return: " + returnStr);

        return returnStr;
    }

    private String getDataPartition(String dataAssetID, String dataPartitionID) {
        
        Configuration config = new Configuration();
        String ip = config.getConfig("DAF.MANAGEMENT.IP");
        String port = config.getConfig("DAF.MANAGEMENT.PORT");
        String resource = config.getConfig("DAF.MANAGEMENT.RESOURCE.DATAASSET") + "/" + DBType.CASSANDRA.getDBType();

        DataPartitionRequest dataPartitionRequest = new DataPartitionRequest("", "", dataAssetID, dataPartitionID);

        String dataPartitionXML = "";
        try {
            dataPartitionXML = JAXBUtils.marshal(dataPartitionRequest, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(MySQLDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        RestfulWSClient rs = new RestfulWSClient(ip, port, resource);
        String dataParstitionXML = rs.callPutMethod(dataPartitionXML);

        return dataParstitionXML;
    }

    @Override
    public String copyDataAssetRepo(MonitoringSession monitoringSession, int dataAssetCounter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
