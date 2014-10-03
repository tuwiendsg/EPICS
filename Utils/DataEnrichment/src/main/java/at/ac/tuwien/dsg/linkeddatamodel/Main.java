/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.linkeddatamodel;

import java.util.LinkedList;

/**
 *
 * @author Anindita
 */
public class Main {
    public static void main(String []p)
    {
        String buildingName="BuildingA";
       LinkedList<String> sensorName=new LinkedList<String>();
       sensorName.add("SensorA");
       sensorName.add("SensorB");
       sensorName.add("SensorC");
       
       LinkedList<String> objectName=new LinkedList<String>();
       objectName.add("ObjectA");
       objectName.add("ObjectB");
       objectName.add("ObjectC");
    new MonitoredObjectModel().dataModelGeneration(buildingName, sensorName, objectName);
    
    
      String sensorName1="SensorA";
      String buildingName1="BuildingA";
      System.out.println("Monitored Object Name : "+new DataRetrieveModel().monitoredObjectName(sensorName1, buildingName1));
    
    
    
    }
    
}

