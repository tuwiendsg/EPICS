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
    //public static String dataModeluri;
    //public static String buildingName;
    //public static Map<String, List<String>> monitoringInformation;
    
    
   
    public void addBuildingName(String uri,String buildingName)
    {
        //this.buildingName=buildingName;
        //return buildingName;
    }
    public void addMonitoringInformation(String uri, String buildingName, Map<String,List<String>> monitoringInformation)
    {
       new MonitoredObjectDataModel().dataModelGeneration(uri, buildingName, monitoringInformation);
    }
    public LinkedList<String> addRetrieveInformation(String subject,String predicate)
    {
        LinkedList<String> monitoreddata=new DataModelManipulation().queryResult(subject, predicate);
       
        return monitoreddata;
    }
}
