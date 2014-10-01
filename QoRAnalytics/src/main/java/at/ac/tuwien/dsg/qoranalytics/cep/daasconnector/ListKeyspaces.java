/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qoranalytics.cep.daasconnector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
/**
 *
 * @author Anindita
 */
public class ListKeyspaces {
    public StringBuilder showKeyspaces(String ipAddress, int port, CloseableHttpClient httpClient)
            
    {
        String strurl="http://"+ipAddress+":"+Integer.toString(port)+"/DaaS/api/xml/keyspace";
        StringBuilder entity=new StringBuilder();
        
        try
        {
        HttpGet request=new HttpGet(strurl);
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
        
	return entity;	 

	} 
    }

