/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "ElasticStateSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class ElasticStateSet {
    
    @XmlElement(name = "initialElasticStateSet", required = true)
    List<ElasticState> initialElasticStateSet;
    
    @XmlElement(name = "finalElasticStateSet", required = true)
    List<ElasticState> finalElasticStateSet;

    public ElasticStateSet() {
    }

    public ElasticStateSet(List<ElasticState> initialElasticStateSet, List<ElasticState> finalElasticStateSet) {
        this.initialElasticStateSet = initialElasticStateSet;
        this.finalElasticStateSet = finalElasticStateSet;
    }

    public List<ElasticState> getInitialElasticStateSet() {
        return initialElasticStateSet;
    }

    public void setInitialElasticStateSet(List<ElasticState> initialElasticStateSet) {
        this.initialElasticStateSet = initialElasticStateSet;
    }

    public List<ElasticState> getFinalElasticStateSet() {
        return finalElasticStateSet;
    }

    public void setFinalElasticStateSet(List<ElasticState> finalElasticStateSet) {
        this.finalElasticStateSet = finalElasticStateSet;
    }
    
    
    
    
}
