/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.throughputmonitor.rest;


import at.ac.tuwien.dsg.throughputmonitor.service.ThroughputMonitorService;
import at.ac.tuwien.dsg.throughputmonitor.util.Configuration;
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
@Path("throughput")
public class ThroughputResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ThroughputResource
     */
    public ThroughputResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.throughputmonitor.ThroughputResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        Configuration cfg = new Configuration();
        String configStr = "Throughput: " + cfg.getConfig("DATA.ASSET.LOADER.IP");
        return configStr;
    }

    /**
     * PUT method for updating or creating an instance of ThroughputResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String monitorThroughput(String dataAssetRequest) {
        
        Logger.getLogger(ThroughputResource.class.getName()).log(Level.INFO, "Monitoring Throughput ... :" + dataAssetRequest);
        ThroughputMonitorService throughputMonitorService = new ThroughputMonitorService(); 
        String throughput = throughputMonitorService.requestThroughputMonitorService(dataAssetRequest);

        return throughput;
     
    }
    
    
    @PUT
    @Path("conf")
    @Consumes(MediaType.APPLICATION_XML)
    public void configure(String dataAssetLoaderIp) {
        Configuration cfg = new Configuration();
        cfg.setProperties("DATA.ASSET.LOADER.IP", dataAssetLoaderIp);
    }
}
