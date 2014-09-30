/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.eda.da;

import java.util.List;

/**
 *
 * @author Jun
 */
public class DataAsset {
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
