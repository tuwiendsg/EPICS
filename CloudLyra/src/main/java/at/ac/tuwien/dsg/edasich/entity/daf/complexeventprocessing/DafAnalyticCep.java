/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing;

import at.ac.tuwien.dsg.edasich.entity.daf.DafAnalytic;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "dafAnalyticCep")
@XmlAccessorType(XmlAccessType.FIELD)
public class DafAnalyticCep  {
    
    @XmlElement(name = "eplStatement", required = true)
    String eplStatement;
    
    @XmlElement(name = "enrichmentURI", required = true)
    String enrichmentURI;

    public DafAnalyticCep() {
    }

    public DafAnalyticCep(String eplStatement, String enrichmentURI) {
        this.eplStatement = eplStatement;
        this.enrichmentURI = enrichmentURI;
    }

    public String getEplStatement() {
        return eplStatement;
    }

    public void setEplStatement(String eplStatement) {
        this.eplStatement = eplStatement;
    }

    public String getEnrichmentURI() {
        return enrichmentURI;
    }

    public void setEnrichmentURI(String enrichmentURI) {
        this.enrichmentURI = enrichmentURI;
    }

    
    
}
