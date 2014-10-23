/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.utility;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import static java.lang.System.out;

/**
 *
 * @author Anindita
 */
public class CassandraConnection {
     /** Cassandra Cluster. */
   private Cluster cluster;

   /** Cassandra Session. */
   private Session session;

   /**
    * Connect to Cassandra Cluster specified by provided node IP
    * address and port number.
    *
    * @param node Cluster node IP address.
    * @param port Port of cluster host.
    */
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
    public void readAll(String query)
      {
          try
          {
            ResultSet results=session.execute(query);
            for(Row row : results.all())
                {
                    System.out.println(row.toString());
                }
          }
          catch(Exception e)
          {
              System.out.println("does not exist");
          }
          
      }
     /** Close cluster. */
   public void close()
   {
       cluster.close();
      
   }
    
}
