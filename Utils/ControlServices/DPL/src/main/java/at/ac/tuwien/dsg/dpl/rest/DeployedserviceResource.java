/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dpl.rest;

import at.ac.tuwien.dsg.dpl.action.DeployWSAction;
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
@Path("deployedservice")
public class DeployedserviceResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DeployedserviceResource
     */
    public DeployedserviceResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.dpl.rest.DeployedserviceResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getXml() {
        //TODO return proper representation object
        return "dpl";
    }

    /**
     * PUT method for updating or creating an instance of DeployedserviceResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    @Produces(MediaType.TEXT_HTML)
    public void deployWS(String serviceID) {
        DeployWSAction deployWSAction = new DeployWSAction();
        deployWSAction.deployWS(serviceID);
    }
}
