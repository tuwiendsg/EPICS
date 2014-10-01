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
import at.ac.tuwien.dsg.edasich.utils.MySqlConnectionManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.Properties;

public class Configuration {

    public Configuration() {
    }

    public static String getConfig(String configureName) {

        Properties prop = new Properties();
        String configString = "";
        InputStream input = null;

        try {

            input = new FileInputStream("config/config.properties");

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
                case "DB.CONTROLACTIONS.IP":
                    configString = prop.getProperty("DB.CONTROLACTIONS.IP");
                    break;
                case "DB.CONTROLACTIONS.PORT":
                    configString = prop.getProperty("DB.CONTROLACTIONS.PORT");
                    break;    
                case "DB.CONTROLACTIONS.DATABASE":
                    configString = prop.getProperty("DB.CONTROLACTIONS.DATABASE");
                    break;    
                case "DB.CONTROLACTIONS.USERNAME":
                    configString = prop.getProperty("DB.CONTROLACTIONS.USERNAME");
                    break;
                case "DB.CONTROLACTIONS.PASSWORD":
                    configString = prop.getProperty("DB.CONTROLACTIONS.PASSWORD");
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
    
    public static AnalyticEngineConfiguration getAnalyticEngineConfiguration(String analyticEngineID){
        AnalyticEngineConfiguration analyticEngineConfiguration=null;
        String ip = getConfig("DB.CONTROLACTIONS.IP");
        String port = getConfig("DB.CONTROLACTIONS.PORT");
        String database = getConfig("DB.CONTROLACTIONS.DATABASE");
        String username = getConfig("DB.CONTROLACTIONS.USERNAME");
        String password = getConfig("DB.CONTROLACTIONS.PASSWORD");
        
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, database, username, password);
        
        String sql = "Select * from ControlAction where analyticEngineID='"+analyticEngineID+"'";
        
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

}
