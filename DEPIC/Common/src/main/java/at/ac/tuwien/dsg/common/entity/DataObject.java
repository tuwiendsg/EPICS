/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity;


import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author Jun
 */
@XmlSeeAlso({DataItem.class})
@XmlRootElement (name = "DataObject")
public class DataObject {
    List<DataItem> listOfDataItems;

    public DataObject() {
    }

    public DataObject(List<DataItem> listOfDataItems) {
        this.listOfDataItems = listOfDataItems;
    }

    public List<DataItem> getListOfDataItems() {
        return listOfDataItems;
    }

    @XmlElement (name ="ListOfDataItems")
    public void setListOfDataItems(List<DataItem> listOfDataItems) {
        this.listOfDataItems = listOfDataItems;
    }
    
}
