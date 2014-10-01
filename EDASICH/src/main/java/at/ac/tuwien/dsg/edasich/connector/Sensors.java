/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.connector;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement(name = "EventPattern")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sensors {
    
    @XmlElement(name = "listOfSensors", required = true)
    List<String> listOfSensors;

    public Sensors() {
    }

    public Sensors(List<String> listOfSensors) {
        this.listOfSensors = listOfSensors;
    }

    public List<String> getListOfSensors() {
        return listOfSensors;
    }

    public void setListOfSensors(List<String> listOfSensors) {
        this.listOfSensors = listOfSensors;
    }
    
    
    
    
}
