/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataassetfunctionmanagement;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.utils.YamlUtils;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.configuration.Configuration;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.engine.WorkflowEngine;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.store.CassandraDataAssetStore;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.store.MySqlDataAssetStore;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.util.IOUtils;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.util.JAXBUtils;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

/**
 * REST Web Service
 *
 * @author bolobala
 */
@Path("daw")
public class DawResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DawResource
     */
    public DawResource() {
    }

    /**
     * Retrieves representation of an instance of
     * at.ac.tuwien.dsg.dataassetfunctionmanagement.DawResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Path("MYSQL")
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        return Configuration.getConfig("DATA.SOURCE.IP");
    }

    /**
     * PUT method for updating or creating an instance of DawResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("MYSQL")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String executeDataAssetFunction(String dafYaml) {

        Logger.getLogger(DawResource.class.getName()).log(Level.INFO, "Recieved: " + dafYaml);
        UUID dafID = UUID.randomUUID();

        MySqlDataAssetStore das = new MySqlDataAssetStore();
        das.cleanTempStore();

        DataAnalyticsFunction daf = YamlUtils.unmarshallYaml(DataAnalyticsFunction.class, dafYaml);
      
        WorkflowEngine wf = new WorkflowEngine(daf, dafID.toString());

        wf.startWFEngine();

        int noOfPartitions = das.getNumberOfPartitionsByDataAssetID(dafID.toString());

        String returnString = dafID.toString() + ";" + String.valueOf(noOfPartitions);
        return returnString;
    }

    @PUT
    @Path("dataasset/MYSQL")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getData(String requestDataPartition) {

        Logger.getLogger(DawResource.class.getName()).log(Level.INFO, "Recieved: " + requestDataPartition);

        DataPartitionRequest dataPartition = null;
        try {
            dataPartition = JAXBUtils.unmarshal(requestDataPartition, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DawResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        String dataAssetID = dataPartition.getDataAssetID();
        String partitionID = dataPartition.getPartitionID();

        MySqlDataAssetStore das = new MySqlDataAssetStore();
        String daXML = das.getDataPartition(dataAssetID, partitionID);

        return daXML;
    }

    @PUT
    @Path("CASSANDRA")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String executeDataAssetFunctionCassandra(String dafYaml) {

        Logger.getLogger(DawResource.class.getName()).log(Level.INFO, "Recieved: " + dafYaml);
        UUID dafID = UUID.randomUUID();

        CassandraDataAssetStore.truncateDataAssetTable();

        DataAnalyticsFunction daf = YamlUtils.unmarshallYaml(DataAnalyticsFunction.class, dafYaml);

        WorkflowEngine wf = new WorkflowEngine(daf, dafID.toString());
        wf.startWFEngine();

        int noOfPartitions = CassandraDataAssetStore.getNoOfParitionDataAsset(dafID.toString());

        String returnString = dafID.toString() + ";" + String.valueOf(noOfPartitions);
        return returnString;
    }

    @PUT
    @Path("connection/open/CASSANDRA")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String openConnection(String xml) {

        CassandraDataAssetStore.openConnection();
        return "";

    }

    @PUT
    @Path("connection/close/CASSANDRA")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String closeConnection(String xml) {
        CassandraDataAssetStore.closeConnection();
        return "";

    }

    @PUT
    @Path("dataasset/CASSANDRA")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getDataCassandra(String requestDataPartition) {

        Logger.getLogger(DawResource.class.getName()).log(Level.INFO, "Recieved: " + requestDataPartition);

        DataPartitionRequest dataPartition = null;
        try {
            dataPartition = JAXBUtils.unmarshal(requestDataPartition, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DawResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataAsset dataAsset = CassandraDataAssetStore.getDataAsset(dataPartition);

        String daXML = "";

        try {
            daXML = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DawResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return daXML;
    }

}