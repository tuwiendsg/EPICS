/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.restws;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetloader.datasource.CassandraDataLoader;

import at.ac.tuwien.dsg.dataassetloader.datasource.GenericDataLoader;
import at.ac.tuwien.dsg.dataassetloader.store.CassandraDataAssetStore;

import at.ac.tuwien.dsg.dataassetloader.store.DataAssetFunctionStore;
import at.ac.tuwien.dsg.dataassetloader.util.ThroughputMonitor;
import at.ac.tuwien.dsg.depic.common.utils.YamlUtils;
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
    public String requestDataAsset(String yaml) {
        
        String log ="RECEIVED: " + yaml;
        Logger.getLogger(DataassetResource.class.getName()).log(Level.INFO, log);
        
        DataAnalyticsFunction daf= YamlUtils.unmarshallYaml(DataAnalyticsFunction.class, yaml);
        System.out.println("form: " + daf.getDataAssetForm());
        
        GenericDataLoader dataLoader = new GenericDataLoader(daf.getDbType());
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
        DBType eDaaSType = DBType.MYSQL ; //dafStore.gEDaaSTypeFromEDaaSName(request.getEdaas());
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
        DBType eDaaSType = dafStore.gEDaaSTypeFromEDaaSName(request.getEdaas());
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
        DBType eDaaSType = dafStore.gEDaaSTypeFromEDaaSName(request.getEdaas());
        
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
        
        
        
        
        String[] strs = dataAsset.getDataAssetID().split(";");
        String edaasName = strs[0];
        String customerID = strs[1];
        String dawName = strs[2];
        
        
        DataAssetFunctionStore dafStore = new DataAssetFunctionStore();
        DBType eDaaSType = dafStore.gEDaaSTypeFromEDaaSName(edaasName);
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
