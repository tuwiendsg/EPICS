/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.datasource;

import at.ac.tuwien.dsg.common.entity.eda.DataAssetFunction;
import at.ac.tuwien.dsg.common.entity.eda.EDaaSType;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataPartitionRequest;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetloader.store.CassandraDataAssetStore;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class CassandraDataLoader implements DataLoader {

    @Override
    public String loadDataAsset(DataAssetFunction dataAssetFunction) {
        
        openConnection();
        
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
            CassandraDataAssetStore.openConnection();
            for (int i = 0; i < numberOfPartitions; i++) {
                String dataAssetXml = getDataPartition(dataAssetID, String.valueOf(i));
                DataAsset da = JAXBUtils.unmarshal(dataAssetXml, DataAsset.class);
                da.setName(dataAssetFunction.getName());
                //dataAssetXml = JAXBUtils.marshal(da, DataAsset.class);

                CassandraDataAssetStore.saveDataAsset(da);

            }
            CassandraDataAssetStore.closeConnection();

        } catch (JAXBException ex) {
            Logger.getLogger(CassandraDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        closeConnection();
        return partitionNo;

    }

    private void openConnection() {
        Configuration config = new Configuration();
        String ip = config.getConfig("DAF.MANAGEMENT.IP");
        String port = config.getConfig("DAF.MANAGEMENT.PORT");

        String resourceOpenConnection = config.getConfig("DAF.MANAGEMENT.RESOURCE.DAW") + "/connection/open/" + EDaaSType.CASSANDRA.geteDaaSType();
       
        RestfulWSClient rs_open = new RestfulWSClient(ip, port, resourceOpenConnection);
        rs_open.callPutMethod("");
    }

    private void closeConnection() {
        Configuration config = new Configuration();
        String ip = config.getConfig("DAF.MANAGEMENT.IP");
        String port = config.getConfig("DAF.MANAGEMENT.PORT");

        String resourceCloseConnection = config.getConfig("DAF.MANAGEMENT.RESOURCE.DAW") + "/connection/close/" + EDaaSType.CASSANDRA.geteDaaSType();

        RestfulWSClient rs_close = new RestfulWSClient(ip, port, resourceCloseConnection);
        rs_close.callPutMethod("");
    }

    @Override
    public String copyDataAssetRepo(DataPartitionRequest request) {
        String rs = "";
        //CassandraDataAssetStore.openConnection();
        rs = CassandraDataAssetStore.copyDataAssetRepo(request);
       // CassandraDataAssetStore.closeConnection();
        return rs;
    }

    @Override
    public String getNoOfParitionRepo(DataPartitionRequest request) {
        String noOfPartition = CassandraDataAssetStore.getNoOfPartitionRepo(request);
        return noOfPartition;
    }

    @Override
    public String getDataPartitionRepo(DataPartitionRequest request) {
        String daXML = CassandraDataAssetStore.getDataPartitionRepo(request);
        return daXML;
    }

    @Override
    public void saveDataPartitionRepo(DataAsset dataAsset) {
        CassandraDataAssetStore.saveDataPartitionRepo(dataAsset);
    }

    private String requestToGetDataAsset(String daw) {

        System.out.println("Request to Get Data Asset ...");
        Configuration config = new Configuration();
        String ip = config.getConfig("DAF.MANAGEMENT.IP");
        String port = config.getConfig("DAF.MANAGEMENT.PORT");
        String resource = config.getConfig("DAF.MANAGEMENT.RESOURCE.DAW") + "/" + EDaaSType.CASSANDRA.geteDaaSType();

        RestfulWSClient rs = new RestfulWSClient(ip, port, resource);
        String returnStr = rs.callPutMethod(daw);

        System.out.println("Return: " + returnStr);

        return returnStr;
    }

    private String getDataPartition(String dataAssetID, String dataPartitionID) {

        Configuration config = new Configuration();
        String ip = config.getConfig("DAF.MANAGEMENT.IP");
        String port = config.getConfig("DAF.MANAGEMENT.PORT");
        String resource = config.getConfig("DAF.MANAGEMENT.RESOURCE.DATAASSET") + "/" + EDaaSType.CASSANDRA.geteDaaSType();

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

}
