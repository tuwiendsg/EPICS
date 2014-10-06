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
        /*String buildingName="BuildingA";
       LinkedList<String> sensorName=new LinkedList<String>();
       sensorName.add("SensorA");
       sensorName.add("SensorB");
       sensorName.add("SensorC");
       
       LinkedList<String> objectName=new LinkedList<String>();
       objectName.add("ObjectA");
       objectName.add("ObjectB");
       objectName.add("ObjectC");
    new MonitoredObjectModel().dataModelGeneration(buildingName, sensorName, objectName);*/
        
        
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
        try
        {
        new TestRDFTuple2().GraphStore(fileName);
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
	
    
    
      /*String sensorName1="SensorA";
      String buildingName1="BuildingA";
      System.out.println("Monitored Object Name : "+new DataRetrieveModel().monitoredObjectName(sensorName1, buildingName1));*/
    
    
    
    }
    
}

