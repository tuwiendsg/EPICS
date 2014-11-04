/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.eda.da;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "DataAsset")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataAsset {
    
    @XmlElement(name = "listOfDataItems", required = true)
    List<DataItem> listOfDataItems;

    public DataAsset() {
    }

    public DataAsset(List<DataItem> listOfDataItems) {
        this.listOfDataItems = listOfDataItems;
    }

    public List<DataItem> getListOfDataItems() {
        return listOfDataItems;
    }

    public void setListOfDataItems(List<DataItem> listOfDataItems) {
        this.listOfDataItems = listOfDataItems;
    }
    
    
    
    
}
