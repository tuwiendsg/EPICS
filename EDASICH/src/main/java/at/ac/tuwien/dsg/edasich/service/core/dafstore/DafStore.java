/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.service.core.dafstore;

import static at.ac.tuwien.dsg.edasich.configuration.Configuration.getConfig;
import at.ac.tuwien.dsg.edasich.utils.MySqlConnectionManager;
import java.sql.ResultSet;

/**
 *
 * @author Jun
 */
public class DafStore {
    public ResultSet getDAF(){
        
        String ip = getConfig("DB.EDASICH.IP");
        String port = getConfig("DB.EDASICH.PORT");
        String database = getConfig("DB.EDASICH.DATABASE");
        String username = getConfig("DB.EDASICH.USERNAME");
        String password = getConfig("DB.EDASICH.PASSWORD");
        
        
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, database, username, password);
        
        String sql = "Select * from Daf";
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        
        return  rs;
    }
    
    
    public void insertDAF(String dafName){
        
        String ip = getConfig("DB.EDASICH.IP");
        String port = getConfig("DB.EDASICH.PORT");
        String database = getConfig("DB.EDASICH.DATABASE");
        String username = getConfig("DB.EDASICH.USERNAME");
        String password = getConfig("DB.EDASICH.PASSWORD");
        
        
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, database, username, password);
        
        String sql = "INSERT INTO Daf (name,status) VALUES ('"+dafName+"','stop')";
        
        connectionManager.ExecuteUpdate(sql);
 
    }
    
    
    public void deleteDAF(String dafName){
        
        String ip = getConfig("DB.EDASICH.IP");
        String port = getConfig("DB.EDASICH.PORT");
        String database = getConfig("DB.EDASICH.DATABASE");
        String username = getConfig("DB.EDASICH.USERNAME");
        String password = getConfig("DB.EDASICH.PASSWORD");
        
        
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, database, username, password);
        
        String sql = "DELETE FROM Daf WHERE name='"+dafName+"'";
        
        connectionManager.ExecuteUpdate(sql);
 
    }
    
    
    public void updateDAF(String dafName,String status){
        
        String ip = getConfig("DB.EDASICH.IP");
        String port = getConfig("DB.EDASICH.PORT");
        String database = getConfig("DB.EDASICH.DATABASE");
        String username = getConfig("DB.EDASICH.USERNAME");
        String password = getConfig("DB.EDASICH.PASSWORD");
        
        
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, database, username, password);
        
        String sql = "UPDATE Daf SET status='"+status+"' WHERE name='"+dafName+"'";
        
        connectionManager.ExecuteUpdate(sql);
 
    }
    
}
