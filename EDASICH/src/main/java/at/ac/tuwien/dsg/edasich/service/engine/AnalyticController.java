/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.service.engine;


import at.ac.tuwien.dsg.edasich.configuration.AnalyticEngineConfiguration;
import at.ac.tuwien.dsg.edasich.configuration.Configuration;
import at.ac.tuwien.dsg.edasich.configuration.MOMConfiguration;
import at.ac.tuwien.dsg.edasich.configuration.TaskDistributionConfiguration;
import at.ac.tuwien.dsg.edasich.connector.MOMService;
import at.ac.tuwien.dsg.edasich.connector.Sensors;
import at.ac.tuwien.dsg.edasich.entity.stream.DataAssetFunctionStreamingData;
import at.ac.tuwien.dsg.edasich.entity.stream.EventPattern;
import at.ac.tuwien.dsg.edasich.utils.EventLog;
import at.ac.tuwien.dsg.edasich.utils.JAXBUtils;
import at.ac.tuwien.dsg.edasich.utils.RestfulWSClient;
import at.ac.tuwien.dsg.edasich.utils.TextParser;
import java.util.ArrayList;
import java.util.List;
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

    public void startAnalyticEngine(String analyticEngineID, String daf) {
        AnalyticEngineConfiguration aec = Configuration.getAnalyticEngineConfiguration(analyticEngineID);
        RestfulWSClient restClient = new RestfulWSClient(aec.getIp(), aec.getPort(), aec.getApi()+"/start");
        restClient.callPutMethod(daf);
        EventLog el = new EventLog();
        el.updateDAF(daf, "start");
        
    }
    
    public void stopAnalyticEngine(String analyticEngineID, String daf){
        EventLog el = new EventLog();
        System.out.println("Stop: " + daf);
        el.updateDAF(daf, "stop");
        
       // AnalyticEngineConfiguration aec = Configuration.getAnalyticEngineConfiguration(analyticEngineID);
       // RestfulWSClient restClient = new RestfulWSClient(aec.getIp(), aec.getPort(), aec.getApi()+"/stop");
       // restClient.callPutMethod(daf);
    }
    
    public void submitDataAssetFunctionStreamingData(String analyticEngineID, DataAssetFunctionStreamingData daf) {
        
        try {
            AnalyticEngineConfiguration aec = Configuration.getAnalyticEngineConfiguration(analyticEngineID);
            RestfulWSClient restClient = new RestfulWSClient(aec.getIp(), aec.getPort(), aec.getApi()+"/dataassetfunction");
            String dafXML = JAXBUtils.marshal(daf, DataAssetFunctionStreamingData.class);
            restClient.callPutMethod(dafXML);
        } catch (JAXBException ex) {
       
        }    
    }
    
    
    
    public void startSensors(DataAssetFunctionStreamingData df) {
        List<EventPattern> listOfEventPatterns = df.getListOfEventPatterns();
        List<String> listOfSensors = new ArrayList<>();
        for (EventPattern eventPattern : listOfEventPatterns) {
            String statement = eventPattern.getStatement();
            List<String> listOfSensorTemp = TextParser.parseSensorName(statement);
            for (String sensor : listOfSensorTemp) {
                if (!listOfSensors.contains(sensor)){
                    listOfSensors.add(sensor);
                }
            }
        }
     
       Sensors sensors = new Sensors(listOfSensors);
       MOMService.startSensorService(sensors);

    }
    
    
    public void stopSensors(DataAssetFunctionStreamingData df) {
        List<EventPattern> listOfEventPatterns = df.getListOfEventPatterns();
        List<String> listOfSensors = new ArrayList<>();
        for (EventPattern eventPattern : listOfEventPatterns) {
            String statement = eventPattern.getStatement();
            List<String> listOfSensorTemp = TextParser.parseSensorName(statement);
            for (String sensor : listOfSensorTemp) {
                if (!listOfSensors.contains(sensor)){
                    listOfSensors.add(sensor);
                }
            }
        }
     
       Sensors sensors = new Sensors(listOfSensors);
       MOMService.stopSensorService(sensors);

    }


   
    
    
    
    
}
