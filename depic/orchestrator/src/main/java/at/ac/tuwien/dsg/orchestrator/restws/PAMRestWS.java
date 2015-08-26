/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.restws;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlAction;
import at.ac.tuwien.dsg.depic.common.repository.ElasticProcessRepositoryManager;
import at.ac.tuwien.dsg.depic.common.repository.PrimitiveActionMetadataManager;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
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
@Path("pam")
public class PAMRestWS {
    
    @Context
    private UriInfo context;

    public PAMRestWS() {
    }
    
    
    @PUT
    @Path("get/monitoringaction")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String getMonitoringAction(String actionName) {

        System.out.println("Action Name: " + actionName);
         PrimitiveActionMetadataManager pamm = new PrimitiveActionMetadataManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        List<MonitoringAction> listOfActions = pamm.getMonitoringActionList();
        
        String actionStr = "";
        for (MonitoringAction action : listOfActions) {
            if (action.getMonitoringActionName().equals(actionName)){
                try {
                    actionStr = JAXBUtils.marshal(action, MonitoringAction.class);
                } catch (JAXBException ex) {
                    Logger.getLogger(PAMRestWS.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        

        return actionStr;
    }
    
    
    @PUT
    @Path("get/adjustmentaction")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String getAdjustmentAction(String actionName) {

        System.out.println("Action Name: " + actionName);
         PrimitiveActionMetadataManager pamm = new PrimitiveActionMetadataManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        List<AdjustmentAction> listOfActions = pamm.getAdjustmentActionList();
        
        String actionStr = "";
        for (AdjustmentAction action : listOfActions) {
            if (action.getActionName().equals(actionName)){
                try {
                    actionStr = JAXBUtils.marshal(action, AdjustmentAction.class);
                } catch (JAXBException ex) {
                    Logger.getLogger(PAMRestWS.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        

        return actionStr;
    }
    
    @PUT
    @Path("get/resourcecontrolaction")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String getResourceControlAction(String actionName) {

        System.out.println("Action Name: " + actionName);
         PrimitiveActionMetadataManager pamm = new PrimitiveActionMetadataManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        List<ResourceControlAction> listOfActions = pamm.getResourceControlActionList();
        
        String actionStr = "";
        for (ResourceControlAction action : listOfActions) {
            if (action.getActionName().equals(actionName)){
                try {
                    actionStr = JAXBUtils.marshal(action, ResourceControlAction.class);
                } catch (JAXBException ex) {
                    Logger.getLogger(PAMRestWS.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
        

        return actionStr;
    }
}
