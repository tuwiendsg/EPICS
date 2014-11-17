/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.addvm.rest;

import at.ac.tuwien.dsg.addvm.service.AddVMService;
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
@Path("vm")
public class VmResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of VmResource
     */
    public VmResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.addvm.rest.VmResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        return "addVM";
    }

    /**addVM
     * PUT method for updating or creating an instance of VmResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String AddVM(String noOfVMs) {
        AddVMService addVMService = new AddVMService();
        return addVMService.requestAddVMService(noOfVMs);
    }
}
