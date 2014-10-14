/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataconfiguration;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

public class Configuration {
    
    
    
    
    public Configuration() {
    }

    public static String getConfig(String configureName) {

       

        String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        
      //  System.out.println("path: " + path);
        
        int index = path.indexOf("/classes/at/ac");
        path = path.substring(0, index);
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            System.out.println("Exception occured  :"+ex);
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
      //  Logger.getLogger(Configuration.class.getName()).log(Level.INFO, path);
        
        Properties prop = new Properties();
        String configString = "";
        InputStream input = null;

        try {

            input = new FileInputStream(path + "/configuration.properties");

            // load a properties file
            prop.load(input);
            
            switch (configureName)
            {
                case "VIRTUOSO.IP":
                    configString=prop.getProperty("VIRTUOSO.IP");
                    break;
                    case "VIRTUOSO.PORT":
                    configString=prop.getProperty("VIRTUOSO.PORT");
                    break;
                        
                    case "ENHANCEMENT.IP":
                    configString=prop.getProperty("ENHANCEMENT.IP");
                    break;
                            
                    case "ENHANCEMENT.PORT":
                    configString=prop.getProperty("ENHANCEMENT.PORT");
                    break;
            }

           /* switch (configureName) {
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
                    configString = prop.getProperty("SMARTCOM.ADAPTER");
                    break;
                case "SALAM.PORT":
                    configString = prop.getProperty("SMARTCOM.ADAPTER");
                    break;
                case "SALAM.TASK.API":
                    configString = prop.getProperty("SMARTCOM.ADAPTER");
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

            }*/

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
    
    /*public static AnalyticEngineConfiguration getAnalyticEngineConfiguration(String analyticEngineID){
        
        System.out.println("Analytic Engine ID: " + analyticEngineID);
        
        AnalyticEngineConfiguration analyticEngineConfiguration=null;
        String ip = getConfig("DB.EDASICH.IP");
        String port = getConfig("DB.EDASICH.PORT");
        String database = getConfig("DB.EDASICH.DATABASE");
        String username = getConfig("DB.EDASICH.USERNAME");
        String password = getConfig("DB.EDASICH.PASSWORD");
        
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, database, username, password);
        
        String sql = "Select * from AnalyticEngine where analyticEngineID='"+analyticEngineID+"'";
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);

        try {
            while (rs.next()) {
                String analyticEngineName = rs.getString("analyticEngineName");
                String analyticEngineIp = rs.getString("ip");
                String analyticEnginePort = rs.getString("port");
                String analyticEngineApi = rs.getString("api");
                analyticEngineConfiguration = new AnalyticEngineConfiguration(analyticEngineID, analyticEngineName, analyticEngineIp, analyticEnginePort, analyticEngineApi);
            
            }

        } catch (Exception ex) {

        }
        
        
        return  analyticEngineConfiguration;
    }
    
    public static void submitMOMConf(String analyticEngineID) {
        
        
        String ip= getConfig("MOM.IP");
    String port= getConfig("MOM.PORT");
    String queue= getConfig("MOM.QUEUE_NAME");
    int limit= Integer.parseInt(getConfig("MESSAGE.LIMIT"));
    
        MOMConfiguration momConf = new MOMConfiguration(ip, port, queue, limit);
        
        try {
            AnalyticEngineConfiguration aec = getAnalyticEngineConfiguration(analyticEngineID);
            RestfulWSClient restClient = new RestfulWSClient(aec.getIp(), aec.getPort(), aec.getApi()+"/momconf");
            String xmlStr = JAXBUtils.marshal(momConf, MOMConfiguration.class);
            restClient.callPutMethod(xmlStr);
        } catch (JAXBException ex) {
       
        }    
    }
    
    public static void submitTaskDistributionConf(String analyticEngineID) {
        
        String ip = getConfig("TASK.DISTRIBUTION.IP");
        String port = getConfig("TASK.DISTRIBUTION.PORT");
        String api = getConfig("TASK.DISTRIBUTION.API"); 
        
        TaskDistributionConfiguration taskDConf = new TaskDistributionConfiguration(ip, port, api);
        
        
        try {
            AnalyticEngineConfiguration aec = getAnalyticEngineConfiguration(analyticEngineID);
            RestfulWSClient restClient = new RestfulWSClient(aec.getIp(), aec.getPort(), aec.getApi()+"/taskdistributionconf");
            String xmlStr = JAXBUtils.marshal(taskDConf, TaskDistributionConfiguration.class);
            restClient.callPutMethod(xmlStr);
        } catch (JAXBException ex) {
       
        }  
    }*/
    

}