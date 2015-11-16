/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.utils;


/**
 *
 * @author Jun
 */


import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import com.sun.jersey.api.client.Client;  
import com.sun.jersey.api.client.ClientResponse;  
import com.sun.jersey.api.client.WebResource; 



public class RestfulWSClient {

    
    private String ip;
    private String port;
    private String resource;
    private String url;
  
    public RestfulWSClient(String ip, String port, String resource) {
        this.ip = ip;
        this.port = port;
        this.resource = resource;
        url = "http://" + ip + ":" + port + resource;
      
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

    public DataAsset callPutMethod(String xmlString) {
        DataAsset dataAsset= null;
          try {  
      
        
            Client client = Client.create();  
            WebResource webResource = client.resource(url);  
              System.out.println(url);

            ClientResponse response = webResource.type("application/xml").accept("application/xml").put(ClientResponse.class, xmlString);
            if (response.getStatus() != 200) {  
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());  
            }  
   
        
            dataAsset = response.getEntity(DataAsset.class);  
            System.out.println("\n============getCResponse============");  
       
   
    
          
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return dataAsset;
    }
    
    
    public int callPutMethodRC(String xmlString) {
        int statusCode =0;
        

        return statusCode;
    }

    public void callPostMethod(String xmlString) {


    }
    
    
    public String callGetMethod() {
      String rs ="";
        return rs;

    }

}
