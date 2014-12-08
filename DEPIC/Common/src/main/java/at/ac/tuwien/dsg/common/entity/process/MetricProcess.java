/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.process;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "MetricProcess")
@XmlAccessorType(XmlAccessType.FIELD)
public class MetricProcess {
    
    @XmlElement(name = "listOfMetricElasticityProcesses", required = true)
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
