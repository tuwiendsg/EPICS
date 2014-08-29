/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.tooling.edo;

import java.util.List;

/**
 *
 * @author Jun
 */
public class DepicConfiguration {
    List listOfDataElasticityMetricConfs;

    public DepicConfiguration() {
    }

    public DepicConfiguration(List listOfDataElasticityMetricConfs) {
        this.listOfDataElasticityMetricConfs = listOfDataElasticityMetricConfs;
    }

    public List getListOfDataElasticityMetricConfs() {
        return listOfDataElasticityMetricConfs;
    }

    public void setListOfDataElasticityMetricConfs(List listOfDataElasticityMetricConfs) {
        this.listOfDataElasticityMetricConfs = listOfDataElasticityMetricConfs;
    }
    
    
    
    
    
}
