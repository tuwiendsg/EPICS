/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataaccuracymonitor.rest;


import at.ac.tuwien.dsg.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.dataaccuracymonitor.service.DataAccuracyMonitorService;
import at.ac.tuwien.dsg.depic.dataaccuracymonitor.util.Configuration;
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

/**
 * REST Web Service
 *
 * @author bolobala
 */
@Path("dataaccuracy")
public class DataAccuracyResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ThroughputResource
     */
    public DataAccuracyResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.throughputmonitor.DataAccuracyResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        Configuration cfg = new Configuration();
        String configStr = "Accuracy: " + cfg.getConfig("DATA.ASSET.LOADER.IP");
        return configStr;
    }

    /**
     * PUT method for updating or creating an instance of DataAccuracyResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String monitorAccuracy(String dataAssetRequest) {
        
        Logger.getLogger(DataAccuracyResource.class.getName()).log(Level.INFO, "Monitoring Data Accuracy ... :" + dataAssetRequest);
        
        DataAccuracyMonitorService dataAccuracyMonitorService = new DataAccuracyMonitorService();
        double accuracy = dataAccuracyMonitorService.requestAccuracyMonitorService(dataAssetRequest);
        accuracy = Math.round(accuracy);
        return String.valueOf(accuracy);
    }
    
    
    @PUT
    @Path("conf")
    @Consumes(MediaType.APPLICATION_XML)
    public void configure(String dataAssetLoaderIp) {
        Configuration cfg = new Configuration();
        cfg.setProperties("DATA.ASSET.LOADER.IP", dataAssetLoaderIp);
    }
}
