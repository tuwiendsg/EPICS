/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.demo;

import at.ac.tuwien.dsg.common.entity.DataObject;
import at.ac.tuwien.dsg.common.entity.others.EDORepo;
import at.ac.tuwien.dsg.common.entity.others.EDORepoJAXB;
import at.ac.tuwien.dsg.common.jaxb.DataObjectJAXB;
import at.ac.tuwien.dsg.common.rest.EDORepoMeasuredAccessREST;
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

        String userID = "1";
        for (int i = 0; i < 100; i++) {

            String key = String.valueOf(i) + ";" + userID;
            DeliveryService deliveryService = new DeliveryService();
            String edoXML = deliveryService.getDeliveryEDO(key);

            EDORepoJAXB eDORepoJAXB = new EDORepoJAXB();
            EDORepo eDORepo = eDORepoJAXB.unmarshallingObject(edoXML);

            
            
            
            
            EDORepoMeasuredAccessREST repoAccessManagement = new EDORepoMeasuredAccessREST("128.130.172.216","8080");
            String xmlString = repoAccessManagement.getElasticDataObjectString(String.valueOf(i), userID);
            
            
           // System.out.println("XML: "+xmlString);
            
            
            EDORepo eDORepoRaw = eDORepoJAXB.unmarshallingObject(xmlString);
            
            
     //   System.out.println("EDO: " + key + "- DataCompleteness: "+ eDORepo.getDataComplenetess() +"- ResponseTime: " + eDORepo.getResponseTime());
            
            String logStr = String.valueOf(i) + "," + eDORepo.getResponseTime() + "," + eDORepo.getTime();
          //  String logStr2 = String.valueOf(i) + ","+ eDORepoRaw.getDataComplenetess() + "," + eDORepo.getDataComplenetess();

            System.out.println(logStr);

        }

    }

}
