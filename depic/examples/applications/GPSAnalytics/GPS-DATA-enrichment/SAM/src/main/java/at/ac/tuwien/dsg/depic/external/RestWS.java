/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.external;


import at.ac.tuwien.dsg.depic.common.entity.runtime.ExternalServiceRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.external.service.MonitoringService;
import at.ac.tuwien.dsg.depic.external.util.Configuration;
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
@Path("monitor")
public class RestWS{

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ParamResource
     */
    public RestWS() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.mlr.RestWS
     * @return an instance of java.lang.String
     */
    @GET
    @Path("message")
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        Configuration cfg = new Configuration();
        String configStr = "AIM: " + cfg.getConfig("DATA.ASSET.LOADER.IP");
        return configStr;
    }

    /**
     * PUT method for updating or creating an instance of RestWS
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String monitorDataAsset(String dataAssetRequest) {
        
        Logger.getLogger(RestWS.class.getName()).log(Level.INFO, "Monitoring Data Asset ... :" + dataAssetRequest);
        
        MonitoringService monitoringService = new MonitoringService();
        double monitoringValue = monitoringService.requestMonitoringService(dataAssetRequest);
        monitoringValue = Math.round(monitoringValue);
        System.out.println("MV: " + monitoringValue);
        return String.valueOf(monitoringValue);
    }
    
    
    @PUT
    @Path("conf")
    @Consumes(MediaType.APPLICATION_XML)
    public void configure(String dataAssetLoaderIp) {
        Configuration cfg = new Configuration();
        cfg.setProperties("DATA.ASSET.LOADER.IP", dataAssetLoaderIp);
    }
}
