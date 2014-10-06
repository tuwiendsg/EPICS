/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.service;

import at.ac.tuwien.dsg.linkeddatamodel.DataModelInterface;
import javax.ws.rs.POST;
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
    @Path("/enrichinfo/monitoringobject")
    @Produces("text/plain")
    public String getXml() {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        //String hello=new TestGet().hello();
        return "Sensor 11 monitors Methane gas in block 1";
    }
    
    
    
    
    @PUT
    @Path("/buildingName")
    @Consumes("text/plain")
    public void putBuildingName(String uri,String buildingName)
    {
        api.addBuildingName(uri,buildingName);
    }
    
    @PUT
    @Path("/monitoringInformation")
    @Consumes("text/plain")
    public void putMonitoringInformation(String uri, String buildingName, Map<String,List<String>> monitoringInformation)
    {
        api.addMonitoringInformation(uri, buildingName, monitoringInformation);
    }
    
    @PUT
    @Path("/rdfGraphStorage")
    @Consumes("text/plain")
    public void putGraphStorage(String fileName,String fileuri)
    {
        api.addRDFGraphGeneration(fileName, fileuri);
    }
    
    @POST
    @Path("/monitoringInformation")
    @Consumes("text/plain")
    @Produces("text/plain")
    public LinkedList<String> postRetrieve(String subject,String predicate)
    {
        LinkedList<String> monitoredInformation=api.addRetrieveInformation(subject,predicate);
        return monitoredInformation;
    }

    
}
