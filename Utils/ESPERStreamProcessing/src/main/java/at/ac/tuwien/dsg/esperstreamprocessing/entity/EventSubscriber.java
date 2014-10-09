/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.esperstreamprocessing.entity;


/*
import at.ac.tuwien.dsg.edasich.configuration.EventPatternLoader;
import at.ac.tuwien.dsg.edasich.connector.MOMConnector;
import at.ac.tuwien.dsg.edasich.connector.SmartComConnector;
import at.ac.tuwien.dsg.edasich.streamprocessing.entity.event.EventMessage;
import at.ac.tuwien.dsg.edasich.streamprocessing.entity.event.EventPattern;
import at.ac.tuwien.dsg.edasich.streamprocessing.entity.event.SensorEvent;
*/

import at.ac.tuwien.dsg.edasich.entity.stream.EventPattern;
import at.ac.tuwien.dsg.edasich.entity.stream.Task;
import at.ac.tuwien.dsg.esperstreamprocessing.handler.SensorEventHandler;
import at.ac.tuwien.dsg.esperstreamprocessing.service.TaskDelivery;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.Configuration;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.IOUtils;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.RestHttpClient;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.RestfulWSClient;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Jun
 */
public class EventSubscriber implements StatementSubscriber {


    private EventPattern eventPattern;  
    private String dafName;
    
    public EventSubscriber() {
    }

    public EventSubscriber(EventPattern eventPattern, String dafName) {
        this.eventPattern = eventPattern;
        this.dafName = dafName;
    }

    
    
    
    
    @Override
    public String getStatement() {

        return eventPattern.getStatement();
    }

    public void update(Map<String, SensorEvent> eventMap) {

        
        StringBuilder sb = new StringBuilder();
        StringBuilder valsLog = new StringBuilder();
        sb.append("--------------------------------------------------");
             
                
        sb.append("\n- ["+eventPattern.getTask().getSeverity()+"]  ");
        

        
        for (Map.Entry<String, SensorEvent> entry : eventMap.entrySet()) {
            SensorEvent sensorEvent = entry.getValue();
            sb.append("["+sensorEvent.getName()+"  - Value: " + sensorEvent.getValue() + "]");
            String valStr ="["+sensorEvent.getName()+"  - Value: " + sensorEvent.getValue() + "]";
            valStr = valStr.replaceAll("\'", "");   
            valsLog.append(valStr + " ; ");
        }

        sb.append("\n--------------------------------------------------");

        Logger.getLogger(SensorEventHandler.class.getName()).log(Level.INFO, sb.toString());
        
        enrichData();
        forwardTask();
        logEvent(valsLog.toString());
        

    }

    public void forwardTask() {
        
        Task task = eventPattern.getTask();
        TaskDelivery delivery = new TaskDelivery();
        delivery.deliver(task);
    }
    
    public void enrichData(){
        
        Task task = eventPattern.getTask();
        String enrichmentURI = eventPattern.getEnrichmentInfo();
        RestfulWSClient ws = new RestfulWSClient(enrichmentURI);
        String taskContent = task.getContent() +"\n";
        taskContent += ws.callGetMethod("");
   
        task.setContent(taskContent);
        
    }
    
    public void logEvent(String eventVals) {
        Task task = eventPattern.getTask();
        TaskDelivery sv = new TaskDelivery();
        sv.logDetectedEvent(dafName, eventVals, task.getSeverity().name());
        
    }

    /*
    private void logTask(Task task) {

       
        try {
            String logData = marshal(task, Task.class);
            IOUtils.writeData(logData, "log");
        } catch (Exception ex) {
            System.out.println("" + ex.toString());
        }
    }

    private Task getLogTask() {

        Task task = null;

        try {
            String logData = IOUtils.readData("log");
            JAXBContext bContext = JAXBContext.newInstance(Task.class);
            Unmarshaller um = bContext.createUnmarshaller();
            task = (Task) um.unmarshal(new StringReader(logData));
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }

        return task;
    }

    */

}
