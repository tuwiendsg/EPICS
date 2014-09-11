/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Jun
 */
public class QElementSet {
    List<QElement> listOfQElements;

    public QElementSet() {
    }

    public QElementSet(List<QElement> listOfQElements) {
        this.listOfQElements = listOfQElements;
    }

    public List<QElement> getListOfQElements() {
        return listOfQElements;
    }
    @XmlElement (name ="listOfQElements")
    public void setListOfQElements(List<QElement> listOfQElements) {
        this.listOfQElements = listOfQElements;
    }
    
    
    
}
