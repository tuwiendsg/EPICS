/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.elasticdaasclient.demo;



import at.ac.tuwien.dsg.edaas.requirement.ConsumerRequirement;
import at.ac.tuwien.dsg.edaas.requirement.CostConstraint;
import at.ac.tuwien.dsg.edaas.requirement.DataAssetRequest;
import at.ac.tuwien.dsg.edaas.requirement.DeliveryTimeConstraint;
import at.ac.tuwien.dsg.edaas.requirement.SpeedArcConstraint;

import at.ac.tuwien.dsg.edaas.requirement.VehicleArcConstraint;

import at.ac.tuwien.dsg.elasticdaasclient.utils.JAXBUtils;
import at.ac.tuwien.dsg.elasticdaasclient.utils.RestfulWSClient;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class RestClientEDaaS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        
        int noOfDaaSConsumers =1;
        
        String str="";
        
        for (int i =0;i<noOfDaaSConsumers;i++){
            String customerID = "c0"+ String.valueOf(i);
            DaaSClient daaSClient = new DaaSClient(customerID);
            daaSClient.start();
            str += customerID + " is running !\n";
            System.out.println(customerID + " is running !");
            
        }
    }
    
    
    
    
}
