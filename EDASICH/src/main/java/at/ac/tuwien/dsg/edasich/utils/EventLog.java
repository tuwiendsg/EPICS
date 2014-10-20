/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.utils;

import static at.ac.tuwien.dsg.edasich.configuration.Configuration.getConfig;
import java.sql.ResultSet;

/**
 *
 * @author Jun
 */
public class EventLog {
    
    public ResultSet getEventLog(String dafName){
        
        String ip = getConfig("DB.EDASICH.IP");
        String port = getConfig("DB.EDASICH.PORT");
        String database = getConfig("DB.EDASICH.DATABASE");
        String username = getConfig("DB.EDASICH.USERNAME");
        String password = getConfig("DB.EDASICH.PASSWORD");
        
        
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, database, username, password);
        
        String sql = "Select * from Event where daf='"+dafName+"' ORDER BY id DESC LIMIT 30";
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        
        return  rs;
    }
    
    
    
    
    
    
    
}
