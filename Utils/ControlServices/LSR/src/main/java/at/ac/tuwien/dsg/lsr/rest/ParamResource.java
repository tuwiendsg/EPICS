/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.lsr.rest;


import at.ac.tuwien.dsg.common.entity.eda.da.DataControlRequest;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.dataaccuracymonitor.util.Configuration;

import at.ac.tuwien.dsg.lsr.algorithm.service.LSRService;
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
@Path("control")
public class ParamResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ParamResource
     */
    public ParamResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.lsr.rest.ParamResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("message")
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        Configuration cfg = new Configuration();
        String configStr = "LSR: " + cfg.getConfig("DATA.ASSET.LOADER.IP");
        return configStr;
    }

    /**
     * PUT method for updating or creating an instance of ParamResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String controlDataAsset(String dataControlRequestXML) {
        
        Logger.getLogger(ParamResource.class.getName()).log(Level.INFO, "RECIEVED: " + dataControlRequestXML);
        
        
        JAXBUtils jAXBUtils = new JAXBUtils();
        try {
            DataControlRequest controlRequest = jAXBUtils.unmarshal(dataControlRequestXML, DataControlRequest.class);
            LSRService lSRService = new LSRService();
            lSRService.requestLSRService(controlRequest);
            
        } catch (JAXBException ex) {
            Logger.getLogger(ParamResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "";
    }
    
    
    @PUT
    @Path("conf")
    @Consumes(MediaType.APPLICATION_XML)
    public void configure(String dataAssetLoaderIp) {
        Configuration cfg = new Configuration();
        cfg.setProperties("DATA.ASSET.LOADER.IP", dataAssetLoaderIp);
    }
    
    
    
    
    
}
