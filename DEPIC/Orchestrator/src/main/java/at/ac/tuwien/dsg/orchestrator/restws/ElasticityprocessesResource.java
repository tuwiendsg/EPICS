/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.restws;

import at.ac.tuwien.dsg.orchestrator.configuration.Configuration;

import at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore.ElasticityProcessesStore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Jun
 */
@Path("elasticityprocesses")
public class ElasticityprocessesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ElasticityprocessesResource
     */
    public ElasticityprocessesResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.orchestrator.restws.ElasticityprocessesResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        Configuration cfg = new Configuration();
        String configStr = "ORCHESTRATOR: " + cfg.getConfig("DB.ELASTICITY.PROCESSES.REPO.IP");
        return configStr;
    }

    /**
     * PUT method for updating or creating an instance of ElasticityprocessesResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String getElasticityProcesses(String eDaaSName) {
        
        Logger.getLogger(ElasticityprocessesResource.class.getName()).log(Level.INFO, "RECIEVED: " + eDaaSName);
        ElasticityProcessesStore eps = new ElasticityProcessesStore();
        String elProcess = eps.getElasticityProcesses(eDaaSName);
     
        return elProcess;
    }
    
   
}
