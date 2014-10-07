/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.linkeddatamodel;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dsg
 */
public class DataModelInterface {
    //for generating rdf data model only for building
    public void addBuildingName(String uri,String buildingName)
    {
        new MonitoredObjectDataModel().dataModelGeneration(uri, buildingName);
        
    }
    
    //for generating rdf data model for building with sensor and monitored object
    public void addMonitoringInformation(String uri, String buildingName, Map<String,List<String>> monitoringInformation)
    {
       new MonitoredObjectDataModel().dataModelGeneration(uri, buildingName, monitoringInformation);
    }
    
    //for generating rdf graph and store the content in rdf quad storage 
    public void addRDFGraphGeneration(String fileName,String fileuri)
    {
        try
        {
             new RDFGraphGeneration().GraphStore(fileName,fileuri);
        }
        catch(Exception e)
        {
            System.out.println("Exception occured :"+e);
        }
    }
     
    //receive the name of monitored object corresponding of sensor
    public LinkedList<String> getRetrieveInformation(String subject,String predicate)
    {
        LinkedList<String> monitoredObject=new DataModelManipulation().queryResult(subject, predicate);
       
        return monitoredObject;
    }
    
   //receive the name of the other sensors which monitor the particular effected object monitored by a sensor
    public Map<String, List<String>> getMonitoringInformation(String subject, String predicate)
    {
        Map<String, List<String>> monitoringFacility=new ObjectMonitor().monitoredObjectInformation(subject, predicate);
        return monitoringFacility;
    }
}
