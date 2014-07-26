/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataresourceaccess.powerconsumption;

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
public class ICU_DB {
    
    private static int numberOfDataObjects = 100;
    private static int counter = 0;

    public static void getDataItems() {

        String mySqlConf = "jdbc:mysql://166.62.8.18:3306/icu_db?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false";
        String userName = "icu_db";
        String password = "aaA@@123";

        String xmlStr = "";
        String sql = "select * from icu_profile";
       
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(mySqlConf, userName, password);

        ResultSet rs = connectionManager.ExecuteQuery(sql);
        
        
     
        try {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
          
               
                String dataItemStr = id +";" + name +";";
                
                System.out.println("RS : " + dataItemStr);
             
            }

        } catch (Exception ex) {

        }
        
        
 

    }
    
}
