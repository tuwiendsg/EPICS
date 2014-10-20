/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.service.core.daf;


import at.ac.tuwien.dsg.edasich.entity.DataAssetFunction;
import at.ac.tuwien.dsg.edasich.entity.stream.DataAssetFunctionStreamingData;
import at.ac.tuwien.dsg.edasich.utils.JAXBUtils;


/**
 *
 * @author Jun
 */

public class DataAssetFunctionParser {
    
    public static DataAssetFunctionStreamingData loadDataAssetFunctionStreamingData(String daFunction) {
        
        DataAssetFunctionStreamingData dataAssetFunction =null;
                   
        try {
            dataAssetFunction = JAXBUtils.unmarshal(daFunction, DataAssetFunctionStreamingData.class);
        } catch (Exception e) {

        }
 
        return dataAssetFunction;
    }
    
  
    
    
}
