/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.tooling.edo.metric;

/**
 *
 * @author Jun
 */
public class PricingPlan {
    String rangeID;
    Double price;

    public PricingPlan() {
    }

    public PricingPlan(String rangeID, Double price) {
        this.rangeID = rangeID;
        this.price = price;
    }

    public String getRangeID() {
        return rangeID;
    }

    public void setRangeID(String rangeID) {
        this.rangeID = rangeID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    
    
    
}
