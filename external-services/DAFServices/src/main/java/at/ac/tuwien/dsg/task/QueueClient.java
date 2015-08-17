/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.task;

/**
 *
 * @author Jun
 */
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.mlr.util.IOUtils;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class QueueClient implements Runnable {

    Thread t;
    private String url;
    private String subject;
    private int limit;

    private String mom_ip;
    private String mom_port;
    private String mon_queue;
    private int maxDataItem;
    private int dataItemCounter;
    private int dataAssetCounter;
    private List<DataItem> listOfDataItems;
    String rootPath;

    public QueueClient() {

    }

    public QueueClient(String mom_ip, String mom_port, String mon_queue) {
        this.mom_ip = mom_ip;
        this.mom_port = mom_port;
        this.mon_queue = mon_queue;
        maxDataItem = 5000;
        dataItemCounter = 0;
        dataAssetCounter = 0;
        listOfDataItems = new ArrayList<DataItem>();
        rootPath = "/home/ubuntu/log/temp";
    }

    public void run() {

        configure();
        openConnection();

    }

    public void start() {
        if (t == null) {
            t = new Thread(this, "momreader");
            t.start();
        }
    }

    public void openConnection() {

        try {

            System.out.println("Openning ActiveMQ ");
            ConnectionFactory connectionFactory
                    = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);
            MessageConsumer consumer = session.createConsumer(destination);

            for (int i = 0; i < limit; i++) {

                Message message = consumer.receive();
                onMessageStream(message);
                // checkInterrupt();
            }

            connection.close();

        } catch (Exception ex) {

        }
    }

    private void onMessageStream(Message message) {

        try {

            if (message instanceof MapMessage) {

                MapMessage mapMessage = (MapMessage) message;
                Enumeration fieldsNames = mapMessage.getMapNames();
                while (fieldsNames.hasMoreElements()) {

                    String sensorOperation = fieldsNames.nextElement().toString();
                    String data = mapMessage.getString(sensorOperation);

                    System.out.println(dataItemCounter + " RECEIVE: " + data);
                    DataItem dataItem = JAXBUtils.unmarshal(data, DataItem.class);
                    listOfDataItems.add(dataItem);
                    dataItemCounter++;

                    if (dataItemCounter >= maxDataItem) {

                        DataAsset dataAsset = new DataAsset("daf-gps-" + String.valueOf(dataAssetCounter), 0, listOfDataItems);
                        String dataAssetXML = JAXBUtils.marshal(dataAsset, DataAsset.class);

                        IOUtils iou = new IOUtils(rootPath);
                        iou.writeData(dataAssetXML, "mom-daf-gps-" + String.valueOf(dataAssetCounter) + ".data");
                        dataAssetCounter++;
                        dataItemCounter = 0;
                        listOfDataItems.clear();

                        long currentTime = System.currentTimeMillis();
                        String log = "daf-gps-" + String.valueOf(dataAssetCounter) + "\t" + String.valueOf(currentTime)+"\n";

                        iou.writeData(log, "dafStartTime.txt");

                    }

                }

            } else {
                System.out.println("Unrecognized message: " + message);
            }

        } catch (Exception ex) {

        }
    }

    private void configure() {

        url = "tcp://" + mom_ip + ":" + mom_port;
        subject = mon_queue;
        limit = Integer.MAX_VALUE;

    }

//    private void checkInterrupt() {
//
// 
//        String ip = Configuration.getConfiguration("DB.CLOUDLYRA.IP");
//        String port = Configuration.getConfiguration("DB.CLOUDLYRA.PORT");
//        String database = Configuration.getConfiguration("DB.CLOUDLYRA.DATABASE");
//        String username = Configuration.getConfiguration("DB.CLOUDLYRA.USERNAME");
//        String password = Configuration.getConfiguration("DB.CLOUDLYRA.PASSWORD");
//
//        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, database, username, password);
//
//        String sql = "SELECT * FROM Daf WHERE name='"+dafName+"'";
//
//        Logger.getLogger(QueueClient.class.getName()).log(Level.INFO, sql);
//        
//        ResultSet rs = connectionManager.ExecuteQuery(sql);
//                try {
//                    while (rs.next()) {
//                      
//                        String status = rs.getString("status");
//                        if (status.equals("stop")){
//                            limit=0;
//                            Logger.getLogger(QueueClient.class.getName()).log(Level.INFO, "STOP: " +dafName );
//                        }
//                        
//
//                    }
//
//                } catch (Exception ex) {
//
//                }
//    
//
//    }
//
//    public void configureDataAssetFunction(DataAnalyticFunctionCep daf){
//        eventHandler = new EventHandler(daf);
//        eventHandler.afterPropertiesSet();
//        dafName  = daf.getDafName();
//        mom_ip = daf.getDafInputCep().getInputDataSource().getIp();
//        mom_port = daf.getDafInputCep().getInputDataSource().getPort();
//        mon_queue = daf.getDafInputCep().getInputDataSource().getQueue();
//        
//        
//        
//        Logger.getLogger(QueueClient.class.getName()).log(Level.INFO, "EPL: " +daf.getDafAnalyticCep().getEplStatement() );
//    }
//    
}
