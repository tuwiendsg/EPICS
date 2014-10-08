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
import at.ac.tuwien.dsg.esperstreamprocessing.utils.Configuration;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.IOUtils;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.RestHttpClient;
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


// enrich data - monitoring object specified enrich info by  developer
     
        Logger.getLogger(SensorEventHandler.class.getName()).log(Level.INFO, sb.toString());
        forwardTask();

    }

    public void forwardTask() {
        
        Task task = eventPattern.getTask();
        
        if (task != null) {

            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            paramList.add(new BasicNameValuePair("name", task.getName()));
            paramList.add(new BasicNameValuePair("content", task.getContent()));
            paramList.add(new BasicNameValuePair("tag", task.getTag()));
            paramList.add(new BasicNameValuePair("severity", task.getSeverity().name()));

            String ip = Configuration.getConfiguration("SALAM.IP");
            String port = Configuration.getConfiguration("SALAM.PORT");
            String resouce = Configuration.getConfiguration("SALAM.RESOURCE");

            RestHttpClient ws = new RestHttpClient(ip, port, resouce);
            ws.callPostMethod(paramList);
            System.out.println("Forward Task !");
            Logger.getLogger(SensorEventHandler.class.getName()).log(Level.INFO, "Forward Task !" + paramList.toString());
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


    
    

    private <T> String marshal(Object source, Class<T> configurationClass) throws JAXBException {
        JAXBContext jAXBContext = JAXBContext.newInstance(configurationClass);
        StringWriter writer = new StringWriter();
        jAXBContext.createMarshaller().marshal(source, writer);
        return writer.toString();
    }
}
