/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.mela;

import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.CloudService;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.ServiceInstance;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.ServiceTopology;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.ServiceUnit;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.rSYBL.deploymentDescription.DeploymentDescription;
import at.ac.tuwien.dsg.cloud.salsa.common.cloudservice.model.rSYBL.deploymentDescription.DeploymentUnit;
import at.ac.tuwien.dsg.cloud.salsa.tosca.extension.SalsaInstanceDescription_VM;
/**
 *
 * @author Jun
 */
public class NewMain1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        RestfulWSClient ws = new RestfulWSClient("http://128.130.172.215:8380/salsa-engine/rest/services/tosca/edaas1CloudService/sybl");
        
        String deploymentDescriptionStr = ws.callGetMethod();
        DeploymentDescription deploymentDescription=null;
         System.out.println("DEPLOYMENT STATUS: " +deploymentDescriptionStr);
         
        try {
        deploymentDescription = JAXBUtils.unmarshal(deploymentDescriptionStr, DeploymentDescription.class);
        } catch (JAXBException ex) {
            Logger.getLogger(NewMain1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
       List<DeploymentUnit> deploymentUnits = deploymentDescription.getDeployments();
       
        
       for (DeploymentUnit du : deploymentUnits){
           System.out.println("Service Unit: " + du.getServiceUnitID());
           System.out.println("IP: " + du.getAssociatedVM().get(0).getIp());
       }
         
    }
    
 
    
}
