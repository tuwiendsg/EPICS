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
@XmlRootElement (name = "ElasticDataAsset")
public class ElasticDataAsset {
    DataAsset da;
    QoR qor;

    public ElasticDataAsset() {
    }

    public ElasticDataAsset(DataAsset da, QoR qor) {
        this.da = da;
        this.qor = qor;
    }

    public DataAsset getDa() {
        return da;
    }

    @XmlElement (name ="da")
    public void setDa(DataAsset da) {
        this.da = da;
    }

    public QoR getQor() {
        return qor;
    }

    @XmlElement (name ="qor")
    public void setQor(QoR qor) {
        this.qor = qor;
    }
    
    
    
  
}
