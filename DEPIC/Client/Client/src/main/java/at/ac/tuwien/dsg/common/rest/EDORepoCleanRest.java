/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.rest;

/**
 *
 * @author Jun
 */


import java.io.*;
import org.apache.http.*;
import org.apache.http.client.methods.*;  //HttpHead, HttpPut, HttpGet, etc...
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;



public class EDORepoCleanRest {
    
    
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private String ip;
    private String port;

    public EDORepoCleanRest(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }
    
    
    
    
    
    
     public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    
    public void emptyEDORepo()  {

        StringBuilder result = new StringBuilder();
        try {
        String url = "http://"+ip+":"+port+"/DataAccessManagement/webresources/EDORepoClean";

  
        
        HttpGet request = new HttpGet(url);
        request.addHeader("content-type","application/xml; charset=utf-8");
        request.addHeader("Accept","application/xml, multipart/related");
  
       
       
        HttpResponse methodResponse = this.getHttpClient().execute(request);
      
       
        
        int statusCode = methodResponse.getStatusLine().getStatusCode();

        System.out.println("Status Code: " + statusCode);
        
        
        BufferedReader rd = new BufferedReader(
	        new InputStreamReader(methodResponse.getEntity().getContent()));
 
	
	String line;
	while ((line = rd.readLine()) != null) {
		result.append(line);
	}
        
        System.out.println("Response String: " + result.toString());
        
        
        
        
        }
        catch (Exception ex) {
            
        }
        
    
    }

}
