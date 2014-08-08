/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.services;

import at.ac.tuwien.dsg.common.entity.others.MongoDBDelivery;
import at.ac.tuwien.dsg.common.entity.others.MySQLConnection;

/**
 *
 * @author Jun
 */
public class DeliveryService {

    public String getDeliveryEDO(String key) {
        String[] vals = key.split(";");

        key = vals[1] + ";" + vals[0];

        MySQLConnection mySQLConnection = new MySQLConnection();

        String edoXML = mySQLConnection.getDeliveryEDOStr(key);
/*
        MongoDBDelivery mongoDBDelivery = new MongoDBDelivery();
        String edoXML = mongoDBDelivery.getDeliveryEDO(key);
*/
        
        
        return edoXML;

    }

}
