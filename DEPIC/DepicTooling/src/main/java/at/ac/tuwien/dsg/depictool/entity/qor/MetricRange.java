/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity.qor;

/**
 *
 * @author Jun
 */
public class MetricRange {
    String metricName;
    String range;

    public MetricRange() {
    }

    public MetricRange(String metricName, String range) {
        this.metricName = metricName;
        this.range = range;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
    
    
    
    
}
