/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.utility;

import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.CategoricalElasticityValue;
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
public class CategoricalElCapCostDB {
    private List<CategoricalElasticityValue> elasticityValues;
    private String name;
    private int lowerBound;
    private int upperBound;

    public CategoricalElCapCostDB() {
        elasticityValues = new ArrayList<CategoricalElasticityValue>();
    }

    public List<CategoricalElasticityValue> getElasticityValues() {
        return elasticityValues;
    }
    
    public void addNewValue(CategoricalElasticityValue elVal){
        elasticityValues.add(elVal);
        
    }

    @XmlElement  (name ="ElasticityCapability")
    public void setElasticityValues(List<CategoricalElasticityValue> elasticityValues) {
        this.elasticityValues = elasticityValues;
    }

    public String getName() {
        return name;
    }

    @XmlAttribute (name = "capabilityName")
    public void setName(String name) {
        this.name = name;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    @XmlAttribute (name = "lowerBound")
    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    @XmlAttribute (name = "upperBound")
    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }
    
    
    
    
}
