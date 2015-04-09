/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataimporter;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataItem;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class CassandraDataImporter {
    
    private String ip;
    private int port;
    private String tableName; 
    private String primaryKey;
    private String listOfColumns;
    private String csvFilePath;
    private int noOfRows;
    
    
    public CassandraDataImporter(String ip, int port, String tableName, String primaryKey, String csvFilePath, int noOfRows) {
        this.ip = ip;
        this.port = port;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
        this.csvFilePath = csvFilePath;
        this.noOfRows = noOfRows;
    }
    

    public void insertDataToCassandra() {

        

        BufferedReader br = null;
        int i = 0;

        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(csvFilePath));

            while ((sCurrentLine = br.readLine()) != null) {
               
                if (i == 0) {
                    // get column name
                    String[] columnNames = sCurrentLine.split("\t");
                    String sql = "CREATE TABLE caskey."+tableName+" (";
                    listOfColumns = "";
               
                    String primaryKeyList = "";

                    String columnStrs = "";

                    String[] strs = primaryKey.split(";");

                    for (int j = 0; j < strs.length; j++) {
                        String key_j = strs[j];
                        String[] keyInfo = key_j.split("!");
                        int keyIndex = Integer.parseInt(keyInfo[0]);

                        if (keyIndex == -1) {
                            String keyName = "uuid";
                            String keyDataType = "int";
                            columnStrs = columnStrs + " " + keyName + " " + keyDataType + ",";
                            primaryKeyList  = primaryKeyList + keyName + ",";
                            listOfColumns = listOfColumns + keyName+",";
                        } else {
                            String keyName = columnNames[keyIndex];
                            String keyDataType = keyInfo[1];
                            columnStrs = columnStrs + " " + keyName + " " + keyDataType + ",";
                            primaryKeyList  = primaryKeyList + keyName + ",";
                            listOfColumns = listOfColumns + keyName+",";
                        }

                    }
                    
                    
                    sql = sql 
                            + " data text,"
                            + " PRIMARY KEY ("+primaryKeyList.substring(0, primaryKeyList.length()-1)+"));";

                    listOfColumns = listOfColumns +"data";
                    System.out.println(i+" SQL: \n" + sql);
                    //create new table
                    //CassandraDataAssetStore.createTable(sql);
                    
            
                    
                } else {
                    //get column data
                    String[] dataPoints = sCurrentLine.split("\t");

                    String valueStr = "";
                    String[] strs = primaryKey.split(";");
                    
                    for (int j = 0; j < strs.length; j++) {
                        String key_j = strs[j];
                        String[] keyInfo = key_j.split("!");
                        int keyIndex = Integer.parseInt(keyInfo[0]);

                        if (keyIndex == -1) {
                           valueStr = valueStr + i +",";
                        } else {
                            String keyDataType = keyInfo[1];
                            if (keyDataType.equals("varchar") || keyDataType.equals("text")){
                                valueStr = valueStr +"'" +dataPoints[keyIndex] +"',";
                            } else {
                                valueStr = valueStr + dataPoints[keyIndex] +",";
                            }
                            
                            
                        }

                    }
                    
                    valueStr = valueStr +"'" + sCurrentLine +"'";
                    
                     String sql = "INSERT INTO caskey."+tableName+" ("+listOfColumns+") "
                    + "VALUES ("+valueStr+ ")";
                    
                     System.out.println(i+" sql: \n" +sql);
                   // CassandraDataAssetStore.insertData(sql);
                     
                   
                }

                
                 i++;
                
                if (i == noOfRows) {
                    break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
