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

import at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing.DataAnalyticFunctionCep;
import at.ac.tuwien.dsg.esperstreamprocessing.handler.EventHandler;
import at.ac.tuwien.dsg.esperstreamprocessing.service.TaskDelivery;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.Configuration;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.IOUtils;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.JAXBUtils;
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

    private DataAnalyticFunctionCep daf;

    public EventSubscriber() {
    }

    public EventSubscriber(DataAnalyticFunctionCep daf) {
        this.daf = daf;
    }

    @Override
    public String getStatement() {
        
        return daf.getDafAnalyticCep().getEplStatement();
    }

    public void update(Map<String, SensorEvent> eventMap) {

        List<String> listOfSensors = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        StringBuilder valsLog = new StringBuilder();
        sb.append("--------------------------------------------------");

     //   sb.append("\n- [" + eventPattern.getTask().getSeverity() + "]  ");

        for (Map.Entry<String, SensorEvent> entry : eventMap.entrySet()) {
            SensorEvent sensorEvent = entry.getValue();
            sb.append("[" + sensorEvent.getName() + "  - Value: " + sensorEvent.getValue() + "]");
            String valStr = "[" + sensorEvent.getName() + "  - Value: " + sensorEvent.getValue() + "]";
            valStr = valStr.replaceAll("\'", "");
            valsLog.append(valStr + " ; ");
            if (!listOfSensors.contains(sensorEvent.getName())) {
                listOfSensors.add(sensorEvent.getName());
            }
        }

        sb.append("\n--------------------------------------------------");

        Logger.getLogger(EventSubscriber.class.getName()).log(Level.INFO, sb.toString());

        logEvent(valsLog.toString());
        
        /*
        if (sendTaskPermission()) {
            enrichData(listOfSensors);
            forwardTask(valsLog.toString());
            logEvent(valsLog.toString());
            logTask();
        }

        */
        
        
    }

    
    
    public void logEvent(String eventVals) {
      
        TaskDelivery sv = new TaskDelivery();
        sv.logDetectedEvent(daf.getDafName(), eventVals, "");

    }
 /*   
    
    public void forwardTask(String eventVals) {

        
 
        
        Task task = eventPattern.getTask();
        TaskDelivery delivery = new TaskDelivery();
        delivery.deliver(task,enrichmentInfo,eventVals);
    }

    public void enrichData(List<String> listOfSensors) {
        
      
        String params="";
        for (int i=0;i<listOfSensors.size();i++){
            if (i<(listOfSensors.size()-1)){
                params = params + listOfSensors.get(i) + ",";
            } else {
                params = params + listOfSensors.get(i);
            }
            
        }

        String enrichmentURI = eventPattern.getEnrichmentInfo();
        RestfulWSClient ws = new RestfulWSClient(enrichmentURI);
        
        enrichmentInfo = ws.callGetDirectURL(params);

        Logger.getLogger(EventSubscriber.class.getName()).log(Level.INFO, "ENRICHMENT DATA: " + enrichmentInfo);
  


    }

    public void logEvent(String eventVals) {
        Task task = eventPattern.getTask();
        TaskDelivery sv = new TaskDelivery();
        sv.logDetectedEvent(dafName, eventVals, task.getSeverity().name());

    }

    private void logTask() {
       
        try {
            String logData = String.valueOf(System.nanoTime());
            IOUtils.writeData(logData, "log");
        } catch (Exception ex) {
            System.out.println("" + ex.toString());
        }
    }

    private String getLogTask() {

        String logData="";

        try {
            logData = IOUtils.readData("log");
        } catch (Exception e) {
            System.out.println("" + e.toString());
        }

        return logData;
    }

    private boolean sendTaskPermission() {
        boolean permission = true;

        String logTask = getLogTask();
        logTask = logTask.replaceAll("\n", "");
        String lt = "LOG TASK -" + logTask+"-";
         Logger.getLogger(EventSubscriber.class.getName()).log(Level.INFO, lt);
        if (!logTask.equals("")) {

            long previousTime = Long.parseLong(logTask);
            long currentTime = System.nanoTime();
            double different = ((currentTime - previousTime) / Math.pow(10, 6));
            double waitingTime = Double.parseDouble(Configuration.getConfiguration("TASK.INTERVAL"));
            Logger.getLogger(EventSubscriber.class.getName()).log(Level.INFO, "Interval time: " + waitingTime);
            Logger.getLogger(EventSubscriber.class.getName()).log(Level.INFO, "Remaining time: " + different);
      
            if (different < waitingTime) {
                permission = false;
                
            }

        } 
            
        if (permission){
            Logger.getLogger(EventSubscriber.class.getName()).log(Level.INFO, "PERMISSION: YES");
        } else {
            Logger.getLogger(EventSubscriber.class.getName()).log(Level.INFO, "PERMISSION: NO");
        }

        return permission;
    }
   */ 
}
