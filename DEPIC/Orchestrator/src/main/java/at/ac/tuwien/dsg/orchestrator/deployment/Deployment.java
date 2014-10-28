/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.deployment;


import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.orchestrator.configuration.Configuration;

/**
 *
 * @author Jun
 */
public class Deployment {
    
    public void deployElasticityProcesses(String deploymentDesctiption){
        Configuration config = new Configuration();
        String ip = config.getConfig("SALSA.IP");
        String port = config.getConfig("SALSA.PORT");
        String resource = config.getConfig("SALSA.RESOURCE");
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        ws.callPutMethod(deploymentDesctiption);
    }

    
    
}
