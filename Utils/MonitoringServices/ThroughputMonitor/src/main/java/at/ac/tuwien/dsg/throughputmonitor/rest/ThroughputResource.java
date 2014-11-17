/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.throughputmonitor.rest;

import at.ac.tuwien.dsg.externalserviceutils.IOUtils;
import at.ac.tuwien.dsg.throughputmonitor.service.ThroughputMonitorService;
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
        return "throughput";
    }

    /**
     * PUT method for updating or creating an instance of ThroughputResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String monitorThroughput(String daID) {
        
        ThroughputMonitorService throughputMonitorService = new ThroughputMonitorService();
        
        return  throughputMonitorService.requestThroughputMonitorService(daID);
    }
    
    
    @PUT
    @Path("conf")
    @Consumes(MediaType.APPLICATION_XML)
    public void configure(String configure) {
        IOUtils iou = new IOUtils();
        iou.writeData(configure, "config.properties");
    }
}
