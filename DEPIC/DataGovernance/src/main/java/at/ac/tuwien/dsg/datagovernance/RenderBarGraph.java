/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.datagovernance;

import at.ac.tuwien.dsg.datagovernance.utility.DeploymentConfiguration;
import at.ac.tuwien.dsg.datagovernance.utility.SALSARestWS;
import at.ac.tuwien.dsg.depic.utility.Utilities;

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
@Path("bargraph")
public class RenderBarGraph {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RenderBarGraph
     */
    public RenderBarGraph() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.datagovernance.RenderBarGraph
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of RenderBarGraph
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String putXml(String content) {
        System.out.println("DataGovernance recieve: " + content);
        System.out.println("Start Service ... ");
        
        
        Utilities util = new Utilities();
        DeploymentConfiguration conf = new DeploymentConfiguration();
        
        String deploymentXML = util.urlToString(conf.getDeploymentScript(content));
        System.out.println("Deployement Script: " + deploymentXML);
        SALSARestWS restWS = new SALSARestWS();
        //restWS.sendDeploymentDescription(deploymentXML);
        
        
        
    return content;
    }
}
