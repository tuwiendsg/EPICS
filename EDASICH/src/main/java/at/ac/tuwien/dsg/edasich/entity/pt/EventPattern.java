/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.entity.pt;

import at.ac.tuwien.dsg.edasich.entity.stream.Task;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "EventPattern")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventPattern {
    
    @XmlElement(name = "dataAssetFunction", required = true)
    DataAssetFunctionStreamingData daf;
    
    @XmlElement(name = "condition", required = true)
    String pattern;
    
    @XmlElement(name = "event", required = true)
    Task task;

    public EventPattern() {
    }

    public EventPattern(DataAssetFunctionStreamingData daf, String pattern, Task task) {
        this.daf = daf;
        this.pattern = pattern;
        this.task = task;
    }

    public DataAssetFunctionStreamingData getDaf() {
        return daf;
    }

    public void setDaf(DataAssetFunctionStreamingData daf) {
        this.daf = daf;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    
    
    
    
    
    
    
    
}
