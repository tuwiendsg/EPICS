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
public class RestClientDataSourceEmulator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        RestClientDataSourceEmulator client = new RestClientDataSourceEmulator();
        client.startGPSProducer();
    }
    
      private void startGPSProducer(){
        
        RestfulWSClient ws = new RestfulWSClient("http://128.130.172.214:8080/DAFServices/rest/daf/producer");
        ws.callGetMethod();
              
        
    }
    
 
    
}
