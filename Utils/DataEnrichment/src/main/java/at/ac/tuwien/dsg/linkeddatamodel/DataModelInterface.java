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
    public String addDataModelURI(String dataModelURI)
    {
       return dataModelURI; 
    }
    public LinkedList<String> addDataModelProperty(LinkedList<String> dataModelProperty)
    {
        return dataModelProperty;
    }
    public String addBuildingName(String buildingName)
    {
        return buildingName;
    }
    public Map<String,List<String>> addMonitoringInformation(Map<String,List<String>> monitoringInformation)
    {
      return monitoringInformation;  
    }
}
