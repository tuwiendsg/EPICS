/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataassetfunctionmanagement;

import at.ac.tuwien.dsg.depic.common.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.configuration.Configuration;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        String ip = "localhost";
        String port = "3306";
        String db = "Power";
        String user = "root";
        String pass = "123";
        
        int startID =1845833;

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, pass);
        
    
        
        
        String sql="SELECT * FROM power_consumption";
        ResultSet rs = connectionManager.ExecuteQuery(sql);
     
        try {
            while (rs.next()) {

                String date = rs.getString("date");
                int id = rs.getInt("id");

                if (id >= startID) {
                    String[] strs = date.split("/");

                    if (strs.length == 3) {

                        String newDate = strs[2] + "-" + strs[1] + "-" + strs[0];

                        String updateSQL = "UPDATE power_consumption SET date='" + newDate + "' WHERE id=" + id + "";
                        connectionManager.ExecuteUpdate(updateSQL);

                        System.out.println(id);
                    }
                }
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
