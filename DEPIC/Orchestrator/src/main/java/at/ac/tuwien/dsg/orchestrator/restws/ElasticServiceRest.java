/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.restws;

import at.ac.tuwien.dsg.common.deployment.ElasticService;
import at.ac.tuwien.dsg.common.deployment.ElasticServices;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitoringSession;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.orchestrator.dataelasticitymonitor.DataElasticityMonitor;
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
@Path("elasticservice")
public class ElasticServiceRest {
    
    @Context
    private UriInfo context;

    public ElasticServiceRest() {
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object

        return "elasticservice";
    }

    /**
     * PUT method for updating or creating an instance of ElasticityprocessesResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void updateElasticServices(String elasticSerivcesXML) {
        
          Logger.getLogger(ElasticServiceRest.class.getName()).log(Level.INFO, "Updating Elastic Serivces ... ");
        
        ElasticServices elasticServices=null;
        try {
            elasticServices = JAXBUtils.unmarshal(elasticSerivcesXML, ElasticServices.class);
        } catch (JAXBException ex) {
            Logger.getLogger(ElasticServiceRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<ElasticService> listOfElasticSerivces = elasticServices.getListOfElasticServices();
        
        ElasticServiceRegistry.updateElasticServices(listOfElasticSerivces);
      
        ElasticityProcessesStore eps = new ElasticityProcessesStore();
        eps.cleanElasticServices();
        eps.storeElasticServices(listOfElasticSerivces);
      
        
    }
}
