/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.datagovernance.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.*;  //HttpHead, HttpPut, HttpGet, etc...
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Jun
 */
public class SALSARestWS {
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private String ip="128.130.172.215";
    private String port = "8080";
    
     public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }
    
    public void sendDeploymentDescription(String deploymentDescription)  {

        
        String url = "http://"+ip+":"+port+"/salsa-engine/rest/services/xml";

        try {

        //HttpGet method = new HttpGet(url);
        StringEntity inputKeyspace = new StringEntity(deploymentDescription);
        
        
        HttpPut request = new HttpPut(url);
        request.addHeader("content-type","application/xml; charset=utf-8");
        request.addHeader("Accept","application/xml, multipart/related");
        request.setEntity(inputKeyspace);
       
       
        HttpResponse methodResponse = this.getHttpClient().execute(request);
      
       
        
        int statusCode = methodResponse.getStatusLine().getStatusCode();

        System.out.println("Status Code: " + statusCode);
        
        
        BufferedReader rd = new BufferedReader(
	        new InputStreamReader(methodResponse.getEntity().getContent()));
 
	StringBuilder result = new StringBuilder();
	String line;
	while ((line = rd.readLine()) != null) {
		result.append(line);
	}
        
        System.out.println("SALSA return: " + result.toString());
        
        }
        catch (Exception ex) {
            
        }
        
        
    }
}
