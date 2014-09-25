/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qoranalytics.connector;

/**
 *
 * @author Jun
 */
import at.ac.tuwien.dsg.daas.entities.CreateRowsStatement;
import at.ac.tuwien.dsg.qoranalytics.configuration.Configuration;
import java.io.StringReader;
import java.util.Enumeration;
import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import at.ac.tuwien.dsg.qoranalytics.configuration.Configuration;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MOMConnector {

    private String url;
    private String subject;
    private int limit;
    
    public void openConnection() {

        try {

            configure();
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
                onMessage(message);
            }

            connection.close();

        } catch (Exception ex) {

        }
    }

    private void onMessage(Message message) {

        try {

            if (message instanceof MapMessage) {

                MapMessage mapMessage = (MapMessage) message;
                Enumeration fieldsNames = mapMessage.getMapNames();
                while (fieldsNames.hasMoreElements()) {

                    String sensorOperation = fieldsNames.nextElement().toString();
                    String data = mapMessage.getString(sensorOperation);

                    System.out.println("Received " + sensorOperation + data);
                    writeData(data);

                    if (sensorOperation.equals("INSERT_ROWS")) {
                        JAXBContext bContext = JAXBContext.newInstance(CreateRowsStatement.class);
                        Unmarshaller um = bContext.createUnmarshaller();
                        CreateRowsStatement crs = (CreateRowsStatement) um.unmarshal(new StringReader(data));

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
    
    private void writeData(String data){
     
        
        FileWriter fstream;
        try {
            fstream = new FileWriter("temp", false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(data);

            
            out.close();
        } catch (IOException ex) {
        }
    }
}
