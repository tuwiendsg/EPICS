/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.linkeddatamodel;

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
        String buildingName1="BuildingB";
        new MonitoredObjectDataModel().dataModelGeneration(uri1,buildingName1); 
        
        //for rdf data model generation
        String uri="http://somewhere/index#";
        String buildingName="BuildingB";
        Map<String, List<String>> monitoringInformation=new HashMap<String, List<String>>();
        
        List<String> monitoringObject1=new ArrayList<String>();
        monitoringObject1.add("ObjectA");
        monitoringObject1.add("ObjectB");
        
        List<String> monitoringObject2=new ArrayList<String>();
        monitoringObject2.add("ObjectA");
        monitoringObject2.add("ObjectB");
        
        monitoringInformation.put("SensorA", monitoringObject1);
        monitoringInformation.put("SensorB", monitoringObject2);
        
        new MonitoredObjectDataModel().dataModelGeneration(uri,buildingName,monitoringInformation); 
        
        //for graph storage
        String fileName="./example/BuildingBTest.rdf";
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
        String subject="http://somewhere/index#SensorA";
        String predicate="MonitoredObjectName";
        LinkedList<String> monitoringInformation2=new DataModelManipulation().queryResult(subject,predicate);
        for(int i=0;i<monitoringInformation2.size();i++)
        {
            System.out.println("monitoring object="+monitoringInformation2.get(i));
        }
	
    
       //for data about monitored object extraction
        
        String subject1="http://somewhere/index#SensorA";
        String predicate1="MonitoredObjectName";
        Map<String, List<String>> monitoredInformation=new ObjectMonitor().monitoredObjectInformation(subject1, predicate1);
        
       for(Map.Entry<String, List<String>> entry : monitoredInformation.entrySet() )
       {
           System.out.println("Object Name : "+entry.getKey());
           System.out.println(" Monitored Sensor Name : "+entry.getValue());
            System.out.println("Object Name : "+entry.getKey());
           System.out.println(" Monitored Sensor Name : "+entry.getValue());
           
       }
    
    
    
    }
    
}

