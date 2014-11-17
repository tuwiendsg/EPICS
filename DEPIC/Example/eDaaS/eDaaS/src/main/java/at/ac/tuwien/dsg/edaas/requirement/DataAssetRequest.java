/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edaas.requirement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "DataAssetRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataAssetRequest {
    
    @XmlElement(name = "dataAssetID", required = true)
    private String dataAssetID;
    
    @XmlElement(name = "consumerRequirement", required = true)
    private ConsumerRequirement consumerRequirement;

    public DataAssetRequest() {
    }

    public DataAssetRequest(String dataAssetID, ConsumerRequirement consumerRequirement) {
        this.dataAssetID = dataAssetID;
        this.consumerRequirement = consumerRequirement;
    }

    public String getDataAssetID() {
        return dataAssetID;
    }

    public void setDataAssetID(String dataAssetID) {
        this.dataAssetID = dataAssetID;
    }

    public ConsumerRequirement getConsumerRequirement() {
        return consumerRequirement;
    }

    public void setConsumerRequirement(ConsumerRequirement consumerRequirement) {
        this.consumerRequirement = consumerRequirement;
    }
    
    
    
    
}
