/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity;

import at.ac.tuwien.dsg.depictool.entity.qor.QoRMetric;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ElasticDataObject {
    DataSource dataSource;
    List<DataAssetFunction> listOfDataObjectFunctions;
    List<QoRMetric> listOfDataElasticityMetrics;
    List<ElasticState> listOfElasticStates;
    
    public ElasticDataObject() {
    }

    public ElasticDataObject(DataSource dataSource, List<DataAssetFunction> listOfDataObjectFunctions, List<QoRMetric> listOfDataElasticityMetrics, List<ElasticState> listOfElasticStates) {
        this.dataSource = dataSource;
        this.listOfDataObjectFunctions = listOfDataObjectFunctions;
        this.listOfDataElasticityMetrics = listOfDataElasticityMetrics;
        this.listOfElasticStates = listOfElasticStates;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<DataAssetFunction> getListOfDataObjectFunctions() {
        return listOfDataObjectFunctions;
    }

    public void setListOfDataObjectFunctions(List<DataAssetFunction> listOfDataObjectFunctions) {
        this.listOfDataObjectFunctions = listOfDataObjectFunctions;
    }

    public List<QoRMetric> getListOfDataElasticityMetrics() {
        return listOfDataElasticityMetrics;
    }

    public void setListOfDataElasticityMetrics(List<QoRMetric> listOfDataElasticityMetrics) {
        this.listOfDataElasticityMetrics = listOfDataElasticityMetrics;
    }

    public List<ElasticState> getListOfElasticStates() {
        return listOfElasticStates;
    }

    public void setListOfElasticStates(List<ElasticState> listOfElasticStates) {
        this.listOfElasticStates = listOfElasticStates;
    }

    
    
    
    
    
    
    
   
    
    
    
}
