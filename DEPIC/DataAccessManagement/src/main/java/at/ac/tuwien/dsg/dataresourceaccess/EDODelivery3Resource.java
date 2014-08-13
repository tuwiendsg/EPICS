/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataresourceaccess;

import at.ac.tuwien.dsg.common.entity.others.EDORepo;
import at.ac.tuwien.dsg.common.entity.others.EDORepoJAXB;
import at.ac.tuwien.dsg.dataresourceaccess.edorepository.EDODeliveryAccessManagement;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author Jun
 */
@Path("EDODelivery3")
public class EDODelivery3Resource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EDODelivery3Resource
     */
    public EDODelivery3Resource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.dataresourceaccess.EDODelivery3Resource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of EDODelivery3Resource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
        
        EDORepoJAXB eDORepoJAXB = new EDORepoJAXB();
        EDORepo eDORepo = eDORepoJAXB.unmarshallingObject(content);
             
        EDODeliveryAccessManagement eDODeliveryAccessManagement = new EDODeliveryAccessManagement();
        eDODeliveryAccessManagement.updateDeliveryEDORepo(eDORepo);
    }
}
