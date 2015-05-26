/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.esperstreamprocessing.queueclient;

/**
 *
 * @author Jun
 */
public class demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String mom_ip = "128.130.172.214";
        String mom_port = "9124";
        String queue_name = "DAF";
        
        QueueClient queueClient = new QueueClient(mom_ip, mom_port, queue_name);
        queueClient.run();
   
        
        
    }
    
}
