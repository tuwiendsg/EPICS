/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qualityevaluator.edo;

import at.ac.tuwien.dsg.common.entity.DataObject;
import at.ac.tuwien.dsg.common.entity.others.EDORepo;
import at.ac.tuwien.dsg.common.entity.others.EDORepoJAXB;
import at.ac.tuwien.dsg.common.jaxb.DataObjectJAXB;
import at.ac.tuwien.dsg.qualityevaluator.datacompleteness.DataCompletenessMeasurement;
import at.ac.tuwien.dsg.qualityevaluator.datacompleteness.ResponseTimeMeasurement;
import at.ac.tuwien.dsg.qualityevaluator.restclient.DataObjectRequest;
import at.ac.tuwien.dsg.qualityevaluator.restclient.EDORepoREST;
import java.rmi.server.ObjID;

/**
 *
 * @author Jun
 */
public class ElasticDataObjectConstructor {
    
    public void constructEDO() {
        
        
        // get Data Object From Data Access Management
        DataObjectRequest request = new DataObjectRequest();
        String dataObjectStr = request.getDataObjectString();
        DataObjectJAXB dataObjectJAXB = new DataObjectJAXB();
        DataObject dataObject = dataObjectJAXB.unmarshallingObject(dataObjectStr);
        
        
        // evaluate data completeness
        DataCompletenessMeasurement measurement = new DataCompletenessMeasurement();
        double dataCompleteness = measurement.evaluateDataCompleteness(dataObject);
        
        System.out.println("Measurement: " + dataCompleteness);
        
        
        
        // evaluate response time
        ResponseTimeMeasurement responseTimeMeasurement = new ResponseTimeMeasurement();
        double responseTime =responseTimeMeasurement.measureResponseTime();
        System.out.println("Measurement: " + dataCompleteness);
        
        
        // Save EDO
        int objID=1;
        int userID=110;
        
        EDORepo edo = new EDORepo(objID, userID, dataObjectStr, dataCompleteness, responseTime);
        EDORepoJAXB jAXB = new EDORepoJAXB();
        String edoStr = jAXB.marshallingObject(edo);
        EDORepoREST rest = new EDORepoREST();
        rest.saveEDO(edoStr);
        
        
        
        
    }
    
    
    
    
}
