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
import at.ac.tuwien.dsg.esperstreamprocessing.utils.IOUtils;
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
public class EventSubscriber implements StatementSubscriber {


    private EventPattern eventPattern;   
    
    public EventSubscriber() {
    }

    public EventSubscriber(EventPattern eventPattern) {
        this.eventPattern = eventPattern;
    }

    
    
    
    @Override
    public String getStatement() {

        return eventPattern.getStatement();
    }

    public void update(Map<String, SensorEvent> eventMap) {

        
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------");
        sb.append("\n- ["+eventPattern.getTask().getSeverity()+"]  ");
        

        
        for (Map.Entry<String, SensorEvent> entry : eventMap.entrySet()) {
            SensorEvent sensorEvent = entry.getValue();
            sb.append("\n- "+sensorEvent.getName()+"  - Value: " + sensorEvent.getValue());
        }

        sb.append("\n--------------------------------------------------");
        SensorEvent val1 = (SensorEvent) eventMap.get("val1");
        SensorEvent val2 = (SensorEvent) eventMap.get("val2");

// enrich data - monitoring object specified enrich info by  developer
        System.out.println(sb.toString());
        
        forwardTask();

    }

    public void forwardTask() {
        
        Task task = eventPattern.getTask();
        
        if (task != null) {

            if (sendTaskPermission()) {
                task.setTaskID(String.valueOf(System.nanoTime()));
                logTask(task);
                
                System.out.println("Forward Task !");
              

            } else {
                System.out.println("Duplications of Pattern within Interval Time !");
            }

        }
    }

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

    private boolean sendTaskPermission() {
        boolean permission = true;

        Task task = getLogTask();
        if (task != null) {

            long previousTime = Long.parseLong(task.getTaskID());
            long currentTime = System.nanoTime();
            double different = ((currentTime - previousTime) / Math.pow(10, 6));
            double waitingTime = eventPattern.getIntervalTime();
            System.out.println("Interval time: " + waitingTime);
            System.out.println("Remaining time: " + different);
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
