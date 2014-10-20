/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.configuration;

/**
 *
 * @author Jun
 */
import at.ac.tuwien.dsg.edasich.service.engine.ext.AnalyticEngineConfiguration;
import at.ac.tuwien.dsg.edasich.utils.RestfulWSClient;
import at.ac.tuwien.dsg.edasich.utils.JAXBUtils;
import at.ac.tuwien.dsg.edasich.utils.MySqlConnectionManager;
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
        
        int index = path.indexOf("/classes/at/ac");
        path = path.substring(0, index);
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Properties prop = new Properties();
        String configString = "";
        InputStream input = null;

        try {
            input = new FileInputStream(path + "/config.properties");
            prop.load(input);
            configString = prop.getProperty(configureName);
 
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
    
    public static AnalyticEngineConfiguration getAnalyticEngineConfiguration(String analyticEngineID){
        
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
    }
    

}
