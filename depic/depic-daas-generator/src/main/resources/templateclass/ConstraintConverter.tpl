
package at.ac.tuwien.dsg.edaas.requirement.service;

import at.ac.tuwien.dsg.edaas.requirement.ConsumerRequirement;
import at.ac.tuwien.dsg.edaas.requirement.CostConstraint;
import at.ac.tuwien.dsg.edaas.requirement.TemplateConstraint;
import java.util.ArrayList;
import java.util.List;
#import_content#


public class ConstraintConverter {
    
    
    public List<TemplateConstraint> getListOfConstraints(ConsumerRequirement consumerRequirement){
        
        List<TemplateConstraint> listOfConstraints = new ArrayList<TemplateConstraint>();
        
        CostConstraint costConstraint =  consumerRequirement.getCostConstraint();
        TemplateConstraint costConstraint_t = new TemplateConstraint(costConstraint.getConstraintName(), costConstraint.getMinValue(), costConstraint.getMaxValue());
        listOfConstraints.add(costConstraint_t);
             
        #conversion_content#
        
        return listOfConstraints;
    }
    
}
