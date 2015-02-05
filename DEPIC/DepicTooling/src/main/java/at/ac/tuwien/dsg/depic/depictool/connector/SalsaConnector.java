/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.connector;

import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depictool.util.Configuration;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.CloudService;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.ServiceTopology;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.ServiceUnit;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.rSYBL.deploymentDescription.DeploymentDescription;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.rSYBL.deploymentDescription.DeploymentUnit;
import at.ac.tuwien.dsg.cloud.salsa.tosca.extension.SalsaInstanceDescription_VM;
import at.ac.tuwien.dsg.common.deployment.DeployAction;
import at.ac.tuwien.dsg.common.deployment.ElasticService;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;


/**
 *
 * @author Jun
 */
public class SalsaConnector {
    
    DeploymentDescription deploymentDescription;


    public SalsaConnector() {
    }

    public void updateCloudServiceInfo(String cloudServiceID){
        
        Configuration cfg = new Configuration();
        String deploymentDescriptionRest = cfg.getConfig("SALSA.DEPLOYMENT.REST");
        RestfulWSClient ws = new RestfulWSClient(deploymentDescriptionRest.replaceAll("cloudServiceID", cloudServiceID));
        
        String salsaSpecs = ws.callGetMethod();
        
         System.out.println("DEPLOYMENT STATUS: " +salsaSpecs);
        try {
        deploymentDescription = JAXBUtils.unmarshal(salsaSpecs, DeploymentDescription.class);
        } catch (JAXBException ex) {
            Logger.getLogger(SalsaConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public List<ElasticService> getDeployedElasticServices(List<DeployAction> controlActions, List<DeployAction> monitoringActions){
        
          
       List<DeploymentUnit> deploymentUnits = deploymentDescription.getDeployments();
       List<ElasticService> elasticServiceList = new ArrayList<ElasticService>();
        
       for (DeploymentUnit du : deploymentUnits){
           System.out.println("Service Unit: " + du.getServiceUnitID());
           System.out.println("IP: " + du.getAssociatedVM().get(0).getIp());
           
           String actionID = du.getServiceUnitID().replaceAll("_SU", "");
           String uri = "http://" + du.getAssociatedVM().get(0).getIp()+":8080" + 
                   findActionRestAPI(du.getServiceUnitID(), controlActions, monitoringActions);
           
           ElasticService elasticService = new ElasticService(actionID, du.getServiceUnitID(), uri);
           elasticServiceList.add(elasticService);
       }
       
       return elasticServiceList;
        
    }
    
    private String findActionRestAPI(String su_id, List<DeployAction> controlActions, List<DeployAction> monitoringActions){
        String api="";
        for (DeployAction dpl : controlActions){
            if ((dpl.getActionID()+"_SU").equals(su_id)){
                api = dpl.getApiEndpoint();
                break;
            }
        }
        for (DeployAction dpl : monitoringActions){
            if ((dpl.getActionID()+"_SU").equals(su_id)){
                api = dpl.getApiEndpoint();
                break;
            }
        }
        
        return api;
        
    }
   
}
