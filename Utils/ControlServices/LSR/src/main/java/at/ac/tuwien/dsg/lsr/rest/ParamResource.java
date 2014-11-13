/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.lsr.rest;

import at.ac.tuwien.dsg.common.deployment.ControlParam;
import at.ac.tuwien.dsg.externalserviceutils.IOUtils;
import at.ac.tuwien.dsg.externalserviceutils.JAXBUtils;
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
        return "lrs";
    }

    /**
     * PUT method for updating or creating an instance of ParamResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String controlDataAsset(String controlParamsXml) {
        
        Logger.getLogger(ParamResource.class.getName()).log(Level.SEVERE, "RECIEVED: " + controlParamsXml);
        
        
        JAXBUtils jAXBUtils = new JAXBUtils();
        try {
            ControlParam controlParam = jAXBUtils.unmarshal(controlParamsXml, ControlParam.class);
            LSRService lSRService = new LSRService();
            lSRService.requestLSRService(controlParam);
            
        } catch (JAXBException ex) {
            Logger.getLogger(ParamResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "ok";
    }
    
    
    @PUT
    @Path("conf")
    @Consumes(MediaType.APPLICATION_XML)
    public void configure(String configure) {
        Logger.getLogger(ParamResource.class.getName()).log(Level.SEVERE, "RECIEVED: " + configure);
        
        IOUtils iou = new IOUtils();
        iou.writeData(configure, "config.properties");
    }
    
    
    
    
    
}
