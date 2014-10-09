/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.esperstreamprocessing.utils;

/**
 *
 * @author Jun
 */

import at.ac.tuwien.dsg.edasich.configuration.MOMConfiguration;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {

    public Configuration() {
    }

    public static String getConfig(String configureName) {

        
        String configString = "";
        
        try {
            String momXML = IOUtils.readData("momconf");
            MOMConfiguration conf = JAXBUtils.unmarshal(momXML, MOMConfiguration.class);


            switch (configureName) {
                case "MOM.IP":
                    configString = conf.getIp();
                    break;
                case "MOM.PORT":
                    configString = conf.getPort();
                    break;
                case "MOM.QUEUE_NAME":
                    configString = conf.getQueue();
                    break;
                case "MESSAGE.LIMIT":
                    configString = String.valueOf(conf.getLimit());
                    break;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } 

        return configString;
    }
    
    
    public static String getConfiguration(String configureName) {

       

        String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        int index = path.indexOf("/classes/at/ac");
        path = path.substring(0, index);
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(Configuration.class.getName()).log(Level.INFO,"Configure Path: " + path);
        
        Properties prop = new Properties();
        String configString = "";
        InputStream input = null;

        try {

            input = new FileInputStream(path + "/config.properties");

            // load a properties file
            prop.load(input);

            switch (configureName) {
                case "MOM.IP":
                    configString = prop.getProperty("MOM.IP");
                    break;
                case "MOM.PORT":
                    configString = prop.getProperty("MOM.PORT");
                    break;
                case "MOM.QUEUE_NAME":
                    configString = prop.getProperty("MOM.QUEUE_NAME");
                    break;
                case "MESSAGE.LIMIT":
                    configString = prop.getProperty("MESSAGE.LIMIT");
                    break;
                case "DAAS.IP":
                    configString = prop.getProperty("DAAS.IP");
                    break;
                case "DAAS.PORT":
                    configString = prop.getProperty("DAAS.PORT");
                    break;
                case "DAAS.SENSOR.START":
                    configString = prop.getProperty("DAAS.SENSOR.START");
                    break;
                case "DAAS.SENSOR.STOP":
                    configString = prop.getProperty("DAAS.SENSOR.STOP");
                    break;    
                case "WF.DAW":
                    configString = prop.getProperty("WF.DAW");
                    break;
                case "LOCAL.STORE":
                    configString = prop.getProperty("LOCAL.STORE");
                    break;  
                case "SMARTCOM.TOKEN":
                    configString = prop.getProperty("SMARTCOM.TOKEN");
                    break;  
                case "SMARTCOM.PEER":
                    configString = prop.getProperty("SMARTCOM.PEER");
                    break;  
                case "SMARTCOM.ADAPTER":
                    configString = prop.getProperty("SMARTCOM.ADAPTER");
                    break;
                case "SALAM.IP":
                    configString = prop.getProperty("SALAM.IP");
                    break;
                case "SALAM.PORT":
                    configString = prop.getProperty("SALAM.PORT");
                    break;
                case "SALAM.RESOURCE":
                    configString = prop.getProperty("SALAM.RESOURCE");
                    break;    
                case "TASK.DISTRIBUTION.IP":
                    configString = prop.getProperty("TASK.DISTRIBUTION.IP");
                    break;
                case "TASK.DISTRIBUTION.PORT":
                    configString = prop.getProperty("TASK.DISTRIBUTION.PORT");
                    break;    
                case "TASK.DISTRIBUTION.API":
                    configString = prop.getProperty("TASK.DISTRIBUTION.API");
                    break;    
                    
                    
                case "DB.EDASICH.IP":
                    configString = prop.getProperty("DB.EDASICH.IP");
                    break;
                case "DB.EDASICH.PORT":
                    configString = prop.getProperty("DB.EDASICH.PORT");
                    break;    
                case "DB.EDASICH.DATABASE":
                    configString = prop.getProperty("DB.EDASICH.DATABASE");
                    break;    
                case "DB.EDASICH.USERNAME":
                    configString = prop.getProperty("DB.EDASICH.USERNAME");
                    break;
                case "DB.EDASICH.PASSWORD":
                    configString = prop.getProperty("DB.EDASICH.PASSWORD");
                    break;    

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return configString;
    }
 
    


}
