/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.elasticdaasclient.demo;

/**
 *
 * @author Jun
 */

import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.elasticdaasclient.utils.RestfulWSClient;


public class RestClientDAFM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        RestClientDAFM client = new RestClientDAFM();

        //client.startRunningDAFM("edaas1");
        client.startGPSProducer();
        
        
     
    }
    
    private void startRunningDAFM(String eDaaSName) {
       
        RestfulWSClient ws = new RestfulWSClient("http://128.130.172.214:8080/data-asset-loader/rest/dataasset/dafm/start");
        ws.callPutMethod(eDaaSName);
        
        
        
      
    }
    
    private void startGPSProducer(){
        
        RestfulWSClient ws = new RestfulWSClient("http://128.130.172.214:8080/DAFServices/rest/daf/producer");
        ws.callGetMethod();
              
        
    }
    
 
}
