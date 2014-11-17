/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edaas.requirement.service;

import at.ac.tuwien.dsg.edaas.requirement.ConsumerRequirement;
import at.ac.tuwien.dsg.edaas.requirement.CostConstraint;
import at.ac.tuwien.dsg.edaas.requirement.DataCompletenessConstraint;
import at.ac.tuwien.dsg.edaas.requirement.TemplateConstraint;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ConstraintConverter {
    
    
    public List<TemplateConstraint> getListOfConstraints(ConsumerRequirement consumerRequirement){
        
        List<TemplateConstraint> listOfConstraints = new ArrayList<TemplateConstraint>();
        
        CostConstraint c1 =  consumerRequirement.getCostConstraint();
        TemplateConstraint c1_t = new TemplateConstraint(c1.getConstraintName(), c1.getMinValue(), c1.getMaxValue());
        listOfConstraints.add(c1_t);
        
        DataCompletenessConstraint c2 =  consumerRequirement.getDataCompletenessConstraint();
        TemplateConstraint c2_t = new TemplateConstraint(c2.getConstraintName(), c2.getMinValue(), c2.getMaxValue());
        listOfConstraints.add(c2_t);
        
        CostConstraint c3 =  consumerRequirement.getCostConstraint();
        TemplateConstraint c3_t = new TemplateConstraint(c3.getConstraintName(), c3.getMinValue(), c3.getMaxValue());
        listOfConstraints.add(c3_t);
        
        
        
        return listOfConstraints;
    }
    
}
