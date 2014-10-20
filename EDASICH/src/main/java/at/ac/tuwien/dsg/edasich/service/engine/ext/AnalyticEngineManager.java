/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.service.engine.ext;

import static at.ac.tuwien.dsg.edasich.configuration.Configuration.getConfig;
import at.ac.tuwien.dsg.edasich.utils.MySqlConnectionManager;
import java.sql.ResultSet;

/**
 *
 * @author Jun
 */
public class AnalyticEngineManager {
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
}
