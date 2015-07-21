/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.elasticdaasclient.testing;

/**
 *
 * @author Jun
 */
public class WorkLoadTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        for (int i=0;i<10;i++) {
        ElasticServiceMonitor thread = new ElasticServiceMonitor("Thread"+i);
        thread.start();
        }
    }
    
}
