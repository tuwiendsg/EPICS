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
import at.ac.tuwien.dsg.daas.entities.CreateRowsStatement;
import at.ac.tuwien.dsg.daas.entities.RowColumn;
import at.ac.tuwien.dsg.daas.entities.TableRow;
import at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing.DataAnalyticFunctionCep;

import at.ac.tuwien.dsg.esperstreamprocessing.entity.SensorEvent;
import at.ac.tuwien.dsg.esperstreamprocessing.handler.EventHandler;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.Configuration;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.IOUtils;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.RestfulWSClient;


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

public class QueueClient implements Runnable {

    Thread runner;
    private String url;
    private String subject;
    private int limit;
    private EventHandler eventHandler;
    private String dafName;

    public QueueClient() {
     
    }

    public void run() {
        configure();
        openConnection();
    }

    public void openConnection() {

        try {

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
                    checkInterrupt();
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

                    if (sensorOperation.equals("INSERT_ROWS")) {
                        JAXBContext bContext = JAXBContext.newInstance(CreateRowsStatement.class);
                        Unmarshaller um = bContext.createUnmarshaller();
                        CreateRowsStatement crs = (CreateRowsStatement) um.unmarshal(new StringReader(data));

                        Collection<TableRow> rows = crs.getRows();

                        for (TableRow row : rows) {
                            Collection<RowColumn> columns = row.getValues();
                            String sensorName = "";
                            double sensorValue = 0;

                            for (RowColumn column : columns) {
                                if (column.getName().equals("sensorName")) {
                                    sensorName = column.getValue().replaceAll("\'", "");
                                }

                                if (column.getName().equals("sensorValue")) {
                                    sensorValue = Double.parseDouble(column.getValue());
                                }

                            }

                            SensorEvent sensorEvent = new SensorEvent(sensorName, sensorValue, new Date());
                            eventHandler.handle(sensorEvent);
                        }

                    }

                }

            } else {
                System.out.println("Unrecognized message: " + message);
            }

        } catch (Exception ex) {

        }
    }
    
    private void configure() {
        url = "tcp://" + Configuration.getConfig("MOM.IP") + ":" + Configuration.getConfig("MOM.PORT");
        subject = Configuration.getConfig("MOM.QUEUE_NAME");
        limit = Integer.parseInt(Configuration.getConfig("MESSAGE.LIMIT"));
        
    }
   
    private void checkInterrupt() {

 
        String ip = Configuration.getConfiguration("DB.EDASICH.IP");
        String port = Configuration.getConfiguration("DB.EDASICH.PORT");
        String database = Configuration.getConfiguration("DB.EDASICH.DATABASE");
        String username = Configuration.getConfiguration("DB.EDASICH.USERNAME");
        String password = Configuration.getConfiguration("DB.EDASICH.PASSWORD");

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, database, username, password);

        String sql = "SELECT * FROM Daf WHERE name='"+dafName+"'";

        Logger.getLogger(QueueClient.class.getName()).log(Level.INFO, sql);
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);
                try {
                    while (rs.next()) {
                      
                        String status = rs.getString("status");
                        if (status.equals("stop")){
                            limit=0;
                            Logger.getLogger(QueueClient.class.getName()).log(Level.INFO, "STOP: " +dafName );
                        }
                        

                    }

                } catch (Exception ex) {

                }
    

    }

    public void configureDataAssetFunction(DataAnalyticFunctionCep daf){
        eventHandler = new EventHandler(daf);
        eventHandler.afterPropertiesSet();
        dafName  = daf.getDafName();
    }
    
    
}
