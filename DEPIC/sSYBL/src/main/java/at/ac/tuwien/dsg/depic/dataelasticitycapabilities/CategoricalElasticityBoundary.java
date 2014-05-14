/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.dataelasticitycapabilities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement
public class CategoricalElasticityBoundary {
    private int upperBoundary;
    private int lowerBoundary;

    public CategoricalElasticityBoundary() {
    }

    
    
    public CategoricalElasticityBoundary(int upperBoundary, int lowerBoundary) {
        this.upperBoundary = upperBoundary;
        this.lowerBoundary = lowerBoundary;
    }

    public int getUpperBoundary() {
        return upperBoundary;
    }

    @XmlElement
    public void setUpperBoundary(int upperBoundary) {
        this.upperBoundary = upperBoundary;
    }

    public int getLowerBoundary() {
        return lowerBoundary;
    }

    @XmlElement
    public void setLowerBoundary(int lowerBoundary) {
        this.lowerBoundary = lowerBoundary;
    }
    
    
    
}
