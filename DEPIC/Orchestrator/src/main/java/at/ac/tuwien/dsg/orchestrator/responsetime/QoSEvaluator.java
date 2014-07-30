/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.responsetime;

import at.ac.tuwien.dsg.common.rest.QualityEnforcementREST;
import at.ac.tuwien.dsg.common.rest.QualityEvaluatorREST;
import at.ac.tuwien.dsg.orchestrator.services.DeliveryService;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
public class QoSEvaluator {
    
    private Monitor monitor = new Monitor(10);
    
    public void evaluate(int i, String userID){
        
       
        
        
   
        
        String key = userID +";"+ String.valueOf(i);
        
    
            
            // reponseTime measurement start
            monitor.addOutstandingRequest(i, new Date());
            
            // start quality evaluator
            QualityEvaluatorREST qualityEvaluatorREST = new QualityEvaluatorREST();
            qualityEvaluatorREST.callQualityEvaluator(key);
             
            // start quality enforcement
       //     QualityEnforcementREST qualityEnforcementREST = new QualityEnforcementREST();
         //   qualityEnforcementREST.putXml(key);
        
            // start delivery service
         //      DeliveryService deliveryService = new DeliveryService();
         //      String edoXML = deliveryService.getDeliveryEDO(key);
         //      System.out.println("EDO: " + key);
            
            // reponseTime measurement stop
               monitor.removeOutstandingRequest(i, new Date());
               
        
        
        
       
    }
    
    
    
    
    
    public MonitoringData getMonitoringData() {
        Number[] l = monitor.getMonitoringData();
        return new MonitoringData(l[0].longValue(), l[1].longValue(), l[2].intValue());
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "MonitoringData")
    public static class MonitoringData {

        @XmlElement(name = "responseTime", required = true)
        private Long averageResponseTime;

        @XmlElement(name = "throughput", required = true)
        private Long averageTroughput;

        @XmlElement(name = "pendingRequests", required = true)
        private Integer pendingRequests;

        public MonitoringData() {
        }

        public MonitoringData(Long averageResponseTime, Long averageTroughput, Integer pendingRequests) {
            this.averageResponseTime = averageResponseTime;
            this.averageTroughput = averageTroughput;
            this.pendingRequests = pendingRequests;
        }

        public Long getAverageResponseTime() {
            return averageResponseTime;
        }

        public void setAverageResponseTime(Long averageResponseTime) {
            this.averageResponseTime = averageResponseTime;
        }

        public Long getAverageTroughput() {
            return averageTroughput;
        }

        public void setAverageTroughput(Long averageTroughput) {
            this.averageTroughput = averageTroughput;
        }

        public Integer getPendingRequests() {
            return pendingRequests;
        }

        public void setPendingRequests(Integer pendingRequests) {
            this.pendingRequests = pendingRequests;
        }

    }
}


