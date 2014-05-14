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
public class NumericalElasticityBoundary {
    private double upperBoundary;
    private double lowerBoundary;

    public NumericalElasticityBoundary() {
    }

    
    
    
    public NumericalElasticityBoundary(double upperBoundary, double lowerBoundary) {
        this.upperBoundary = upperBoundary;
        this.lowerBoundary = lowerBoundary;
    }

    public double getUpperBoundary() {
        return upperBoundary;
    }

    @XmlElement
    public void setUpperBoundary(double upperBoundary) {
        this.upperBoundary = upperBoundary;
    }

    public double getLowerBoundary() {
        return lowerBoundary;
    }

    @XmlElement
    public void setLowerBoundary(double lowerBoundary) {
        this.lowerBoundary = lowerBoundary;
    }
    
    
    
    
}
