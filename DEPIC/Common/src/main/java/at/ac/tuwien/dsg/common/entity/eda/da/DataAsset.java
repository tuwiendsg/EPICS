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
    
    @XmlElement(name = "name", required = true)
    String name;
    
    @XmlElement(name = "partition", required = true)
    int partition;

    @XmlElement(name = "listOfDataItems", required = true)
    List<DataItem> listOfDataItems;

    public DataAsset() {
    }

    public DataAsset(String name, int partition, List<DataItem> listOfDataItems) {
        this.name = name;
        this.partition = partition;
        this.listOfDataItems = listOfDataItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public List<DataItem> getListOfDataItems() {
        return listOfDataItems;
    }

    public void setListOfDataItems(List<DataItem> listOfDataItems) {
        this.listOfDataItems = listOfDataItems;
    }

    
}
