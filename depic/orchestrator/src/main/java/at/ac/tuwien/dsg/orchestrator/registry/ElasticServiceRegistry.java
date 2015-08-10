/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.registry;

import at.ac.tuwien.dsg.depic.common.entity.runtime.ElasticService;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.utils.Configuration;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.common.utils.Logger;

import at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore.ElasticityProcessesStore;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;


/**
 *
 * @author Jun
 */
public class ElasticServiceRegistry {
    
    private static List<ElasticService> listOfElasticServices;
    private static List<String> listOfActiveServices;
    private static int customerPerService=0;

    private ElasticServiceRegistry() {
    }

    
    public static String getElasticServiceURI(String serviceID, DBType eDaaSType) {
        
        String uri = "";
        
        List<String> potentialList = new ArrayList<String>();
        if(customerPerService == 0){
            Configuration cfg = new Configuration("");
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
                 
                   potentialList.add(elasticService.getUri());
                }
            }

           if (!potentialList.isEmpty()){
               Random random = new Random();
               int min = 0;
               int max = potentialList.size();
               int randomIndex = random.nextInt(max - min) + min;
               uri = potentialList.get(randomIndex);
           } 
        
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
            Configuration cfg = new Configuration("");
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
            
            System.err.println("\n" + log);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ElasticServiceRegistry.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
    }
    



}
