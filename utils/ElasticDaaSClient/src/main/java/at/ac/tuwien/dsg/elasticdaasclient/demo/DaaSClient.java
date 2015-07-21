/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.elasticdaasclient.demo;

import at.ac.tuwien.dsg.depic.common.entity.runtime.ElasticService;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
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
public class DaaSClient implements Runnable {

    private Thread t;
    private String threadName;

    public DaaSClient(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
      

            String ip = "localhost";
            // String ip="localhost";
            String port = "8080";
            String resource = "/eDaaS/rest/dataasset/request";
            int velocity = 1;
            int noOfDataAsset = 100;

            for (int i = 0; i < noOfDataAsset; i++) {
                
                long t1 = System.currentTimeMillis();
                
                DataAssetRequest dataAssetRequest = sampleDataAssetRequest("daf-gps-"+i);
                try {
                    String darequestXML = JAXBUtils.marshal(dataAssetRequest, DataAssetRequest.class);

                    RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
                    String noOfDataParititons = ws.callPutMethod(darequestXML);
                    // noPars = Integer.parseInt(noOfDataParititons);
                  //  System.out.println("Customer:" +threadName+ " - Data asset:" +i+ " - Number of Data Partitions: " + noOfDataParititons);
                    
                    
                    
                } catch (JAXBException ex) {
                    Logger.getLogger(demo_0.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    Thread.sleep(velocity);

                } catch (InterruptedException ex) {

                }

                
                long t2 = System.currentTimeMillis();
                    
                
                String logMessage =System.currentTimeMillis() + "\t" + threadName+"\t"+ "daf-gps-"+i +"\t" + String.valueOf(t2-t1)+"\n" ;
                
                System.out.println(logMessage);
                
                IOUtils iou = new IOUtils();
                iou.writeData(logMessage, "logClient_"+ threadName +".txt");
                    
                    
                    
            }

      
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    private DataAssetRequest sampleDataAssetRequest(String dataAssetID) {

        CostConstraint costConstraint = new CostConstraint(10, 10);
        VehicleArcConstraint vehicleArcConstraint = new VehicleArcConstraint(90, 100);
        SpeedArcConstraint speedArcConstraint = new SpeedArcConstraint(90, 100);
        DeliveryTimeConstraint deliveryTimeConstraint = new DeliveryTimeConstraint(0, 0.1);

        ConsumerRequirement consumerRequirement = new ConsumerRequirement();

        consumerRequirement.setCostConstraint(costConstraint);
        consumerRequirement.setVehicleArcConstraint(vehicleArcConstraint);
        consumerRequirement.setSpeedArcConstraint(speedArcConstraint);
        consumerRequirement.setDeliveryTimeConstraint(deliveryTimeConstraint);

        DataAssetRequest dataAssetRequest = new DataAssetRequest(dataAssetID, threadName, consumerRequirement);

        return dataAssetRequest;

    }

}
