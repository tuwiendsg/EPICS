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
    MetricCondition condition;

    public ElasticState() {
    }

    public ElasticState(String eStateID, MetricCondition condition) {
        this.eStateID = eStateID;
        this.condition = condition;
    }
    

    public String geteStateID() {
        return eStateID;
    }

    public void seteStateID(String eStateID) {
        this.eStateID = eStateID;
    }

    public MetricCondition getCondition() {
        return condition;
    }

    public void setCondition(MetricCondition condition) {
        this.condition = condition;
    }

    
    
    
    
    
    
    
}
