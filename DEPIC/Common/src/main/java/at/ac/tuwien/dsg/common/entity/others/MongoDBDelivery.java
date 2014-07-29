/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.others;

import static at.ac.tuwien.dsg.common.entity.others.MongoDBConnection.DOMAIN;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

/**
 *
 * @author Jun
 */
public class MongoDBDelivery {
    final static String DOMAIN= "ds053429.mongolab.com";
    final static int PORT = 53429;
    final static String USER="junnguyen";
    final static String PASSWORD="siunhan123";
    final static String DBNAME = "edo_repo";
    final static String COLLECTION = "edo_delivery";
    
    
    public void saveEDO2Repo(String rsID, String dataAndResultString) {

        try {
            MongoClient mongoClient = new MongoClient(DOMAIN, PORT);

            DB db = mongoClient.getDB(DBNAME);
            boolean auth = db.authenticate(USER, PASSWORD.toCharArray());

            System.out.println("Connection: " + auth);

            DBCollection table = db.getCollection(COLLECTION);

            BasicDBObject document = new BasicDBObject();
            document.put("id", rsID);
            document.put("content", dataAndResultString);

            table.insert(document);

        } catch (Exception ex) {
             System.out.println("EX:" + ex.toString());
        }

    }
    
    
    
    public String getDeliveryEDO(String key){
        
        String xmlStr="";
        try {
            MongoClient mongoClient = new MongoClient(DOMAIN, PORT);

            DB db = mongoClient.getDB(DBNAME);
            boolean auth = db.authenticate(USER, PASSWORD.toCharArray());
            DBCollection table = db.getCollection(COLLECTION);

            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("id", key);

            DBCursor cursor = table.find(searchQuery);
            
            
            
            while (cursor.hasNext()) {

                BasicDBObject obj = (BasicDBObject) cursor.next();

                xmlStr = obj.get("content").toString();
                
            }

        } catch (Exception ex) {

        }
        
        return xmlStr;
        
    }
}
