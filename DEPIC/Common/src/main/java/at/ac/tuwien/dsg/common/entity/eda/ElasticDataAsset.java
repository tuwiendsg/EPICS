/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.eda;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.ep.ElasticityProcess;
import at.ac.tuwien.dsg.depictool.entity.others.DataSource;
import at.ac.tuwien.dsg.common.entity.qor.QoRMetric;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ElasticDataAsset {

    DataAsset dataAsset;
    ElasticityProcess elasticityProcess;
    List<ElasticState> listOfElasticStates;
    
    public ElasticDataAsset() {
    }

    public ElasticDataAsset(DataAsset dataAsset, ElasticityProcess elasticityProcess, List<ElasticState> listOfElasticStates) {
        this.dataAsset = dataAsset;
        this.elasticityProcess = elasticityProcess;
        this.listOfElasticStates = listOfElasticStates;
    }

    public DataAsset getDataAsset() {
        return dataAsset;
    }

    public void setDataAsset(DataAsset dataAsset) {
        this.dataAsset = dataAsset;
    }

    public ElasticityProcess getElasticityProcess() {
        return elasticityProcess;
    }

    public void setElasticityProcess(ElasticityProcess elasticityProcess) {
        this.elasticityProcess = elasticityProcess;
    }

    public List<ElasticState> getListOfElasticStates() {
        return listOfElasticStates;
    }

    public void setListOfElasticStates(List<ElasticState> listOfElasticStates) {
        this.listOfElasticStates = listOfElasticStates;
    }
    
    
    

    
    
}
