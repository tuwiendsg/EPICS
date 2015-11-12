/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.elasticdaasclient.demo;

import at.ac.tuwien.dsg.elasticdaasclient.utils.RestfulWSClient;

/**
 *
 * @author Jun
 */
public class RestClientPAM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        RestClientPAM client = new RestClientPAM();

        client.getMonitoringAction("VAM");
        client.getAdjustmentAction("VAA");
        client.getResourceControlAction("DTM");
        
    }
    
    private void getMonitoringAction(String actionName) {
       
        RestfulWSClient ws = new RestfulWSClient("http://localhost:8080/Orchestrator/rest/pam/get/monitoringaction");
        String rs = ws.callPutMethod(actionName);
        System.out.println(rs);
    }
    
    private void getAdjustmentAction(String actionName) {
       
        RestfulWSClient ws = new RestfulWSClient("http://localhost:8080/Orchestrator/rest/pam/get/adjustmentaction");
        String rs = ws.callPutMethod(actionName);
        System.out.println(rs);
    }
    
    private void getResourceControlAction(String actionName) {
       
        RestfulWSClient ws = new RestfulWSClient("http://localhost:8080/Orchestrator/rest/pam/get/resourcecontrolaction");
        String rs = ws.callPutMethod(actionName);
        System.out.println(rs);
    }
    
}
