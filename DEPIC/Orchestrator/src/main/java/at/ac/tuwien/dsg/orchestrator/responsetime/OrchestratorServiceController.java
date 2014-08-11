/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.responsetime;

import at.ac.tuwien.dsg.common.rest.QualityEnforcementREST;
import at.ac.tuwien.dsg.common.rest.QualityEvaluatorREST;
import at.ac.tuwien.dsg.orchestrator.properties.PropertiesConfiguration;
import at.ac.tuwien.dsg.orchestrator.properties.VMCluster;
import at.ac.tuwien.dsg.orchestrator.services.DeliveryService;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
public class OrchestratorServiceController {
    
    private Monitor monitor = new Monitor(10);
    private int noOfActiveClusters;

    public OrchestratorServiceController() {
    }

    
    
    
    
    
    
    
    public void evaluate(int i, String userID){
        
       
        updateNoOfAtiveCluster();
        
        
   
        
        String key = userID +";"+ String.valueOf(i);
        
    
            
            // reponseTime measurement start
            monitor.addOutstandingRequest(i, new Date());
            
            PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
            List<VMCluster> ls =  propertiesConfiguration.getProperties();
            
            VMCluster vmCluster = ls.get(0);
            // start quality evaluator
            System.out.println("Call Quality Evaluator " + vmCluster.getIp()+":" + vmCluster.getPort());
            QualityEvaluatorREST qualityEvaluatorREST = new QualityEvaluatorREST(vmCluster.getIp(),vmCluster.getPort());
            qualityEvaluatorREST.callQualityEvaluator(key);
             
          /*  
            try {
			//delay for one second
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
		}
            */
            
            
        
            // start delivery service
         //      DeliveryService deliveryService = new DeliveryService();
         //      String edoXML = deliveryService.getDeliveryEDO(key);
         //      System.out.println("EDO: " + key);
            
            // reponseTime measurement stop
               monitor.removeOutstandingRequest(i, new Date());
               
        
        
        
       
    }
    
    public void updateNoOfAtiveCluster(){
        
        
        
        
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


