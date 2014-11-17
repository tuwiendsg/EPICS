/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edaas.requirement.service;

import at.ac.tuwien.dsg.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.common.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.edaas.config.Configuration;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class ElasticityProcessesStore {
    
    public ElasticDataAsset getEDAModel(String dataAssetID){
        
        Configuration configuration  = new Configuration();
        String ip = configuration.getConfig("ELASTICITY.PROCESSES.IP");
        String port = configuration.getConfig("ELASTICITY.PROCESSES.PORT");
        String db = configuration.getConfig("ELASTICITY.PROCESSES.DB");
        String user = configuration.getConfig("ELASTICITY.PROCESSES.USER");
        String password = configuration.getConfig("ELASTICITY.PROCESSES.PASSWORD");
        
        MySqlConnectionManager connectionManager = new MySqlConnectionManager(ip, port, db, user, password);
        
        //store as file
        String sql ="SELECT * from EDA WHERE dataAssetID='"+dataAssetID+"'";
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        
        try {
            while (rs.next()) {
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ElasticityProcessesStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ElasticDataAsset eda = new ElasticDataAsset();
        
        return eda;
        
    }
    
}
