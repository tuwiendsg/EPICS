/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataresourceaccess;

import at.ac.tuwien.dsg.common.entity.DataObject;
import at.ac.tuwien.dsg.common.entity.others.EDORepo;
import at.ac.tuwien.dsg.common.entity.others.EDORepoJAXB;
import at.ac.tuwien.dsg.common.jaxb.DataObjectJAXB;
import at.ac.tuwien.dsg.dataresourceaccess.edorepository.EDORepoAccessManagement;
import at.ac.tuwien.dsg.dataresourceaccess.powerconsumption.PowerConsumptionDB;
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
@Path("EDORepo")
public class EDORepoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EDORepoResource
     */
    public EDORepoResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.dataresourceaccess.EDORepoResource
     * @return an instance of java.lang.String
     */
    @GET
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        
        
    
        
        return "Saved";
    }

    /**
     * PUT method for updating or creating an instance of EDORepoResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    @Produces("application/xml")
    public String putXml(String content) {
        
        System.out.println("DataAccessManagement: get EDO index " + content);
        
        EDORepoAccessManagement repoAccessManagement = new EDORepoAccessManagement();
        String xmlStr = repoAccessManagement.getRawEDO(content);
        System.out.println("DataAccessManagement: xmlStr " + xmlStr);
        
        return xmlStr;
        
    }
}
