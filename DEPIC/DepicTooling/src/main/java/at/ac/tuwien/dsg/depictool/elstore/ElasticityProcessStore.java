/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depictool.elstore;

import at.ac.tuwien.dsg.common.deployment.DeployAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.ElasticityProcess;
import at.ac.tuwien.dsg.common.entity.process.ActionDependency;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.MySqlConnectionManager;

import at.ac.tuwien.dsg.depictool.util.Configuration;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class ElasticityProcessStore {

    MySqlConnectionManager connectionManager;
    
    public ElasticityProcessStore() {
        Configuration config = new Configuration();
        String ip = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.IP");
        String port = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PORT");
        String database = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.DATABASE");
        String username = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.USERNAME");
        String password = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PASSWORD");
        connectionManager = new MySqlConnectionManager(ip, port, database, username, password);

    }
    
    
    
    public void storeQoRAndElasticityProcesses(String edaas, String qor, String elasticityProcesses){
        InputStream qorStream = new ByteArrayInputStream(qor.getBytes(StandardCharsets.UTF_8));
        InputStream elasticityProcessesStream = new ByteArrayInputStream(qor.getBytes(StandardCharsets.UTF_8));
        List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
        listOfInputStreams.add(qorStream);
        listOfInputStreams.add(elasticityProcessesStream);
        
        String sql = "INSERT INTO eDaaS (name, qor, elasticity_process_config) VALUES ('"+edaas+"',?,?)";
        connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);
        
        
        
        
    }
    
    public void storeDeploymentDescription(String edaas, String deploymentDescription){
        InputStream deploymentDescriptionStream = new ByteArrayInputStream(deploymentDescription.getBytes(StandardCharsets.UTF_8));
      
        List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
        listOfInputStreams.add(deploymentDescriptionStream);
       
        String sql = "INSERT INTO DeploymentDescription (edaas, specs) VALUES ('"+edaas+"',?)";
        connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);
        
        
        
        
    }
    
    
    
    public  void storeElasticityProcesses(ElasticityProcess elasticityProcesses, String deploymentDesciption){
       
        String epXml="";
        try {
            epXml = JAXBUtils.marshal(elasticityProcesses, ElasticityProcess.class);
        } catch (JAXBException ex) {
            Logger.getLogger(ElasticityProcessStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        InputStream epStream = new ByteArrayInputStream(epXml.getBytes(StandardCharsets.UTF_8));
        String sql = "INSERT INTO ElasticityProcessStore (elasticityProcesses, deployementDescription) VALUES (?,?)";
      //  connectionManager.ExecuteUpdateBlob(sql, epStream);
    }
    
   
    
    
    public List<ActionDependency> getControlActionDependencyDB(String actionID){
        String sql = "select * from ControlActionDependency where actionID='" + actionID+"'";
        
        List<ActionDependency> listOfActionDependencies = new ArrayList<ActionDependency>();
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);

        try {

            while (rs.next()) {
            
                String prerequisiteActionID = rs.getString("prerequisiteActionID");     
                ActionDependency actionDependency = new ActionDependency(actionID, prerequisiteActionID);
                listOfActionDependencies.add(actionDependency);

            }

        } catch (Exception ex) {

        }
        
        return listOfActionDependencies;
    }
    
    
    public DeployAction getPrimitiveAction(String actionID){
        String sql = "select * from PrimitiveAction where actionID='" + actionID+"'";
        DeployAction deployAction=null;
        
        List<ActionDependency> listOfActionDependencies = new ArrayList<ActionDependency>();
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        try {
            while (rs.next()) {
            
                String actionName = rs.getString("actionName");    
                String artifact = rs.getString("artifact"); 
                String type = rs.getString("type"); 
                deployAction = new DeployAction(actionID, actionName, type, artifact);          
            }

        } catch (Exception ex) {

        }
        
        return deployAction;
    }
}
