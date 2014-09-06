/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.tooling.edo.metric;

/**
 *
 * @author Jun
 */
public class ElasticityMetricConf {
    
    GeneralInfo info;
    PricingScheme pricingScheme;
    MonitorConf monitorConf;
    ControlProcessMatrix controlProcessMatrix;

    public ElasticityMetricConf() {
    }

    
    
    
    public ElasticityMetricConf(GeneralInfo info, PricingScheme pricingScheme, MonitorConf monitorConf, ControlProcessMatrix controlProcessMatrix) {
        this.info = info;
        this.pricingScheme = pricingScheme;
        this.monitorConf = monitorConf;
        this.controlProcessMatrix = controlProcessMatrix;
    }

    public GeneralInfo getInfo() {
        return info;
    }

    public void setInfo(GeneralInfo info) {
        this.info = info;
    }

    public PricingScheme getPricingScheme() {
        return pricingScheme;
    }

    public void setPricingScheme(PricingScheme pricingScheme) {
        this.pricingScheme = pricingScheme;
    }

    public MonitorConf getMonitorConf() {
        return monitorConf;
    }

    public void setMonitorConf(MonitorConf monitorConf) {
        this.monitorConf = monitorConf;
    }

    public ControlProcessMatrix getControlProcessMatrix() {
        return controlProcessMatrix;
    }

    public void setControlProcessMatrix(ControlProcessMatrix controlProcessMatrix) {
        this.controlProcessMatrix = controlProcessMatrix;
    }
    
    
    
    
    
}
