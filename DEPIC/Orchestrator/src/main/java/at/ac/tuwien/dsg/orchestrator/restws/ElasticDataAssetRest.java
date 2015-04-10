/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.restws;

import at.ac.tuwien.dsg.depic.common.deployment.ElasticService;
import at.ac.tuwien.dsg.depic.common.deployment.ElasticServices;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.ElasticDataAsset;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore.ElasticityProcessesStore;
import at.ac.tuwien.dsg.orchestrator.registry.ElasticServiceRegistry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
@Path("eda")
public class ElasticDataAssetRest {
    
    
    @Context
    private UriInfo context;

    public ElasticDataAssetRest() {
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object

        return "eda";
    }

    /**
     * PUT method for updating or creating an instance of ElasticityprocessesResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String getElasticDataAsset(String dataAssetID) {
        
          Logger.getLogger(ElasticDataAssetRest.class.getName()).log(Level.INFO, "Get EDA model ... ");
        
          ElasticityProcessesStore eps = new ElasticityProcessesStore();
          ElasticDataAsset eda =  eps.getElasticDataAsset(dataAssetID);
        
          String edaXML="";
        try {
            edaXML = JAXBUtils.marshal(eda, ElasticDataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(ElasticDataAssetRest.class.getName()).log(Level.SEVERE, null, ex);
        }
          return edaXML;
          
    }
    
    
}
