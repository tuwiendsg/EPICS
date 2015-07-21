/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.elasticdaasclient.testing;


import at.ac.tuwien.dsg.elasticdaasclient.utils.RestfulWSClient;


/**
 *
 * @author Jun
 */
public class ElasticServiceMonitor implements Runnable {
   private Thread t;
   private String threadName;
   
   ElasticServiceMonitor( String name){
        threadName = name;
    }

    public void run() {

        
    }

    public void start() {
        if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
      }
   }

}