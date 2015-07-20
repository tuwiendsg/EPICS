/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataimporter;

/**
 *
 * @author Jun
 */
public class DataImportApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String cassandraIP = "128.130.172.230";
        int cassandraPort = 8080;
        String tableName = "KDDAvertising";
        String primaryKey = "-1!int;0!int;1!varchar;2!int";
        String filePath = "/Volumes/DATA/BigData/KDD/track2/training.txt";
        int limitColumns = 10;
        
        
        CassandraDataImporter cassandraDataImporter = new CassandraDataImporter(cassandraIP, cassandraPort, tableName, primaryKey, filePath, limitColumns);
        cassandraDataImporter.insertDataToCassandra();
        
        
    }
    
}
