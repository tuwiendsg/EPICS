/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.service.engine;


import at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing.DataAnalyticFunctionCep;
import at.ac.tuwien.dsg.edasich.service.engine.ext.AnalyticEngineConfiguration;
import at.ac.tuwien.dsg.edasich.service.core.dafstore.DafStore;
import at.ac.tuwien.dsg.edasich.service.engine.ext.AnalyticEngineManager;
import at.ac.tuwien.dsg.edasich.utils.JAXBUtils;
import at.ac.tuwien.dsg.edasich.utils.RestfulWSClient;
import at.ac.tuwien.dsg.edasich.utils.TextParser;
import java.util.ArrayList;
import java.util.List;
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

    public void startAnalyticEngine(String analyticEngineID, String daf) {
        
        DafStore dafStore = new DafStore();
        dafStore.updateDAF(daf, "start");        

        AnalyticEngineConfiguration aec = AnalyticEngineManager.getAnalyticEngineConfiguration(analyticEngineID);
        RestfulWSClient restClient = new RestfulWSClient(aec.getIp(), aec.getPort(), aec.getApi()+"/start");
        restClient.callPutMethod(daf);
        
        
    }
    
    public void stopAnalyticEngine(String analyticEngineID, String daf){
        DafStore dafStore = new DafStore();
        System.out.println("Stop: " + daf);
        dafStore.updateDAF(daf, "stop");
        
       // AnalyticEngineConfiguration aec = Configuration.getAnalyticEngineConfiguration(analyticEngineID);
       // RestfulWSClient restClient = new RestfulWSClient(aec.getIp(), aec.getPort(), aec.getApi()+"/stop");
       // restClient.callPutMethod(daf);
    }
    
    public void submitDataAnalytic(String analyticEngineID, String dafXML) {

        AnalyticEngineConfiguration aec = AnalyticEngineManager.getAnalyticEngineConfiguration(analyticEngineID);
            RestfulWSClient restClient = new RestfulWSClient(aec.getIp(), aec.getPort(), aec.getApi()+"/daf");
            restClient.callPutMethod(dafXML);

    }
    
}
