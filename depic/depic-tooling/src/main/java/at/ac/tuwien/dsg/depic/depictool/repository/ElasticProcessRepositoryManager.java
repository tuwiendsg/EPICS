/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.repository;

import at.ac.tuwien.dsg.depic.common.entity.runtime.DeployAction;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ElasticService;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveAction;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticProcess;

import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.MySqlConnectionManager;

import at.ac.tuwien.dsg.depic.depictool.utils.Configuration;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Jun
 */
public class ElasticProcessRepositoryManager {

    MySqlConnectionManager connectionManager;
    
    public ElasticProcessRepositoryManager() {
        Configuration config = new Configuration();
        String ip = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.IP");
        String port = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PORT");
        String database = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.DATABASE");
        String username = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.USERNAME");
        String password = config.getConfig("DB.ELASTICITY.PROCESSES.REPO.PASSWORD");
        
//        String ip = "localhost";
//        String port = "3306";
//        String database = "ElasticityProcess";
//        String username = "root";
//        String password = "123";
        
        
        connectionManager = new MySqlConnectionManager(ip, port, database, username, password);

    }
    
    
    
    public void storeQoRAndElasticityProcesses(String edaas, String qor, String elasticityProcesses, DBType type){
        InputStream qorStream = new ByteArrayInputStream(qor.getBytes(StandardCharsets.UTF_8));
        InputStream elasticityProcessesStream = new ByteArrayInputStream(elasticityProcesses.getBytes(StandardCharsets.UTF_8));
        
        
        List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
        listOfInputStreams.add(qorStream);
        listOfInputStreams.add(elasticityProcessesStream);
        
     //   String sql = "INSERT INTO InputSpecification (name, qor, elasticity_process_config, type) VALUES ('"+edaas+"',?,?,'"+type.geteDaaSType()+"')";
     //   connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);
        
        
        
        
    }
    

    
    
    
    public  void storeElasticityProcesses(String name, String elasticStateSetXML, String elasticityProcesses, String deploymentDesciption, String type){
       
        InputStream elasticStateSetStream = new ByteArrayInputStream(elasticStateSetXML.getBytes(StandardCharsets.UTF_8));
        InputStream elasticityProcessesStream = new ByteArrayInputStream(elasticityProcesses.getBytes(StandardCharsets.UTF_8));
        InputStream deploymentDesciptionStream = new ByteArrayInputStream(deploymentDesciption.getBytes(StandardCharsets.UTF_8));
        InputStream typeStream = new ByteArrayInputStream(type.getBytes(StandardCharsets.UTF_8));
        
        List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
        listOfInputStreams.add(elasticStateSetStream);
        listOfInputStreams.add(elasticityProcessesStream);
        listOfInputStreams.add(deploymentDesciptionStream);
        listOfInputStreams.add(typeStream);
        
        String sql = "INSERT INTO ElasticDaaS (name, elasticStateSet, elasticity_processes, deployment_descriptions, type) VALUES ('"+name+"',?,?,?,?)";
    
        connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);
    }
    
    public String getElasticityProcesses(String eDaaSName){
     
        String sql = "SELECT * FROM ElasticDaaS WHERE name='" + eDaaSName + "'";
        String elProcessXML = "";
       

        ResultSet rs = connectionManager.ExecuteQuery(sql);

        try {
            while (rs.next()) {
                InputStream inputStream = rs.getBinaryStream("elasticity_processes");
                StringWriter writer = new StringWriter();
                String encoding = StandardCharsets.UTF_8.name();
                IOUtils.copy(inputStream, writer, encoding);
                elProcessXML = writer.toString();          
                
            }

            rs.close();
        } catch (Exception ex) {

        }

        return elProcessXML;

    }
    
    
    public  void storeDataAssetFunction(String edaasName, String dataAssetID, String dataAssetFunction, String noOfPartition){
       
       
        InputStream dataAssetFunctionStream = new ByteArrayInputStream(dataAssetFunction.getBytes(StandardCharsets.UTF_8));
        
        List<InputStream> listOfInputStreams = new ArrayList<InputStream>();
        listOfInputStreams.add(dataAssetFunctionStream);

        String sql = "INSERT INTO DataAssetFunction (edaas, dataAssetID, noOfPartition, dataAssetFunction) "
                + "VALUES ('"+edaasName+"','"+dataAssetID+"',"+noOfPartition+",?)";
    
        connectionManager.ExecuteUpdateBlob(sql, listOfInputStreams);
    }
    
    public void updateDataAssetFunction(String edaasName, String dataAssetID, String dataAssetFunction, String noOfPartition){
        String sql = "UPDATE DataAssetFunction SET noOfPartition="+noOfPartition+" WHERE dataAssetFunction='"+dataAssetFunction+"'";
        connectionManager.ExecuteUpdate(sql);
    }
    
   
    
