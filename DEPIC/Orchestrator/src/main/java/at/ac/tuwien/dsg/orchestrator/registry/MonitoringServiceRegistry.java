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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class MonitoringServiceRegistry {

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
        String sql = "select * from MonitoringService where id='" + serviceID+"'";
        String uri = "";
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        try {
            while (rs.next()) {
                uri = rs.getString("uri");    
 
            }

        } catch (Exception ex) {

        }
        
        return  uri;
        
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
