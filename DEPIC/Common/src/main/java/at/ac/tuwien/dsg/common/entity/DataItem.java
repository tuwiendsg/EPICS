/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Jun
 */

@XmlRootElement (name = "DataItem")
public class DataItem {
    
    int id;
    String value;

    public DataItem() {
    }

    public DataItem(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    @XmlElement (name ="ID")
    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    @XmlElement (name ="Value")
    public void setValue(String value) {
        this.value = value;
    }
    
    
    
    
    
  
}
