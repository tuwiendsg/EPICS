/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.entity.stream;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "Task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task {
    
    @XmlElement(name = "taskID", required = true)
    String taskID;
    
    @XmlElement(name = "taskName", required = true)
    String taskName;
    
    @XmlElement(name = "taskContent", required = true)
    String taskContent;
    
    @XmlElement(name = "tag", required = true)
    String tag; 
    
    @XmlElement(name = "severity", required = true)
    SeverityLevel severity;

    public enum SeverityLevel {
        NOTICE, WARNING, CRITICAL, ALERT, EMERGENCY
    }

    public Task() {
    }

    public Task(String taskID, String taskName, String taskContent, String responseInterface, String tag, SeverityLevel severity) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskContent = taskContent;
        this.tag = tag;
        this.severity = severity;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public SeverityLevel getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityLevel severity) {
        this.severity = severity;
    }
    
    

   
}
