/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.dataelasticitycapabilities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement
public class DataSourceValue {
    String sourceName;
    double cost;

    public DataSourceValue() {
    }

    
    
    public DataSourceValue(String sourceName, double cost) {
        this.sourceName = sourceName;
        this.cost = cost;
    }

    public String getSourceName() {
        return sourceName;
    }

    @XmlElement (name ="Name")
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public double getCost() {
        return cost;
    }

    @XmlElement (name ="Cost")
    public void setCost(double cost) {
        this.cost = cost;
    }
    
    
    
    
}
