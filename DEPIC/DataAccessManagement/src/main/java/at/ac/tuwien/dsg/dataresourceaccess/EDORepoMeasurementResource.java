/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataresourceaccess;

import at.ac.tuwien.dsg.common.entity.others.EDORepo;
import at.ac.tuwien.dsg.common.entity.others.EDORepoJAXB;
import at.ac.tuwien.dsg.dataresourceaccess.edorepository.EDORepoAccessManagement;
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
@Path("EDORepoMeasurement")
public class EDORepoMeasurementResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EDORepoMeasurementResource
     */
    public EDORepoMeasurementResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.dataresourceaccess.EDORepoMeasurementResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of EDORepoMeasurementResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    @Produces("application/xml")
    public void putXml(String content) {
        
        EDORepoJAXB eDORepoJAXB = new EDORepoJAXB();
        EDORepo eDORepo = eDORepoJAXB.unmarshallingObject(content);
             
        EDORepoAccessManagement repoAccessManagement = new EDORepoAccessManagement();
        
        repoAccessManagement.saveEDO2Repo(eDORepo);
   
    }
}
