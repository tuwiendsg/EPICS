/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qualityenforcement.repoaccessmanagement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author Jun
 */
public class EDODeliveryRepoAccessREST {
    
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private String ip="localhost";
    private String port = "8080";
    
    
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
    
    
    public void saveEDO(String xmlString){
        
        try {
            String url = "http://"+ip+":"+port+"/DataAccessManagement/webresources/EDODelivery";

            //HttpGet method = new HttpGet(url);
            StringEntity inputKeyspace = new StringEntity(xmlString);
            
            
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
            
            System.out.println("Response String: " + result.toString());
        } catch (Exception ex) {
           
        }
        
    }
    
}