//    
//    public List<ActionDependency> getActionDependencyDB(String actionID){
//        String sql = "select * from ActionDependency where actionID='" + actionID+"'";
//        
//        List<ActionDependency> listOfActionDependencies = new ArrayList<ActionDependency>();
//        
//        ResultSet rs = connectionManager.ExecuteQuery(sql);
//
//        try {
//
//            while (rs.next()) {
//            
//                String prerequisiteActionID = rs.getString("prerequisiteActionID");     
//                ActionDependency actionDependency = new ActionDependency(actionID, prerequisiteActionID);
//                listOfActionDependencies.add(actionDependency);
//
//            }
//            
//            rs.close();
//
//        } catch (Exception ex) {
//
//        }
//        
//        return listOfActionDependencies;
//    }
//    
//    
//    public List<ActionDependency> getAllActionDependencies(){
//        String sql = "select * from ActionDependency";
//        
//        List<ActionDependency> listOfActionDependencies = new ArrayList<ActionDependency>();
//        
//        ResultSet rs = connectionManager.ExecuteQuery(sql);
//
//        try {
//
//            while (rs.next()) {
//                String actionID = rs.getString("actionID");
//                String prerequisiteActionID = rs.getString("prerequisiteActionID");     
//                ActionDependency actionDependency = new ActionDependency(actionID, prerequisiteActionID);
//                listOfActionDependencies.add(actionDependency);
//
//            }
//            
//            rs.close();
//
//        } catch (Exception ex) {
//
//        }
//        
//        return listOfActionDependencies;
//    }
    
    
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
    
//    
//    public PrimitiveAction getPrimitiveActions(){
//        String sql = "select * from PrimitiveAction";
//        List<DeployAction> listOfDeployActions = new ArrayList<DeployAction>();
//        
//        ResultSet rs = connectionManager.ExecuteQuery(sql);
//        try {
//            while (rs.next()) {
//            
//                String actionName = rs.getString("actionName"); 
//                String artifact = rs.getString("artifact"); 
//                String type = rs.getString("type"); 
//                String restapi = rs.getString("restapi"); 
//                DeployAction deployAction = new DeployAction(actionName, actionName, type, artifact, restapi);
//                listOfDeployActions.add(deployAction);
//            }
//            rs.close();
//
//        } catch (Exception ex) {
//
//        }
//        
//        PrimitiveAction primitiveAction = new PrimitiveAction(listOfDeployActions);
//        
//        return primitiveAction;
//    }
    
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
        
        String sql_es ="TRUNCATE TABLE ElasticService";
        connectionManager.ExecuteUpdate(sql_es);
        
        for (ElasticService es : listOfElasticServices){
            
            if (es.getRequest()!=-1) {
            String sql = "INSERT INTO ElasticService (actionID, serviceID, uri) VALUES ('"+es.getActionID()+"','"+es.getServiceID()+"','"+es.getUri()+"')";
            connectionManager.ExecuteUpdate(sql);
            }
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
