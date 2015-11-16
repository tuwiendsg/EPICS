/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.lsr.rest;

import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.entity.runtime.MonitoringMetric;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.lsr.algorithm.AccuracyMonitoringBasedMultiLinearRegression;
import at.ac.tuwien.dsg.lsr.algorithm.service.LSRMonitoringService;
import at.ac.tuwien.dsg.lsr.test.testLSR;
import at.ac.tuwien.dsg.lsr.util.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */

@Path("/monitor")
public class RestWSMonitor {
 
    
    
    @GET
    @Path("/dataasset")
    @Produces(MediaType.APPLICATION_XML)
    public String monitorDataAsset() {
        
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
        
         System.out.println("Monitoring Result - LSR: " + monitoringValue);
        String monitoringValueSTR = String.valueOf(monitoringValue);
        
        //MonitoringMetric monitoringMetric = new MonitoringMetric("accuracyOfGoldTrend", monitoringValue);
        
        return monitoringValueSTR;
               
    }
    
 
}
