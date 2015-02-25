/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.datasource;

import at.ac.tuwien.dsg.common.entity.eda.DataAssetFunction;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataPartitionRequest;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetloader.store.CassandraDataAssetStore;

import java.io.FileNotFoundException;
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

    @Override
    public String loadDataAsset(DataAssetFunction dataAssetFunction) {

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

            for (int i = 0; i < numberOfPartitions; i++) {
                String dataAssetXml = getDataPartition(dataAssetID, String.valueOf(i));
                DataAsset da = JAXBUtils.unmarshal(dataAssetXml, DataAsset.class);
                da.setName(dataAssetFunction.getName());
                dataAssetXml = JAXBUtils.marshal(da, DataAsset.class);

                CassandraDataAssetStore cdas = new CassandraDataAssetStore();
                cdas.saveDataAsset(dataAssetXml, dataAssetFunction.getName(), String.valueOf(i));

            }

        } catch (JAXBException ex) {
            Logger.getLogger(CassandraDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return partitionNo;

    }

    @Override
    public String copyDataAssetRepo(DataPartitionRequest request) {
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        return cdas.copyDataAssetRepo(request);
    }

    @Override
    public String getNoOfParitionRepo(DataPartitionRequest request) {
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        String noOfPartition = cdas.getNoOfPartitionRepo(request);
        return noOfPartition;
    }

    @Override
    public String getDataPartitionRepo(DataPartitionRequest request) {
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        String daXML = cdas.getDataPartitionRepo(request);
        return daXML;
    }

    @Override
    public void saveDataPartitionRepo(DataAsset dataAsset) {
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        cdas.saveDataPartitionRepo(dataAsset);
    }

    private String requestToGetDataAsset(String daw) {

        Configuration config = new Configuration();
        String ip = config.getConfig("DAF.MANAGEMENT.IP");
        String port = config.getConfig("DAF.MANAGEMENT.PORT");
        String resource = config.getConfig("DAF.MANAGEMENT.RESOURCE.DAW");

        RestfulWSClient rs = new RestfulWSClient(ip, port, resource);
        String returnStr = rs.callPutMethod(daw);

        return returnStr;
    }

    private String getDataPartition(String dataAssetID, String dataPartitionID) {

        Configuration config = new Configuration();
        String ip = config.getConfig("DAF.MANAGEMENT.IP");
        String port = config.getConfig("DAF.MANAGEMENT.PORT");
        String resource = config.getConfig("DAF.MANAGEMENT.RESOURCE.DATAASSET");

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
