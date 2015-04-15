/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.test;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetloader.datasource.CassandraDataLoader;
import at.ac.tuwien.dsg.dataassetloader.store.CassandraDataAssetStore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */

@Path("test")
public class RestTest {
    
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DataassetResource
     */
    public RestTest() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.dataassetloader.restws.DataassetResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("daw")
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        Configuration config = new Configuration();
        return config.getConfig("EDA.REPOSITORY.CASSANDRA.IP"); 
    }
    
    @PUT
    @Path("repo/dataasset/open")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String openConnection(String xml) {
        
        DataPartitionRequest request=null;
        try {
            request = JAXBUtils.unmarshal(xml, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(RestTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CassandraDataLoader cdl = new CassandraDataLoader();
        cdl.openConnectionEDARepo(request);
     
        
    
        
        
        return "";
        
    }
    
    @PUT
    @Path("repo/dataasset/close")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String closeConnection(String xml) {
       
         DataPartitionRequest request=null;
        try {
            request = JAXBUtils.unmarshal(xml, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(RestTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CassandraDataLoader cdl = new CassandraDataLoader();
        cdl.closeConnectionEDARepo(request);
        
        
        return "";
        
    }
    
    @PUT
    @Path("repo/dataasset/setup")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String setupEDA(String xml) {
       
        DataPartitionRequest request=null;
        try {
            request = JAXBUtils.unmarshal(xml, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(RestTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CassandraDataLoader cdl = new CassandraDataLoader();
        cdl.setupEDARepo(request);
        
        
        return "";
        
    }
    
    

//    
//    @PUT
//    @Path("repo/dataasset/insert")
//    @Consumes("application/xml")
//    @Produces("application/xml")
//    public String insertDataTableDataAsset(String xml) {
//        
//        DataAsset da=null;
//        try {
//            da = JAXBUtils.unmarshal(xml, DataAsset.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(RestTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        CassandraDataLoader cdl = new CassandraDataLoader();
//        //cdl.in
//        return "";
//    }
//    
//    @PUT
//    @Path("repo/dataasset/get")
//    @Consumes("application/xml")
//    @Produces("application/xml")
//    public String getDataTableDataAsset(String xml) {
//        
//        DataPartitionRequest request = null;
//        
//        try {
//            request = at.ac.tuwien.dsg.common.utils.JAXBUtils.unmarshal(xml, DataPartitionRequest.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(RestTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//        String data = CassandraDataAssetStore.getDataAssetXML(request.getDataAssetID(), request.getPartitionID());
//        
//        
//        return data;
//    }
//
//    @PUT
//    @Path("repo/dataasset/nopar")
//    @Consumes("application/xml")
//    @Produces("application/xml")
//    public String getNoParTableDataAsset(String xml) {
//
//        String noOfPartitions = CassandraDataAssetStore.getNoOfPartitionDataAssetTable();
//
//        return noOfPartitions;
//    }
//
    @PUT
    @Path("repo/dataasset/clear")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String truncateTableDataAsset(String xml) {

        DataPartitionRequest request=null;
        try {
            request = JAXBUtils.unmarshal(xml, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(RestTest.class.getName()).log(Level.SEVERE, null, ex);
        }
         CassandraDataLoader cdl = new CassandraDataLoader();
         cdl.truncateDataAssetTable(request);
        

        return "";

    }
//
// 
//    
//    @PUT
//    @Path("repo/processingdataasset/insert")
//    @Consumes("application/xml")
//    @Produces("application/xml")
//    public String insertDataTableProcessingDataAsset(String dataAssetRequestXML) {
//        
//        DataAsset da=null;
//        try {
//            da = JAXBUtils.unmarshal(dataAssetRequestXML, DataAsset.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(RestTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        CassandraDataAssetStore.insertDataPartitionRepo(da);
//        
//
//        return "";
//    }
//    
//    @PUT
//    @Path("repo/processingdataasset/nopar")
//    @Consumes("application/xml")
//    @Produces("application/xml")
//    public String getNoPartitionTableProcessingDataAsset(String xml) {
//        
//        DataPartitionRequest da=null;
//        try {
//            da = JAXBUtils.unmarshal(xml, DataPartitionRequest.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(RestTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        String noOfPartitions = CassandraDataAssetStore.getNoOfPartitionRepo(da);
//        
//        
//
//        return noOfPartitions;
//    }
//    
//    @PUT
//    @Path("repo/processingdataasset/get")
//    @Consumes("application/xml")
//    @Produces("application/xml")
//    public String getDataTableProcessingDataAsset(String xml) {
//        
//        DataPartitionRequest da=null;
//        try {
//            da = JAXBUtils.unmarshal(xml, DataPartitionRequest.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(RestTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        String data = CassandraDataAssetStore.getDataPartitionRepo(da);
//        
//        
//
//        return data;
//    }
//    
//    @PUT
//    @Path("repo/processingdataasset/update")
//    @Consumes("application/xml")
//    @Produces("application/xml")
//    public String updateDataTableProcessingDataAsset(String xml) {
//        
//        DataAsset da=null;
//        try {
//            da = JAXBUtils.unmarshal(xml, DataAsset.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(RestTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        CassandraDataAssetStore.saveDataPartitionRepo(da);
//        
//        
//
//        return "";
//    }
//    
//    
    @PUT
    @Path("repo/processingdataasset/clear")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String truncateTableProcessingDataAsset(String xml) {
        
       
         DataPartitionRequest request=null;
        try {
            request = JAXBUtils.unmarshal(xml, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(RestTest.class.getName()).log(Level.SEVERE, null, ex);
        }
         CassandraDataLoader cdl = new CassandraDataLoader();
        cdl.truncateProcessingDataAssetTable(request);

        return "";
    }
//    
//
//    
    
    
}
