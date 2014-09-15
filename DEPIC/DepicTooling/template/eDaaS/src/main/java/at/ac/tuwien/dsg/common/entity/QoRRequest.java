/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity;

import at.ac.tuwien.dsg.edaas.userrequirement.ConsumerRequirement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement (name = "QoRRequest")
public class QoRRequest {
    String assetName;
    String qElementID;
    ConsumerRequirement consumerRequirement;

    public QoRRequest() {
    }

    public QoRRequest(String assetName, String qElementID, ConsumerRequirement consumerRequirement) {
        this.assetName = assetName;
        this.qElementID = qElementID;
        this.consumerRequirement = consumerRequirement;
    }

    public String getAssetName() {
        return assetName;
    }

    @XmlElement (name ="assetName")
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getqElementID() {
        return qElementID;
    }

    @XmlElement (name ="qElementID")
    public void setqElementID(String qElementID) {
        this.qElementID = qElementID;
    }

    public ConsumerRequirement getConsumerRequirement() {
        return consumerRequirement;
    }

    @XmlElement (name ="consumerRequirement")
    public void setConsumerRequirement(ConsumerRequirement consumerRequirement) {
        this.consumerRequirement = consumerRequirement;
    }
    
    
    
}
