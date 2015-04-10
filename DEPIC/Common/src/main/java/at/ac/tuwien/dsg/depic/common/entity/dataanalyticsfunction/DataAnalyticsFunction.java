/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction;

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
public class DataAnalyticsFunction {
    
    @XmlElement(name = "name", required = true)
    String name;
    
    @XmlElement(name = "dataAssetForm", required = true)
    DataAssetForm dataAssetForm;
    
    @XmlElement(name = "daw", required = true)
    String daw;

    public DataAnalyticsFunction() {
    }

    public DataAnalyticsFunction(String name, DataAssetForm dataAssetForm, String daw) {
        this.name = name;
        this.dataAssetForm = dataAssetForm;
        this.daw = daw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataAssetForm getDataAssetForm() {
        return dataAssetForm;
    }

    public void setDataAssetForm(DataAssetForm dataAssetForm) {
        this.dataAssetForm = dataAssetForm;
    }

    public String getDaw() {
        return daw;
    }

    public void setDaw(String daw) {
        this.daw = daw;
    }
    
    
    
  
}
