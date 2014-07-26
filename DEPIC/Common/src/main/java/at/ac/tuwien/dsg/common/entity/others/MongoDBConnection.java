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
    
    /*

    public void getEstimatedSpeed(int rsID) {
        double estimatedSpeed = -1;
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

                String xmlStr = obj.get("content").toString();
                JAXBLib jaxbl = new JAXBLib();
                DataAndResult dataAndResult = jaxbl.unmarshallingDataAndResult(xmlStr);

                estimatedSpeed = dataAndResult.getEstimatedSpeed().getValue();
                DaaSService dataConcern = dataAndResult.getDataConcern();

                double dataaccuracy = 0;
                double datacompletness = 0;
                List<DataAssetDescription> dataassetList = dataConcern.getDataasset();

                for (int i = 0; i < dataassetList.size(); i++) {

                    DataAssetDescription asset = dataassetList.get(i);

                    List<QoD> qodlist = asset.getQod();

                    for (int j = 0; j < qodlist.size(); j++) {
                        QoD qoD = qodlist.get(j);
                        dataaccuracy = qoD.getAccuracy();
                        datacompletness = qoD.getDatasetcompleteness();
                    }

                }

                 if (!Double.isNaN(estimatedSpeed)) {
                System.out.println("");     
                System.out.println("[x] Estimated Speed (m/s): " + estimatedSpeed);
                System.out.println("--- data accuracy: " + dataaccuracy*100);
                System.out.println("--- data completeness: " + datacompletness*100);
                 }
                //   System.out.println("Found: " + obj.get("id"));
                //   System.out.println("Content: " + obj.get("content"));
            }

        } catch (Exception ex) {

        }

    }
*/
}
