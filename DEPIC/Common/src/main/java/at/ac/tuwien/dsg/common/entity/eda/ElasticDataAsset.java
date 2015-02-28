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
    
    @XmlElement(name = "type", required = true)
    EDaaSType type;
    
    @XmlElement(name = "elasticityProcesses", required = true)
    ElasticityProcess elasticityProcess;
    
    @XmlElement(name = "elasticStateSet", required = true)
    ElasticStateSet elasticStateSet;
    
    public ElasticDataAsset() {
    }

    public ElasticDataAsset(String dataAssetID, EDaaSType type, ElasticityProcess elasticityProcess, ElasticStateSet elasticStateSet) {
        this.dataAssetID = dataAssetID;
        this.type = type;
        this.elasticityProcess = elasticityProcess;
        this.elasticStateSet = elasticStateSet;
    }

    public String getDataAssetID() {
        return dataAssetID;
    }

    public void setDataAssetID(String dataAssetID) {
        this.dataAssetID = dataAssetID;
    }

    public EDaaSType getType() {
        return type;
    }

    public void setType(EDaaSType type) {
        this.type = type;
    }

    public ElasticityProcess getElasticityProcess() {
        return elasticityProcess;
    }

    public void setElasticityProcess(ElasticityProcess elasticityProcess) {
        this.elasticityProcess = elasticityProcess;
    }

    public ElasticStateSet getElasticStateSet() {
        return elasticStateSet;
    }

    public void setElasticStateSet(ElasticStateSet elasticStateSet) {
        this.elasticStateSet = elasticStateSet;
    }

    

    
    
}
