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
public class PricingScheme {
    List listOfPricingPlans;

    public PricingScheme() {
      
    }
    
    public PricingScheme(List listOfPricingPlans) {
        this.listOfPricingPlans = listOfPricingPlans;
    }

    public List getListOfPricingPlans() {
        return listOfPricingPlans;
    }

    public void setListOfPricingPlans(List listOfPricingPlans) {
        this.listOfPricingPlans = listOfPricingPlans;
    }
    
    
}
