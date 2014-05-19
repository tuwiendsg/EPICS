/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.datagovernance.utility;

/**
 *
 * @author Jun
 */
public class DeploymentConfiguration {
    
   
    public String getDeploymentScript(String functID){

        String deploymentScriptURL="";
        if (functID.equals("bargraph01")) {
            deploymentScriptURL ="http://128.130.172.216/elfinder/files/tosca_governance_hadoop_low_level.xml";
        } else if (functID.equals("bargraph02")) {
            deploymentScriptURL ="http://128.130.172.216/elfinder/files/tosca_governance_hadoop_low_level.xml";
        }
        
        
        return deploymentScriptURL;
        
    }
    
}
