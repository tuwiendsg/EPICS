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
public class ElasticState {
    
    String eStateID;
    List<String> listOfMetricRanges;
    double price;

    public ElasticState() {
    }

    public ElasticState(String eStateID, List<String> listOfMetricRanges, double price) {
        this.eStateID = eStateID;
        this.listOfMetricRanges = listOfMetricRanges;
        this.price = price;
    }

    public String geteStateID() {
        return eStateID;
    }

    public void seteStateID(String eStateID) {
        this.eStateID = eStateID;
    }

    public List<String> getListOfMetricRanges() {
        return listOfMetricRanges;
    }

    public void setListOfMetricRanges(List<String> listOfMetricRanges) {
        this.listOfMetricRanges = listOfMetricRanges;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
    
    
    
    
}
