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
import at.ac.tuwien.dsg.daas.entities.RowColumn;
import at.ac.tuwien.dsg.daas.entities.TableRow;
import at.ac.tuwien.dsg.qoranalytics.configuration.Configuration;
import java.io.StringReader;
import java.util.Enumeration;
import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import at.ac.tuwien.dsg.qoranalytics.configuration.Configuration;
import at.ac.tuwien.dsg.qoranalytics.batchjobprocessing.daw.engine.WorkflowEngine;
import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event.SensorEvent;
import at.ac.tuwien.dsg.qoranalytics.streamprocessing.handler.SensorEventHandler;
import at.ac.tuwien.dsg.smartcom.model.Identifier;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MOMConnector {

    private String url;
    private String subject;
    private int limit;
    private SensorEventHandler sensorEventHandler;

    public MOMConnector() {
        sensorEventHandler = new SensorEventHandler();
        sensorEventHandler.afterPropertiesSet();
    }
    
    
    
    
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
                    
                    if (sensorOperation.equals("INSERT_ROWS")) {
                        JAXBContext bContext = JAXBContext.newInstance(CreateRowsStatement.class);
                        Unmarshaller um = bContext.createUnmarshaller();
                        CreateRowsStatement crs = (CreateRowsStatement) um.unmarshal(new StringReader(data));
              
                        Collection<TableRow> rows = crs.getRows();
                        
                        for(TableRow row : rows) {
                            Collection<RowColumn> columns = row.getValues();
                            String sensorName ="";
                            double sensorValue = 0;
                            
                            for (RowColumn column : columns) {
                                if (column.getName().equals("sensorName")){
                                    sensorName = column.getValue();           
                                }
                                
                                if (column.getName().equals("sensorValue")){
                                    sensorValue = Double.parseDouble(column.getValue());           
                                }
             
                            }
                            
                            SensorEvent sensorEvent = new SensorEvent(sensorName,sensorValue, new Date());
                                    sensorEventHandler.handle(sensorEvent);
                        }
                            
                    }
                       
                }

            } else {
                System.out.println("Unrecognized message: " + message);
            }

        } catch (Exception ex) {

        }
    }
    
        
      
    

    private void onMessageBatchJob(Message message) {

        try {

            if (message instanceof MapMessage) {

                MapMessage mapMessage = (MapMessage) message;
                Enumeration fieldsNames = mapMessage.getMapNames();
                while (fieldsNames.hasMoreElements()) {

                    String sensorOperation = fieldsNames.nextElement().toString();
                    String data = mapMessage.getString(sensorOperation);

                    writeData(data);
                    executeWorkflowEngine();
                    storeAnalysisResult();
                    sendCriticalMessage();     
                            
                }

            } else {
                System.out.println("Unrecognized message: " + message);
            }

        } catch (Exception ex) {

        }
    }
    
    private void executeWorkflowEngine(){
        
        WorkflowEngine wfEngine = new WorkflowEngine(Configuration.getConfig("WF.DAW"));
        wfEngine.startWFEngine();
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
    
    private void storeAnalysisResult(){
        CassandraConnector cc = new CassandraConnector();
        
    }
    
    
    public void sendCriticalMessage(){
        SmartComConnector scc = new SmartComConnector();
        at.ac.tuwien.dsg.smartcom.model.Message message = buildMessage();
        try {
            scc.sendMessage(message);
        } catch (Exception ex) {
            Logger.getLogger(MOMConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
    
    public at.ac.tuwien.dsg.smartcom.model.Message buildMessage(){
        String testId = "m01"; //unique identifier of message
            String testcontent = "High Temperature";  //unique identifier of message content
            String testType = "control message";      //the type of message
            String testSubType = "lower temperature";          //component specific
            String sender = "QoRAnalytic";               //unique identifier of sender
            String receiver = "smartcom01";           //unique identifier of receiver
            //String conversationId="conversationId"; //OPTIONAL  //If any two process run parallaly then they communicate with each other using this id
            long ttl = 3;                             //OPTIONAL  //the time duration to monitor the message
            String testLanguage = "English";          //OPTIONAL //the language of the message
            String securityToken = "SecurityToken";   //OPTIONAL //information about the authenticity of the message
            String code = null;
            
            at.ac.tuwien.dsg.smartcom.model.Message message = new at.ac.tuwien.dsg.smartcom.model.Message.MessageBuilder()
                .setId(Identifier.message(testId))
                .setContent(testcontent)
                .setType(testType)
                .setSubtype(testSubType)
                .setSenderId(Identifier.peer(sender))
                .setReceiverId(Identifier.peer(receiver))
                .setConversationId("" + System.nanoTime())
                .setTtl(ttl)
                .setLanguage(testLanguage)
                .setSecurityToken(securityToken)
                .create();
            
            return message;
    }
    
    
    
}
