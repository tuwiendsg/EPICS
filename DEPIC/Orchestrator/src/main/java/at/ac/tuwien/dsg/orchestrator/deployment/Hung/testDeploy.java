/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.deployment.Hung;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class testDeploy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        DeploymentDescription deploymentDescription = sampleDeploymentDescription();
       
        //    DeploymentDescriptionJAXB deploymentDescriptionJAXB = new DeploymentDescriptionJAXB();      
        System.out.println("BEFORE DEPLOYMENT \n");
        System.out.println(deploymentDescription.toXMLString());
        
        SALSAConnector salsaCon = new SALSAConnector(deploymentDescription);
       
        System.out.println("\n TOSCA XML \n");
        System.out.println(salsaCon.toToscaString());
        salsaCon.newServicesInstance();
        
        System.out.println("\n AFTER DEPLOYMENT \n");
        System.out.println(deploymentDescription.toXMLString());
        
        
        
        
    }
    
    public static DeploymentDescription sampleDeploymentDescription(){
              
        List<DeployAction> listOfDeployActions = new ArrayList<>();        
                
        listOfDeployActions.add(new DeployAction("LSR", "LeastSquareRegession", "war", "http://128.130.172.215/salsa/upload/files/Jun/artifactExample/PoliceDaaS.war"));
        listOfDeployActions.add(new DeployAction("action2", "test 2 name", "war", "http://128.130.172.215/salsa/upload/files/Jun/artifactExample/PoliceDaaS.war"));
        //listOfDeployActions.add(new DeployAction("action3", "test 3 name", "war", "http://128.130.172.215/salsa/upload/files/Jun/artifactExample/PoliceDaaS.war"));
        
        DeploymentDescription deploymentDescription = new DeploymentDescription(listOfDeployActions);
        
        return  deploymentDescription;
    }

}
