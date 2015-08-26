/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.elasticdaasclient.demo;


import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;


/**
 *
 * @author Jun
 */
@Path("test")
public class ElasticDataAssetRestZ {
    
    
    @Context
    private UriInfo context;

    public ElasticDataAssetRestZ() {
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
       
        int noOfDaaSConsumers =5;
        
        String str="";
        
        for (int i =0;i<noOfDaaSConsumers;i++){
            String customerID = "c0"+ String.valueOf(i);
            DaaSClient daaSClient = new DaaSClient(customerID);
            daaSClient.start();
            str += customerID + " is running !\n";
            System.out.println(customerID + " is running !");
            
        }

        return str;
    }

   
    
}
