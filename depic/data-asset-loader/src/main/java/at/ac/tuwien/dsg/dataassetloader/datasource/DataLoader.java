/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.datasource;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.entity.runtime.MonitoringSession;

/**
 *
 * @author Jun
 */
public interface DataLoader {

    public String loadDataAsset(DataAnalyticsFunction dataAssetFunction);

    public String copyDataAssetRepo(MonitoringSession monitoringSession, int dataAssetCounter);

    public String getNoOfParitionRepo(DataPartitionRequest request);

    public String getDataPartitionRepo(DataPartitionRequest request);


   
   
}
