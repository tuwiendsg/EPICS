/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.demo;

import at.ac.tuwien.dsg.common.entity.others.EDORepo;
import at.ac.tuwien.dsg.common.entity.others.EDORepoJAXB;
import at.ac.tuwien.dsg.orchestrator.services.DeliveryService;

/**
 *
 * @author Jun
 */
public class StatisticApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String userID="1";
        for (int i=0;i<10;i++) {
        
        String key = String.valueOf(i) +";"+ userID;
        DeliveryService deliveryService = new DeliveryService();
        String edoXML = deliveryService.getDeliveryEDO(key);
        
            EDORepoJAXB eDORepoJAXB = new EDORepoJAXB();
            EDORepo eDORepo= eDORepoJAXB.unmarshallingObject(edoXML);
            
            
        
        System.out.println("EDO: " + key + "- DataCompleteness: "+ eDORepo.getDataComplenetess() +"- ResponseTime: " + eDORepo.getResponseTime());
        
        
        
        
        }
        
        
        
        
    }
    
}
