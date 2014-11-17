/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edaas.requirement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 *
 * @author Jun
 */

@XmlRootElement(name = "ConsumerRequirement")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsumerRequirement {
    
    @XmlElement(name = "costConstraint", required = true)
    CostConstraint costConstraint;
    
    @XmlElement(name = "dataCompletenessConstraint", required = true)
    DataCompletenessConstraint dataCompletenessConstraint;
    
    @XmlElement(name = "throughputConstraint", required = true)
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

    public void setCostConstraint(CostConstraint costConstraint) {
        this.costConstraint = costConstraint;
    }

    public DataCompletenessConstraint getDataCompletenessConstraint() {
        return dataCompletenessConstraint;
    }

    public void setDataCompletenessConstraint(DataCompletenessConstraint dataCompletenessConstraint) {
        this.dataCompletenessConstraint = dataCompletenessConstraint;
    }

    public ThroughputConstraint getThroughputConstraint() {
        return throughputConstraint;
    }

    public void setThroughputConstraint(ThroughputConstraint throughputConstraint) {
        this.throughputConstraint = throughputConstraint;
    }

    
    
    
    
}
