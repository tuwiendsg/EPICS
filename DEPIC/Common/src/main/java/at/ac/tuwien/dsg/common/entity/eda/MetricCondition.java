/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.eda;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "ElasticState")
@XmlAccessorType(XmlAccessType.FIELD)
public class MetricCondition {
    
    @XmlElement(name = "metricName", required = true)
    String metricName;
    
    @XmlElement(name = "upperBound", required = true)
    double upperBound;
    
    @XmlElement(name = "lowerBound", required = true)
    double lowerBound;

    public MetricCondition() {
    }

    public MetricCondition(String metricName, double upperBound, double lowerBound) {
        this.metricName = metricName;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }
    
    
    
    
    
}
