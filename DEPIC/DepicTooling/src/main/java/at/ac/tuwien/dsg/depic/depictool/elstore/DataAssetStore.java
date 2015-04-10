/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.elstore;

import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depic.depictool.util.Configuration;
import at.ac.tuwien.dsg.depic.common.utils.Logger;

/**
 *
 * @author Jun
 */
public class DataAssetStore {
    
    
    public String requestToGetDataAsset(String dataAssetID){
        
        Configuration configuration  = new Configuration();
        String ip = configuration.getConfig("DATA.ASSET.LOADER.IP");
        String port = configuration.getConfig("DATA.ASSET.LOADER.PORT");
        String resource = configuration.getConfig("DATA.ASSET.LOADER.RESOURCE.REQUEST");
      
        
        Logger.logInfo("IP: " + ip);
        Logger.logInfo("PORT: " + port);
        Logger.logInfo("RESOURSE: " + resource);
        
      //  IOUtils iou = new IOUtils();
      //  String dafXML = iou.readData(dataAssetID);
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        String noOfDataPartitions = ws.callPutMethod(dataAssetID+";c01");
   
        return noOfDataPartitions;
    }
    
    
    
}
