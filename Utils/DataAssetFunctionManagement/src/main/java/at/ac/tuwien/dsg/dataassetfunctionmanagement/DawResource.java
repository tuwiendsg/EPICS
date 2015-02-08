/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetfunctionmanagement;

import at.ac.tuwien.dsg.common.entity.eda.da.DataPartitionRequest;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.engine.WorkflowEngine;
import at.ac.tuwien.dsg.dataassetfunctionmanagement.store.DataAssetStore;
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
     * Retrieves representation of an instance of at.ac.tuwien.dsg.dataassetfunctionmanagement.DawResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        return Configuration.getConfig("DATA.SOURCE.IP");
    }

    /**
     * PUT method for updating or creating an instance of DawResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    @Produces("application/xml")
    public String executeDataAssetFunction(String dataAssetFunctionXML) {
    
        Logger.getLogger(DawResource.class.getName()).log(Level.INFO, "Recieved: " + dataAssetFunctionXML);
        UUID dafID = UUID.randomUUID();

        WorkflowEngine wf = new WorkflowEngine(dataAssetFunctionXML,dafID.toString());
        wf.startWFEngine();
        
        DataAssetStore das = new DataAssetStore();
        int noOfPartitions = das.getNumberOfPartitionsByDataAssetID(dafID.toString());
        
        String returnString = dafID.toString()+";"+String.valueOf(noOfPartitions);
        return returnString;
    }
    
    
    @PUT
    @Path("dataasset")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getData(String requestDataPartition) {
        
        Logger.getLogger(DawResource.class.getName()).log(Level.INFO, "Recieved: " + requestDataPartition);
        
        DataPartitionRequest dataPartition=null;
        try {
            dataPartition = JAXBUtils.unmarshal(requestDataPartition, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DawResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        String dataAssetID = dataPartition.getDataAssetID();
        String partitionID = dataPartition.getPartitionID();
        
        
        
        DataAssetStore das = new DataAssetStore();
        String daXML = das.getDataPartition(dataAssetID, partitionID);
        
        return daXML;
    }
    
    
}
