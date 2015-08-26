/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.eda.dataasset;

/**
 *
 * @author Jun
 */
public class DataItemCluster {
    
    int dataItemIndex;
    int clusterIndex;

    public DataItemCluster(int dataItemIndex, int clusterIndex) {
        this.dataItemIndex = dataItemIndex;
        this.clusterIndex = clusterIndex;
    }

    public int getDataItemIndex() {
        return dataItemIndex;
    }

    public void setDataItemIndex(int dataItemIndex) {
        this.dataItemIndex = dataItemIndex;
    }

    public int getClusterIndex() {
        return clusterIndex;
    }

    public void setClusterIndex(int clusterIndex) {
        this.clusterIndex = clusterIndex;
    }
    
    
    
}
