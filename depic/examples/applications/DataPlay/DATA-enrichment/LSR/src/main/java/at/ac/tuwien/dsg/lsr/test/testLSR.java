/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.lsr.test;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ExternalServiceRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.lsr.algorithm.service.LSRMonitoringService;
import at.ac.tuwien.dsg.lsr.algorithm.service.LSRService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class testLSR {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        DataPartitionRequest daRequest = new DataPartitionRequest("dataplay", "c01", "", "0");
        
        String dataRequestXML = "";
        try {
            dataRequestXML = JAXBUtils.marshal(daRequest, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(testLSR.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        LSRMonitoringService monitoringService = new LSRMonitoringService();

        double monitoringValue = monitoringService.requestMonitorAccuracyOfTrend(dataRequestXML);
        monitoringValue = Math.round(monitoringValue);
        
        System.out.println("Monitoring Result: " + monitoringValue);
        
//        List<Parameter> listOfParameter = new ArrayList<Parameter>();
//        ExternalServiceRequest controlRequest = new ExternalServiceRequest("dataplay", "c01", "dataplay", listOfParameter);
//        
//        LSRService lSRService = new LSRService();
//            lSRService.requestLSRService(controlRequest);
        
        
        
    }
    
}
