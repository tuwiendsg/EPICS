/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataresourceaccess.edorepository;

import at.ac.tuwien.dsg.common.entity.others.EDORepo;
import at.ac.tuwien.dsg.common.entity.others.EDORepoJAXB;
import at.ac.tuwien.dsg.common.entity.others.MongoDBConnection;
import at.ac.tuwien.dsg.dataresourceaccess.MySqlConnectionManager;
import java.sql.ResultSet;

/**
 *
 * @author Jun
 */
public class EDORepoAccessManagement {
    
    
    public void saveEDO2Repo(EDORepo edo){
        
 
        EDORepoJAXB jaxb = new EDORepoJAXB();
        String xmlStr = jaxb.marshallingObject(edo);
        String key = String.valueOf(edo.getObjID()) + ";" + String.valueOf(edo.getUserID());
        
        MongoDBConnection mongoDBConnection = new MongoDBConnection();
        mongoDBConnection.setupConnection(key, xmlStr);
        
    }
    
    
}
