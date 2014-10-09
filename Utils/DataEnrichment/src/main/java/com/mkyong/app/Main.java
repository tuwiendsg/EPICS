/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Anindita
 */
public class Main {
    public static void main(String []p)
    {
        
        //for rdf model generation of a building
        
        String uri1="http://somewhere/index#";
        String buildingName1="WindTunnel";
        new MonitoredObjectDataModel().dataModelGeneration(uri1,buildingName1); 
        
        //for rdf data model generation
        String uri="http://somewhere/index#";
        String buildingName="WindTunnel";
        Map<String, List<String>> monitoringInformation=new HashMap<String, List<String>>();
        /*HashMap<String, String> sensorLocation=new HashMap<String, String>();
        
        sensorLocation.put("Sensor1", "Sile No.1");
        sensorLocation.put("Sensor2", "Sile No.2");
        sensorLocation.put("Sensor3", "Sile No.3");*/
        LinkedList<String> sensorLocation=new LinkedList<String>();
        sensorLocation.add("Sile No.1");
        sensorLocation.add("Sile No.2");
        sensorLocation.add("Sile No.3");
        
        List<String> monitoringObject1=new ArrayList<String>();
        monitoringObject1.add("Methane");
        monitoringObject1.add("Acetone");
        monitoringObject1.add("Acetal dehyde");
        monitoringObject1.add("Ammonia");
        monitoringObject1.add("Butanol");
        
        List<String> monitoringObject2=new ArrayList<String>();
        monitoringObject2.add("Ethylene");
        monitoringObject2.add("Methane");
        monitoringObject2.add("Metanol");
        monitoringObject2.add("Carbonmonooxide");
        
        List<String> monitoringObject3=new ArrayList<String>();
        monitoringObject3.add("Ethylene");
        monitoringObject3.add("Methane");
        monitoringObject3.add("Benze");
        monitoringObject3.add("Toluene");
        
        
        monitoringInformation.put("Sensor1", monitoringObject1);
        monitoringInformation.put("Sensor2", monitoringObject2);
        monitoringInformation.put("Sensor3", monitoringObject3);
        
        new MonitoredObjectDataModel().dataModelGeneration(uri,buildingName,monitoringInformation,sensorLocation); 
        
        //for graph storage
        String fileName="./example/WindTunnelTest.rdf";
        String fileuri="http://somewhere/index";
        try
        {
        new RDFGraphGeneration().GraphStore(fileName,fileuri);
        }
        catch(Exception e)
        {
            System.out.println("exception occured="+e);
        }
        
        //for data extraction
        String subject="Sensor1";
        //String predicate="MonitoredObjectName";
        //String predicate="MonitoredObjectInformation";
        LinkedList<String> monitoringInformation2=new DataModelManipulation1().queryResult(subject);
        for(int i=0;i<monitoringInformation2.size();i++)
        {
            System.out.println("monitoring object="+monitoringInformation2.get(i));
        }
	
    
       //for data about monitored object extraction
        
        /*String subject1="http://somewhere/index#Sensor1";
        //String predicate1="MonitoredObjectName";
        String predicate1="MonitoredObjectInformation";
        Map<String, List<String>> monitoredInformation=new ObjectMonitor().monitoredObjectInformation(subject1, predicate1);
        
       for(Map.Entry<String, List<String>> entry : monitoredInformation.entrySet() )
       {
           System.out.println("Object Name : "+entry.getKey());
           System.out.println(" Monitored Sensor Name : "+entry.getValue());
            System.out.println("Object Name : "+entry.getKey());
           System.out.println(" Monitored Sensor Name : "+entry.getValue());
           
       }*/
    
    
    
    }
    
}

