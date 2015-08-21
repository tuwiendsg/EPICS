/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.restws;

/**
 *
 * @author Jun
 */

import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.DataElasticityManagementProcess;
import at.ac.tuwien.dsg.depic.common.entity.runtime.MonitoringSession;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller.DataElasticityMonitor;

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
import javax.xml.bind.JAXBException;


@Path("monitoring")
public class MonitoringProcessRest {
    
    @Context
    private UriInfo context;

    public MonitoringProcessRest() {
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object

        return "monitoring";
    }

    /**
     * PUT method for updating or creating an instance of ElasticityprocessesResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void startMonitoringService(String monitoringSessionXML) {
        
        Logger.getLogger(MonitoringProcessRest.class.getName()).log(Level.INFO, "START Monitoring Session ... :" + monitoringSessionXML);
        
        MonitoringSession monitoringSession=null;
        
        try {
            monitoringSession = JAXBUtils.unmarshal(monitoringSessionXML, MonitoringSession.class);
        } catch (JAXBException ex) {
            Logger.getLogger(MonitoringProcessRest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      DataElasticityMonitor dataElasticityMonitor = new DataElasticityMonitor(monitoringSession);
      dataElasticityMonitor.start();
        
    }
    
    
}
