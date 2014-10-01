/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qoranalytics.cep.daasconnector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 *
 * @author Anindita
 */
public class RetrieveStructure {
    public String retrieveValue(String ipAddress, int port, CloseableHttpClient httpClient, String xmlString, String path)
    {
        String strurl="http://"+ipAddress+":"+Integer.toString(port)+"/DaaS/api"+path;
        StringBuilder entity=new StringBuilder();
        try
        {
        StringEntity entity1=new StringEntity(xmlString);     
        
        HttpPost request=new HttpPost(strurl);
        request.setEntity(entity1);
        request.addHeader("Accept","application/xml");
        request.addHeader("Content-Type", "application/xml");
        
       
        CloseableHttpResponse response=httpClient.execute(request);
        int statuscode=response.getStatusLine().getStatusCode();
        
        if (statuscode!=200)
        {
            BufferedReader br=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            while(br.readLine()!=null)
            {
                entity=entity.append(br.readLine());
            }
           
        }
        else
        {
            entity=new StringBuilder("HTTP error occured : "+statuscode);
        }
        
        }
        catch(Exception e)
        {
            System.out.println("Exception occured : "+e);
        }
        
	return new String(entity);	 
}
}
