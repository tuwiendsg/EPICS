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
import at.ac.tuwien.dsg.dataassetloader.datasource.DataAssetQueue;
import at.ac.tuwien.dsg.dataassetloader.datasource.DataAssetRepositoryMetadata;
import at.ac.tuwien.dsg.dataassetloader.datasource.DataBufferSession;

import at.ac.tuwien.dsg.dataassetloader.datasource.GenericDataLoader;
import at.ac.tuwien.dsg.dataassetloader.store.CassandraDataAssetStore;

import at.ac.tuwien.dsg.dataassetloader.store.DataAssetFunctionStore;
import at.ac.tuwien.dsg.dataassetloader.util.ThroughputMonitor;
import at.ac.tuwien.dsg.depic.common.entity.runtime.MonitoringSession;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depic.common.utils.YamlUtils;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.util.ArrayList;
import java.util.List;
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
     * Retrieves representation of an instance of
     * at.ac.tuwien.dsg.dataassetloader.restws.DataassetResource
     *
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
    @Path("dafm/start")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String requestStartDAFM(String edaasName) {

        System.out.println("data asset loader: start edaas " + edaasName);

        DataAssetFunctionStore dafStore = new DataAssetFunctionStore();

        DataAnalyticsFunction daf = dafStore.getDAFFromEDaaSName(edaasName);

        DataAssetQueue daq = new DataAssetQueue(daf.getName(), daf.getName());
        DataAssetRepositoryMetadata.addDataAssetQueue(daq);

        Configuration cfg = new Configuration();
        String dafmEndpoint = cfg.getConfig("DAFM.ENDPOINT");

        String dafXML = "";

        try {
            dafXML = JAXBUtils.marshal(daf, DataAnalyticsFunction.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        RestfulWSClient ws = new RestfulWSClient(dafmEndpoint);
        ws.callPutMethod(dafXML);

        return "";
    }

    @PUT
    @Path("repo/datacopy")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String copyDataAsset(String monitoringSessionXML) {

        String log = "Start Copying Data Asset: ";
        Logger.getLogger(DataassetResource.class.getName()).log(Level.INFO, log);

        MonitoringSession monitoringSession = null;
        try {
            monitoringSession = JAXBUtils.unmarshal(monitoringSessionXML, MonitoringSession.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!DataAssetRepositoryMetadata.existDataBufferSession(monitoringSession.getSessionID())) {

            DataBufferSession dataBufferSession = new DataBufferSession(monitoringSession.getSessionID());
            DataAssetRepositoryMetadata.addDataBufferSession(dataBufferSession);

        }

        DataAssetQueue daq = DataAssetRepositoryMetadata.getDataAssetQueueFromEDaaSName(monitoringSession.getEdaasName());
        int dataAssetCounter = daq.getDataAssetCounter();

        DataBufferSession dataBufferSession = DataAssetRepositoryMetadata.
                getDataBufferSessionFromSessionID(monitoringSession.getSessionID());

        int readyDataAssetWindowIndex = Integer.parseInt(monitoringSession.getDataAssetIndex()) + 1;

       
        boolean isRunning = false;
        
        boolean isInCopiedList = false;
        System.out.println("LIST: COPIED WINDOW ");
        for (String windowID : dataBufferSession.getListOfCopiedDataAssetWindows()){
            System.out.println(windowID);
            
            if (windowID.equals(String.valueOf(readyDataAssetWindowIndex))) {
                isInCopiedList = true;
            }  
        }
        
        boolean isInReadyList = false;
        System.out.println("LIST: READY WINDOW ");
        for (String windowID : dataBufferSession.getListOfDataAssetWindows()){
            System.out.println(windowID);
            if (windowID.equals(String.valueOf(readyDataAssetWindowIndex))) {
                isInReadyList = true;
            } 
        }
        
        
        
        
        
        
        if (isInCopiedList){
            System.out.println("COPIED DATA WINDOW DETECT !");
            if (!isInReadyList){
                 System.out.println("DATA IS PROCESSING");
                 isRunning = true;
            }
            
        }

        if (isRunning) {
            readyDataAssetWindowIndex = Integer.parseInt(monitoringSession.getDataAssetIndex());
            

        } else {

            boolean isReady = false;
            if (readyDataAssetWindowIndex < dataAssetCounter) {
                isReady = true;
                System.out.println("STARTING COPYING DATA WINDOW");

                DataAssetFunctionStore dafStore = new DataAssetFunctionStore();
                DBType eDaaSType = dafStore.gEDaaSTypeFromEDaaSName(monitoringSession.getEdaasName());

                GenericDataLoader dataLoader = new GenericDataLoader(eDaaSType);
                dataLoader.copyDataAssetRepo(monitoringSession, readyDataAssetWindowIndex);
                dataBufferSession.getListOfCopiedDataAssetWindows().add(String.valueOf(readyDataAssetWindowIndex));
            } else {
                System.out.println("WAITING ... FOR NEXT WINDOW");
            }

            if (!isReady) {
                readyDataAssetWindowIndex = Integer.parseInt(monitoringSession.getDataAssetIndex());
            }
        }

        return String.valueOf(readyDataAssetWindowIndex);
    }

    @PUT
    @Path("repo/getlastestda")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getLatestReadyDataAssetWindow(String monitoringSessionXML) {

        System.out.println("RECEIVED LASTEST DATA ASSET WINDOW");

        MonitoringSession monitoringSession = null;
        try {
            monitoringSession = JAXBUtils.unmarshal(monitoringSessionXML, MonitoringSession.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!DataAssetRepositoryMetadata.existDataBufferSession(monitoringSession.getSessionID())) {

            DataBufferSession dataBufferSession = new DataBufferSession(monitoringSession.getSessionID());
            DataAssetRepositoryMetadata.addDataBufferSession(dataBufferSession);

        }

        DataBufferSession dataBufferSession = DataAssetRepositoryMetadata.
                getDataBufferSessionFromSessionID(monitoringSession.getSessionID());

        int retrievedDataAssetIndex = dataBufferSession.getRetrivedDataAssetIndex();
        int readyDataAssetIndex = retrievedDataAssetIndex + 1;
        List<String> listOfReadyDataWindow = dataBufferSession.getListOfDataAssetWindows();
        System.out.println("CHECK READY DATA INDEX: " + readyDataAssetIndex);

        boolean isReady = false;
        for (String readyDataWindowID : listOfReadyDataWindow) {

            if (readyDataWindowID.equals(String.valueOf(readyDataAssetIndex))) {
                System.out.println("READY DATA INDEX: " + readyDataWindowID);
                isReady = true;
                break;
            }
        }

        String readyDataAssetWindow = "";

        if (isReady) {
            DataPartitionRequest request = new DataPartitionRequest(
                    monitoringSession.getEdaasName(),
                    monitoringSession.getSessionID(),
                    monitoringSession.getDataAssetID(),
                    String.valueOf(readyDataAssetIndex));

            DataAssetFunctionStore dafStore = new DataAssetFunctionStore();
            DBType eDaaSType = dafStore.gEDaaSTypeFromEDaaSName(request.getEdaas());
            GenericDataLoader dataLoader = new GenericDataLoader(eDaaSType);
            readyDataAssetWindow = dataLoader.getDataPartitionRepo(request);

            ThroughputMonitor.trackingLoad(request);
            dataBufferSession.setRetrivedDataAssetIndex(readyDataAssetIndex);
        }

        return readyDataAssetWindow;
    }

    @PUT
    @Path("repo/notifyreadyda")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String notifyReadyDataAssetWindow(String monitoringSessionXML) {

        System.out.println("NOTIFY READY DATA ASSET WINDOW");

        MonitoringSession monitoringSession = null;
        try {
            monitoringSession = JAXBUtils.unmarshal(monitoringSessionXML, MonitoringSession.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataBufferSession dataBufferSession = DataAssetRepositoryMetadata.
                getDataBufferSessionFromSessionID(monitoringSession.getSessionID());
        System.out.println("ADDING: " + monitoringSession.getDataAssetIndex());
        dataBufferSession.getListOfDataAssetWindows().add(monitoringSession.getDataAssetIndex());

        return "";
    }

    @PUT
    @Path("repo/getpartition")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getDataPartition(String dataAssetRequestXML) {

        String log = "RECEIVED DATA PARTITION REQUEST : " + dataAssetRequestXML;

        Logger.getLogger(DataassetResource.class.getName()).log(Level.INFO, log);

        DataPartitionRequest request = null;
        try {
            request = JAXBUtils.unmarshal(dataAssetRequestXML, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataAssetFunctionStore dafStore = new DataAssetFunctionStore();
        DBType eDaaSType = dafStore.gEDaaSTypeFromEDaaSName(request.getEdaas());
        GenericDataLoader dataLoader = new GenericDataLoader(eDaaSType);
        String daXML = dataLoader.getDataPartitionRepo(request);

        ThroughputMonitor.trackingLoad(request);

        return daXML;
    }

    @PUT
    @Path("repo/noofpartition")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getNoOfPartition(String dataAssetRequestXML) {

        String log = "RECEIVED: " + dataAssetRequestXML;
        Logger.getLogger(DataassetResource.class.getName()).log(Level.INFO, log);

        DataPartitionRequest request = null;
        try {
            request = JAXBUtils.unmarshal(dataAssetRequestXML, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataAssetFunctionStore dafStore = new DataAssetFunctionStore();
        DBType eDaaSType = dafStore.gEDaaSTypeFromEDaaSName(request.getEdaas());

        GenericDataLoader dataLoader = new GenericDataLoader(eDaaSType);
        String noOfDataPartitionRepo = dataLoader.getNoOfParitionRepo(request);

        return noOfDataPartitionRepo;

    }

    @PUT
    @Path("repo/savepartition")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String saveDataPartition(String dataAssetXML) {

        // String log ="RECEIVED: " + dataAssetXML;
        // Logger.getLogger(DataassetResource.class.getName()).log(Level.INFO,log);
        DataAsset dataAsset = null;
        try {
            dataAsset = JAXBUtils.unmarshal(dataAssetXML, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        String[] strs = dataAsset.getDataAssetID().split(";");

        for (String str : strs) {
            System.out.println("strs: " + str);
        }
        String edaasName = strs[0];
        String customerID = strs[1];
        String dawName = strs[2];

        DataAssetFunctionStore dafStore = new DataAssetFunctionStore();
        DBType eDaaSType = dafStore.gEDaaSTypeFromEDaaSName(edaasName);
        GenericDataLoader dataLoader = new GenericDataLoader(eDaaSType);
        dataLoader.saveDataPartitionRepo(dataAsset);

        DataPartitionRequest request = new DataPartitionRequest(edaasName, customerID, dawName, "");
        ThroughputMonitor.trackingLoad(request);

        return "";
    }

    @PUT
    @Path("repo/storedataasset")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String storeDataAssetFromDAFM(String dataAssetXML) {

        System.out.println("Storing data asset from DAFM");

        DataAsset dataAsset = null;
        try {
            dataAsset = JAXBUtils.unmarshal(dataAssetXML, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataassetResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataAssetQueue dataAssetQueue = DataAssetRepositoryMetadata.getDataAssetQueueFromEDaaSName(dataAsset.getDataAssetID());
        dataAssetQueue.increaseCounter();

        dataAsset.setPartition(dataAssetQueue.getDataAssetCounter());

        DataAssetFunctionStore dafStore = new DataAssetFunctionStore();
        DBType eDaaSType = dafStore.gEDaaSTypeFromEDaaSName(dataAssetQueue.geteDaaS());
        GenericDataLoader dataLoader = new GenericDataLoader(eDaaSType);
        dataLoader.storeDataPartitionfromDAFM(dataAsset);

        return "";
    }

    @PUT
    @Path("repo/throughput")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getThroughput(String dataAssetRequestXML) {
        DataPartitionRequest request = null;
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

        DataPartitionRequest request = null;
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

        DataPartitionRequest request = null;
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
