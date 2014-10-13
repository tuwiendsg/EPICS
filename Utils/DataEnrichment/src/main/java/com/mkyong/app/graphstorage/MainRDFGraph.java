/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.app.graphstorage;

import com.mkyong.app.graphstorage.RDFGraphStorage;

/**
 *
 * @author dsg
 */
public class MainRDFGraph {
   public static void main(String []p)
   {
       String fileuri="http://windtunnel.com";
       
       //for windtunnel.rdf file
       String fileName="./example6/WindTunnel.rdf";
       
        
        try
        {
        new RDFGraphStorage().GraphStore(fileName,fileuri);
        }
        catch(Exception e)
        {
            System.out.println("exception occured="+e);
        }
        
        //for Fan.rdf file
        
        String fileName1="./example6/Fan.rdf";
        try
        {
        new RDFGraphStorage().GraphStore(fileName1,fileuri);
        }
        catch(Exception e)
        {
            System.out.println("exception occured="+e);
        }
        
        ///Sensor21
        String fileName2="./example6/SensoryModule.rdf";
        
        try
        {
        new RDFGraphStorage().GraphStore(fileName2,fileuri);
        }
        catch(Exception e)
        {
            System.out.println("exception occured="+e);
        }
        
        
        String fileName3="./example6/Sensor21.rdf";
        
        try
        {
        new RDFGraphStorage().GraphStore(fileName3,fileuri);
        }
        catch(Exception e)
        {
            System.out.println("exception occured="+e);
        }
        
        //
        String fileName4="./example6/MOXGasSensor.rdf";
        
        try
        {
        new RDFGraphStorage().GraphStore(fileName4,fileuri);
        }
        catch(Exception e)
        {
            System.out.println("exception occured="+e);
        }
        
        String fileName5="./example6/Sensor22.rdf";
        
        try
        {
        new RDFGraphStorage().GraphStore(fileName5,fileuri);
        }
        catch(Exception e)
        {
            System.out.println("exception occured="+e);
        }
        
        String fileName6="./example6/Sensor23.rdf";
        
        try
        {
        new RDFGraphStorage().GraphStore(fileName6,fileuri);
        }
        catch(Exception e)
        {
            System.out.println("exception occured="+e);
        }
   }
}
