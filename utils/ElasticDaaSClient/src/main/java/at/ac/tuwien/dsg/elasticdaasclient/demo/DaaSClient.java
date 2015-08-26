/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.elasticdaasclient.demo;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
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

        String ip = "128.130.172.214";
        // String ip="localhost";
        String port = "8080";
        String resource = "/eDaaS/rest/dataasset/request";

        String edaasname = "edaas1";

        DataAssetRequest dataAssetRequest = sampleDataAssetRequest(edaasname);
        try {
            String darequestXML = JAXBUtils.marshal(dataAssetRequest, DataAssetRequest.class);

            RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
            ws.callPutMethod(darequestXML);

        } catch (JAXBException ex) {
            Logger.getLogger(RestClientEDaaS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        do {

            getLatestDataAsset();
            
            
            try {
                Thread.sleep(30000);

            } catch (InterruptedException ex) {

            }
        } while (true);

    }

    private void getLatestDataAsset() {

        String ip = "128.130.172.214";
        // String ip="localhost";
        String port = "8080";
        String resource = "/eDaaS/rest/dataasset/get";
        String edaasname = "edaas1";

        long t1 = System.currentTimeMillis();
        String dataAssetXML = "";

        DataAssetRequest dataAssetRequest = sampleDataAssetRequest(edaasname);
        try {
            String darequestXML = JAXBUtils.marshal(dataAssetRequest, DataAssetRequest.class);

            RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
            dataAssetXML = ws.callPutMethod(darequestXML);

        } catch (JAXBException ex) {
            Logger.getLogger(RestClientEDaaS.class.getName()).log(Level.SEVERE, null, ex);
        }

        long t2 = System.currentTimeMillis();

        if (!dataAssetXML.equals("")) {

            try {
                DataAsset da = JAXBUtils.unmarshal(dataAssetXML, DataAsset.class);
                String logMessage = System.currentTimeMillis() + "\t" + threadName + "\t" + "window:" + da.getPartition() + "\t" + String.valueOf(t2 - t1) + "\n";

                System.out.println(logMessage);
            } catch (JAXBException ex) {
                Logger.getLogger(DaaSClient.class.getName()).log(Level.SEVERE, null, ex);
            }

//                
//                IOUtils iou = new IOUtils("/Volumes/DATA/Temp");
//                iou.writeData(logMessage, "logClient_"+ threadName +".txt");
        } else {
            System.out.println("WAITING");
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
        VehicleArcConstraint vehicleArcConstraint = new VehicleArcConstraint(81, 100);
        SpeedArcConstraint speedArcConstraint = new SpeedArcConstraint(81, 100);
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
