/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qoranalytics.cep.daasconnector;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.annotation.NotThreadSafe;
import java.net.URI;
/**
 *
 * @author Anindita
 */
@NotThreadSafe
public class DeleteStructure extends HttpEntityEnclosingRequestBase {
    
        public DeleteStructure() 
        {
            super();
         }
    
        public DeleteStructure(String uri) 
        {
        super();
        setURI(URI.create(uri));
        }
       
        @Override
        public String getMethod()
        {
            return "DELETE";
        }
    
        public String dropStructure(String ipAddress, int port, CloseableHttpClient httpClient, String xmlString, String path)
       {
        String strurl="http://"+ipAddress+":"+Integer.toString(port)+"/DaaS/api"+path;
        String entity=new String();
        
        try
        {
       
        StringEntity bodyxml=new StringEntity(xmlString); 
        
        DeleteStructure request=new DeleteStructure(strurl);
        request.setEntity(bodyxml);
        request.addHeader("Content-Type", "application/xml");
        
       
        CloseableHttpResponse response=httpClient.execute(request);
        int statuscode=response.getStatusLine().getStatusCode();
        
        if (statuscode!=200)
        {
            entity="successfully deleted";
        }
        else
        {
            entity="HTTP error occured : "+statuscode;
        }
        
        }
        catch(Exception e)
        {
            System.out.println("Exception occured : "+e);
        }
        
	return entity;	 

	} 
    }

