/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.jbpmengine.hadoop;

import at.ac.tuwien.dsg.utility.CassandraConnection;

/**
 *
 * @author Anindita
 */
public class Query {
    private static final Query INSTANCE = new Query();
    
    String ipAddress="localhost";
    int port=9042;

    public static Query getInstance() {
        return INSTANCE;
    }

    public void start(String param) {
        System.out.println("Query Starting ...");
        //cassandra connection
        System.out.println("SQL: " + param);
        
        CassandraConnection client=new CassandraConnection();
        client.connect(ipAddress, port);
        client.readAll(param);
        
        client.close();
        

    }
}
