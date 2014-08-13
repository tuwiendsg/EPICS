/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataresourceaccess;

import at.ac.tuwien.dsg.common.entity.others.MySQLConnection;
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
@Path("EDORepoClean")
public class EDORepoCleanResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EDORepoCleanResource
     */
    public EDORepoCleanResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.dataresourceaccess.EDORepoCleanResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        
        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.emptyAllTables();
        
        return "OK";
    }

    /**
     * PUT method for updating or creating an instance of EDORepoCleanResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
