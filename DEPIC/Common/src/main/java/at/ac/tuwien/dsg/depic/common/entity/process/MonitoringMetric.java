/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.process;

/**
 *
 * @author Jun
 */
public class MonitoringMetric {
    String metricName;
    double metricValue;

    public MonitoringMetric() {
    }

    public MonitoringMetric(String metricName, double metricValue) {
        this.metricName = metricName;
        this.metricValue = metricValue;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public double getMetricValue() {
        return metricValue;
    }

    public void setMetricValue(double metricValue) {
        this.metricValue = metricValue;
    }
    
    
    
}
