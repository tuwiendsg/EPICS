/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.streamprocessing.entity.event;

import at.ac.tuwien.dsg.smartcom.model.Message;
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
    
    @XmlElement(name = "message")
    EventMessage message;

    public EventPattern() {
    }

    public EventPattern(String statement, EventMessage message) {
        this.statement = statement;
        this.message = message;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public EventMessage getMessage() {
        return message;
    }

    public void setMessage(EventMessage message) {
        this.message = message;
    }
    
    
    
    
 
       
}
