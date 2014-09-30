/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.connector;

/**
 *
 * @author Jun
 */


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import static java.lang.System.out;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author dsg
 */
public class CassandraConnector {
    

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
      for (final Host host : metadata.getAllHosts())
      {
          //  host.getDatacenter(), host.getAddress(), host.getRack());
      }
      session = cluster.connect();
   }
   
//create schema
   
   public void createKeyspace(String keyspace,int node)
   {
       try
       {
       session.execute("CREATE KEYSPACE "+keyspace+" WITH replication " + 
      "= {'class':'SimpleStrategy', 'replication_factor':"+node+"};"); 
       System.out.println(keyspace+" created");
       }
       catch(Exception e)
               {
                   System.out.println(keyspace+" already exist");
               }
   }
   
  
   
//create table
   public void createtable(String keyspace, String table, LinkedList<String> columns, LinkedList<String> columnsdatatype)
   {
       try
       {
       String createtablequery="CREATE TABLE "+keyspace+"."+table+" (id uuid PRIMARY KEY" ;
       for(int i=0; i<columns.size();i++)
       {
           createtablequery+=","+columns.get(i)+ " "+columnsdatatype.get(i);
       }
       createtablequery+=");";
       session.execute(createtablequery); 
       System.out.println(keyspace+"."+table+" created");
       }
       catch(Exception e)
       {
           System.out.println(keyspace+"."+table+" already exist");
       }
      
   }
           
     //insert data  
      public void insertdata(String keyspace, String table, LinkedList<String> columns,
                                 LinkedList<String> columnsdatatype, LinkedList<String> rows) 
      {
          String insertquery="INSERT INTO "+keyspace+"."+table+" (id";
          
          String columnsquery=new String();
          String rowsquery=new String();
          //double idvalue=Math.random();
          
          for(int i=0;i<columns.size();i++)
          {
             columnsquery+=", "+columns.get(i);
             
             if(columnsdatatype.get(i).equals("text"))
             {
             rowsquery+=",'"+rows.get(i)+"'";
             }
             
             if(columnsdatatype.get(i).equals("varchar"))
             {
             rowsquery+=",'"+rows.get(i)+"'";
             }
             
             if(columnsdatatype.get(i).equals("double"))
             {
                 rowsquery+=","+Double.parseDouble(rows.get(i));
             }
             if(columnsdatatype.get(i).equals("int"))
             {
                 rowsquery+=","+Integer.parseInt(rows.get(i));
             }
             if(columnsdatatype.get(i).equals("float"))
             {
                 rowsquery+=","+Float.parseFloat(rows.get(i));
             }
             
          }

          
          insertquery+=columnsquery+") VALUES ("+UUID.randomUUID()+rowsquery+");";
          session.execute(insertquery);
      }
      
      //show all the data content of table
      public void readAll(String keyspace, String table)
      {
          try
          {
            ResultSet results=session.execute("SELECT * FROM "+keyspace+"."+table+";");
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
      
      //show the data content on the base of certain condition
      public void readCondition(String keyspace, String table, String condition)
      {
          try
          {
            ResultSet result=session.execute("SELECT * FROM "+keyspace+"."+table+" WHERE "+condition);
                for(Row row : result.all())
                {
                    System.out.println(row.toString()+" for "+condition);
                }
            }
           catch(Exception e)
            {
                System.out.println("does not exist");
          }
          
      }
      
     // delete the keyspace
      public void deleteKeyspace(String keyspace)
      {
          try
          {
          session.execute("DROP KEYSPACE "+keyspace+";");
          System.out.println(keyspace+" deleted");
          }
          catch(Exception e)
          {
              System.out.println("does not exist");
          }
      }
      
      // delete the data table
      public void deleteTable(String keyspace, String table)
      {
          try
            {
                session.execute("DROP TABLE "+keyspace+"."+table+";");
                System.out.println(keyspace+"."+table+" is deleted");
            }
          catch(Exception e)
          {
                System.out.println("does not exist");
          }
      }
      
      //delete the data conent of table
      public void deleteRow(String keyspace, String table, String condition)
      {
          try
          {
            session.execute("DELETE FROM "+keyspace+"."+table+" WHERE "+condition);
          }
          catch(Exception e)
          {
              System.out.println("does not exist");
          }
      }
      
      //delete the data content of column of table
      public void deleteColumnContent(String keyspace, String table, String column, String condition)
      {
          try
          {
            session.execute("DELETE "+column+" FROM "+keyspace+"."+table+" WHERE "+condition+";");
            System.out.println(column+" is deleted from "+keyspace+"."+table+" on the base of "+condition );
          }
          catch(Exception e)
          {
              System.out.println("does not exist");
          }
      }
      
      //delete the column from table
      public void deleteAllColumn(String keyspace, String table, String column)
      {
          try
          {
            session.execute("ALTER TABLE "+keyspace+"."+table+" DROP "+column);
          }
          catch(Exception e)
          {
              System.out.println("does not exist");
          }
      }
      
      //add a column in table 
      public void addColumn(String keyspace, String table, String column, String columnDataType)
      {
          try
          {
              session.execute("ALTER TABLE "+keyspace+"."+table+" ADD "+column+" "+columnDataType+";");
          }
          catch(Exception e)
          {
              System.out.println("does not exist");
          }
      }
      
     
   public Session getSession()
   {
      return this.session;
   }

   /** Close cluster. */
   public void close()
   {
       cluster.close();
      
   }
}

