/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity;

import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement (name = "DataAsset")
public class DataAsset {
    
    List<DataItem> listOfDataItems;

    public DataAsset(List<DataItem> listOfDataItems) {
        this.listOfDataItems = listOfDataItems;
    }

    public List<DataItem> getListOfDataItems() {
        return listOfDataItems;
    }

    @XmlElement (name ="listOfDataItems")
    public void setListOfDataItems(List<DataItem> listOfDataItems) {
        this.listOfDataItems = listOfDataItems;
    }
    
    
    
    
}
