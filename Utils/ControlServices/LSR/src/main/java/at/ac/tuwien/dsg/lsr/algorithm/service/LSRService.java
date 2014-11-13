/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.lsr.algorithm.service;

import at.ac.tuwien.dsg.common.deployment.ControlParam;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.process.Parameter;
import at.ac.tuwien.dsg.externalserviceutils.DataAssetStore;
import at.ac.tuwien.dsg.lsr.algorithm.LinearRegression;
import java.util.List;



/**
 *
 * @author Jun
 */
public class LSRService {

    public LSRService() {
    }
    
    
    public void requestLSRService(ControlParam controlParam){
        
        DataAssetStore das = new DataAssetStore();
        DataAsset da = das.getDataAsset(controlParam.getDataAssetID());
        
        List<Parameter> listOfParameters = controlParam.getListOfParameters();
        
        double datacompleteness = 0;
        String[] attributeIndice = null;        
        
        for (Parameter parameter : listOfParameters) {
            
            if (parameter.getParaName().equals("expectedDataCompleteness")) {
                datacompleteness = Double.parseDouble(parameter.getValue());
            }
            
            if (parameter.getParaName().equals("attributeIndice")){
                String params = parameter.getValue();
                attributeIndice = params.split(";");
                
            }
        }
        
        LinearRegression linearRegression = new LinearRegression();
        DataAsset dataAsset= linearRegression.improveDataCompleteness(da, datacompleteness, attributeIndice);
        das.storeDataAsset(dataAsset);
        
        
        
    }
    
}
