/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.rule;

import at.ac.tuwien.dsg.qoranalytics.configuration.EventPatternLoader;
import at.ac.tuwien.dsg.qoranalytics.connector.MOMConnector;
import at.ac.tuwien.dsg.qoranalytics.connector.SmartComConnector;
import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event.EventMessage;
import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event.EventPattern;
import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event.SensorEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Jun
 */
public class WarningEventSubscriber implements StatementSubscriber {

    private String EventSubscriberType = "WARNING";

    public String getStatement() {

        // Example using 'Match Recognise' syntax.
        String warningEventExpression = EventPatternLoader.getStatemement(EventSubscriberType);

        return warningEventExpression;
    }

    public void update(Map<String, SensorEvent> eventMap) {

        /*
         SensorEvent val1 = (SensorEvent) eventMap.get("val1");
         SensorEvent val2 = (SensorEvent) eventMap.get("val2");
         */
        
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------");
        sb.append("\n- [WARNING] : THRESHOLD DETECTED ");
        sb.append("\n--------------------------------------------------");

        System.out.println(sb.toString());
        
        sendCriticalMessage();

    }

    public void sendCriticalMessage() {
        EventMessage eventMessage = EventPatternLoader.getEventMessage(EventSubscriberType);

        if (eventMessage != null) {

            if (sendMessagePermission()) {
                eventMessage.setConversationId(String.valueOf(System.nanoTime()));
                logMessage(eventMessage);
                SmartComConnector scc = new SmartComConnector();
                at.ac.tuwien.dsg.smartcom.model.Message message = scc.buildMessage(eventMessage);

                try {
                    scc.sendMessage(message);
                } catch (Exception ex) {
                    Logger.getLogger(MOMConnector.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                System.out.println("Replication of messages. NO message sent !");
            }

        }
    }

    private void logMessage(EventMessage message) {

        FileWriter fstream;
        try {
            String xmlData = marshal(message, EventMessage.class);
            fstream = new FileWriter("log/" + EventSubscriberType, false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(xmlData);
            out.close();
        } catch (Exception ex) {
            System.out.println("" + ex.toString());
        }
    }

    private EventMessage getLogMessage() {

        EventMessage message = null;

        try {
            FileReader inputFile = new FileReader("log/" + EventSubscriberType);
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String dataStr = "";
            String line = "";
            while ((line = bufferReader.readLine()) != null) {
                //  System.out.println(line);
                dataStr += line;
            }

            JAXBContext bContext = JAXBContext.newInstance(EventMessage.class);
            Unmarshaller um = bContext.createUnmarshaller();
            message = (EventMessage) um.unmarshal(new StringReader(dataStr));

            bufferReader.close();
        } catch (Exception e) {

        }

        return message;
    }

    private boolean sendMessagePermission() {
        boolean permission = true;

        EventMessage message = getLogMessage();
        if (message != null) {

            long previousTime = Long.parseLong(message.getConversationId());
            long currentTime = System.nanoTime();
            double different = ((currentTime - previousTime) / Math.pow(10, 6));
            double waitingTime = message.getWaitingTime();
            System.out.println("waiting time: " + waitingTime);
            System.out.println("different time: " + different);
            if (different < waitingTime) {
                permission = false;
            }

        }

        return permission;
    }

    private <T> String marshal(Object source, Class<T> configurationClass) throws JAXBException {
        JAXBContext jAXBContext = JAXBContext.newInstance(configurationClass);
        StringWriter writer = new StringWriter();
        jAXBContext.createMarshaller().marshal(source, writer);
        return writer.toString();
    }
}
