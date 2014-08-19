/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.services;

import at.ac.tuwien.dsg.common.entity.others.EDORepo;
import at.ac.tuwien.dsg.common.entity.others.EDORepoJAXB;
import at.ac.tuwien.dsg.orchestrator.responsetime.OrchestratorServiceController;
import at.ac.tuwien.dsg.common.rest.ResponseTimeRest;
import at.ac.tuwien.dsg.orchestrator.properties.PropertiesConfiguration;
import at.ac.tuwien.dsg.orchestrator.properties.VMCluster;
import java.util.Calendar;
import java.util.List;

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
          
          
          
            
        int noOfDataObject = 300;
        int i = 0;

  
        while (i < noOfDataObject) {
            
           
            OrchestratorServiceController eval = new OrchestratorServiceController();
            eval.evaluate(i, userID);
            
            
            

            System.out.println("User: " + runner.getName() + " - obj: " + i);
            
            //double responseTime = eval.getMonitoringData().getAverageResponseTime();
            double responseTime = eval.getResponseTime();
            
            System.out.println("Response time: " + responseTime + " ms");
            
            PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
            List<VMCluster> ls =  propertiesConfiguration.getProperties();
            
            VMCluster vmCluster = ls.get(0);
        
   
            Calendar calendar = Calendar.getInstance();
        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int time = minute*60 + second;
            
            
            ResponseTimeRest responseTimeRest = new ResponseTimeRest(vmCluster.getIp(), vmCluster.getPort());
            responseTimeRest.updateResponseTime(userID, i, responseTime, time);
            
            
            
            
            i++;
        }
          
          
       
        
    }

}
