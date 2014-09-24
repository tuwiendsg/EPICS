/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity.others;

import java.util.List;

/**
 *
 * @author Jun
 */
public class DataElasticityProcessConfiguration {
    List<MetricElasticityProcess> listOfMetricElasticityProcesses;

    public DataElasticityProcessConfiguration() {
    }

    public DataElasticityProcessConfiguration(List<MetricElasticityProcess> listOfMetricControlActions) {
        this.listOfMetricElasticityProcesses = listOfMetricControlActions;
    }

    public List<MetricElasticityProcess> getListOfMetricElasticityProcesses() {
        return listOfMetricElasticityProcesses;
    }

    public void setListOfMetricElasticityProcesses(List<MetricElasticityProcess> listOfMetricElasticityProcesses) {
        this.listOfMetricElasticityProcesses = listOfMetricElasticityProcesses;
    }


    
    
    
}
