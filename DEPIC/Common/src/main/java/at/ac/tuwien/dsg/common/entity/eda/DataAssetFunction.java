/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.eda;

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
public class DataAssetFunction {
    
    @XmlElement(name = "name", required = true)
    String name;
    
    @XmlElement(name = "type", required = true)
    String type;
    
    @XmlElement(name = "daw", required = true)
    String daw;

    public DataAssetFunction() {
    }

    public DataAssetFunction(String name, String type, String daw) {
        this.name = name;
        this.type = type;
        this.daw = daw;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDaw() {
        return daw;
    }

    public void setDaw(String daw) {
        this.daw = daw;
    }
    
    
    
  
}
