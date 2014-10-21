/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing;

import at.ac.tuwien.dsg.edasich.entity.daf.DafDelegator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "analyticResultDelegate")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnalyticResultDelegate {
    
    @XmlElement(name = "delegateMessage", required = true)
    String delegateMessage;
    
    @XmlElement(name = "dafDelegator", required = true)
    DafDelegator dafDelegator;

    public AnalyticResultDelegate() {
    }

    public AnalyticResultDelegate(String delegateMessage, DafDelegator dafDelegator) {
        this.delegateMessage = delegateMessage;
        this.dafDelegator = dafDelegator;
    }

    public String getDelegateMessage() {
        return delegateMessage;
    }

    public void setDelegateMessage(String delegateMessage) {
        this.delegateMessage = delegateMessage;
    }

    public DafDelegator getDafDelegator() {
        return dafDelegator;
    }

    public void setDafDelegator(DafDelegator dafDelegator) {
        this.dafDelegator = dafDelegator;
    }
}
