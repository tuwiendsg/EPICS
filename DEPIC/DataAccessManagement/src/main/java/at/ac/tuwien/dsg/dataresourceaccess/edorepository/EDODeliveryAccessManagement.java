/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataresourceaccess.edorepository;

import at.ac.tuwien.dsg.common.entity.others.MySQLConnection;
import at.ac.tuwien.dsg.common.entity.others.EDORepo;
import at.ac.tuwien.dsg.common.entity.others.EDORepoJAXB;
import at.ac.tuwien.dsg.common.entity.others.MongoDBConnection;
import at.ac.tuwien.dsg.common.entity.others.MongoDBDelivery;

/**
 *
 * @author Jun
 */
public class EDODeliveryAccessManagement {

    public void save2DeliveryEDORepo(EDORepo edo) {

        EDORepoJAXB jaxb = new EDORepoJAXB();
        String xmlStr = jaxb.marshallingObject(edo);
        String key = String.valueOf(edo.getObjID()) + ";" + String.valueOf(edo.getUserID());

        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.saveDeliveryEDOStr(key, xmlStr);

        /*
         MongoDBDelivery mongoDBDelivery = new MongoDBDelivery();
         mongoDBDelivery.saveEDO2Repo(key, xmlStr);
         */
    }
    
     public void updateDeliveryEDORepo(EDORepo edo) {

        EDORepoJAXB jaxb = new EDORepoJAXB();
        String xmlStr = jaxb.marshallingObject(edo);
        String key = String.valueOf(edo.getObjID()) + ";" + String.valueOf(edo.getUserID());

        MySQLConnection mySQLConnection = new MySQLConnection();
        mySQLConnection.updateDeliveryEDOStr(key, xmlStr);

        /*
         MongoDBDelivery mongoDBDelivery = new MongoDBDelivery();
         mongoDBDelivery.saveEDO2Repo(key, xmlStr);
         */
    }
    

    public String getDeliveryEDOXML(String key) {

        MySQLConnection mySQLConnection = new MySQLConnection();

        String xmlString = mySQLConnection.getDeliveryEDOStr(key);

        return xmlString;
    }
    
    
    
}
