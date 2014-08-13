/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataresourceaccess.edorepository;

import at.ac.tuwien.dsg.common.entity.others.MySQLConnection;

/**
 *
 * @author Jun
 */
public class ResponseTimeAccessManagement {
    
    
    public double getResponseTime(String key) {

        MySQLConnection mySQLConnection = new MySQLConnection();

        double xmlString = mySQLConnection.getResponseTime(key);

        return xmlString;
    }
    
    
    public int getTimeStamp(String key) {

        MySQLConnection mySQLConnection = new MySQLConnection();

        int xmlString = mySQLConnection.getTimeStamp(key);

        return xmlString;
    }
    
    public void saveResponseTime(String key, double responseTime, int time) {

        MySQLConnection mySQLConnection = new MySQLConnection();

        mySQLConnection.saveResponseTime(key, responseTime, time);

       
    }
    
    
}
