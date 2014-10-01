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
        sb.append("\n- [WARNING] : PATTERN DETECTED ");
        sb.append("\n--------------------------------------------------");

        System.out.println(sb.toString());
        
        forwardTask();

    }

    public void forwardTask() {
        
        Task task = eventPattern.getTask();
        
        if (task != null) {

            if (sendTaskPermission()) {
                task.setTaskID(String.valueOf(System.nanoTime()));
                logTask(task);
                
              

            } else {
                System.out.println("Replication of messages. NO message sent !");
            }

        }
    }

    private void logTask(Task task) {

        FileWriter fstream;
        try {
            String xmlData = marshal(task, Task.class);
            fstream = new FileWriter("log/" + task.getTaskID(), false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(xmlData);
            out.close();
        } catch (Exception ex) {
            System.out.println("" + ex.toString());
        }
    }

    private Task getLogTask() {

        Task task = null;

        try {
            FileReader inputFile = new FileReader("log/" + task.getTaskID());
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String dataStr = "";
            String line = "";
            while ((line = bufferReader.readLine()) != null) {
                //  System.out.println(line);
                dataStr += line;
            }

            JAXBContext bContext = JAXBContext.newInstance(Task.class);
            Unmarshaller um = bContext.createUnmarshaller();
            task = (Task) um.unmarshal(new StringReader(dataStr));

            bufferReader.close();
        } catch (Exception e) {

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
