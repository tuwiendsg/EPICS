/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qualityevaluator.services;

import at.ac.tuwien.dsg.common.rest.QualityEnforcementREST;
import at.ac.tuwien.dsg.qualityevaluator.edo.ElasticDataObjectConstructor;

/**
 *
 * @author Jun
 */
public class QualityEvaluatorController implements Runnable {
    
    Thread runner;
    String userID;
    String objID;
       
        
	public QualityEvaluatorController() {
	}
	public QualityEvaluatorController(String content) {
            
            
            System.out.println("QualityEvaluator: " + content);
            String[] vals = content.split(";");
        
            this.userID = vals[0];
            this.objID = vals[1];
            
            
            runner = new Thread(this); // (1) Create a new thread.
                
	
	}
	public void run() {
                
		startQualityEvaluator();
                
        }
    public void startQualityEvaluator(){
        
        
        ElasticDataObjectConstructor elasticDataObjectConstructor = new ElasticDataObjectConstructor();
        elasticDataObjectConstructor.constructEDO(Integer.parseInt(userID), Integer.parseInt(objID));
        
        // start quality enforcement
            System.out.println("Start Enforcement, userUD: " + userID + " , objID: " + objID);
            QualityEnforcementREST qualityEnforcementREST = new QualityEnforcementREST("localhost","8080");
            String key = userID +";"+ objID;
            qualityEnforcementREST.callQualityEnforcement(key);
        
        
        
        /*
        int noOfDataObjects = 100;
        
        int i = 0;
        
        
        while (i < noOfDataObjects) {

            ElasticDataObjectConstructor elasticDataObjectConstructor = new ElasticDataObjectConstructor();
            elasticDataObjectConstructor.constructEDO(i);
            i++;
            
        }
                
                */
    }
    
}
