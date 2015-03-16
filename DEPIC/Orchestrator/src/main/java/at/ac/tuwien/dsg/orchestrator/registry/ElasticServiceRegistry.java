/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.registry;


import at.ac.tuwien.dsg.common.deployment.ElasticService;
import at.ac.tuwien.dsg.common.entity.eda.EDaaSType;
import at.ac.tuwien.dsg.common.utils.Logger;
import at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore.ElasticityProcessesStore;
import java.util.ArrayList;

import java.util.List;
import org.eclipse.persistence.queries.ANTLRQueryBuilder;


/**
 *
 * @author Jun
 */
public class ElasticServiceRegistry {
    
    private static List<ElasticService> listOfElasticServices;

       public static String getElasticServiceURI(String serviceID, EDaaSType eDaaSType) {

        String uri = "";

        if (listOfElasticServices == null) {

            ElasticityProcessesStore eps = new ElasticityProcessesStore();
            listOfElasticServices = eps.getElasticServices();

        } 
        
        Logger.logInfo("No Of Elastic Services: " + listOfElasticServices.size());
        
            int minNoOfRequest = Integer.MAX_VALUE;
            int elasticServiceIndex = 0;

            for (ElasticService elasticService : listOfElasticServices) {

                if (elasticService.getActionID().equals(serviceID)) {
                    if (elasticService.getRequest() < minNoOfRequest) {
                        elasticServiceIndex = listOfElasticServices.indexOf(elasticService);
                    }
                }
            }

            ElasticService selectedElasticService = listOfElasticServices.get(elasticServiceIndex);
            int currentRequest = selectedElasticService.getRequest();
            selectedElasticService.setRequest(++currentRequest);
            uri = selectedElasticService.getUri();// + "/" + eDaaSType.geteDaaSType();
        

        return uri;

    }

    public static void updateElasticServices(List<ElasticService> updatedElasticServices){
        
        System.out.println("On updateing new services");
        
        if (listOfElasticServices==null) {
            listOfElasticServices = new ArrayList<ElasticService>();
        }
 
        
        for (ElasticService es : updatedElasticServices){
            
            String uri = es.getUri();
            if (!isServiceExist(uri)) {
                System.out.println("Adding_new_serice: " + es.getActionID());
                ElasticService newElasticService = new ElasticService(es.getActionID(), es.getServiceID(), es.getUri());
                listOfElasticServices.add(newElasticService);
            }
 
        }
        
//        
//        for (ElasticService es : listOfElasticServices){
//            
//            String uri = es.getUri();
//            if (isServiceRemoved(uri, updatedElasticServices)) {
//                listOfElasticServices.remove(es);
//            }
// 
//        }
        
        for (ElasticService es : listOfElasticServices){
            System.out.println("service: " + es.getServiceID() + 
                    " - request: " + es.getRequest() + 
                    " - uri: " + es.getUri());
            
        }
        
        
        
        // don't clear, check for new uri and add new uri to register
        // check for no longer exist uri and remove from register
        
//        listOfElasticServices.clear();
//        listOfElasticServices.addAll(updatedElasticServices);
//        
        
//        

    
        
    }
    
    
    
    private static boolean isServiceExist(String uri){
        
        for (ElasticService elasticService : listOfElasticServices){
            if (elasticService.getUri().equals(uri)){
                return  true;
            }  
        }     
        return false;
    }
    
    
    private static boolean isServiceRemoved(String uri,List<ElasticService> updatedElasticServices){
        
        for (ElasticService elasticService : updatedElasticServices){
            if (elasticService.getUri().equals(uri)){
                return  false;
            }  
        }     
        return true;
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
