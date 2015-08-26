/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.elasticdaasclient.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author Jun
 */
public class RestfulWSClient {

    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private String ip;
    private String port;
    private String resource;
    private String url;
    private Logger logger;

    public RestfulWSClient(String ip, String port, String resource) {
        this.ip = ip;
        this.port = port;
        this.resource = resource;
        url = "http://" + ip + ":" + port + resource;
        logger = Logger.getLogger(this.getClass().getName());
    }

    public RestfulWSClient(String url) {
        this.url = url;
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

    public String callPutMethod(String xmlString) {
        String rs = "";

        try {

            //HttpGet method = new HttpGet(url);
            StringEntity inputKeyspace = new StringEntity(xmlString);

         //   Logger.getLogger(RestfulWSClient.class.getName()).log(Level.INFO, "Connection .. " + url);

            HttpPut request = new HttpPut(url);
            request.addHeader("content-type", "application/xml; charset=utf-8");
            request.addHeader("Accept", "application/xml, multipart/related");
            request.setEntity(inputKeyspace);

            HttpResponse methodResponse = this.getHttpClient().execute(request);

            int statusCode = methodResponse.getStatusLine().getStatusCode();

          //  Logger.getLogger(RestfulWSClient.class.getName()).log(Level.INFO, "Status Code: " + statusCode);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(methodResponse.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            rs = result.toString();
            // System.out.println("Response String: " + result.toString());
        } catch (Exception ex) {

        }
        return rs;
    }
    
    
    

    public String callPutMethodObj(Object obj) {
        String rs = "";
        String returnObj = null;

        try {

            String xmlString = "";

            xmlString = JAXBUtils.marshal(obj, obj.getClass());
            System.out.println(xmlString);
       

            Logger.getLogger(RestfulWSClient.class.getName()).log(Level.INFO, "Connection .. " + url);

            HttpPut request = new HttpPut(url);
            request.addHeader("content-type", "application/xml; charset=utf-8");
            request.addHeader("Accept", "application/xml, multipart/related");
            HttpEntity entity = new ByteArrayEntity(xmlString.getBytes("UTF-8"));
            
            request.setEntity(entity);

            HttpResponse methodResponse = this.getHttpClient().execute(request);

            int statusCode = methodResponse.getStatusLine().getStatusCode();

            
            Logger.getLogger(RestfulWSClient.class.getName()).log(Level.INFO, "Status Code: " + statusCode);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(methodResponse.getEntity().getContent()));
            
           
            
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            rs = result.toString();

          //  returnObj = (T) JAXBUtils.unmarshal(rs, returnObj.getClass());

            // System.out.println("Response String: " + result.toString());
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.toString());
        }
        return returnObj;
    }

    public void callPostMethod(String xmlString) {

        try {

            //HttpGet method = new HttpGet(url);
            StringEntity inputKeyspace = new StringEntity(xmlString);
            System.out.println("Connection .. " + url);
            //HttpPut request = new HttpPut(url);

            HttpPost request = new HttpPost(url);
            request.addHeader("content-type", "application/json");
          // request.addHeader("content-type", "application/x-www-form-urlencoded");

            // request.addHeader("Accept", "application/json, multipart/related");
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
            System.out.println("Exception: " + ex.toString());
        }

    }

    public String callGetMethod() {
        String rs = "";
        try {

            //HttpGet method = new HttpGet(url);
            System.out.println("Connection .. " + url);
            //HttpPut request = new HttpPut(url);

            HttpGet request = new HttpGet(url);
            request.addHeader("content-type", "application/json");
          // request.addHeader("content-type", "application/x-www-form-urlencoded");

            // request.addHeader("Accept", "application/json, multipart/related");
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
            rs = result.toString();
            //System.out.println("Response String: " + result.toString());
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.toString());
        }

        return rs;

    }

}
