/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.rule;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "EventStatement")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventStatement {
    
    @XmlAttribute(name = "statement", required = true)
    String statement;

    public EventStatement() {
    }
    
    public EventStatement(String statement) {
        this.statement = statement;
    }
 
    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
    
    

 
       
}
