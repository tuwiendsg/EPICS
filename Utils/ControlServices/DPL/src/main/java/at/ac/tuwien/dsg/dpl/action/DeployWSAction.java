/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dpl.action;

import at.ac.tuwien.dsg.externalserviceutils.Configuration;
import at.ac.tuwien.dsg.externalserviceutils.RestfulWSClient;

/**
 *
 * @author Jun
 */
public class DeployWSAction {
    
    public void deployWS(String serviceID) {
        
        Configuration config = new Configuration();
        
        String ip = config.getConfig("SALSA.IP");
        String port = config.getConfig("SALSA.PORT");
        String resource = config.getConfig("SALSA.RESOURCE.DEPLOYWS");
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        ws.callPutMethod(serviceID);
        
        
    }
    
}
