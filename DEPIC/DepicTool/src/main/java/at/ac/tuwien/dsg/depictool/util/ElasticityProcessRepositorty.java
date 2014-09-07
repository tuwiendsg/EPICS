/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.util;

import at.ac.tuwien.dsg.common.entity.others.MySqlConnectionManager;
import at.ac.tuwien.dsg.depictool.entity.ActionDependency;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ElasticityProcessRepositorty {

    MySqlConnectionManager mySqlConnectionManager;
    
    
    public ElasticityProcessRepositorty() {
        
        String ip="localhost";
        String userName="root";
        String password="123";
        String database="ElasticityProcess";
        String connectionString = "jdbc:mysql://"+ip+":3306/"+database+"?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false";
        
        mySqlConnectionManager = new MySqlConnectionManager(connectionString, userName, password);
        
        
    }
    
    public List<ActionDependency> getControlActionDependencyDB(String actionID){
        String sql = "select * from ControlActionDependency where actionID='" + actionID+"'";
        
        List<ActionDependency> listOfActionDependencies = new ArrayList<>();
        
        ResultSet rs = mySqlConnectionManager.ExecuteQuery(sql);

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
    
    
}
