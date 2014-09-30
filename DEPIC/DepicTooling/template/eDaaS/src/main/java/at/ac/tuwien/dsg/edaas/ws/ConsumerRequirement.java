/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edaas.ws;

import at.ac.tuwien.dsg.common.entity.QElementSet;
import at.ac.tuwien.dsg.common.entity.QoRRequest;
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
@Path("ConsumerRequirement")
public class ConsumerRequirement {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ConsumerRequirement
     */
    public ConsumerRequirement() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.edaas.ws.ConsumerRequirement
     * @return an instance of at.ac.tuwien.dsg.edaas.userrequirement.ConsumerRequirement
     */
    
    /*
    @GET
    @Produces("application/xml")
    public at.ac.tuwien.dsg.edaas.userrequirement.ConsumerRequirement getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
*/
    
    
    /**
     * PUT method for updating or creating an instance of ConsumerRequirement
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    @Produces("application/xml")
    public QElementSet putXml(at.ac.tuwien.dsg.common.entity.QoRRequest cReq) {
        
        return new QElementSet();
    }
}
