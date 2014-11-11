/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataassetloader.datasource;

import at.ac.tuwien.dsg.common.entity.eda.DataAssetFunction;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.dataassetloader.configuration.Configuration;
import at.ac.tuwien.dsg.dataassetloader.dastore.DataAssetStore;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class DataLoader {
    
    
    public void loadDataAsset(String dataAssetFunctionStr){
        
        try {
            DataAssetFunction daf = JAXBUtils.unmarshal(dataAssetFunctionStr, DataAssetFunction.class);
            String log = "DAF Name: " + daf.getName();
            Logger.getLogger(DataLoader.class.getName()).log(Level.INFO,log);
            
 
            String daw = daf.getDaw();
           
            Configuration config = new Configuration();
            String ip = config.getConfig("DAF.MANAGEMENT.IP");
            String port = config.getConfig("DAF.MANAGEMENT.PORT");
            String resource = config.getConfig("DAF.MANAGEMENT.RESOURCE");
            
            RestfulWSClient rs = new RestfulWSClient(ip, port, resource);
            String dataAssetXml = rs.callPutMethod(daw);
          
           
            DataAsset da = JAXBUtils.unmarshal(dataAssetXml, DataAsset.class);
            da.setName(daf.getName());
            
            dataAssetXml = JAXBUtils.marshal(da, DataAsset.class);
            
            
            System.out.println(dataAssetXml);
            
            //DataAsset da = JAXBUtils.unmarshal(dataAssetXml, DataAsset.class);
            
            
            DataAssetStore das = new DataAssetStore();
            
            das.saveDataAsset(dataAssetXml, daf.getName());
            
            
        } catch (JAXBException ex) {
            Logger.getLogger(DataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    } 
 
}
