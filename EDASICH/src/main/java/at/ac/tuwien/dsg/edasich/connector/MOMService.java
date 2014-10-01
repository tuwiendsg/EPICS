/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.connector;

import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.edasich.configuration.AnalyticEngineConfiguration;
import at.ac.tuwien.dsg.edasich.configuration.Configuration;
import at.ac.tuwien.dsg.edasich.utils.JAXBUtils;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */

public class MOMService {

    public MOMService() {
    }

    public static void startSensorService(Sensors sensors) {
        try {
            String daasIP = Configuration.getConfig("DAAS.IP");
            String daasPort = Configuration.getConfig("DAAS.PORT");
            String daasAPI = Configuration.getConfig("DAAS.SENSOR.START");
            
            RestfulWSClient restClient = new RestfulWSClient(daasIP, daasPort, daasAPI);      
            String sensorsXML = JAXBUtils.marshal(sensors, Sensors.class);
            
            restClient.callRestfulWebService(sensorsXML);
        } catch (JAXBException ex) {
            Logger.getLogger(MOMService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void stopSensorService(Sensors sensors) {
        try {
            String daasIP = Configuration.getConfig("DAAS.IP");
            String daasPort = Configuration.getConfig("DAAS.PORT");
            String daasAPI = Configuration.getConfig("DAAS.SENSOR.STOP");
            
            RestfulWSClient restClient = new RestfulWSClient(daasIP, daasPort, daasAPI);      
            String sensorsXML = JAXBUtils.marshal(sensors, Sensors.class);
            
            restClient.callRestfulWebService(sensorsXML);
        } catch (JAXBException ex) {
            Logger.getLogger(MOMService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 

}
