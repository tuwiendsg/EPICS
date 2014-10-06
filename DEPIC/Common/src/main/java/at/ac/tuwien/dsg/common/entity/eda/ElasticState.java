/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.eda;

import at.ac.tuwien.dsg.common.entity.qor.MetricRange;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ElasticState {
    
    String eStateID;
    List<MetricCondition> listOfConditions;

    public ElasticState() {
    }

    public ElasticState(String eStateID, List<MetricCondition> listOfConditions) {
        this.eStateID = eStateID;
        this.listOfConditions = listOfConditions;
    }

    public String geteStateID() {
        return eStateID;
    }

    public void seteStateID(String eStateID) {
        this.eStateID = eStateID;
    }

    public List<MetricCondition> getListOfConditions() {
        return listOfConditions;
    }

    public void setListOfConditions(List<MetricCondition> listOfConditions) {
        this.listOfConditions = listOfConditions;
    }

    
    
    
    
    
    
    
    
    
}
