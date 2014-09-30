/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.qor;

import java.util.List;

/**
 *
 * @author Jun
 */
public class QoRMetric {
    String name;
    String unit;
    List<Range> listOfRanges;

    public QoRMetric() {
        
    }

    public QoRMetric(String name, String unit, List<Range> listOfRanges) {
        this.name = name;
        this.unit = unit;
        this.listOfRanges = listOfRanges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Range> getListOfRanges() {
        return listOfRanges;
    }

    public void setListOfRanges(List<Range> listOfRanges) {
        this.listOfRanges = listOfRanges;
    }

    
    
    
    
}
