/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.service.engine;

import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.edasich.configuration.AnalyticEngineConfiguration;
import at.ac.tuwien.dsg.edasich.configuration.Configuration;
import at.ac.tuwien.dsg.edasich.entity.stream.DataAssetFunctionStreamingData;
import at.ac.tuwien.dsg.edasich.utils.JAXBUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class AnalyticController {
    // control analytics engine
    // control data stream
    // task distribution
    
    
    
    public AnalyticController(){
    }

    public void startAnalyticEngine(String analyticEngineID) {
        AnalyticEngineConfiguration aec = Configuration.getAnalyticEngineConfiguration(analyticEngineID);
        RestfulWSClient restClient = new RestfulWSClient(aec.getIp(), aec.getPort(), aec.getApi()+"/start");
        restClient.callRestfulWebService("");
    }
    
    public void stopAnalyticEngine(String analyticEngineID){
        AnalyticEngineConfiguration aec = Configuration.getAnalyticEngineConfiguration(analyticEngineID);
        RestfulWSClient restClient = new RestfulWSClient(aec.getIp(), aec.getPort(), aec.getApi()+"/stop");
        restClient.callRestfulWebService("");
    }
    
    public void submitDataAssetFunctionStreamingData(String analyticEngineID, DataAssetFunctionStreamingData daf) {
        
        try {
            AnalyticEngineConfiguration aec = Configuration.getAnalyticEngineConfiguration(analyticEngineID);
            RestfulWSClient restClient = new RestfulWSClient(aec.getIp(), aec.getPort(), aec.getApi()+"/dataassetfunction");
            String dafXML = JAXBUtils.marshal(daf, DataAssetFunctionStreamingData.class);
            restClient.callRestfulWebService(dafXML);
        } catch (JAXBException ex) {
       
        }    
    }
    
    
   
    
    
    
    
}
