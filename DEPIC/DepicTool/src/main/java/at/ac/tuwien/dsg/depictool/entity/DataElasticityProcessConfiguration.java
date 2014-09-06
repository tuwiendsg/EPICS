/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity;

import java.util.List;

/**
 *
 * @author Jun
 */
public class DataElasticityProcessConfiguration {
    List<MetricElasticityProcess> listOfMetricControlActions;

    public DataElasticityProcessConfiguration() {
    }

    public DataElasticityProcessConfiguration(List<MetricElasticityProcess> listOfMetricControlActions) {
        this.listOfMetricControlActions = listOfMetricControlActions;
    }

    public List<MetricElasticityProcess> getListOfMetricControlActions() {
        return listOfMetricControlActions;
    }

    public void setListOfMetricControlActions(List<MetricElasticityProcess> listOfMetricControlActions) {
        this.listOfMetricControlActions = listOfMetricControlActions;
    }
    
    
    
    
}
