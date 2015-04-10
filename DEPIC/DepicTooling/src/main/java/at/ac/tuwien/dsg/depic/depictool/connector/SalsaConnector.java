/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.connector;

import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depic.depictool.util.Configuration;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.CloudService;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.ServiceTopology;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.ServiceUnit;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.rSYBL.deploymentDescription.AssociatedVM;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.rSYBL.deploymentDescription.DeploymentDescription;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.rSYBL.deploymentDescription.DeploymentUnit;
import at.ac.tuwien.dsg.cloud.salsa.tosca.extension.SalsaInstanceDescription_VM;
import at.ac.tuwien.dsg.depic.common.deployment.DeployAction;
import at.ac.tuwien.dsg.depic.common.deployment.ElasticService;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.Logger;
import groovy.lang.ListWithDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.xml.bind.JAXBException;


/**
 *
 * @author Jun
 */
public class SalsaConnector {
    
    DeploymentDescription deploymentDescription;
    

    public SalsaConnector() {
    }

    public boolean updateCloudServiceInfo(String cloudServiceID){
        
        Configuration cfg = new Configuration();
        String deploymentDescriptionRest = cfg.getConfig("SALSA.DEPLOYMENT.REST");
        RestfulWSClient ws = new RestfulWSClient(deploymentDescriptionRest.replaceAll("cloudServiceID", cloudServiceID));

        String salsaSpecs = ws.callGetMethod();

        if (!salsaSpecs.contains("<html><head><title>") && !salsaSpecs.equals("") && salsaSpecs != null) {

            Logger.logInfo("DEPLOYMENT STATUS: " + salsaSpecs);
            try {
                deploymentDescription = JAXBUtils.unmarshal(salsaSpecs, DeploymentDescription.class);
            } catch (JAXBException ex) {
                Logger.logInfo(ex.toString());
            }
        } else {
            Logger.logInfo("ON SCALING ...");
            return false;
        }
        
        
        return true;
    }

    public List<ElasticService> getDeployedElasticServices(List<DeployAction> controlActions, List<DeployAction> monitoringActions) {

          
       List<DeploymentUnit> deploymentUnits = deploymentDescription.getDeployments();
       List<ElasticService> elasticServiceList = new ArrayList<ElasticService>();
      
        for (DeploymentUnit du : deploymentUnits) {

            List<AssociatedVM> listOfVMs = du.getAssociatedVM();

            for (AssociatedVM vm : listOfVMs) {

                Logger.logInfo("Service Unit: " + du.getServiceUnitID());
                Logger.logInfo("IP: " + vm.getIp());

                String actionID = du.getServiceUnitID().replaceAll("_SU", "");
                String uri = "http://" + vm.getIp() + ":8080"
                        + findActionRestAPI(du.getServiceUnitID(), controlActions, monitoringActions);

                ElasticService elasticService = new ElasticService(actionID, du.getServiceUnitID(), uri);
                elasticService.setRequest(0);
                elasticServiceList.add(elasticService);

            }
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
