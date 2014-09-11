/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity;

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
@Path("EDA")
public class EDAResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EDAResource
     */
    public EDAResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.common.entity.EDAResource
     * @return an instance of at.ac.tuwien.dsg.common.entity.ElasticDataAsset
     */
    
    /*
    @GET
    @Produces("application/xml")
    public ElasticDataAsset getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
*/
    /**
     * PUT method for updating or creating an instance of EDAResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    @Produces("application/xml")
    public ElasticDataAsset putXml(EDARequest content) {
    
        return new ElasticDataAsset();
    } 
}
