/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.deployment;

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
       
        DeploymentDescriptionJAXB deploymentDescriptionJAXB = new DeploymentDescriptionJAXB();      
        String xmlDeploymentDescription = deploymentDescriptionJAXB.marshallingObject(deploymentDescription);
        
        DeploymentRestWS deploymentRestWS = new DeploymentRestWS("salsa ip", "salsa port");
        deploymentRestWS.callDeploymentService(xmlDeploymentDescription);
        
        
        
    }
    
    public static DeploymentDescription sampleDeploymentDescription(){
        String actionID="LSR";
        String actionName="LeastSquareRegession";
        String ip="";
        String port="";
        String artifact="";
        
        DeployAction deployAction = new DeployAction(actionID, actionName, ip, port, artifact);
        
        List<DeployAction> listOfDeployActions = new ArrayList<>();
                
        listOfDeployActions.add(deployAction);
        
        DeploymentDescription deploymentDescription = new DeploymentDescription(listOfDeployActions);
        
        return  deploymentDescription;
    }

}
