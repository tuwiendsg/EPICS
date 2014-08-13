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
    
    private String ip="localhost";
    private String mySqlConf = "jdbc:mysql://"+ip+":3306/edo_repo?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false";
    private String userName = "root";
    private String password = "123";
    
    
    public void saveRawEDOStr(String rsID, String dataAndResultString) {

  
        

        System.out.println("Raw EDO: " + dataAndResultString);
        String sql = "INSERT INTO edo VALUES ('"+rsID+ "', '"+dataAndResultString+"') ";
      

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(mySqlConf, userName, password);

        connectionManager.ExecuteUpdate(sql);
        
   

    }
    
    
    public void saveDeliveryEDOStr(String rsID, String dataAndResultString) {

  
      

        System.out.println("Delivery EDO: " + dataAndResultString);
        String sql = "INSERT INTO edo_delivery VALUES ('"+rsID+ "', '"+dataAndResultString+"') ";
      

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(mySqlConf, userName, password);

        connectionManager.ExecuteUpdate(sql);
        
   

    }
    
    
    public void saveResponseTime(String rsID, double responseTime) {

  
   
        String sql = "INSERT INTO responsetime VALUES ('"+rsID+ "', "+responseTime+") ";
      

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(mySqlConf, userName, password);

        connectionManager.ExecuteUpdate(sql);
        
   

    }
    
    
    public void updateDeliveryEDOStr(String rsID, String dataAndResultString) {

  
      

        System.out.println("Delivery EDO: " + dataAndResultString);
        String sql = "UPDATE edo_delivery SET edo_delivery.content='"+dataAndResultString+"' WHERE edo_delivery.key = '"+rsID+ "'";
      

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(mySqlConf, userName, password);

        connectionManager.ExecuteUpdate(sql);
        
   

    }
    
    public String getRawEDOStr(String key){
        
     
  

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
        
     
    
        String xmlStr = "";
        String sql = "select * from edo_delivery where edo_delivery.key='" + key + "'";
      

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
    
    
    public double getResponseTime (String key) {
        
        double xmlStr=0;
        String sql = "select * from responsetime where responsetime.key='" + key + "'";
      

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(mySqlConf, userName, password);

        ResultSet rs = connectionManager.ExecuteQuery(sql);
        
        
        try {

            while (rs.next()) {
             
                xmlStr = rs.getDouble("value");
         
            }

        } catch (Exception ex) {

        }
        
      
        return xmlStr;
    }
    
}
