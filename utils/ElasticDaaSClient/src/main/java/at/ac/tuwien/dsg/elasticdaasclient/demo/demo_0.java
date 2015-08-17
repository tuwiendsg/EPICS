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
public class demo_0 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        //openConnection();
        getNoOfPartitions();

       // closeConnection();
    }
    
    
    private static int getNoOfPartitions(){
        String ip ="128.130.172.214";
       // String ip="localhost";
        String port ="8080";
        String resource ="/eDaaS/rest/dataasset/request";
        
        int noPars = 0;
        
        DataAssetRequest dataAssetRequest = sampleDataAssetRequest();
        try {
            String darequestXML = JAXBUtils.marshal(dataAssetRequest, DataAssetRequest.class);
          
            RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
            String noOfDataParititons = ws.callPutMethod(darequestXML);
           // noPars = Integer.parseInt(noOfDataParititons);
            System.out.println("Number of Data Partitions: " + noOfDataParititons);
            
        } catch (JAXBException ex) {
            Logger.getLogger(demo_0.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return noPars;
    }
    
//    
//    private static void getDataAsset(String dataAssetID, int noOfPartitions){
// 
//        String ip ="localhost";
//        String port ="8080";
//        String resource ="/eDaaS/rest/dataasset/get";
//
//        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
//     
//        for (int i = 0; i < noOfPartitions; i++) {
//            DataPartition dataPartition = new DataPartition(dataAssetID, String.valueOf(i));
//            String dataPartitionXML = "";
//            try {
//                dataPartitionXML = JAXBUtils.marshal(dataPartition, DataPartition.class);
//            } catch (JAXBException ex) {
//                Logger.getLogger(demo.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            String daXML = ws.callPutMethod(dataPartitionXML);
//            System.out.println(daXML);
//        }
//        
//        
//        
//    }
    
    
    
    
    private static DataAssetRequest sampleDataAssetRequest(){
        
       CostConstraint costConstraint = new CostConstraint(10, 10);
        VehicleArcConstraint vehicleArcConstraint = new VehicleArcConstraint(90, 100);
        SpeedArcConstraint speedArcConstraint = new SpeedArcConstraint(90, 100);
        DeliveryTimeConstraint deliveryTimeConstraint = new DeliveryTimeConstraint(0, 0.1);
        
     
        ConsumerRequirement consumerRequirement = new ConsumerRequirement();
        
        
        consumerRequirement.setCostConstraint(costConstraint);
        consumerRequirement.setVehicleArcConstraint(vehicleArcConstraint);
        consumerRequirement.setSpeedArcConstraint(speedArcConstraint);
        consumerRequirement.setDeliveryTimeConstraint(deliveryTimeConstraint);
        
        DataAssetRequest dataAssetRequest = new DataAssetRequest("daf-gps-2", "c01", consumerRequirement);
        
        
        return dataAssetRequest;
 
    }
    
    

    
    private static void openConnection(){
      
        String ip = "128.130.172.216";
        String port = "8080";
        String resource = "/DataAssetLoader/rest/dataasset/repo/cassandra/open";
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        ws.callPutMethod("");
    }
    
    private static void closeConnection(){
        

        String ip = "128.130.172.216";
        String port = "8080";
        String resource = "/DataAssetLoader/rest/dataasset/repo/cassandra/close";
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        ws.callPutMethod("");
    }
    
    
}
