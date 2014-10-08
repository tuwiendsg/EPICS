/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.entity.pt;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "DataAnalytic")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataAnalytic {
    
    @XmlElement(name = "listOfEventPatterns", required = true)
    List<EventPattern> listOfEventPatterns;

    public DataAnalytic() {
    }

    public DataAnalytic(List<EventPattern> listOfEventPatterns) {
        this.listOfEventPatterns = listOfEventPatterns;
    }

    public List<EventPattern> getListOfEventPatterns() {
        return listOfEventPatterns;
    }

    public void setListOfEventPatterns(List<EventPattern> listOfEventPatterns) {
        this.listOfEventPatterns = listOfEventPatterns;
    }

    
    
    
    
}
