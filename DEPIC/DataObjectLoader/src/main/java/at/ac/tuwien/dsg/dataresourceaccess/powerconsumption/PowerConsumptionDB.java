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
public class PowerConsumptionDB {

    private int numberOfDataObjects = 100;
   

    public DataObject getDataItems(int dataObjectIndex) {

        int counter = dataObjectIndex*100;
        String mySqlConf = "jdbc:mysql://localhost:3306/Power?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false";
        String userName = "root";
        String password = "123";

        String xmlStr = "";
        String sql = "select * from power_consumption where power_consumption.id>" + counter + " and power_consumption.id<" + (counter + numberOfDataObjects + 1);
      

        MySqlConnectionManager connectionManager = new MySqlConnectionManager(mySqlConf, userName, password);

        ResultSet rs = connectionManager.ExecuteQuery(sql);
        
        
        List<DataItem> listOfDataItems = new ArrayList();
        try {

            while (rs.next()) {
                int id = rs.getInt("id");
                String date = rs.getString("date");
                String time = rs.getString("time");
                
                double global_active_power = rs.getDouble("global_active_power");
                double global_reactive_power = rs.getDouble("global_reactive_power");
                double voltage = rs.getDouble("voltage");
                double global_intensity = rs.getDouble("global_intensity");

                
                String dataItemStr = date +";" + time +";" 
                        + String.valueOf(global_active_power) +";" 
                        + String.valueOf(global_reactive_power) +";" 
                        + String.valueOf(voltage) +";" 
                        + String.valueOf(global_intensity);
                
                System.out.println("Data Item: " + id + " - Content: " + dataItemStr);
                DataItem dataItem = new DataItem(id, dataItemStr);
                listOfDataItems.add(dataItem);
            }

        } catch (Exception ex) {

        }
        
        
        DataObject dataObject = new DataObject(listOfDataItems);

        return dataObject;

    }

}
