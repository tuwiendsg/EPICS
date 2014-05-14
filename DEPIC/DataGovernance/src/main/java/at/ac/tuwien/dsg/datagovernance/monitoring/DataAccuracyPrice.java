/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.datagovernance.monitoring;

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
@Path("DataAccuracyPrice")
public class DataAccuracyPrice {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of DataAccuracyPrice
     */
    public DataAccuracyPrice() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.datagovernance.monitoring.DataAccuracyPrice
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        DataAccuracy da = new DataAccuracy();
        return da.getDataAccuracyPrice();
    }

    /**
     * PUT method for updating or creating an instance of DataAccuracyPrice
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
