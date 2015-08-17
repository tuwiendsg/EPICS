/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.store;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;

import java.sql.ResultSet;


/**
 *
 * @author Jun
 */
public interface DataAssetStore {
  
    public void storeDataAsset(DataAsset da);

    public void updateDataAsset(String dataAssetID, String dataParitionID, String data);

    public <T> T getDataAssetByID(String dawID);
    
    public int getNumberOfPartitionsByDataAssetID(String dataAssetID);
    
    public String getDataPartition(String dataAssetID, String dataPartitionID);
    
    public void cleanTempStore();
}
