/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.app;

/**
 *
 * @author Anindita
 */
public class OperateProperty {
    
    public String getGraphStorageURI()
    {
        //return "jdbc:virtuoso://localhost:1111";
        return "jdbc:virtuoso://128.130.172.230:1111";
        //return "jdbc:virtuoso://10.99.0.9:1111";
    }
    
    public String getrdfURI()
    {
        return "http://windtunnel.com";
    }
    
    public String getGraphStorageUserName()
    {
        return "dba";
    }
    
    public String getGraphStoragePassword()
    {
        return "dba";
    }
    public String getgui()
    {
        return "http://localhost:8080/DataEnrichment/" ;
        //return "http://128.130.172.230:8080/DataEnrichment/" ;
    }
    
}
