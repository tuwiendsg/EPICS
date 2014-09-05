/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity;

import java.util.List;

/**
 *
 * @author Jun
 */
public class DataElasticityMetric {
    String name;
    List<Range> listOfRanges;

    public DataElasticityMetric() {
        
    }

    public DataElasticityMetric(String name, List<Range> listOfRanges) {
        this.name = name;
        this.listOfRanges = listOfRanges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Range> getListOfRanges() {
        return listOfRanges;
    }

    public void setListOfRanges(List<Range> listOfRanges) {
        this.listOfRanges = listOfRanges;
    }
    
    
    
    
    
}
