/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity;

import java.util.List;

/**
 *
 * @author Jun
 */
public class ElasticDataObject {
    List<DataObjectFunction> listOfDataObjectFunctions;
    List<DataElasticityMetric> listOfDataElasticityMetrics;
    List<ElasticState> listOfElasticStates;
    
    public ElasticDataObject() {
    }

    public ElasticDataObject(List<DataObjectFunction> listOfDataObjectFunctions, List<DataElasticityMetric> listOfDataElasticityMetrics, List<ElasticState> listOfElasticStates) {
        this.listOfDataObjectFunctions = listOfDataObjectFunctions;
        this.listOfDataElasticityMetrics = listOfDataElasticityMetrics;
        this.listOfElasticStates = listOfElasticStates;
    }

    public List<DataObjectFunction> getListOfDataObjectFunctions() {
        return listOfDataObjectFunctions;
    }

    public void setListOfDataObjectFunctions(List<DataObjectFunction> listOfDataObjectFunctions) {
        this.listOfDataObjectFunctions = listOfDataObjectFunctions;
    }

    public List<DataElasticityMetric> getListOfDataElasticityMetrics() {
        return listOfDataElasticityMetrics;
    }

    public void setListOfDataElasticityMetrics(List<DataElasticityMetric> listOfDataElasticityMetrics) {
        this.listOfDataElasticityMetrics = listOfDataElasticityMetrics;
    }

    public List<ElasticState> getListOfElasticStates() {
        return listOfElasticStates;
    }

    public void setListOfElasticStates(List<ElasticState> listOfElasticStates) {
        this.listOfElasticStates = listOfElasticStates;
    }
    
    
    
    
    
    
    
    
   
    
    
    
}
