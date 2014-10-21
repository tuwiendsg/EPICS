/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "dataAnalyticFunctionCep")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataAnalyticFunctionCep {
    
    @XmlElement(name = "dafInputCep", required = true)
    DafInputCep dafInputCep;
    
    @XmlElement(name = "dafOutputCep", required = true)
    DafOutputCep dafOutputCep;
    
    @XmlElement(name = "dafAnalyticCep", required = true)
    DafAnalyticCep dafAnalyticCep;
    
    @XmlElement(name = "analyticResultDelegate", required = true)
    AnalyticResultDelegate analyticResultDelegate;

    public DataAnalyticFunctionCep() {
    }

    public DataAnalyticFunctionCep(DafInputCep dafInputCep, DafOutputCep dafOutputCep, DafAnalyticCep dafAnalyticCep, AnalyticResultDelegate analyticResultDelegate) {
        this.dafInputCep = dafInputCep;
        this.dafOutputCep = dafOutputCep;
        this.dafAnalyticCep = dafAnalyticCep;
        this.analyticResultDelegate = analyticResultDelegate;
    }

    public DafInputCep getDafInputCep() {
        return dafInputCep;
    }

    public void setDafInputCep(DafInputCep dafInputCep) {
        this.dafInputCep = dafInputCep;
    }

    public DafOutputCep getDafOutputCep() {
        return dafOutputCep;
    }

    public void setDafOutputCep(DafOutputCep dafOutputCep) {
        this.dafOutputCep = dafOutputCep;
    }

    public DafAnalyticCep getDafAnalyticCep() {
        return dafAnalyticCep;
    }

    public void setDafAnalyticCep(DafAnalyticCep dafAnalyticCep) {
        this.dafAnalyticCep = dafAnalyticCep;
    }

    public AnalyticResultDelegate getAnalyticResultDelegate() {
        return analyticResultDelegate;
    }

    public void setAnalyticResultDelegate(AnalyticResultDelegate analyticResultDelegate) {
        this.analyticResultDelegate = analyticResultDelegate;
    }
      
}
