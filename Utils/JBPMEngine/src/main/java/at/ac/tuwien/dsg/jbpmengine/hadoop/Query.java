package at.ac.tuwien.dsg.jbpmengine.hadoop;

import at.ac.tuwien.dsg.utility.CassandraConnection;
import java.util.LinkedList;

/**
 *
 * @author Anindita
 */
public class Query {
    private static final Query INSTANCE = new Query();
    CassandraConnection client=new CassandraConnection();
    
    Boolean exist=false;
    
    String ipAddress="localhost";
    int port=9042;

    public static Query getInstance() {
        return INSTANCE;
    }
    
   
  

    public Boolean start(String tableName, String keySpaceName) {
       
       client.connect(ipAddress, port);
        
        System.out.println("Query Starting ...");
        String tableQuery="SELECT columnfamily_name FROM system.schema_columnfamilies WHERE keyspace_name='"+keySpaceName+"';";
        
        exist=client.getTableNotification(tableQuery, tableName); 
        
        //String xQuery="SELECT collection_data FROM sensor.sensor21 WHERE collection_date = '2010/12/10';";
        //String xQuery= "SELECT "+x+" FROM "+keySpaceName+"."+tableName+" WHERE "+condition+";";
        //LinkedList<String> xValue=client.readAll(xQuery);
        
        System.out.println("SQL: " + exist);
        
        
        
        
        client.close();
        
       return exist;
        
    }
    
}
