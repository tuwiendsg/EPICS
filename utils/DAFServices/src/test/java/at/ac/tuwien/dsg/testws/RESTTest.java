/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.testws;

import at.ac.tuwien.dsg.mlr.util.RestfulWSClient;

/**
 *
 * @author Jun
 */
public class RESTTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        testMOMReader();
        testSpeedEstimation();
        testOutputer();
    }
    
    private static void testMOMReader(){
        String uri = "http://localhost:8080/dafservices/rest/daf/momreader";
        
        RestfulWSClient ws = new RestfulWSClient(uri);
        String rs  = ws.callPutMethod("");
        System.out.println("rs: " + rs);
                
                
    }
    
    private static void testSpeedEstimation(){
        String uri = "http://localhost:8080/dafservices/rest/daf/speedestimation";
        
        RestfulWSClient ws = new RestfulWSClient(uri);
        String rs  = ws.callPutMethod("");
        System.out.println("rs: " + rs);
                
                
    }
    
    private static void testOutputer(){
        String uri = "http://128.130.172.214:8080/DAFServices/rest/daf/outputer";
        
        RestfulWSClient ws = new RestfulWSClient(uri);
        String rs  = ws.callPutMethod("");
        System.out.println("rs: " + rs);
                
                
    }
    
}
