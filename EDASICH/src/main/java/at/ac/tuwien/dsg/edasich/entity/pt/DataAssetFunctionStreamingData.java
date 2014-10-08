/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.entity.pt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "DataAssetFunction")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataAssetFunctionStreamingData {
    
    @XmlElement(name = "name", required = true)
    String name;
    
    @XmlElement(name = "sensors", required = true)
    String sensors;

    public DataAssetFunctionStreamingData() {
    }

    public DataAssetFunctionStreamingData(String name, String sensors) {
        this.name = name;
        this.sensors = sensors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSensors() {
        return sensors;
    }

    public void setSensors(String sensors) {
        this.sensors = sensors;
    }
    
    
    
    
}
