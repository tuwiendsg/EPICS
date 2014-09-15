/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edaas.userrequirement;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement (name = "ConsumerRequirement")
public class ConsumerRequirement {
    CostConstraint costConstraint;
    DataCompletenessConstraint dataCompletenessConstraint;
    ThroughputConstraint throughputConstraint;

    public ConsumerRequirement() {
    }

    public ConsumerRequirement(CostConstraint costConstraint, DataCompletenessConstraint dataCompletenessConstraint, ThroughputConstraint throughputConstraint) {
        this.costConstraint = costConstraint;
        this.dataCompletenessConstraint = dataCompletenessConstraint;
        this.throughputConstraint = throughputConstraint;
    }

    public CostConstraint getCostConstraint() {
        return costConstraint;
    }

    @XmlElement (name ="CostConstraint")
    public void setCostConstraint(CostConstraint costConstraint) {
        this.costConstraint = costConstraint;
    }

    public DataCompletenessConstraint getDataCompletenessConstraint() {
        return dataCompletenessConstraint;
    }

    @XmlElement (name ="DataCompletenessConstraint")
    public void setDataCompletenessConstraint(DataCompletenessConstraint dataCompletenessConstraint) {
        this.dataCompletenessConstraint = dataCompletenessConstraint;
    }

    public ThroughputConstraint getThroughputConstraint() {
        return throughputConstraint;
    }

    @XmlElement (name ="ThroughputConstraint")
    public void setThroughputConstraint(ThroughputConstraint throughputConstraint) {
        this.throughputConstraint = throughputConstraint;
    }
    
    
    
    
}
