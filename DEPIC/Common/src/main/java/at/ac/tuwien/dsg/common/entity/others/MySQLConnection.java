/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.others;

import at.ac.tuwien.dsg.common.entity.DataItem;
import at.ac.tuwien.dsg.common.entity.DataObject;

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
    
    
    public void saveDeliveryEDOStr(String rsID, String dataAndResultString) {

  
        String mySqlConf = "jdbc:mysql://localhost:3306/edo_repo?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false";
        String userName = "root";
        String password = "";

        System.out.println("Delivery EDO: " + dataAndResultString);
        String sql = "INSERT INTO edo_delivery VALUES ('"+rsID+ "', '"+dataAndResultString+"') ";
      

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(mySqlConf, userName, password);

        connectionManager.ExecuteUpdate(sql);
        
   

    }
    
    public String getRawEDOStr(String key){
        
     
        String mySqlConf = "jdbc:mysql://localhost:3306/edo_repo?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false";
        String userName = "root";
        String password = "";

        String xmlStr = "";
        String sql = "select * from edo where edo.key='" + key + "'";
      

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(mySqlConf, userName, password);

        ResultSet rs = connectionManager.ExecuteQuery(sql);
        
        
        List<DataItem> listOfDataItems = new ArrayList();
        try {

            while (rs.next()) {
             
                xmlStr = rs.getString("content");
         
            }

        } catch (Exception ex) {

        }
        
      
        return xmlStr;
        
    }
    
    public String getDeliveryEDOStr(String key){
        
     
        String mySqlConf = "jdbc:mysql://localhost:3306/edo_repo?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false";
        String userName = "root";
        String password = "";

        String xmlStr = "";
        String sql = "select * from edo where edo.key='" + key + "'";
      

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(mySqlConf, userName, password);

        ResultSet rs = connectionManager.ExecuteQuery(sql);
        
        
        List<DataItem> listOfDataItems = new ArrayList();
        try {

            while (rs.next()) {
             
                xmlStr = rs.getString("content");
         
            }

        } catch (Exception ex) {

        }
        
      
        return xmlStr;
        
    }
    
}
