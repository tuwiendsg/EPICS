/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataresourceaccess;

import at.ac.tuwien.dsg.common.entity.DataObject;
import at.ac.tuwien.dsg.common.jaxb.DataObjectJAXB;
import at.ac.tuwien.dsg.dataresourceaccess.powerconsumption.PowerConsumptionDB;
import java.util.List;
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
@Path("powerconsumption")
public class PowerconsumptionResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PowerconsumptionResource
     */
    public PowerconsumptionResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.dataresourceaccess.PowerconsumptionResource
     * @return an instance of java.lang.String
     */
    @GET
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        
    
       
        
        return "";
    }

    /**
     * PUT method for updating or creating an instance of PowerconsumptionResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    @Produces("application/xml")
    public String putXml(String content) {
        
        System.out.println("Data Object Index: " + content);
        PowerConsumptionDB powerConsumptionDB = new PowerConsumptionDB();
        DataObject dataObject = powerConsumptionDB.getDataItems(Integer.parseInt(content));
        
        
        DataObjectJAXB dataObjectJAXB = new DataObjectJAXB();
        String xmlStr = dataObjectJAXB.marshallingObject(dataObject);
        
        
        return xmlStr;
        
    }
}
