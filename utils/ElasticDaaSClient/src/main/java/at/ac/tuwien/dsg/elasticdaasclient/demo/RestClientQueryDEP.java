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
public class RestClientQueryDEP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         RestClientQueryDEP client = new RestClientQueryDEP();

        client.getQoR("edaas1");
        client.getDAF("edaas1");
        client.getDEP("edaas1");
    }
    
    private void getQoR(String eDaaSName) {
       
        RestfulWSClient ws = new RestfulWSClient("http://localhost:8080/Orchestrator/rest/dep/get/qor");
        String rs = ws.callPutMethod(eDaaSName);
        System.out.println(rs);
    }
    
    private void getDAF(String eDaaSName) {
       
        RestfulWSClient ws = new RestfulWSClient("http://localhost:8080/Orchestrator/rest/dep/get/daf");
        String rs = ws.callPutMethod(eDaaSName);
        System.out.println(rs);
    }
    
    private void getDEP(String eDaaSName) {
       
        RestfulWSClient ws = new RestfulWSClient("http://localhost:8080/Orchestrator/rest/dep/get/dep");
        String rs = ws.callPutMethod(eDaaSName);
        System.out.println(rs);
    }
    
}
