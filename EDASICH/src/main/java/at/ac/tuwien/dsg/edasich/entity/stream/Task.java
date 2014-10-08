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
    private Integer id;

    @XmlElement(name = "taskName", required = true)
    private String name;

    @XmlElement(name = "taskContent", required = true)
    private String content;

    @XmlElement(name = "tag", required = true)
    private String tag; 

    @XmlElement(name = "severity", required = true)
    private SeverityLevel severity;

    public enum SeverityLevel {
        NOTICE, WARNING, CRITICAL, ALERT, EMERGENCY
    }
    
   
    public Task() {
    }

    public Task(Integer id, String name, String content, String tag, SeverityLevel severity) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.tag = tag;
        this.severity = severity;
    }

    public Task(String name, String content, String tag, SeverityLevel severity) {
        this.name = name;
        this.content = content;
        this.tag = tag;
        this.severity = severity;
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
