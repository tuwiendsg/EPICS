/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.esperstreamprocessing.utils;

import at.ac.tuwien.dsg.esperstreamprocessing.handler.EventHandler;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author Jun
 */
public class RestHttpClient {
    
    private String ip;
    private String port;
    private String resource;
    private String url;
    

    public RestHttpClient(String ip, String port, String resource) {
        this.ip = ip;
        this.port = port;
        this.resource = resource;
        url = "http://" + ip + ":" + port + resource;
       
    }

    public RestHttpClient(String url) {
        this.url = url;
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public void callPostMethod(List<NameValuePair> paramList){
        
        try {
            
            HttpPost post = new HttpPost(url);
            
            // add header
          //  post.setHeader("Host", "smartsoc.infosys.tuwien.ac.at");
            //post.setHeader("User-Agent", USER_AGENT);
            //post.setHeader("Connection", "keep-alive");
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
         
            // set content
            post.setEntity(new UrlEncodedFormEntity(paramList));            
        //    Logger.getLogger(EventHandler.class.getName()).log(Level.INFO, "\nSending 'POST' request to URL : " + url);
         //   Logger.getLogger(EventHandler.class.getName()).log(Level.INFO, "Configuring..");
            
         //   Logger.getLogger(EventHandler.class.getName()).log(Level.INFO, "Post parameters : " + paramList);
  

            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(post);
            
            int responseCode = response.getStatusLine().getStatusCode();
         
            Logger.getLogger(RestHttpClient.class.getName()).log(Level.INFO, "Connection : " + url);
            Logger.getLogger(RestHttpClient.class.getName()).log(Level.INFO, "Response Code : " + responseCode);
    
         
            BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
         
            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
         
          //  Logger.getLogger(EventHandler.class.getName()).log(Level.INFO, result.toString());
  

        } catch (Exception ex) {
         
        }
    
    }
}
