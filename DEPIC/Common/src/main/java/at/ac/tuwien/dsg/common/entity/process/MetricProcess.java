/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.process;

import java.util.List;

/**
 *
 * @author Jun
 */
public class MetricProcess {
    List<MetricElasticityProcess> listOfMetricElasticityProcesses;

    public MetricProcess() {
    }

    public MetricProcess(List<MetricElasticityProcess> listOfMetricElasticityProcesses) {
        this.listOfMetricElasticityProcesses = listOfMetricElasticityProcesses;
    }

    public List<MetricElasticityProcess> getListOfMetricElasticityProcesses() {
        return listOfMetricElasticityProcesses;
    }

    public void setListOfMetricElasticityProcesses(List<MetricElasticityProcess> listOfMetricElasticityProcesses) {
        this.listOfMetricElasticityProcesses = listOfMetricElasticityProcesses;
    }

    

    
    
    
}
