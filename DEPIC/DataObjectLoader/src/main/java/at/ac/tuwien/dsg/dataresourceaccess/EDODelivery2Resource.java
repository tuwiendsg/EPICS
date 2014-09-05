/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataresourceaccess;

import at.ac.tuwien.dsg.common.entity.others.EDORepo;
import at.ac.tuwien.dsg.common.entity.others.EDORepoJAXB;
import at.ac.tuwien.dsg.dataresourceaccess.edorepository.EDODeliveryAccessManagement;
import at.ac.tuwien.dsg.dataresourceaccess.edorepository.ResponseTimeAccessManagement;
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
@Path("EDODelivery2")
public class EDODelivery2Resource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EDODelivery2Resource
     */
    public EDODelivery2Resource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.dataresourceaccess.EDODelivery2Resource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of EDODelivery2Resource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    @Produces("application/xml")
    public String putXml(String key) {
        
        String xmlStr="";
             
        EDODeliveryAccessManagement eDODeliveryAccessManagement = new EDODeliveryAccessManagement();
        xmlStr =eDODeliveryAccessManagement.getDeliveryEDOXML(key);
        
        
        ResponseTimeAccessManagement responseTimeAccessManagement = new ResponseTimeAccessManagement();
        double responseTime= responseTimeAccessManagement.getResponseTime(key);
        int timeStamp = responseTimeAccessManagement.getTimeStamp(key);
        
        EDORepoJAXB jAXB = new EDORepoJAXB();
        EDORepo eDORepo = jAXB.unmarshallingObject(xmlStr);
        eDORepo.setResponseTime(responseTime);
        eDORepo.setTime(timeStamp);
        
        
        
        xmlStr = jAXB.marshallingObject(eDORepo);
        
        
        
        
        
        
        return xmlStr;
    }
}
