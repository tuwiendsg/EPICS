/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.restws;

import at.ac.tuwien.dsg.common.entity.eda.DataAssetFunction;
import at.ac.tuwien.dsg.common.entity.eda.EDaaSType;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataPartitionRequest;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetloader.datasource.CassandraDataLoader;

import at.ac.tuwien.dsg.dataassetloader.datasource.GenericDataLoader;
import at.ac.tuwien.dsg.dataassetloader.store.CassandraDataAssetStore;

import at.ac.tuwien.dsg.dataassetloader.store.DataAssetFunctionStore;
import at.ac.tuwien.dsg.dataassetloader.util.ThroughputMonitor;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

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
 * @author Jun
 */
@Path("dataasset")
public class DataassetResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DataassetResource
     */
    public DataassetResource() {
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
        return config.getConfig("EDA.REPOSITORY.MYSQL.IP"); 
    }

    @PUT
    @Path("dafmanagement")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String requestDataAsset(String xml) {
        
        
        String[] strs = xml.split(";");
        String dataAssetID = strs[0];
        String customerID = strs[1];

        String log ="RECEIVED: " + dataAssetID;
        Logger.getLogger(DataassetResource.class.getName()).log(Level.INFO, null, log);
        
        DataAssetFunctionStore dafStore = new DataAssetFunctionStore();
        String dataAssetFunctionXML = dafStore.getDataAssetFunction(dataAssetID);
        
        System.out.println("Data Asset Function: " +dataAssetFunctionXML);
        
        
        EDaaSType eDaaSType = dafStore.gEDaaSTypeFromDataAssetID(dataAssetID);
        
        System.out.println("EDaaSType: " +eDaaSType.geteDaaSType());
        
        DataAssetFunction daf=null;
        try {
            daf = JAXBUtils.unmarshal(dataAssetFunctionXML, DataAssetFunction.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        daf.setType(customerID);
        GenericDataLoader dataLoader = new GenericDataLoader(eDaaSType);
        String noOfPartitions = dataLoader.loadDataAsset(daf);
        
        return noOfPartitions;
    }
    
    
    @PUT
    @Path("repo/datacopy")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String copyDataAsset(String dataAssetRequestXML) {

        String log ="RECEIVED: " + dataAssetRequestXML;
        Logger.getLogger(DataassetResource.class.getName()).log(Level.INFO,  log);
        
        DataPartitionRequest request=null;
        try {
            request = JAXBUtils.unmarshal(dataAssetRequestXML, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DataAssetFunctionStore dafStore = new DataAssetFunctionStore();
        EDaaSType eDaaSType = dafStore.gEDaaSTypeFromEDaaSName(request.getEdaas());
        GenericDataLoader dataLoader = new GenericDataLoader(eDaaSType);
        String noOfPartitions = dataLoader.copyDataAssetRepo(request);
        
        
        return noOfPartitions;
    }
    
    
    @PUT
    @Path("repo/getpartition")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getDataPartition(String dataAssetRequestXML) {

       String log ="RECEIVED: " + dataAssetRequestXML;
       
        Logger.getLogger(DataassetResource.class.getName()).log(Level.INFO, log);
        
        DataPartitionRequest request=null;
        try {
            request = JAXBUtils.unmarshal(dataAssetRequestXML, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DataAssetFunctionStore dafStore = new DataAssetFunctionStore();
        EDaaSType eDaaSType = dafStore.gEDaaSTypeFromEDaaSName(request.getEdaas());
        GenericDataLoader dataLoader = new GenericDataLoader(eDaaSType);
        String daXML =dataLoader.getDataPartitionRepo(request);
               
        ThroughputMonitor.trackingLoad(request);
        
        return daXML;
    }
    
    @PUT
    @Path("repo/noofpartition")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getNoOfPartition(String dataAssetRequestXML) {

       String log ="RECEIVED: " + dataAssetRequestXML;
        Logger.getLogger(DataassetResource.class.getName()).log(Level.INFO, log);
        
        DataPartitionRequest request=null;
        try {
            request = JAXBUtils.unmarshal(dataAssetRequestXML, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        DataAssetFunctionStore dafStore = new DataAssetFunctionStore();
        EDaaSType eDaaSType = dafStore.gEDaaSTypeFromEDaaSName(request.getEdaas());
        
        GenericDataLoader dataLoader = new GenericDataLoader(eDaaSType);
        String noOfDataPartitionRepo =dataLoader.getNoOfParitionRepo(request);
        
        return noOfDataPartitionRepo;
        
    }
    
    @PUT
    @Path("repo/savepartition")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String saveDataPartition(String dataAssetXML) {

        // String log ="RECEIVED: " + dataAssetXML;
       // Logger.getLogger(DataassetResource.class.getName()).log(Level.INFO,log);
        
        DataAsset dataAsset=null;
        try {
            dataAsset = JAXBUtils.unmarshal(dataAssetXML, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        String[] strs = dataAsset.getName().split(";");
        String edaasName = strs[0];
        String customerID = strs[1];
        String dawName = strs[2];
        
        
        DataAssetFunctionStore dafStore = new DataAssetFunctionStore();
        EDaaSType eDaaSType = dafStore.gEDaaSTypeFromEDaaSName(edaasName);
        GenericDataLoader dataLoader = new GenericDataLoader(eDaaSType);
        dataLoader.saveDataPartitionRepo(dataAsset);
        
        DataPartitionRequest request= new DataPartitionRequest(edaasName, customerID, dawName, "");
        ThroughputMonitor.trackingLoad(request);
       
        
        
        
        return "";
    }
    
    @PUT
    @Path("repo/throughput")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getThroughput(String dataAssetRequestXML) {
        DataPartitionRequest request=null;
        try {
            request = JAXBUtils.unmarshal(dataAssetRequestXML, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
       
      
       double throughput = ThroughputMonitor.calculateThroughput(request);
        
        return String.valueOf(throughput);
        
    }
    
    
    @PUT
    @Path("repo/cassandra/open")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String openConnection(String xml) {

        DataPartitionRequest request=null;
        try {
            request = JAXBUtils.unmarshal(xml, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CassandraDataLoader cdl = new CassandraDataLoader();
        cdl.openConnectionEDARepo(request);
     
        
        return "";
        
    }
    
    @PUT
    @Path("repo/cassandra/close")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String closeConnection(String xml) {
       
        DataPartitionRequest request=null;
        try {
            request = JAXBUtils.unmarshal(xml, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        CassandraDataLoader cdl = new CassandraDataLoader();
        cdl.closeConnectionEDARepo(request);
        
        return "";
        
    }
    

}
