/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement (name = "EDARequest")
public class EDARequest {
    String assetName;
    String qElementID;

    public EDARequest() {
    }

    public EDARequest(String assetName, String qElementID) {
        this.assetName = assetName;
        this.qElementID = qElementID;
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
    
    
    
    
}
