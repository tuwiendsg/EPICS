/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.lsr.rest;


import at.ac.tuwien.dsg.depic.common.entity.runtime.ExternalServiceRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;


import at.ac.tuwien.dsg.lsr.algorithm.service.LSRService;
import at.ac.tuwien.dsg.lsr.util.Configuration;
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
@Path("/control")
public class RestWSAdjustment {

    @GET
    @Path("/message")
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
    @Path("/dataasset")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String controlDataAsset(String dataControlRequestXML) {
        
        Logger.getLogger(RestWSAdjustment.class.getName()).log(Level.INFO, "RECIEVED: " + dataControlRequestXML);
        
        
        JAXBUtils jAXBUtils = new JAXBUtils();
        try {
            ExternalServiceRequest controlRequest = jAXBUtils.unmarshal(dataControlRequestXML, ExternalServiceRequest.class);
            LSRService lSRService = new LSRService();
            lSRService.requestLSRService(controlRequest);
            
        } catch (JAXBException ex) {
            Logger.getLogger(RestWSAdjustment.class.getName()).log(Level.SEVERE, null, ex);
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
