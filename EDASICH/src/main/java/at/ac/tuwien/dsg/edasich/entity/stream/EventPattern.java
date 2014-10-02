/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.entity.stream;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "EventPattern")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventPattern {
    
    @XmlElement(name = "statement", required = true)
    String statement;
    
    @XmlElement(name = "task")
    Task task;
    
    @XmlElement(name = "intervalTime")
    Long intervalTime;
    
   

    public EventPattern() {
    }

    public EventPattern(String statement, Task task, Long intervalTime) {
        this.statement = statement;
        this.task = task;
        this.intervalTime = intervalTime;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(Long intervalTime) {
        this.intervalTime = intervalTime;
    }

    

    
    
    
    
    
 
       
}
