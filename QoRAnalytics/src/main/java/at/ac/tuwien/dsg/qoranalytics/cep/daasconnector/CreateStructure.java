/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qoranalytics.cep.daasconnector;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;


/**
 *
 * @author Anindita
 */
public class CreateStructure {
    public String putQuery(String ipAddress, int port, CloseableHttpClient httpClient, String xmlString, String path)
            
    {
        String strurl="http://"+ipAddress+":"+Integer.toString(port)+"/DaaS/api"+path;
        String entity=new String();
        try
        {
        StringEntity entity1=new StringEntity(xmlString);     
        
        HttpPut request=new HttpPut(strurl);
        request.setEntity(entity1);
        request.addHeader("Content-Type", "application/xml");
        
       
        CloseableHttpResponse response=httpClient.execute(request);
        int statuscode=response.getStatusLine().getStatusCode();
        
        if (statuscode!=200)
        {
            entity="successfully generated";
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

