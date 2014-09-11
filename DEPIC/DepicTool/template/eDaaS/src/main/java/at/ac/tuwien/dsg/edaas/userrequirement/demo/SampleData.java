/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edaas.userrequirement.demo;

import at.ac.tuwien.dsg.edaas.userrequirement.CostConstraint;
import at.ac.tuwien.dsg.edaas.userrequirement.DataCompletenessConstraint;
import at.ac.tuwien.dsg.edaas.userrequirement.ThroughputConstraint;
import at.ac.tuwien.dsg.edaas.userrequirement.ConsumerRequirement;

/**
 *
 * @author Jun
 */
public class SampleData {
    
    
    public void sampleConsumerRequirement(){
            
        CostConstraint constraint = new CostConstraint(0.5, 0.8);
        DataCompletenessConstraint dataCompletenessConstraint = new DataCompletenessConstraint(50, 100);
        ThroughputConstraint throughputConstraint = new ThroughputConstraint(1, 10);
        
        ConsumerRequirement consumerRequirement = new ConsumerRequirement(constraint, dataCompletenessConstraint, throughputConstraint);
        
        
        
        
    }
    
    
    
    
    
    
}
