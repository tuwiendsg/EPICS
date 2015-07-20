/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.utils;


import java.util.logging.Logger;
import com.sun.jersey.api.client.Client;  
import com.sun.jersey.api.client.ClientResponse;  
import com.sun.jersey.api.client.WebResource; 
import javax.ws.rs.core.MediaType;


/**
 *
 * @author Jun
 */
public class RestfulWSClient {

  
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

    public <T> T callPutMethod(Object object, Class<T> configurationClass) {

        //put get response data
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type("application/xml").accept("application/xml").put(ClientResponse.class, object);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        T output = (T) response.getEntity(configurationClass);

        return output;
    }
    
    
    public int callPutMethodRC(String xmlString) {
        
        //put get response code

        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type("application/xml").accept("application/xml").put(ClientResponse.class, xmlString);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
      
        return response.getStatus();
    }

    public void callPostMethod(String xmlString) {

        

    }
    
    
    public String callGetMethod() {
        String rs="";
        
        try {  
            Client client = Client.create();  
            WebResource webResource = client.resource(url);  
            ClientResponse response = webResource.type(MediaType.APPLICATION_XML).accept(MediaType.APPLICATION_XML).get(ClientResponse.class);  
            if (response.getStatus() != 200) {  
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());  
            }  
   
            String output = response.getEntity(String.class);  
            System.out.println("\n============getCResponse============");  
            System.out.println(output);  
   
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
      
        return rs;

    }

    public String callPutMethod(String xmlStr) {
        //put get response data
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type("application/xml").accept("application/xml").put(ClientResponse.class, xmlStr);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        String output =  response.getEntity(String.class);
        
        return output;
    }

}
