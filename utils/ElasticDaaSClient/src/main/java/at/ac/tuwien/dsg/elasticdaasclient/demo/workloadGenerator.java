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
import at.ac.tuwien.dsg.elasticdaasclient.utils.RestfulWSClient;

/**
 *
 * @author Jun
 */
public class workloadGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       
        
        
        int noOfDaaSConsumers =5;
        
        for (int i =0;i<noOfDaaSConsumers;i++){
            String customerID = "c0"+ String.valueOf(i);
            DaaSClient daaSClient = new DaaSClient(customerID);
            daaSClient.start();
            System.out.println(customerID + " is running !");
            
        }
        
        
    }
    
    private static void runRESTTest(){
        
        String ip = "localhost";
        String port = "8080";
        String resource = "/eDaaS/rest/dataasset/request";
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        DataAssetRequest dataAssetRequest = sampleDataAssetRequest("daf1");
        
        
        ws.callPutMethod("");
        
    }
    
    
    private static DataAssetRequest sampleDataAssetRequest(String dataAssetID) {

        CostConstraint costConstraint = new CostConstraint(10, 10);
        VehicleArcConstraint vehicleArcConstraint = new VehicleArcConstraint(90, 100);
        SpeedArcConstraint speedArcConstraint = new SpeedArcConstraint(90, 100);
        DeliveryTimeConstraint deliveryTimeConstraint = new DeliveryTimeConstraint(0, 0.1);

        ConsumerRequirement consumerRequirement = new ConsumerRequirement();

        consumerRequirement.setCostConstraint(costConstraint);
        consumerRequirement.setVehicleArcConstraint(vehicleArcConstraint);
        consumerRequirement.setSpeedArcConstraint(speedArcConstraint);
        consumerRequirement.setDeliveryTimeConstraint(deliveryTimeConstraint);

        DataAssetRequest dataAssetRequest = new DataAssetRequest(dataAssetID, "aaa", consumerRequirement);

        return dataAssetRequest;

    }
    
    
}
