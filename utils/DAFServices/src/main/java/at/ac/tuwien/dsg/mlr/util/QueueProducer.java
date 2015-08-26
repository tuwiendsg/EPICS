/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.mlr.util;

/**
 *
 * @author Jun
 */
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

public class QueueProducer implements Runnable{

    Thread t;
    private String mom_ip;
    private String mom_port;
    private String mon_queue;
    private String url;

    public QueueProducer(String mom_ip, String mom_port, String mon_queue) {
        this.mom_ip = mom_ip;
        this.mom_port = mom_port;
        this.mon_queue = mon_queue;
        configure();
    }
    
    
    public void run() {

        produce();

    }
    
    public void start() {
        if (t == null) {
            t = new Thread(this, "producer");
            t.start();
        }
    }

    public void produce() {

        try {
            // Create a ConnectionFactory

            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue(mon_queue);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            for (int fileName = 0; fileName < 1; fileName++) {

                // String dataPath = "/Volumes/DATA/BigData/data/2014-09-10";
                String dataPath = "/home/ubuntu/data/2014-09-10";
                
                dataPath = dataPath + "/" + String.valueOf(fileName) + ".txt";

                try {
                    BufferedReader br = new BufferedReader(new FileReader(dataPath));

                    String line = br.readLine();

                    while (line != null) {

                        line = br.readLine();
                        String[] strs = line.split(",");
                        List<DataAttribute> listOfDataAttributes = new ArrayList<DataAttribute>();
                        for (int i = 0; i < strs.length; i++) {
                            DataAttribute dataAttribute = new DataAttribute(String.valueOf(i), strs[i]);
                            listOfDataAttributes.add(dataAttribute);

                        }

                        DataItem dataItem = new DataItem(listOfDataAttributes);

                        String dataItemXML = JAXBUtils.marshal(dataItem, DataItem.class);
                        System.out.println("Sending : " + dataItemXML);

                        MapMessage message = session.createMapMessage();
                        message.setString("DataItem", dataItemXML);

                        producer.send(message);

//                        try {
//                            Thread.sleep(3000);
//
//                        } catch (InterruptedException ex) {
//
//                        }
                    }

                    br.close();
                } catch (Exception e) {

                }

            }

            session.close();
            connection.close();
        } catch (Exception e) {
            System.err.println(e);
        }

//                        recordIndex++;
//                 
//                        if (recordIndex >= windowConst) {
//          
//                            DataAsset da = new DataAsset(String.valueOf(dataAssetID), 0, listOfDataItems);
//                            String daXML = JAXBUtils.marshal(da, DataAsset.class);
//
////                            MapMessage message = session.createMapMessage();
////                            message.setString("DataAsset", daXML);
//
//                            System.out.println("Sending : " +recordIndex);
////                            producer.send(message);
////
////                            try {
////                                Thread.sleep(5000);
////
////                            } catch (InterruptedException ex) {
////
////                            }
////                            
//                            recordIndex =0;
//                        } 
    }

    private void configure() {

        url = "tcp://" + mom_ip + ":" + mom_port;

    }
}
