/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.app;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Anindita
 */
public class MonitoredObjectDataModel {
    public void dataModelGeneration(String uri,String buildingName, Map<String, List<String>> sensor,LinkedList<String> sensorLocation)
   {
       
       
       
       
       Model model=ModelFactory.createDefaultModel();
       Resource building=model.createResource(uri+buildingName);
       
       String geouri="http://www.w3.org/2003/01/geo/wgs84_pos#";
       final Property g_lat = model.createProperty( "http://www.w3.org/2003/01/geo/wgs84_pos#lat" );
       final Property g_long = model.createProperty( "http://www.w3.org/2003/01/geo/wgs84_pos#long" );
       
       Property buildingPropertyName=model.createProperty(uri+"BuildingName");
       building.addProperty(buildingPropertyName, buildingName);
       building.addProperty(g_lat, "8.90");
       building.addProperty(g_long, "6.78");
       
       Property sensorProperty=model.createProperty(uri+"SensorInformation");
       
       Property objectPropertyName=model.createProperty(uri+"MonitoredObjectName");
       Property monitoredObject=model.createProperty(uri+"MonitoredObjectInformation");
       Property sensorPropertyName=model.createProperty(uri+"SensorName");
       Property sensorLocationName=model.createProperty(uri+"SensorLocation");
       
       
       
       
       
       double count=0.0;
       
       
       for(Map.Entry<String, List<String>> entry : sensor.entrySet() )
       {
       
       List<String> monitoredObjectName=entry.getValue();
       Resource sensorStructure=model.createResource(uri+entry.getKey());
       building.addProperty(sensorProperty, sensorStructure);
       
       sensorStructure.addProperty(sensorPropertyName, entry.getKey());
      // sensorStructure.addProperty(RDFS.seeAlso, sensorStructure);
        sensorStructure.addProperty(RDFS.seeAlso, "http://sensorName");
        sensorStructure.addProperty(g_lat, ""+(8.01+count));
        sensorStructure.addProperty(g_long, ""+(7.0+count));
        if(entry.getKey().equals("Sensor1"))
        {
        sensorStructure.addProperty(sensorLocationName,sensorLocation.get(0) );
        }
        if(entry.getKey().equals("Sensor2"))
        {
        sensorStructure.addProperty(sensorLocationName,sensorLocation.get(1) );
        }
        if(entry.getKey().equals("Sensor3"))
        {
        sensorStructure.addProperty(sensorLocationName,sensorLocation.get(2) );
        }
       
       for(int k=0;k<monitoredObjectName.size();k++)
       {
         Resource objectResource=model.createResource(uri+monitoredObjectName.get(k));
         sensorStructure.addProperty(monitoredObject, objectResource);
         objectResource.addProperty(objectPropertyName, monitoredObjectName.get(k));
         objectResource.addProperty(RDFS.seeAlso, "http://monitoredobject");
           //sensorStructure.addProperty(objectPropertyName,monitoredObjectName.get(i)); 
         
       }
               
      
       count+=0.1;                                    
       }
       
       
       model.setNsPrefix("Building", uri);
       model.setNsPrefix("geo",geouri );
       model.write(System.out);
       try
       {
       FileWriter fw=new FileWriter("./example/"+buildingName+"Test.rdf");
       model.write(fw);
       }
       catch(Exception e)
       {
           System.out.println();
       }
       
       
      
   }
    public void dataModelGeneration(String uri,String buildingName)
    {
       Model model=ModelFactory.createDefaultModel();
       Resource building=model.createResource(uri+buildingName);
       
       Property buildingPropertyName=model.createProperty(uri+"BuildingName");
       building.addProperty(buildingPropertyName, buildingName); 
       
       model.setNsPrefix("Building", uri);
       model.write(System.out);
       try
       {
       FileWriter fw=new FileWriter("./example/"+buildingName+".rdf");
       model.write(fw);
       }
       catch(Exception e)
       {
           System.out.println();
       }
       
    }
}
