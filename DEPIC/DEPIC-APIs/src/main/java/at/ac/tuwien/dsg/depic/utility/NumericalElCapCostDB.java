/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.utility;

import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.CategoricalElasticityValue;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.NumericalElasticityValue;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement (name = "ElasticityCapabilities")
public class NumericalElCapCostDB {
    
    private List<NumericalElasticityValue> elasticityValues;
    private String name;
    private double lowerBound;
    private double upperBound;

    public NumericalElCapCostDB() {
        elasticityValues = new ArrayList<NumericalElasticityValue>();
    }
    
    public void addNewValue(NumericalElasticityValue elVal){
        elasticityValues.add(elVal);
        
    }

    public List<NumericalElasticityValue> getElasticityValues() {
        return elasticityValues;
    }

    @XmlElement  (name ="ElasticityCapability")
    public void setElasticityValues(List<NumericalElasticityValue> elasticityValues) {
        this.elasticityValues = elasticityValues;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute (name = "capabilityName")
    public void setName(String name) {
        this.name = name;
    }

    public double getLowerBound() {
        return lowerBound;
    }

    @XmlAttribute (name = "lowerBound")
    public void setLowerBound(double lowerBound) {
        this.lowerBound = lowerBound;
    }

    public double getUpperBound() {
        return upperBound;
    }

    @XmlAttribute (name = "upperBound")
    public void setUpperBound(double upperBound) {
        this.upperBound = upperBound;
    }
    
    
    
    
}
