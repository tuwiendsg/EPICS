/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.app.graphstorage;


/**
 *
 * @author Anindita
 */
public class MainRDFGraph {
   public static void main(String []p)
   {
       String fileuri="http://windtunnel.com";
       //for clean the graph
       new GraphClean().graphRemove(fileuri);
       
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
        
        ///SensoryModule
        String fileName2="./example6/SensoryModule.rdf";
        
        try
        {
        new RDFGraphStorage().GraphStore(fileName2,fileuri);
        }
        catch(Exception e)
        {
            System.out.println("exception occured="+e);
        }
        
        String fileName4="./example6/MOXGasSensor.rdf";
        
        try
        {
        new RDFGraphStorage().GraphStore(fileName4,fileuri);
        }
        catch(Exception e)
        {
            System.out.println("exception occured="+e);
        }
        
        //MOX gas sensor
        for(int i=21;i<30;i++)
        {
        String fileName3="./example6/Sensor"+i+".rdf";
        
        try
        {
        new RDFGraphStorage().GraphStore(fileName3,fileuri);
        }
        catch(Exception e)
        {
            System.out.println("exception occured="+e);
        }
        }
        
        
   }
}