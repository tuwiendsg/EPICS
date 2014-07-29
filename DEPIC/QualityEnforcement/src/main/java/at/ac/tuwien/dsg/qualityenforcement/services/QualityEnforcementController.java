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
public class QualityEnforcementController {
    
    
    
    public void startQualityEnforcement(String key){
        
        String[] vals = key.split(";");
        
        int objID = Integer.parseInt(vals[1]);
        String userID= vals[0];
        
        QualityEnforcement qualityEnforcement = new QualityEnforcement();
        qualityEnforcement.doQualityEnforcement(objID, userID);

            
        
    }
    
    
}
