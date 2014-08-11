/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.demo;

import at.ac.tuwien.dsg.common.rest.QualityEnforcementREST;
import at.ac.tuwien.dsg.common.rest.QualityEvaluatorREST;
import at.ac.tuwien.dsg.orchestrator.responsetime.Monitor;
import at.ac.tuwien.dsg.orchestrator.responsetime.OrchestratorServiceController;
import at.ac.tuwien.dsg.orchestrator.services.DeliveryService;
import at.ac.tuwien.dsg.orchestrator.services.OrchestratorService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jun
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        
        int noOfUser = 1;
        List<Thread> threadList = new ArrayList<>();
                    
                
            
                for (int i=0;i<noOfUser;i++) {
                    
                    String userID = String.valueOf(i+1);
                    Thread ti = new Thread(new OrchestratorService(userID), userID);
                    threadList.add(ti);
                }
                
               
                for (Thread t : threadList) {
                    t.start();
                }
            
             
                
		try {
			//delay for one second
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
		}
		

    }

}
