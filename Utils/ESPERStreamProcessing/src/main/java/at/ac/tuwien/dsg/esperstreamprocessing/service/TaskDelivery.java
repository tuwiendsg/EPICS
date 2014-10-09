/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.esperstreamprocessing.service;

import at.ac.tuwien.dsg.edasich.entity.stream.Task;
import at.ac.tuwien.dsg.esperstreamprocessing.handler.SensorEventHandler;

import at.ac.tuwien.dsg.esperstreamprocessing.utils.Configuration;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.RestHttpClient;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Jun
 */
public class TaskDelivery {
    
    public void deliver(Task task){
        
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
            //System.out.println("Forward Task !");
            String log = "Forward Task !" + paramList.toString();
            Logger.getLogger(SensorEventHandler.class.getName()).log(Level.INFO, log);
        }
    }
    
    public void logDetectedEvent(String dafName, String eventValues, String severity) {

        Date dNow = new Date();
        SimpleDateFormat ft
                = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        String detectedTime = ft.format(dNow);

        String ip = Configuration.getConfiguration("DB.EDASICH.IP");
        String port = Configuration.getConfiguration("DB.EDASICH.PORT");
        String database = Configuration.getConfiguration("DB.EDASICH.DATABASE");
        String username = Configuration.getConfiguration("DB.EDASICH.USERNAME");
        String password = Configuration.getConfiguration("DB.EDASICH.PASSWORD");

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, database, username, password);

        String sql = "INSERT INTO Event (daf,detected_time,event_values,severity) "
                + "VALUES ('" + dafName + "','" + detectedTime + "','" + eventValues + "','" + severity + "')";

        Logger.getLogger(SensorEventHandler.class.getName()).log(Level.INFO, sql);
        connectionManager.ExecuteUpdate(sql);

    }
}
