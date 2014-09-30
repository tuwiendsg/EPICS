/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.common.entity.others;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class MongoDBConnection {
    
    
 
    
    final static String DOMAIN= "ds053429.mongolab.com";
    final static int PORT = 53429;
    final static String USER="junnguyen";
    final static String PASSWORD="siunhan123";
    final static String DBNAME = "edo_repo";
    final static String COLLECTION = "edo";
    
    
    
    
    public void setupConnection(String rsID, String dataAndResultString) {

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
    
  

    public String getRawEDOString(String rsID) {
   
        
        String xmlStr="";
        try {
            MongoClient mongoClient = new MongoClient(DOMAIN, PORT);

            DB db = mongoClient.getDB(DBNAME);
            boolean auth = db.authenticate(USER, PASSWORD.toCharArray());
            DBCollection table = db.getCollection(COLLECTION);

            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("id", rsID);

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
