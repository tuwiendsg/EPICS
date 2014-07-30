/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.services;

import at.ac.tuwien.dsg.orchestrator.responsetime.QoSEvaluator;

/**
 *
 * @author Jun
 */
public class OrchestratorService implements Runnable {
    
    
        Thread runner;
        String userID;
        
      
        
	public OrchestratorService() {
	}
	public OrchestratorService(String userID) {
            
                this.userID = userID;
                
		runner = new Thread(this, userID); // (1) Create a new thread.
                
                System.out.println(runner.getName());
		//runner.start(); // (2) Start the thread.
	}
	public void run() {
		
                 System.out.println("START ...");
                handleRequest();
                 System.out.println("DONE ...");       
               
        }
    
    
      public void handleRequest() {
          
          
          
            
        int noOfDataObject = 5;
        int i = 0;

  
        while (i < noOfDataObject) {
            
           
            QoSEvaluator eval = new QoSEvaluator();
            eval.evaluate(i, userID);

            System.out.println("Thread: " + runner.getName() + " - obj: " + i);
            System.out.println("Response time: " + eval.getMonitoringData().getAverageResponseTime() + " ms");
        
            
            i++;
        }
          
          
       
        
    }

}
