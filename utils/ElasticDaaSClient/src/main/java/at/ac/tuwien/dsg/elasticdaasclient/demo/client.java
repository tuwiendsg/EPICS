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
public class client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int addUserInterval = 6*60*1000;
        int numberOfCustomer =3;
      //  openConnection();
        
        
        int currentCustomerNo = 0;
           do {

               currentCustomerNo++;
                String daasCustomerID = "c" +currentCustomerNo;
                DaasClientDemo daasClientDemo = new DaasClientDemo(daasCustomerID);
                daasClientDemo.start();
            

            try {
                Thread.sleep(addUserInterval);

            } catch (InterruptedException ex) {

            }
        } while (currentCustomerNo<numberOfCustomer);


     //   closeConnection();
        
        
        
    }
    
    
    private static void openConnection(){
      
        String ip = "128.130.172.216";
        String port = "8080";
        String resource = "/DataAssetLoader/rest/dataasset/repo/cassandra/open";
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        ws.callPutMethod("");
    }
    
    private static void closeConnection(){
        

        String ip = "128.130.172.216";
        String port = "8080";
        String resource = "/DataAssetLoader/rest/dataasset/repo/cassandra/close";
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        ws.callPutMethod("");
    }
    
}
