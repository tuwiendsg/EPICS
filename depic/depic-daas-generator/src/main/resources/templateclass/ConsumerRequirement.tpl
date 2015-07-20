
package at.ac.tuwien.dsg.edaas.requirement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "ConsumerRequirement")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsumerRequirement {
    
    @XmlElement(name = "costConstraint", required = true)
    CostConstraint costConstraint;
    
    #declaration_content#

    public ConsumerRequirement() {
    }

    public CostConstraint getCostConstraint() {
        return costConstraint;
    }

    public void setCostConstraint(CostConstraint costConstraint) {
        this.costConstraint = costConstraint;
    }

    #get_content#

    #set_content#
    

}
