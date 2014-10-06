/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.service;

import at.ac.tuwien.dsg.linkeddatamodel.DataModelInterface;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
 * @author dsg
 */
@Path("service")
public class ServiceResource {

    @Context
    private UriInfo context;
    public DataModelInterface api=new DataModelInterface();

    /**
     * Creates a new instance of ServiceResource
     */
    public ServiceResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.service.ServiceResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        return("<a>hello</a>");
    }
    
    @PUT
    @Path("/uri")
    @Consumes("text/plain")
    public void putURI(String uri)
    {
       api.addDataModelURI(uri);
    }
    
    
    @PUT
    @Path("/buildingName")
    @Consumes("text/plain")
    public void putBuildingName(String buildingName)
    {
        api.addBuildingName(buildingName);
    }
    
    @PUT
    @Path("/monitoringInformation")
    @Consumes("text/plain")
    public void putMonitoringInformation(Map<String,List<String>> monitoringInformation)
    {
        api.addMonitoringInformation(monitoringInformation);
    }

    /**
     * PUT method for updating or creating an instance of ServiceResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
