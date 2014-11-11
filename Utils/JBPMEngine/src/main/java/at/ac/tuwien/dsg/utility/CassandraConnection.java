/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.utility;

import at.ac.tuwien.dsg.jbpmengine.hadoop.Query;
import ch.qos.logback.classic.db.names.TableName;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.ExecutionInfo;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.google.common.util.concurrent.ListenableFuture;
import static java.lang.System.out;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author Anindita
 */
public class CassandraConnection {
     /** Cassandra Cluster. */
   private Cluster cluster;

   /** Cassandra Session. */
   private Session session;

    /*private static final CassandraConnection INSTANCE = new CassandraConnection();
    
    public static CassandraConnection getInstance() {
        return INSTANCE;
    }*/
   
   
   
   public void connect(final String node, final int port)
   {
      this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build();
      final Metadata metadata = cluster.getMetadata();
      out.printf("Connected to cluster: %s\n", metadata.getClusterName());
      for (final Host host : metadata.getAllHosts())
      {
         out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
            host.getDatacenter(), host.getAddress(), host.getRack());
      }
      session = cluster.connect();
   }
   
   //read data from cassandra
    public LinkedList<String> readAll(String xQuery)
      {
          LinkedList<String> queryValue=new LinkedList<String>();
          //String xQuery= "SELECT "+xColumn+" FROM "+getkeySpaceName()+"."+getTableName()+" WHERE "+condition+";";
          try
          {
            ResultSet results=session.execute(xQuery);
            int i=0;
            for(Row row : results.all())
                {
                    //BigInteger value;
                //value = row.getVarint(i);
                    /*String val1=value.toString();
                    queryValue.add(val1);*/
                    StringTokenizer st=new StringTokenizer(row.toString(),"[]");
                    while(st.hasMoreTokens())
                    {
                        st.nextToken();
                        
                        queryValue.add(st.nextToken());
                    }
                    
                    System.out.println("value: "+row.toString());
                    i++;
                }
          }
          catch(Exception e)
          {
              System.out.println("does not exist="+e);
          }
          return queryValue;
      }
    
    ///
    public Boolean getTableNotification(String tablequery, String tableName)
    {
        //System.out.println("tableName   ="+getTableName());
        Boolean value=false;
           ResultSet result=session.execute(tablequery);
           int i=0;
           for(Row row : result.all())
                {
                    System.out.println("value: "+row.getString(i)+"   i="+i);
                    if(row.getString(i).equalsIgnoreCase(tableName))
                    {
                        value=true;
                        break;
                    }
                    else{
                        value=false;
                    }
                    
                    
                    i++;
                }
           //System.out.println("result="+row.t);
           return value;
           
    }
     /** Close cluster. */
   public void close()
   {
       cluster.close();
      
   }
    
}
