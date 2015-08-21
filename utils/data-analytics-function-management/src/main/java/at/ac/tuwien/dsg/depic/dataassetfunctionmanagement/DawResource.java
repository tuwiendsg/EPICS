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
@Path("daf")
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
    @Path("ip")
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
    @Path("start")
    @Consumes("application/xml")
    @Produces("application/xml")
    public String executeDataAssetFunction(String dafXML) {

        Logger.getLogger(DawResource.class.getName()).log(Level.INFO, "DAFM STARTING ...");
       
        DataAnalyticsFunction daf = null;
        
        try {
            daf = JAXBUtils.unmarshal(dafXML, DataAnalyticsFunction.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DawResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        UUID dafID = UUID.randomUUID();
        
        WorkflowEngine workflowEngine = new WorkflowEngine(daf, dafID.toString());
        workflowEngine.startWFEngine();
        
       
        return "";
        
        
    }

   

}
