/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.configuration;


import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event.EventMessage;
import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event.EventPattern;
import at.ac.tuwien.dsg.smartcom.model.Message;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Jun
 */
public class EventPatternLoader {
    
    public static String getStatemement(String statementName) {

        String statementStr = ""; 
        String statementStore="";
   
        
        switch (statementName) {
                case "MONITOR":
                    statementStore=EventPatternLoader.class.getClassLoader().getResource("cep/monitor-event-rule.xml").getPath();
                    break;
                case "WARNING":
                    statementStore=EventPatternLoader.class.getClassLoader().getResource("cep/warning-event-rule.xml").getPath();
                    break; 

            }
        try {
            statementStore = URLDecoder.decode(statementStore, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EventPatternLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        try {
            FileReader inputFile = new FileReader(statementStore);
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String dataStr = "";
            String line = "";
            while ((line = bufferReader.readLine()) != null) {
               // System.out.println(line);
                dataStr += line;
            }

            JAXBContext bContext = JAXBContext.newInstance(EventPattern.class);
            Unmarshaller um = bContext.createUnmarshaller();
            EventPattern eventRule = (EventPattern) um.unmarshal(new StringReader(dataStr));
            statementStr = eventRule.getStatement();
            
            bufferReader.close();
        } catch (Exception e) {

        }
 
        return statementStr;
    }
    
    public static EventMessage getEventMessage(String statementName) {

        EventMessage message = null; 
        String statementStore="";
   
        
        switch (statementName) {
                case "MONITOR":
                    statementStore=EventPatternLoader.class.getClassLoader().getResource("cep/monitor-event-rule.xml").getPath();
                    break;
                case "WARNING":
                    statementStore=EventPatternLoader.class.getClassLoader().getResource("cep/warning-event-rule.xml").getPath();
                    break; 

            }
        try {
            statementStore = URLDecoder.decode(statementStore, "utf-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EventPatternLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        try {
            FileReader inputFile = new FileReader(statementStore);
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String dataStr = "";
            String line = "";
            while ((line = bufferReader.readLine()) != null) {
              //  System.out.println(line);
                dataStr += line;
            }

            JAXBContext bContext = JAXBContext.newInstance(EventPattern.class);
            Unmarshaller um = bContext.createUnmarshaller();
            EventPattern eventRule = (EventPattern) um.unmarshal(new StringReader(dataStr));
            message = eventRule.getMessage();
            
            bufferReader.close();
        } catch (Exception e) {

        }
 
        return message;
    }
    
    public static void saveConfig(String statement, String store, EventMessage message){
    
        EventPattern eventRule = new EventPattern(statement, message);
        
        FileWriter fstream;
        try {
            String xmlData = marshal(eventRule, EventPattern.class);
            fstream = new FileWriter(store, false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(xmlData);
            out.close();
        } catch (Exception ex) {
            System.out.println("" + ex.toString());
        }
        
    }

    
    private static <T> String marshal(Object source, Class<T> configurationClass) throws JAXBException {
        JAXBContext jAXBContext = JAXBContext.newInstance(configurationClass);
        StringWriter writer = new StringWriter();
        jAXBContext.createMarshaller().marshal(source, writer);
        return writer.toString();
    }
}
