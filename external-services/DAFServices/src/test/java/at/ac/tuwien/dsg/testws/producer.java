/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.testws;

/**
 *
 * @author Jun
 */
public class producer {

    /**
     * @param args the command line argu
     * ments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        String mom_ip = "128.130.172.230";
        String mom_port = "9124";
        String queue_name = "DAAS";

        
        QueueProducer queueProducer = new QueueProducer(mom_ip, mom_port, queue_name);
        queueProducer.produce();
    }
    
}
