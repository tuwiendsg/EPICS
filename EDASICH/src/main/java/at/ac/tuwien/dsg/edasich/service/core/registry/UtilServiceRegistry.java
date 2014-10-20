/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.service.core.registry;

import at.ac.tuwien.dsg.edasich.utils.MySqlConnectionManager;
import java.sql.ResultSet;

/**
 *
 * @author Jun
 */
public class UtilServiceRegistry {
    
    
    public ResultSet executeQueryMySQL(String ip, String port, String database, String username, String password, String sql){
        
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, database, username, password);  
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        
        return rs;
    }
    
}
