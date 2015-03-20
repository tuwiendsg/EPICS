/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.datasource;

import at.ac.tuwien.dsg.common.entity.eda.DataAssetFunction;
import at.ac.tuwien.dsg.common.entity.eda.EDaaSType;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataPartitionRequest;

/**
 *
 * @author Jun
 */
public interface DataLoader {

    public String loadDataAsset(DataAssetFunction dataAssetFunction);

    public String copyDataAssetRepo(DataPartitionRequest request);

    public String getNoOfParitionRepo(DataPartitionRequest request);

    public String getDataPartitionRepo(DataPartitionRequest request);


   
   
}
