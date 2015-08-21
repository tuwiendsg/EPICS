/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetloader.datasource;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class DataAssetRepositoryMetadata {
    
    public static List<DataAssetQueue> listOfDataAssetQueues;

    
    
    public static void addDataAssetQueue(DataAssetQueue dataAssetQueue){
        
        
        if (listOfDataAssetQueues==null){
            listOfDataAssetQueues = new ArrayList<DataAssetQueue>();
        }
        
        listOfDataAssetQueues.add(dataAssetQueue);
       
    }
    
    
    public static DataAssetQueue getDataAssetQueueFromEDaaSName(String eDaaSName) {
        
        if (listOfDataAssetQueues==null){
            listOfDataAssetQueues = new ArrayList<DataAssetQueue>();
        }
        
        DataAssetQueue foundDataAssetQueue = null;
        
        for (DataAssetQueue dataAssetQueue : listOfDataAssetQueues){
         
            if (dataAssetQueue.geteDaaS().equals(eDaaSName)){
                foundDataAssetQueue = dataAssetQueue;
            }
        }
        
        
        return foundDataAssetQueue;
    }

    
    public static DataAssetQueue getDataAssetQueueFromDataAssetID(String dataAssetID) {
        
        if (listOfDataAssetQueues==null){
            listOfDataAssetQueues = new ArrayList<DataAssetQueue>();
        }
        
        DataAssetQueue foundDataAssetQueue = null;
        
        for (DataAssetQueue dataAssetQueue : listOfDataAssetQueues){
         
            if (dataAssetQueue.getDataAssetID().equals(dataAssetID)){
                foundDataAssetQueue = dataAssetQueue;
            }
        }
        
        
        return foundDataAssetQueue;
    }
    
    
    
}
