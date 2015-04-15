/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.common.entity.eda;


import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticStateSet;
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
  
    @XmlElement(name = "elasticProcess", required = true)
    ElasticProcess elasticProcess;
    
    @XmlElement(name = "elasticStateSet", required = true)
    ElasticStateSet elasticStateSet;
    
    public ElasticDataAsset() {
    }

    public String getDataAssetID() {
        return dataAssetID;
    }

    public void setDataAssetID(String dataAssetID) {
        this.dataAssetID = dataAssetID;
    }

    public ElasticProcess getElasticProcess() {
        return elasticProcess;
    }

    public void setElasticProcess(ElasticProcess elasticProcess) {
        this.elasticProcess = elasticProcess;
    }

    public ElasticStateSet getElasticStateSet() {
        return elasticStateSet;
    }

    public void setElasticStateSet(ElasticStateSet elasticStateSet) {
        this.elasticStateSet = elasticStateSet;
    }

    

    
    
}
