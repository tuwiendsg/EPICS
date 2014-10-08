/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.esperstreamprocessing.test;

import at.ac.tuwien.dsg.daas.entities.CreateRowsStatement;
import at.ac.tuwien.dsg.daas.entities.RowColumn;
import at.ac.tuwien.dsg.daas.entities.TableRow;
import at.ac.tuwien.dsg.edasich.entity.stream.DataAssetFunctionStreamingData;
import at.ac.tuwien.dsg.esperstreamprocessing.entity.SensorEvent;
import at.ac.tuwien.dsg.esperstreamprocessing.handler.SensorEventHandler;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.Configuration;
import java.io.StringReader;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author Jun
 */
public class MoMClient {

    /**
     * @param args the command line arguments
     */
   
    private String url;
    private String subject;
    private int limit;
 
    
    
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
                    
                    System.out.println("data: " + data);
                    /*
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
                                    sensorName = column.getValue();
                                    System.out.println("sensorName: " + sensorName);
                                }

                                if (column.getName().equals("sensorValue")) {
                                    sensorValue = Double.parseDouble(column.getValue());
                                    System.out.println("sensorValue: " + sensorValue);
                                }

                            }

                            
                            SensorEvent sensorEvent = new SensorEvent(sensorName, sensorValue, new Date());
                            
                        }

                    }
                    
                    */

                }

            } else {
                System.out.println("Unrecognized message: " + message);
            }

        } catch (Exception ex) {

        }
    }
    
    private void configure() {
        url = "tcp://128.130.172.216:9124";
        subject = "DB_LOG";
        limit = 1000000;
    }
    
    
}
