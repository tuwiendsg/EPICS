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

import java.io.File;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.*;  //HttpHead, HttpPut, HttpGet, etc...
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class EDORepoMeasuredAccessREST {
    
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private String ip;
    private String port;

    public EDORepoMeasuredAccessREST(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }


    
    
    
    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
    
    public String getElasticDataObjectString(String dataObjectIndex, String userID)  {

        StringBuilder result = new StringBuilder();
        try {
        String url = "http://"+ip+":"+port+"/DataAccessManagement/webresources/EDORepo";

        //HttpGet method = new HttpGet(url);
            StringEntity inputKeyspace = new StringEntity(dataObjectIndex+";"+userID);
            
            
            HttpPut request = new HttpPut(url);
            request.addHeader("content-type","application/xml; charset=utf-8");
            request.addHeader("Accept","application/xml, multipart/related");
            request.setEntity(inputKeyspace);
            
            
            HttpResponse methodResponse = this.getHttpClient().execute(request);
      
       
        
        int statusCode = methodResponse.getStatusLine().getStatusCode();

        //System.out.println("Status Code: " + statusCode);
        
        
        BufferedReader rd = new BufferedReader(
	        new InputStreamReader(methodResponse.getEntity().getContent()));
 
	
	String line;
	while ((line = rd.readLine()) != null) {
		result.append(line);
	}
        
       // System.out.println("Response String: " + result.toString());
        
        
        
        
        }
        catch (Exception ex) {
            
        }
        
        return result.toString();
    }
}
