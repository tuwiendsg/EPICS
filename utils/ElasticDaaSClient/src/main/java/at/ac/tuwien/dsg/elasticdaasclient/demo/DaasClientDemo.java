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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class DaasClientDemo implements Runnable{
    private Thread t;
    private String threadName;

    public DaasClientDemo(String threadName) {
        this.threadName = threadName;
    }
  


    public void run() {

            System.out.println("I'm running: " + threadName);
           
    }

    public void start() {
        if (t == null) {
            t = new Thread( (Runnable) this, threadName);
            t.start();
        }
    }
    
    
    private void addDaasCustomer(){
        
        long startTime = System.currentTimeMillis();
        
        int noOfPartitions = getNoOfPartitions();
        System.out.println("No Of Partitions: " + noOfPartitions);
        
        
        long endTime = System.currentTimeMillis();
        long totalTIme = endTime - startTime;

        System.out.println("Time ms: " + totalTIme);
        
    }
    
    private int getNoOfPartitions(){
        String ip ="128.130.172.216";
       // String ip="localhost";
        String port ="8080";
        String resource ="/eDaaS/rest/dataasset/request";

        int noPars = 0;
        
        DataAssetRequest dataAssetRequest = sampleDataAssetRequest();
        try {
            String darequestXML = JAXBUtils.marshal(dataAssetRequest, DataAssetRequest.class);
            
            RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
            String noOfDataParititons = ws.callPutMethod(darequestXML);
            noPars = Integer.parseInt(noOfDataParititons);
            System.out.println("Number of Data Partitions: " + noOfDataParititons);
            
        } catch (JAXBException ex) {
            Logger.getLogger(DaasClientDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return noPars;
    }
    
    private DataAssetRequest sampleDataAssetRequest(){
        
        
        CostConstraint costConstraint = new CostConstraint(10, 10);
        VehicleArcConstraint vehicleArcConstraint = new VehicleArcConstraint(90, 100);
        SpeedArcConstraint speedArcConstraint = new SpeedArcConstraint(90, 100);
        DeliveryTimeConstraint deliveryTimeConstraint = new DeliveryTimeConstraint(0, 0.1);
        
     
        ConsumerRequirement consumerRequirement = new ConsumerRequirement();
        
        
        consumerRequirement.setCostConstraint(costConstraint);
        consumerRequirement.setVehicleArcConstraint(vehicleArcConstraint);
        consumerRequirement.setSpeedArcConstraint(speedArcConstraint);
        consumerRequirement.setDeliveryTimeConstraint(deliveryTimeConstraint);
        
        
        DataAssetRequest dataAssetRequest = new DataAssetRequest("daf5", threadName, consumerRequirement);
        
        
        return dataAssetRequest;
 
    }

   
}
