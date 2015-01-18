/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.registry;

import at.ac.tuwien.dsg.common.deployment.DeployAction;
import at.ac.tuwien.dsg.common.entity.process.ActionDependency;
import at.ac.tuwien.dsg.common.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.orchestrator.configuration.Configuration;
import at.ac.tuwien.dsg.orchestrator.entity.MonitoringService;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Jun
 */
public class MonitoringServiceRegistry {
    
    List<MonitoringService> listOfMonitoringServices;

    MySqlConnectionManager connectionManager;
    
    public MonitoringServiceRegistry() {
        Configuration config = new Configuration();
        String ip = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.IP");
        String port = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PORT");
        String database = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.DATABASE");
        String username = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.USERNAME");
        String password = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PASSWORD");
        connectionManager = new MySqlConnectionManager(ip, port, database, username, password);
    }

    public String getMonitoringServiceURI(String serviceID){
        
        List<String> listOfServices = new ArrayList<String>();
        
        for (MonitoringService monitoringService : listOfMonitoringServices){
            
            if (monitoringService.getActionID().equals(serviceID)){
                String uri = monitoringService.getUri();
                listOfServices.add(uri);
            }
        }
        
        int randomIndex = randomInt(0, listOfServices.size()-1);
        return  listOfServices.get(randomIndex);
        
    }
    
    private int randomInt(int min, int max){
        
        Random random = new Random();
        
        int randomNumber = random.nextInt(max+1 - min) + min;
        return randomNumber;
    }
    
    
    public void getMonitoringServices(){
        String sql = "select * from MonitoringService";
        
        listOfMonitoringServices = new ArrayList<MonitoringService>();
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        try {
            while (rs.next()) {
                String actionID = rs.getString("actionID");
                String uri = rs.getString("uri");    
                
                MonitoringService monitoringService = new MonitoringService(actionID, uri);
                listOfMonitoringServices.add(monitoringService);
            }

        } catch (Exception ex) {

        }
        
        
        
        
    }
    
    
    public String getMonitoringMetricName(String serviceID){
        String sql = "select * from MonitoringService where id='" + serviceID+"'";
        String metricName = "";
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        try {
            while (rs.next()) {
                metricName = rs.getString("metricName");    
 
            }

        } catch (Exception ex) {

        }
        
        return  metricName;
        
    }
    
}
