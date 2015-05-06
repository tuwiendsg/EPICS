/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.primitiveaction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "ResourceControlStrategy")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResourceControlStrategy {
    
    @XmlElement(name = "estimatedResult", required = true)
    MetricCondition estimatedResult;
    
    @XmlElement(name = "scaleInCondition", required = true)
    MetricCondition scaleInCondition;
    
    @XmlElement(name = "scaleOutCondition", required = true)
    MetricCondition scaleOutCondition;
    
    @XmlElement(name = "dataSize", required = true)
    double dataSize;

    public ResourceControlStrategy() {
    }

    public ResourceControlStrategy(MetricCondition scaleInCondition, MetricCondition scaleOutCondition, double dataSize) {
        this.scaleInCondition = scaleInCondition;
        this.scaleOutCondition = scaleOutCondition;
        this.dataSize = dataSize;
    }

    public MetricCondition getScaleInCondition() {
        return scaleInCondition;
    }

    public void setScaleInCondition(MetricCondition scaleInCondition) {
        this.scaleInCondition = scaleInCondition;
    }

    public MetricCondition getScaleOutCondition() {
        return scaleOutCondition;
    }

    public void setScaleOutCondition(MetricCondition scaleOutCondition) {
        this.scaleOutCondition = scaleOutCondition;
    }

    public double getDataSize() {
        return dataSize;
    }

    public void setDataSize(double dataSize) {
        this.dataSize = dataSize;
    }

    public MetricCondition getEstimatedResult() {
        return estimatedResult;
    }

    public void setEstimatedResult(MetricCondition estimatedResult) {
        this.estimatedResult = estimatedResult;
    }
    
    
    
    
}
