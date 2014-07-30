/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataresourceaccess.edorepository;

import at.ac.tuwien.dsg.common.entity.DataItem;
import at.ac.tuwien.dsg.common.entity.DataObject;
import at.ac.tuwien.dsg.dataresourceaccess.MySqlConnectionManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class MySQLConnection {
    
    
    public void saveRawEDOStr(String rsID, String dataAndResultString) {

  
        String mySqlConf = "jdbc:mysql://localhost:3306/edo_repo?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false";
        String userName = "root";
        String password = "";

        System.out.println("Raw EDO: " + dataAndResultString);
        String sql = "INSERT INTO edo VALUES ('"+rsID+ "', '"+dataAndResultString+"') ";
      

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(mySqlConf, userName, password);

        connectionManager.ExecuteUpdate(sql);
        
   

    }
    
}
