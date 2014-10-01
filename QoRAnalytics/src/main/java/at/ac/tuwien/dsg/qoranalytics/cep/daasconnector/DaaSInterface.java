/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qoranalytics.cep.daasconnector;

import java.io.IOException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author Anindita
 */
public class DaaSInterface 
{
    
    CloseableHttpClient httpclient=HttpClients.createDefault();
    String ipAddress;
    int port;
  
    //assign the ipAddress and port number of DaaS server
    public DaaSInterface(String ipAddress, int port)
    {
        this.ipAddress=ipAddress;
        this.port=port;
    }
    
    //create of httpclent
    public CloseableHttpClient getHttpClient()
    {
        return this.httpclient;
    }
    
    //for IPAddress
    public void setIP(String ipAddress)
    {
       this.ipAddress=ipAddress;
    }
    public String getIP()
    {
       return this.ipAddress;
    }
    
    //for Port number
    public void setPort(int port)
    {
        this.port=port;
    }
    public int getPort()
    {
        return this.port;
    }
    
    //close the session
    public void close()throws IOException
    {
       this.httpclient.close();
    }
    
    //monitor of DaaS
    public String monitor()  
    {
	String entity=new DaaSMonitor().monitor(ipAddress,port,httpclient);
        return entity;
    }
    
    //create of Keyspace
    public String createKeyspace(String xmlString)
    {
        String path="/xml/keyspace";
        String entity=new CreateStructure().putQuery(ipAddress,port,httpclient,xmlString,path);
        return entity;
    }
    
    //create of table
    public String createTable(String xmlString)
    {
        String path="/xml/table";
        String entity=new CreateStructure().putQuery(ipAddress, port, httpclient, xmlString,path);
        return entity;
    }
    
    //insert rows in table
    public String insertRow(String xmlString)
    {
        String path="/xml/table/row";
        String entity=new CreateStructure().putQuery(ipAddress, port, httpclient, xmlString,path);
        return entity;
    }
    
    //insert of index value
    public String assignIndex(String xmlString)
    {
        String path="/xml/table/index";
        String entity=new CreateStructure().putQuery(ipAddress, port, httpclient, xmlString,path);
        return entity;
    }
    
    //retrieve of list of keyspaces
    public String listKeyspaces()
    {
        String entity=new String(new ListKeyspaces().showKeyspaces(ipAddress, port, httpclient));
        return entity;
    }
    
    //retrieve all the rows
    public String retrieverows(String xmlString)
    {
        String path="/table/row";
        String entity=new RetrieveStructure().retrieveValue(ipAddress, port, httpclient, xmlString,path);
        return entity;
    }
    
   
   //delete keyspace 
   public String deleteKeyspace(String xmlString)
    {
        String path="/xml/keyspace";
        String entity=new DeleteStructure().dropStructure(ipAddress, port, httpclient, xmlString,path);
        return entity;
    }
   
   //delete of table
    public String deletetable(String xmlString)
    {
        String path="/xml/table";
        String entity=new DeleteStructure().dropStructure(ipAddress, port, httpclient, xmlString,path);
        return entity;
    }
    
    //delete of index
    public String deleteIndex(String xmlString)
    {
        String path="/xml/table/index";
        String entity=new DeleteStructure().dropStructure(ipAddress, port, httpclient, xmlString,path);
        return entity;
    }
    
    //delete of rows
    public String deleteRow(String xmlString)
    {
        String path="/xml/table/row";
        String entity=new DeleteStructure().dropStructure(ipAddress, port, httpclient, xmlString,path);
        return entity;
    }
    
}
  



