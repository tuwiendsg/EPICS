/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity;

import java.util.List;

/**
 *
 * @author Jun
 */
public class QElement {
    String qElementID;
    List<MetricRange> listOfMetricRanges;
    double price;

    public QElement() {
    }

    public QElement(String qElementID, List<MetricRange> listOfMetricRanges, double price) {
        this.qElementID = qElementID;
        this.listOfMetricRanges = listOfMetricRanges;
        this.price = price;
    }

    public String getqElementID() {
        return qElementID;
    }

    
    public void setqElementID(String qElementID) {
        this.qElementID = qElementID;
    }

    public List<MetricRange> getListOfMetricRanges() {
        return listOfMetricRanges;
    }

    public void setListOfMetricRanges(List<MetricRange> listOfMetricRanges) {
        this.listOfMetricRanges = listOfMetricRanges;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
    
    
}
