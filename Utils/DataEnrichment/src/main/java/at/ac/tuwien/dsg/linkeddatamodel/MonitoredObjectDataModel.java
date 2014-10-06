/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.linkeddatamodel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dsg
 */
public class MonitoredObjectDataModel {
    public void dataModelGeneration(String buildingName, Map<String, List<String>> sensor)
   {
       
       Map<String, List<String, String>> sensor
       String uri="http://somewhere/index#";
       Model model=ModelFactory.createDefaultModel();
       Resource building=model.createResource(uri+buildingName);
       
       Property buildingPropertyName=model.createProperty(uri+"BuildingName");
       building.addProperty(buildingPropertyName, buildingName);
       
       Property sensorProperty=model.createProperty(uri+"SensorInformation");
       
       Property objectPropertyName=model.createProperty(uri+"MonitoredObjectName");
       Property sensorPropertyName=model.createProperty(uri+"SensorName");
       
       
       
       for(int i=0;i<sensorName.size();i++)
       {
       
       
       building.addProperty(sensorProperty, 
                                     model.createResource(uri+sensorName.get(i))
                                              .addProperty(sensorPropertyName, sensorName.get(i))
                                              .addProperty(objectPropertyName,objectName.get(i)));
       }
       
       
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
