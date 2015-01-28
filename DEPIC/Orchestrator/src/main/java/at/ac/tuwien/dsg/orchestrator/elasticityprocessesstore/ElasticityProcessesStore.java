/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore;

import at.ac.tuwien.dsg.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.common.entity.eda.ElasticStateSet;
import at.ac.tuwien.dsg.common.entity.eda.ep.ElasticityProcess;
import at.ac.tuwien.dsg.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.MySqlConnectionManager;
import at.ac.tuwien.dsg.orchestrator.configuration.Configuration;
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
public class ElasticityProcessesStore {
    
    MySqlConnectionManager connectionManager;
    
    public ElasticityProcessesStore() {
        Configuration config = new Configuration();
        String ip = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.IP");
        String port = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PORT");
        String database = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.DATABASE");
        String username = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.USERNAME");
        String password = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PASSWORD");
        connectionManager = new MySqlConnectionManager(ip, port, database, username, password);

    }
    
    
    
    public String getDeployementDescription(String edaas) {

        String deploymentDescription = "";
        try {
            InputStream inputStream = null;

            String sql = "Select * from DeploymentDescription where edaas='" + edaas + "'";

            ResultSet rs = connectionManager.ExecuteQuery(sql);

            try {
                while (rs.next()) {
                    inputStream = rs.getBinaryStream("specs");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ElasticityProcessesStore.class.getName()).log(Level.SEVERE, null, ex);
            }

            StringWriter writer = new StringWriter();
            String encoding = StandardCharsets.UTF_8.name();

            IOUtils.copy(inputStream, writer, encoding);
            deploymentDescription = writer.toString();
          

        } catch (IOException ex) {
            Logger.getLogger(ElasticityProcessesStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        return deploymentDescription;
    }
    
    
    public ElasticDataAsset getElasticDataAsset(String dataAssetID){
        
                 
            String elasticityProcessesXML="";
            String elasticStateSetXML="";
        
        try {

            InputStream elProcessStream = null;
            InputStream eStateSetStream = null;
            
            
            String sql = "SELECT * FROM ElasticDaaS, DataAssetFunction "
                    + "WHERE ElasticDaaS.name = DataAssetFunction.edaas "
                    + "AND DataAssetFunction.dataAssetID='"+dataAssetID+"'";
            
            ResultSet rs = connectionManager.ExecuteQuery(sql);
            
            
            
            
            try {
                while (rs.next()) {
                    elProcessStream = rs.getBinaryStream("elasticity_processes");
                    eStateSetStream = rs.getBinaryStream("elasticStateSet");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ElasticityProcessesStore.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            StringWriter writer1 = new StringWriter();
            String encoding = StandardCharsets.UTF_8.name();
            IOUtils.copy(elProcessStream, writer1, encoding);
            elasticityProcessesXML = writer1.toString();
            
            StringWriter writer2 = new StringWriter();
            IOUtils.copy(eStateSetStream, writer2, encoding);
            elasticStateSetXML = writer2.toString();
            
            
            
        } catch (IOException ex) {
                Logger.getLogger(ElasticityProcessesStore.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        ElasticityProcess elasticityProcess = null;
        try {
            elasticityProcess = JAXBUtils.unmarshal(elasticityProcessesXML, ElasticityProcess.class);
        } catch (JAXBException ex) {
            Logger.getLogger(ElasticityProcessesStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        ElasticStateSet elasticStateSet = null;
        
        try {
            elasticStateSet = JAXBUtils.unmarshal(elasticStateSetXML, ElasticStateSet.class);
        } catch (JAXBException ex) {
            Logger.getLogger(ElasticityProcessesStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ElasticDataAsset eda = new ElasticDataAsset(dataAssetID, elasticityProcess, elasticStateSet);
        
        
        return  eda;
        
    }
    
    
    
    public QoRModel getQoRModel(String dataAssetID) {
        
            
                    
            String qorXML="";
        
        try {

            InputStream inputStream = null;
            
            String sql = "SELECT * FROM InputSpecification, DataAssetFunction "
                    + "WHERE InputSpecification.name = DataAssetFunction.edaas "
                    + "AND DataAssetFunction.dataAssetID='"+dataAssetID+"'";
            
            ResultSet rs = connectionManager.ExecuteQuery(sql);
            
            try {
                while (rs.next()) {
                    inputStream = rs.getBinaryStream("elasticity_processes");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ElasticityProcessesStore.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            StringWriter writer = new StringWriter();
            String encoding = StandardCharsets.UTF_8.name();

            IOUtils.copy(inputStream, writer, encoding);
            qorXML = writer.toString();
            
        } catch (IOException ex) {
                Logger.getLogger(ElasticityProcessesStore.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        QoRModel qoRModel=null;
        try {
            qoRModel = JAXBUtils.unmarshal(qorXML, QoRModel.class);
        } catch (JAXBException ex) {
            Logger.getLogger(ElasticityProcessesStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return qoRModel;
        
    }
    
}