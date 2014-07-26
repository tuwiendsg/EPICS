/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.common.dataaccess;

import at.ac.tuwien.dsg.depic.common.entity.DataItem;
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
    List listOfDataItems;

    public DataObject() {
    }

    public DataObject(List listOfDataItems) {
        this.listOfDataItems = listOfDataItems;
    }

    public List getListOfDataItems() {
        return listOfDataItems;
    }

    @XmlElement (name ="ListOfDataItems")
    public void setListOfDataItems(List listOfDataItems) {
        this.listOfDataItems = listOfDataItems;
    }
    
}
