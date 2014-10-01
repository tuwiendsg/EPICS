/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qoranalytics.cep.daasconnector;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Anindita
 */
public class DaaSMonitor 
{
    public String monitor(String ipAddress,int port,CloseableHttpClient httpclient)  
  {
	String strurl="http://"+ipAddress+":"+Integer.toString(port)+"/DaaS/api/monitoring";
        String entity=new String();
      
        HttpGet httpget=new HttpGet(strurl);
        httpget.addHeader("Accept", "application/xml");
        
        try
        {
        CloseableHttpResponse response=httpclient.execute(httpget);
        int statuscode=response.getStatusLine().getStatusCode();
        HttpEntity httpentity=response.getEntity();
        if (httpentity!=null)
        {
            entity=EntityUtils.toString(httpentity);
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
  



