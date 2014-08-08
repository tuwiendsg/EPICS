/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qualityenforcement.services;

import at.ac.tuwien.dsg.qualityenforcement.qodenforcement.QualityEnforcement;

/**
 *
 * @author Jun
 */
public class QualityEnforcementController implements Runnable {
    
    Thread runner;
    String userID;
    int objID;

    public QualityEnforcementController() {
    }
    
    
    public QualityEnforcementController(String content) {
        
        System.out.println("QualityEnforcement: " + content);
        String[] vals = content.split(";");
        
        objID = Integer.parseInt(vals[1]);
        userID= vals[0];
    }
    
    
    public void run() {
                
        
		startQualityEnforcement();
                
        }
    
    public void startQualityEnforcement(){
        
        
        
        QualityEnforcement qualityEnforcement = new QualityEnforcement();
        qualityEnforcement.doQualityEnforcement(objID, userID);

            
        
    }
    
    
}
