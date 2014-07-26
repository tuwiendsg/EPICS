/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity;

import java.util.List;

/**
 *
 * @author Jun
 */
public class ElasticDataObject {
    ElasticState elasticState;
    List<DataItem> dataItemList;

    public ElasticDataObject() {
    }

    public ElasticDataObject(ElasticState elasticState, List<DataItem> dataItemList) {
        this.elasticState = elasticState;
        this.dataItemList = dataItemList;
    }

    public ElasticState getElasticState() {
        return elasticState;
    }

    public void setElasticState(ElasticState elasticState) {
        this.elasticState = elasticState;
    }

    public List<DataItem> getDataItemList() {
        return dataItemList;
    }

    public void setDataItemList(List<DataItem> dataItemList) {
        this.dataItemList = dataItemList;
    }

    
    
    
    
}
