/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.registry;


import at.ac.tuwien.dsg.common.deployment.ElasticService;
import at.ac.tuwien.dsg.common.entity.eda.EDaaSType;

import java.util.List;


/**
 *
 * @author Jun
 */
public class ElasticServiceRegistry {
    
    private static List<ElasticService> listOfElasticServices;

   
    public static String getElasticServiceURI(String serviceID, EDaaSType eDaaSType){
        
        
        int minNoOfRequest=Integer.MAX_VALUE;
        int elasticServiceIndex = 0;
        
        for (ElasticService elasticService : listOfElasticServices){
            
            if (elasticService.getActionID().equals(serviceID)){
                if (elasticService.getRequest()<minNoOfRequest){                 
                    elasticServiceIndex = listOfElasticServices.indexOf(elasticService);
                }             
            }
        }
        
        ElasticService selectedElasticService = listOfElasticServices.get(elasticServiceIndex);
        int currentRequest = selectedElasticService.getRequest();
        selectedElasticService.setRequest(++currentRequest);
          
        return  selectedElasticService.getUri() + "/" + eDaaSType.geteDaaSType();
        
    }
    
    public static void updateElasticServices(List<ElasticService> updatedElasticServices){
        listOfElasticServices.clear();
        listOfElasticServices.addAll(updatedElasticServices);
    
        
    }
    
//    private int randomInt(int min, int max){
//        
//        Random random = new Random();
//        
//        int randomNumber = random.nextInt(max+1 - min) + min;
//        return randomNumber;
//    }
//    
    

}
