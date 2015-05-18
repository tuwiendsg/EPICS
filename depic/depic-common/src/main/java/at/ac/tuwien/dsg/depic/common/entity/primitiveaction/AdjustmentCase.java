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

@XmlRootElement(name = "AdjustmentCase")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdjustmentCase {
    
    @XmlElement(name = "estimatedResult")
    MetricCondition estimatedResult;
    
    @XmlElement(name = "listOfParameters")
    List<Parameter> listOfParameters;
    
    @XmlElement(name = "listOfAnalyticTasks")
    List<AnalyticTask> listOfAnalyticTasks;
    

    public AdjustmentCase() {
    }

    public AdjustmentCase(MetricCondition estimatedResult, List<Parameter> listOfParameters, List<AnalyticTask> listOfAnalyticTasks) {
        this.estimatedResult = estimatedResult;
        this.listOfParameters = listOfParameters;
        this.listOfAnalyticTasks = listOfAnalyticTasks;
    }

    public MetricCondition getEstimatedResult() {
        return estimatedResult;
    }

    public void setEstimatedResult(MetricCondition estimatedResult) {
        this.estimatedResult = estimatedResult;
    }

    public List<Parameter> getListOfParameters() {
        return listOfParameters;
    }

    public void setListOfParameters(List<Parameter> listOfParameters) {
        this.listOfParameters = listOfParameters;
    }

    public List<AnalyticTask> getListOfAnalyticTasks() {
        return listOfAnalyticTasks;
    }

    public void setListOfAnalyticTasks(List<AnalyticTask> listOfAnalyticTasks) {
        this.listOfAnalyticTasks = listOfAnalyticTasks;
    }

    

    
    
    
    
}
