/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.rmctrl.action;

import at.ac.tuwien.dsg.externalserviceutils.Configuration;
import at.ac.tuwien.dsg.externalserviceutils.RestfulWSClient;

/**
 *
 * @author Jun
 */
public class RemoveVMAction {
    
    public String removeVM(String noOfVM) {
        Configuration config = new Configuration();
        
        String ip = config.getConfig("SALSA.IP");
        String port = config.getConfig("SALSA.PORT");
        String resource = config.getConfig("SALSA.RESOURCE.ADDVM");
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        String vmIP = ws.callPutMethod(noOfVM);
        
        return vmIP;
    }
    
}
