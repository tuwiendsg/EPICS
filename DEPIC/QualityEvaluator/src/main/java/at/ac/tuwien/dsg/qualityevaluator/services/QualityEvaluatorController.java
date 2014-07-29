/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qualityevaluator.services;

import at.ac.tuwien.dsg.qualityevaluator.edo.ElasticDataObjectConstructor;

/**
 *
 * @author Jun
 */
public class QualityEvaluatorController {
    
    
    public void startQualityEvaluator(String userID, String objID){
        
        
        ElasticDataObjectConstructor elasticDataObjectConstructor = new ElasticDataObjectConstructor();
        elasticDataObjectConstructor.constructEDO(Integer.parseInt(userID), Integer.parseInt(objID));
        
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
