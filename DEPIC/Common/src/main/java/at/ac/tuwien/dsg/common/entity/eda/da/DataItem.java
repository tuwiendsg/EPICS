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
public class DataItem {
    List<DataAttribute> listOfAttributes;

    public DataItem() {
    }

    public DataItem(List<DataAttribute> listOfAttributes) {
        this.listOfAttributes = listOfAttributes;
    }

    public List<DataAttribute> getListOfAttributes() {
        return listOfAttributes;
    }

    public void setListOfAttributes(List<DataAttribute> listOfAttributes) {
        this.listOfAttributes = listOfAttributes;
    }
    
    
}
