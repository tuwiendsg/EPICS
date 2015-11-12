/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataassetfunctionmanagement;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.configuration.Configuration;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.engine.WorkflowEngine;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.store.CassandraDataAssetStore;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.store.MySqlDataAssetStore;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.util.CsvImporter;
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
    @Path("repo/cassandra/open")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String openConnection(String xml) {
       
        
        CassandraDataAssetStore.openConnection();
        
        
        
        return "";
        
    }
    
    @PUT
    @Path("repo/cassandra/close")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String closeConnection(String xml) {
       
        CassandraDataAssetStore.closeConnection();
        
        
        return "";
        
    }
    
    
    @PUT
    @Path("repo/cassandra/keyspace")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String createCassandraKeyspace(String xml) {
       
     
        CassandraDataAssetStore.createKeySpace();
       
        
        
        return "";
        
    }
    
    
    
    @PUT
    @Path("repo/cassandra/tablekdd")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String createCassandraTableKDD(String xml) {
       
     
        CassandraDataAssetStore.createTableKDD();
        
        
        return "";
        
    }
    
    @PUT
    @Path("repo/cassandra/insertkdd")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String insertCassandraKDDData(String xml) {
       
        DataAsset dataAsset = null;
        
        try {
            dataAsset = at.ac.tuwien.dsg.depic.common.utils.JAXBUtils.unmarshal(xml, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(TestResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
        CassandraDataAssetStore.insertKDD(dataAsset);
        
        
        return "";
        
    }
    
    
    @PUT
    @Path("repo/cassandra/nokdd")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getNoOfItemKDDData(String xml) {
       
        String no =CassandraDataAssetStore.getNoOfParitionKDD();
        
        
        return no;
        
    }
    
    @PUT
    @Path("repo/cassandra/getkddpartition")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getDataKDD(String query) {
        
      
        
       
      
        DataAsset dataAsset = CassandraDataAssetStore.getDataKDD(query);
     
        
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
       
 
        CassandraDataAssetStore.truncateKDDTable();
     
        return "";
        
    }
    
    
    
    @PUT
    @Path("repo/cassandra/cql")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String runCQLKDD(String cql) {
       
 
        CassandraDataAssetStore.runCQLStatementKDD(cql);
        return "";
        
    }
    
    
    
    
    // END KDD
    
    
    
    // START DATA ASSET
    
    
    
    @PUT
    @Path("repo/cassandra/tabledataasset")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String createCassandraTableDataAsset(String xml) {
       
  
        CassandraDataAssetStore.createTableDataAsset();
     
        
        
        return "";
        
    }
    
     @PUT
    @Path("repo/cassandra/insertdataasset")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String insertCassandraDataAsset(String xml) {
       
        DataAsset dataAsset = null;
        
        try {
            dataAsset = at.ac.tuwien.dsg.depic.common.utils.JAXBUtils.unmarshal(xml, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(TestResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
        CassandraDataAssetStore.insertDataAsset(dataAsset);
       
        
        return "";
        
    }
    
    
    
    @PUT
    @Path("repo/cassandra/truncatetabledataasset")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String truncateTableDataAsset(String xml) {
       
     
        CassandraDataAssetStore.truncateDataAssetTable();
     
        
        
        return "";
        
    }
    
    @PUT
    @Path("repo/cassandra/getdataassetpartition")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getDataDataAsset(String xml) {
        
      DataPartitionRequest request = null;
        
        try {
            request = at.ac.tuwien.dsg.depic.common.utils.JAXBUtils.unmarshal(xml, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(TestResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        DataAsset dataAsset = CassandraDataAssetStore.getDataAsset(request);

        
        String rsXML="";
        try {
            rsXML = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(TestResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rsXML;
        
    }

    @PUT
    @Path("repo/cassandra/nodataasset")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getNoOfPartitionsDataAsset(String xml) {

        DataPartitionRequest request = null;

        try {
            request = at.ac.tuwien.dsg.depic.common.utils.JAXBUtils.unmarshal(xml, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(TestResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        int no = CassandraDataAssetStore.getNoOfParitionDataAsset(request.getDataAssetID());

        return String.valueOf(no);

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
   
        CassandraDataAssetStore.updateDataAsset(dataAsset);
     
        
       
        
        
        return "";
    }
    
    
    @PUT
    @Path("repo/cassandra/loadcsv")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String loadCSV(String rowNumbers) {

         String log ="Starting load csv file. ";
        Logger.getLogger(TestResource.class.getName()).log(Level.INFO,log);
     
        CsvImporter csvImporter = new CsvImporter();
        csvImporter.insertDataToCassandra(rowNumbers);
        return "Finish load csv file";
    }
    
   
}
