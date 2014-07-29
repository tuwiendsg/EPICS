/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.demo;

import at.ac.tuwien.dsg.common.rest.QualityEnforcementREST;
import at.ac.tuwien.dsg.common.rest.QualityEvaluatorREST;
import at.ac.tuwien.dsg.orchestrator.responsetime.Monitor;
import at.ac.tuwien.dsg.orchestrator.responsetime.QoSEvaluator;
import at.ac.tuwien.dsg.orchestrator.services.DeliveryService;
import java.util.Date;

/**
 *
 * @author Jun
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        QoSEvaluator eval = new QoSEvaluator();
        
        int noOfDataObject = 100;
        int i = 0;

        String userID = "101";
        while (i < noOfDataObject) {
            
            eval.evaluate(i, userID);

            System.out.println("AVG Throughput: " + eval.getMonitoringData().getAverageTroughput());
            System.out.println("AVG response time: " + eval.getMonitoringData().getAverageResponseTime());

            i++;
        }

    }

}
