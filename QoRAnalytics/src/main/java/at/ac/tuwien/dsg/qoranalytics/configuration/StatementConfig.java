/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.configuration;


import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.rule.EventStatement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Jun
 */
public class StatementConfig {
    
    public static String getStatemement(String statementName) {

        String statementStr = ""; 
        String statementStore="";
        
        switch (statementName) {
                case "MONITOR":
                    statementStore="config/monitor-event-rule.xml";
                    break;
                case "WARNING":
                    statementStore="config/warning-event-rule.xml";
                    break; 

            }
 
        try {
            FileReader inputFile = new FileReader(statementStore);
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String dataStr = "";
            String line = "";
            while ((line = bufferReader.readLine()) != null) {
                System.out.println(line);
                dataStr += line;
            }

            JAXBContext bContext = JAXBContext.newInstance(EventStatement.class);
            Unmarshaller um = bContext.createUnmarshaller();
            EventStatement eventRule = (EventStatement) um.unmarshal(new StringReader(dataStr));
            statementStr = eventRule.getStatement();
            
            bufferReader.close();
        } catch (Exception e) {

        }
 
        return statementStr;
    }
    
    public static void saveConfig(String statement, String store){
    
        EventStatement eventRule = new EventStatement(statement);
        
        FileWriter fstream;
        try {
            String xmlData = marshal(eventRule, EventStatement.class);
            fstream = new FileWriter(store, false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(xmlData);
            out.close();
        } catch (Exception ex) {
        }
        
    }

    
    private static <T> String marshal(Object source, Class<T> configurationClass) throws JAXBException {
        JAXBContext jAXBContext = JAXBContext.newInstance(configurationClass);
        StringWriter writer = new StringWriter();
        jAXBContext.createMarshaller().marshal(source, writer);
        return writer.toString();
    }
}
