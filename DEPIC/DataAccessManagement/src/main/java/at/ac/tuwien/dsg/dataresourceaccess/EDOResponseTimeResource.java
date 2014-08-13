/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataresourceaccess;

import at.ac.tuwien.dsg.common.entity.others.EDORepo;
import at.ac.tuwien.dsg.common.entity.others.EDORepoJAXB;
import at.ac.tuwien.dsg.dataresourceaccess.edorepository.ResponseTimeAccessManagement;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author Jun
 */
@Path("EDOResponseTime")
public class EDOResponseTimeResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EDOResponseTimeResource
     */
    public EDOResponseTimeResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.dataresourceaccess.EDOResponseTimeResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of EDOResponseTimeResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
        EDORepoJAXB jAXB = new EDORepoJAXB();
        EDORepo eDORepo = jAXB.unmarshallingObject(content);
        
        
        String key = String.valueOf(eDORepo.getObjID()) +";"+ String.valueOf(eDORepo.getUserID());
        ResponseTimeAccessManagement responseTimeAccessManagement = new ResponseTimeAccessManagement();
        responseTimeAccessManagement.saveResponseTime(key, eDORepo.getResponseTime());
        
        
    
    
    }
}
