/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.primitiveaction;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "ResourceControlCase")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResourceControlCase {
    
    @XmlElement(name = "estimatedResult", required = true)
    MetricCondition estimatedResult;
    
    @XmlElement(name = "dataSize", required = true)
    MetricCondition dataSize;
    
    @XmlElement(name = "listOfResourceControlStrategy", required = true)
    List<ResourceControlStrategy> listOfResourceControlStrategies;
    
    

    public ResourceControlCase() {
    }

    public ResourceControlCase(MetricCondition estimatedResult, MetricCondition dataSize, List<ResourceControlStrategy> listOfResourceControlStrategies) {
        this.estimatedResult = estimatedResult;
        this.dataSize = dataSize;
        this.listOfResourceControlStrategies = listOfResourceControlStrategies;
    }

    public MetricCondition getEstimatedResult() {
        return estimatedResult;
    }

    public void setEstimatedResult(MetricCondition estimatedResult) {
        this.estimatedResult = estimatedResult;
    }

    public MetricCondition getDataSize() {
        return dataSize;
    }

    public void setDataSize(MetricCondition dataSize) {
        this.dataSize = dataSize;
    }

    public List<ResourceControlStrategy> getListOfResourceControlStrategies() {
        return listOfResourceControlStrategies;
    }

    public void setListOfResourceControlStrategies(List<ResourceControlStrategy> listOfResourceControlStrategies) {
        this.listOfResourceControlStrategies = listOfResourceControlStrategies;
    }

    
    
}
