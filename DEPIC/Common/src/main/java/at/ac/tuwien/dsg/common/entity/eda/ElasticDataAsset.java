/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.eda;


import at.ac.tuwien.dsg.common.entity.eda.ep.ElasticityProcess;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "ElasticDataAsset")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElasticDataAsset {

    @XmlElement(name = "dataAssetID", required = true)
    String dataAssetID;
    
    @XmlElement(name = "elasticityProcess", required = true)
    ElasticityProcess elasticityProcess;
    
    @XmlElement(name = "listOfElasticStates", required = true)
    List<ElasticState> listOfElasticStates;
    
    public ElasticDataAsset() {
    }

    public ElasticDataAsset(String dataAssetID, ElasticityProcess elasticityProcess, List<ElasticState> listOfElasticStates) {
        this.dataAssetID = dataAssetID;
        this.elasticityProcess = elasticityProcess;
        this.listOfElasticStates = listOfElasticStates;
    }

    public String getDataAssetID() {
        return dataAssetID;
    }

    public void setDataAssetID(String dataAssetID) {
        this.dataAssetID = dataAssetID;
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
