/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.store;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Jun
 */
public class DataAssetFunctionStore {

    MySqlConnectionManager connectionManager;
    
    public DataAssetFunctionStore() {
        Configuration config = new Configuration();
        String ip = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.IP");
        String port = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PORT");
        String database = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.DATABASE");
        String username = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.USERNAME");
        String password = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PASSWORD");
        connectionManager = new MySqlConnectionManager(ip, port, database, username, password);
    }
    
    public String getDataAssetFunction(String dataAssetID){

        String dafXML = "";
        try {
            InputStream inputStream = null;

            String sql = "select * from DataAssetFunction WHERE dataAssetID='" + dataAssetID + "'";
            ResultSet rs = connectionManager.ExecuteQuery(sql);

            try {
                while (rs.next()) {
                    inputStream = rs.getBinaryStream("dataAssetFunction");
                }
                
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAssetFunctionStore.class.getName()).log(Level.SEVERE, null, ex);
            }

            StringWriter writer = new StringWriter();
            String encoding = StandardCharsets.UTF_8.name();

            IOUtils.copy(inputStream, writer, encoding);
            dafXML = writer.toString();
            Logger.getLogger(DataAssetFunctionStore.class.getName()).log(Level.INFO, null, "DAF XML: " + dafXML);
                

        } catch (IOException ex) {
            Logger.getLogger(DataAssetFunctionStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return dafXML;
    }
    
    
    public int getNoOfPartitions(String dataAssetID){

        int noOfPartition=0;
      

            String sql = "select * from DataAssetFunction WHERE dataAssetID='" + dataAssetID + "'";
            ResultSet rs = connectionManager.ExecuteQuery(sql);

            try {
                while (rs.next()) {
                    noOfPartition = rs.getInt("noOfPartition");
                }
                
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAssetFunctionStore.class.getName()).log(Level.SEVERE, null, ex);
            }

     
        
        
        return noOfPartition;
    }
    
    
    public DataAnalyticsFunction getDAFFromEDaaSName(String eDaaSName){
        
        String sql = "SELECT * FROM InputSpecification WHERE name='" + eDaaSName + "'";
        DataAnalyticsFunction dataAnalyticsFunction = null;
       

        ResultSet rs = connectionManager.ExecuteQuery(sql);

        InputStream inputStream = null;
        
        
        
            try {
                while (rs.next()) {
                    inputStream = rs.getBinaryStream("daf");
                }
                
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DataAssetFunctionStore.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            StringWriter writer = new StringWriter();
            String encoding = StandardCharsets.UTF_8.name();

        try {
            IOUtils.copy(inputStream, writer, encoding);
            String dafXML = writer.toString();
            
            dataAnalyticsFunction = JAXBUtils.unmarshal(dafXML, DataAnalyticsFunction.class);
        } catch (IOException ex) {
            Logger.getLogger(DataAssetFunctionStore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetFunctionStore.class.getName()).log(Level.SEVERE, null, ex);
        }
            

        return dataAnalyticsFunction;

        
    }
    
    public DBType gEDaaSTypeFromEDaaSName(String eDaaSName){
        
        String sql = "SELECT * FROM ElasticDaaS WHERE name='" + eDaaSName + "'";
        DBType eDaaSType = null;
       

        ResultSet rs = connectionManager.ExecuteQuery(sql);

        try {
            while (rs.next()) {

                String type = rs.getString("type");
                eDaaSType = DBType.valueOf(type);
            }

            rs.close();
        } catch (Exception ex) {

        }

        return eDaaSType;

        
    }
    
    
    public DBType gEDaaSTypeFromDataAssetID(String dataAssetID){
        
       String sql = "SELECT * FROM ElasticDaaS, DataAssetFunction "
                    + "WHERE ElasticDaaS.name = DataAssetFunction.edaas "
                    + "AND DataAssetFunction.dataAssetID='"+dataAssetID+"'";
        DBType eDaaSType = null;
       

        ResultSet rs = connectionManager.ExecuteQuery(sql);

        System.out.println("Get DB Type ");
        try {
            while (rs.next()) {

                String type = rs.getString("type");
                System.out.println("Type DB: " + type);
                 
                eDaaSType = DBType.valueOf(type);
            }

            rs.close();
        } catch (Exception ex) {

        }
        
        System.out.println("Return Type: " + eDaaSType.getDBType());
         System.out.println("Return Type: " + eDaaSType.toString());
         System.out.println("Return Type: " + eDaaSType.name());

        return eDaaSType;
    }
    
    
    
}
