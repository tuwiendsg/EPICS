/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.registry;

import at.ac.tuwien.dsg.depic.common.entity.runtime.ElasticService;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.common.utils.Logger;
import at.ac.tuwien.dsg.orchestrator.configuration.Configuration;
import at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore.ElasticityProcessesStore;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;


/**
 *
 * @author Jun
 */
public class ElasticServiceRegistry {
    
    private static List<ElasticService> listOfElasticServices;
    private static List<String> listOfActiveServices;
    private static int customerPerService=0;
    
    public static String getElasticServiceURI(String serviceID, DBType eDaaSType) {
        
        String uri = "";
        
        
        if(customerPerService == 0){
            Configuration cfg = new Configuration();
            customerPerService = Integer.parseInt(cfg.getConfig("CUSTOMER.PER.SERVICE"));
        }
        
        if (listOfActiveServices == null) {
            listOfActiveServices = new ArrayList<String>();
        }
        
        if (listOfElasticServices == null) {
            
            Logger.logInfo("EMPTY REGISTRY - SETUP ...");
            ElasticityProcessesStore eps = new ElasticityProcessesStore();
            listOfElasticServices = eps.getElasticServices();
            
        }        
        
        Logger.logInfo("No Of Elastic Services: " + listOfElasticServices.size());

            for (ElasticService elasticService : listOfElasticServices) {

                if (elasticService.getActionID().equals(serviceID) && !isServiceBlock(elasticService.getUri())) {
                    uri = elasticService.getUri();
                    break;
                }
            }

//        
//        
//        ElasticService selectedElasticService = listOfElasticServices.get(elasticServiceIndex);
//        int currentRequest = selectedElasticService.getRequest();
//        selectedElasticService.setRequest(++currentRequest);
//        uri = selectedElasticService.getUri();// + "/" + eDaaSType.geteDaaSType();
        
        return uri;
        
    }
    
    public static void occupyElasticService(String uri){
       listOfActiveServices.add(uri);
    }
    
    public static void releaseElasticService(String uri){
        
        for (String s : listOfActiveServices){
            
            
            if (s.equals(uri)){
                listOfActiveServices.remove(s);
                break;
            }
            
        }
        
        listOfActiveServices.remove(uri);
    }

    public static boolean isServiceBlock(String uri) {

        
        boolean rs = false;
        int blockCounter =0;
        
        if(customerPerService == 0){
            Configuration cfg = new Configuration();
            customerPerService = Integer.parseInt(cfg.getConfig("CUSTOMER.PER.SERVICE"));
        }
       

        if (listOfActiveServices != null) {
            
            Logger.logInfo("NO_OF_BLOCK_SERVICES: " + listOfActiveServices.size());

            for (String s : listOfActiveServices) {        
                if (s.equals(uri)) {
                    blockCounter++;
                   
                }
            }
        } else {
            listOfActiveServices = new ArrayList<String>();
        }
        
        if (blockCounter>=customerPerService){
            rs=true;
        }
        
        
        
        return rs;
    }


    public static void updateElasticServices(List<ElasticService> updatedElasticServices) {
        
        listOfElasticServices = updatedElasticServices;
        
        String log = "\n" +String.valueOf(listOfElasticServices.size());
         try {
           
            
            IOUtils iou = new IOUtils("/home/ubuntu/log");
            iou.writeData(log, "vm_log.xml");
            
            System.out.println("\n" + log);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ElasticServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
//        System.out.println("On updateing new services");
//        
//        if (listOfElasticServices == null) {
//            listOfElasticServices = new ArrayList<ElasticService>();
//        }
//        
//        for (ElasticService es : updatedElasticServices) {
//            
//            String uri = es.getUri();
//            if (!isServiceExist(uri)) {
//                System.out.println("Adding_new_serice: " + es.getActionID());
//                ElasticService newElasticService = new ElasticService(es.getActionID(), es.getServiceID(), es.getUri());
//                
//                newElasticService.setRequest(getDefaultRequest(newElasticService.getActionID()));
//                
//                listOfElasticServices.add(newElasticService);
//            }
//            
//        }
//        
//        for (ElasticService es : listOfElasticServices) {
//            
//            String uri = es.getUri();
//            if (isServiceRemoved(uri, updatedElasticServices)) {
//                listOfElasticServices.remove(es);
//            }
//            
//        }
//        
//        for (ElasticService es : listOfElasticServices) {
//            System.out.println("service: " + es.getServiceID()
//                    + " - request: " + es.getRequest()
//                    + " - uri: " + es.getUri());
//            
//        }

      
    }
    
    private static int getDefaultRequest(String actionID) {
        
        int noOfRequest = Integer.MAX_VALUE;
        
        for (ElasticService es : listOfElasticServices) {
            if (es.getActionID().equals(actionID)) {
                if (es.getRequest() < noOfRequest) {
                    noOfRequest = es.getRequest()-1;
                }
            }
        }
        
        if (noOfRequest==Integer.MAX_VALUE){
            noOfRequest=0;
        }
        return noOfRequest;
    }
    
    private static boolean isServiceExist(String uri) {
        
        for (ElasticService elasticService : listOfElasticServices) {
            if (elasticService.getUri().equals(uri)) {
                return true;
            }            
        }        
        return false;
    }
    
    private static boolean isServiceRemoved(String uri, List<ElasticService> updatedElasticServices) {
        
        for (ElasticService elasticService : updatedElasticServices) {
            if (elasticService.getUri().equals(uri)) {
                return false;
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
