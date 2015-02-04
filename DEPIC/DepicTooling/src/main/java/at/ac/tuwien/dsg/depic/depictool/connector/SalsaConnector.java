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
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
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
    
    public void printCloudServiceInfo(){
        
          
       List<DeploymentUnit> deploymentUnits = deploymentDescription.getDeployments();
       
        
       for (DeploymentUnit du : deploymentUnits){
           System.out.println("Service Unit: " + du.getServiceUnitID());
           System.out.println("IP: " + du.getAssociatedVM().get(0).getIp());
       }
        
    }
    
   
}
