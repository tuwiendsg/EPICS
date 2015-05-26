/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.esperstreamprocessing.queueclient;

/**
 *
 * @author Jun
 */
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
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
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class QueueProducer {

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

            int windowConst = 1000; // no of records
            
                // change to DataAsset format, marshalling and send to MOM
//            for (int i= 0 ;i<100;i++){ 
//                 String text = "SEND " + i;
//                 
//                 MapMessage message = session.createMapMessage();
//                 message.setString("StrAAA", text);
//                
//                System.out.println("Sent message: "+ text );
//                producer.send(message);
//                
//               try {
//                        Thread.sleep(1000);
//
//                    } catch (InterruptedException ex) {
//
//                    }
//             }
             ////////////
            
            int recordIndex =0;
            int dataAssetID = 0;
            List<DataItem> listOfDataItems = new ArrayList<DataItem>();
            
            for (int fileName = 0; fileName < 96; fileName++) {

                //String dataPath = cfx.getConfigPath();
                String dataPath = "/Volumes/DATA/BigData/data/2014-09-10";
                dataPath = dataPath + "/" + String.valueOf(fileName) + ".txt";

                try {
                    BufferedReader br = new BufferedReader(new FileReader(dataPath));

                    // StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {

                // sb.append(line);
                        //sb.append(System.lineSeparator());
                        line = br.readLine();
                        String[] strs = line.split(",");
                        String newLine = "";
                        List<DataAttribute> listOfDataAttributes = new ArrayList<DataAttribute>();
                        for (int i = 0; i < strs.length; i++) {
                            DataAttribute dataAttribute = new DataAttribute(String.valueOf(i), strs[i]);
                            listOfDataAttributes.add(dataAttribute);
//                            
//                            
//                            if (i != strs.length - 1) {
//                                newLine = newLine + "\t" + strs[i];
//                            } else {
//                                newLine += strs[i];
//                            }
                            
                        }
                        
                        DataItem dataItem = new DataItem(listOfDataAttributes);
                        listOfDataItems.add(dataItem);
                       // System.out.println("Counter: " + recordIndex);
                        recordIndex++;
                 
                        if (recordIndex >= windowConst) {
                            //marshalling and send data asset
                            DataAsset da = new DataAsset(String.valueOf(dataAssetID), 0, listOfDataItems);
                            String daXML = JAXBUtils.marshal(da, DataAsset.class);

//                            MapMessage message = session.createMapMessage();
//                            message.setString("DataAsset", daXML);

                            System.out.println("Sending : " +recordIndex);
//                            producer.send(message);
//
//                            try {
//                                Thread.sleep(5000);
//
//                            } catch (InterruptedException ex) {
//
//                            }
//                            
                            recordIndex =0;
                        } 
                        
                        
                        if (dataAssetID==10){
                            break;
                        }
                    }

                    br.close();
                } catch (Exception e) {

                }

            }

                ///////////
            // Clean up
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }

    }

    private void configure() {

        url = "tcp://" + mom_ip + ":" + mom_port;

    }
}
