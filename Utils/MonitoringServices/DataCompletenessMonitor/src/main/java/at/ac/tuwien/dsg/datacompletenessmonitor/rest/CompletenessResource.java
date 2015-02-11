/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.datacompletenessmonitor.rest;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.utils.IOUtils;
import at.ac.tuwien.dsg.datacompletenessmonitor.algorithm.CompletenessMonitor;
import at.ac.tuwien.dsg.datacompletenessmonitor.service.CompletenessService;
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
import javax.xml.bind.JAXBException;

/**
 * REST Web Service
 *
 * @author bolobala
 */
@Path("completeness")
public class CompletenessResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CompletenessResource
     */
    public CompletenessResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.datacompletenessmonitor.CompletenessResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        Configuration cfg = new Configuration();
        String configStr = "Completeness: " + cfg.getConfig("DATA.ASSET.LOADER.IP");
        return configStr;
    }

    /**
     * PUT method for updating or creating an instance of CompletenessResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String monitorDataCompleteness(String dataAssetRequest) {
        
        Logger.getLogger(CompletenessResource.class.getName()).log(Level.INFO, "Monitoring Data Completeness ... :" + dataAssetRequest);
        
        
        CompletenessService completenessService = new CompletenessService();
        double completeness = completenessService.requestMonitorDataCompletenessService(dataAssetRequest);
        completeness = Math.round(completeness);
        
        return String.valueOf(completeness);
    }
    
    
    @PUT
    @Path("conf")
    @Consumes(MediaType.APPLICATION_XML)
    public void configure(String dataAssetLoaderIp) {
        Configuration cfg = new Configuration();
        cfg.setProperties("DATA.ASSET.LOADER.IP", dataAssetLoaderIp);
    }
}
