/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.generator;

import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;

/**
 *
 * @author Jun
 */
public class UpdateIP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String configureUri = "http://localhost:8080/VAM/rest/monitor/conf";
        RestfulWSClient ws = new RestfulWSClient(configureUri);
                int responseCode =  ws.callPutMethodRC("10.1.1.1");
                System.out.println("CODE: " + responseCode);
    }
    
}
