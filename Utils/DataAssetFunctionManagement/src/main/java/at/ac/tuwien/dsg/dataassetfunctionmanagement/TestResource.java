/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetfunctionmanagement;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataPartitionRequest;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.engine.WorkflowEngine;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.store.CassandraDataAssetStore;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.store.MySqlDataAssetStore;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.util.IOUtils;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.util.JAXBUtils;
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
 *
 * @author Jun
 */
@Path("test")
public class TestResource {
        //cassandra
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DawResource
     */
    public TestResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.dataassetfunctionmanagement.DawResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        return Configuration.getConfig("DATA.SOURCE.IP");
    }
    
    
    @PUT
    @Path("repo/cassandra/keyspace")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String createCassandraKeyspace(String xml) {
       
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        cdas.openConnection();
        cdas.createKeySpace();
        cdas.closeConnection();
        
        
        return "";
        
    }
    
    
    
    @PUT
    @Path("repo/cassandra/tablekdd")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String createCassandraTableKDD(String xml) {
       
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        cdas.openConnection();
        cdas.createTableKDD();
        cdas.closeConnection();
        
        
        return "";
        
    }
    
    @PUT
    @Path("repo/cassandra/insertkdd")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String insertCassandraKDDData(String xml) {
       
        DataAsset dataAsset = null;
        
        try {
            dataAsset = at.ac.tuwien.dsg.common.utils.JAXBUtils.unmarshal(xml, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(TestResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        cdas.openConnection();
        cdas.insertKDD(dataAsset);
        cdas.closeConnection();
        
        
        return "";
        
    }
    
    @PUT
    @Path("repo/cassandra/getkddpartition")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getDataKDD(String query) {
        
      
        
       
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        cdas.openConnection();
        DataAsset dataAsset = cdas.getDataKDD(query);
        cdas.closeConnection();
        
        String rsXML="";
        try {
            rsXML = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(TestResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rsXML;
        
    }
    
    
    @PUT
    @Path("repo/cassandra/truncatetablekdd")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String truncateTableKDD(String xml) {
       
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        cdas.openConnection();
        cdas.truncateKDDTable();
        cdas.closeConnection();
        
        
        return "";
        
    }
    
    
    
    
    
    
    
    // END KDD
    
    
    
    // START DATA ASSET
    
    
    
    @PUT
    @Path("repo/cassandra/tabledataasset")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String createCassandraTableDataAsset(String xml) {
       
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        cdas.openConnection();
        cdas.createTableDataAsset();
        cdas.closeConnection();
        
        
        return "";
        
    }
    
     @PUT
    @Path("repo/cassandra/insertdataasset")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String insertCassandraDataAsset(String xml) {
       
        DataAsset dataAsset = null;
        
        try {
            dataAsset = at.ac.tuwien.dsg.common.utils.JAXBUtils.unmarshal(xml, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(TestResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        cdas.openConnection();
        cdas.insertDataAsset(dataAsset);
        cdas.closeConnection();
        
        
        return "";
        
    }
    
    
    
     @PUT
    @Path("repo/cassandra/truncatetabledataasset")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String truncateTableDataAsset(String xml) {
       
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        cdas.openConnection();
        cdas.truncateDataAssetTable();
        cdas.closeConnection();
        
        
        return "";
        
    }
    
    @PUT
    @Path("repo/cassandra/getdataassetpartition")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getDataDataAsset(String xml) {
        
      DataPartitionRequest request = null;
        
        try {
            request = at.ac.tuwien.dsg.common.utils.JAXBUtils.unmarshal(xml, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(TestResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
       
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        cdas.openConnection();
        DataAsset dataAsset = cdas.getDataAsset(request);
        cdas.closeConnection();
        
        String rsXML="";
        try {
            rsXML = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(TestResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rsXML;
        
    }
   
    @PUT
    @Path("repo/cassandra/updatedataassetpartition")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String updateDataAssetPartition(String dataAssetXML) {

         String log ="RECEIVED: " + dataAssetXML;
        Logger.getLogger(TestResource.class.getName()).log(Level.INFO,log);
        
        DataAsset dataAsset=null;
        try {
            dataAsset = JAXBUtils.unmarshal(dataAssetXML, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(TestResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CassandraDataAssetStore cdas = new CassandraDataAssetStore();
        cdas.openConnection();
        cdas.updateDataAsset(dataAsset);
        cdas.closeConnection();
        
       
        
        
        return "";
    }
    
   
}
