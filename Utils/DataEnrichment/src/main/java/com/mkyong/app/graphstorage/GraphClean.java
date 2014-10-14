/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.app.graphstorage;

import at.ac.tuwien.dsg.dataenrichment.Configuration;
//import com.mkyong.app.OperateProperty;
import virtuoso.jena.driver.VirtGraph;

/**
 *
 * @author dsg
 */
public class GraphClean {
    public void graphRemove(String fileURI)
    {
         OperateProperty operateProperty=new OperateProperty();
        String url=operateProperty.getGraphStorageURI();
        String username=operateProperty.getGraphStorageUserName();
        String password=operateProperty.getGraphStoragePassword();
        
        /*String url="jdbc:virtuoso://"+Configuration.getConfig("VIRTUOSO.IP")+":"+Configuration.getConfig("VIRTUOSO.PORT");
        String username=Configuration.getConfig("VIRTUOSO.USERNAME");
        String password=Configuration.getConfig("VIRTUOSO.PASSWORD");*/
        VirtGraph virtgraph=new VirtGraph(fileURI,url,username,password);
         
        if(!virtgraph.isEmpty())
            {
                System.out.println("clean the graph");
                virtgraph.clear();
            }
         else
            {
                System.out.println("do not need to clean the graph");
            }
    }
}
