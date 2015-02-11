/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depictool.elstore;

import at.ac.tuwien.dsg.common.deployment.DeployAction;
import at.ac.tuwien.dsg.common.deployment.ElasticService;
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
        InputStream elasticityProcessesStream = new ByteArrayInputStream(elasticityProcesses.getBytes(StandardCharsets.UTF_8));
        
        
        List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
        listOfInputStreams.add(qorStream);
        listOfInputStreams.add(elasticityProcessesStream);
        
        String sql = "INSERT INTO InputSpecification (name, qor, elasticity_process_config) VALUES ('"+edaas+"',?,?)";
        connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);
        
        
        
        
    }
    

    
    
    
    public  void storeElasticityProcesses(String name, String elasticStateSetXML, String elasticityProcesses, String deploymentDesciption){
       
        InputStream elasticStateSetStream = new ByteArrayInputStream(elasticStateSetXML.getBytes(StandardCharsets.UTF_8));
        InputStream elasticityProcessesStream = new ByteArrayInputStream(elasticityProcesses.getBytes(StandardCharsets.UTF_8));
        InputStream deploymentDesciptionStream = new ByteArrayInputStream(deploymentDesciption.getBytes(StandardCharsets.UTF_8));
        
        List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
        listOfInputStreams.add(elasticStateSetStream);
        listOfInputStreams.add(elasticityProcessesStream);
        listOfInputStreams.add(deploymentDesciptionStream);
        
        String sql = "INSERT INTO ElasticDaaS (name, elasticStateSet, elasticity_processes, deployment_descriptions) VALUES ('"+name+"',?,?,?)";
    
        connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);
    }
    
    
    public  void storeDataAssetFunction(String edaasName, String dataAssetID, String dataAssetFunction){
       
       
        InputStream dataAssetFunctionStream = new ByteArrayInputStream(dataAssetFunction.getBytes(StandardCharsets.UTF_8));
        
        List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
        listOfInputStreams.add(dataAssetFunctionStream);

        String sql = "INSERT INTO DataAssetFunction (edaas, dataAssetID, dataAssetFunction) VALUES ('"+edaasName+"','"+dataAssetID+"',?)";
    
        connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);
    }
    
   
    
    
    public List<ActionDependency> getActionDependencyDB(String actionID){
        String sql = "select * from ActionDependency where actionID='" + actionID+"'";
        
        List<ActionDependency> listOfActionDependencies = new ArrayList<ActionDependency>();
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);

        try {

            while (rs.next()) {
            
                String prerequisiteActionID = rs.getString("prerequisiteActionID");     
                ActionDependency actionDependency = new ActionDependency(actionID, prerequisiteActionID);
                listOfActionDependencies.add(actionDependency);

            }
            
            rs.close();

        } catch (Exception ex) {

        }
        
        return listOfActionDependencies;
    }
    
    
    public List<ActionDependency> getAllActionDependencies(){
        String sql = "select * from ActionDependency";
        
        List<ActionDependency> listOfActionDependencies = new ArrayList<ActionDependency>();
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);

        try {

            while (rs.next()) {
                String actionID = rs.getString("actionID");
                String prerequisiteActionID = rs.getString("prerequisiteActionID");     
                ActionDependency actionDependency = new ActionDependency(actionID, prerequisiteActionID);
                listOfActionDependencies.add(actionDependency);

            }
            
            rs.close();

        } catch (Exception ex) {

        }
        
        return listOfActionDependencies;
    }
    
    
    public DeployAction getPrimitiveAction(String actionName){
        String sql = "select * from PrimitiveAction where actionName='" + actionName+"'";
        DeployAction deployAction=null;
  
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        try {
            while (rs.next()) {
            
            
                String artifact = rs.getString("artifact"); 
                String type = rs.getString("type"); 
                String restapi = rs.getString("restapi"); 
                deployAction = new DeployAction(actionName,actionName, type, artifact, restapi);          
            }
            rs.close();

        } catch (Exception ex) {

        }
        
        return deployAction;
    }
    
    public List<String> getElasticDaasNames(){
        String sql = "select name from InputSpecification";
        
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        List<String> listOfEDaases = new ArrayList<String>();
        try {
            while (rs.next()) {
            
                String name = rs.getString("name");    
                listOfEDaases.add(name);
       
            }
            
            rs.close();

        } catch (Exception ex) {

        }
        
        return listOfEDaases;
    }
    
    
    public ResultSet getDataAssetFunction(String edaasName){
        
        String sql = "select edaas, dataAssetID  from DataAssetFunction WHERE edaas='"+edaasName+"'";
        ResultSet rs = connectionManager.ExecuteQuery(sql);
        return rs;
        
    }
    
    public void storeElasticServices(List<ElasticService> listOfElasticServices){
        
        for (ElasticService es : listOfElasticServices){
            
            String sql = "INSERT INTO ElasticService (actionID, serviceID, uri) VALUES ('"+es.getActionID()+"','"+es.getServiceID()+"','"+es.getUri()+"')";
            connectionManager.ExecuteUpdate(sql);
            
        }
    }
    
    public void cleanElasticityProcess(){

        String sql_daf ="TRUNCATE TABLE DataAssetFunction";
        String sql_es ="TRUNCATE TABLE ElasticService";
        String sql_ed ="TRUNCATE TABLE ElasticDaaS";
        String sql_in ="TRUNCATE TABLE InputSpecification";
      
        
        connectionManager.ExecuteUpdate(sql_daf);
        connectionManager.ExecuteUpdate(sql_es);
        connectionManager.ExecuteUpdate(sql_ed);
        connectionManager.ExecuteUpdate(sql_in);
        

    }
}
