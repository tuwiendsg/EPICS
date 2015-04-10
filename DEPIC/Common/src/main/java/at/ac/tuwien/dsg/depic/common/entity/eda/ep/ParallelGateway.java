/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.eda.ep;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "ParallelGateway")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParallelGateway {
    
    @XmlElement(name = "id", required = true)
    String id; 
    
    @XmlElement(name = "incomming", required = true)
    List<String> incomming;
    
    @XmlElement(name = "outgoing", required = true)
    List<String> outgoing;

    public ParallelGateway() {
    }

    public ParallelGateway(String id, List<String> incomming, List<String> outgoing) {
        this.id = id;
        this.incomming = incomming;
        this.outgoing = outgoing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getIncomming() {
        return incomming;
    }

    public void setIncomming(List<String> incomming) {
        this.incomming = incomming;
    }

    public List<String> getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(List<String> outgoing) {
        this.outgoing = outgoing;
    }

    
    
    
}
