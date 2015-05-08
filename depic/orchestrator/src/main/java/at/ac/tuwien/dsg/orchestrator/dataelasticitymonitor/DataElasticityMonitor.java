/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.dataelasticitymonitor;


import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticState;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticStateSet;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MetricCondition;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.AdjustmentProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticProcess;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.MonitoringProcess;
import at.ac.tuwien.dsg.depic.common.entity.runtime.MonitoringSession;

import at.ac.tuwien.dsg.depic.common.entity.runtime.MonitoringMetric;

import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.Logger;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.orchestrator.configuration.Configuration;
import at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller.DataElasticityController;
import at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore.ElasticityProcessesStore;

import at.ac.tuwien.dsg.orchestrator.registry.ElasticServiceRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.xml.bind.JAXBException;
import sun.java2d.pipe.BufferedMaskBlit;

/**
 *
 * @author Jun
 */
public class DataElasticityMonitor{

    List<MonitoringMetric> listOfMonitoringMetrics;
    List<ElasticState> listOfElasticStates;
    List<ElasticState> listOfExpectedElasticStates;
    MonitoringSession monitoringSession;
    MonitoringProcess monitorProcess;
    List<AdjustmentProcess> listOfControlProcesses;
    DBType eDaaSType;
 

    public DataElasticityMonitor(MonitoringSession monitoringSession) {
        this.monitoringSession = monitoringSession;
        listOfMonitoringMetrics = new ArrayList<MonitoringMetric>();
        config();
    }
     

    public void startMonitoringService() {
        List<MonitoringAction> listOfMonitoringActions = null; // monitorProcess.getListOfMonitorActions();
        
            Logger.logInfo("Execute Monitoring Process ...");
            
              long t1 = System.currentTimeMillis();
            
            for (MonitoringAction monitorAction : listOfMonitoringActions) {
                
                String monitoringServiceID = monitorAction.getMonitorActionID();
      
                Logger.logInfo("Get monitoring service info: " + monitoringServiceID);

                String uri = "";

                do {

                    uri = ElasticServiceRegistry.getElasticServiceURI(monitoringServiceID, eDaaSType);
                    if (uri.equals("")) {
                        Logger.logInfo("Waiting_for_Active_Elastic_Serivce ... " + monitoringSession.getSessionID() +" - " +monitoringServiceID);
                    } else {
                        Logger.logInfo("Ready_Service: " + monitoringSession.getSessionID() +" - " + uri);
                        ElasticServiceRegistry.occupyElasticService(uri);
                    }
                    
                    
                    
                    
                    try {
                        Thread.sleep(10000);

                    } catch (InterruptedException ex) {

                    }
                    
                    
                    

                } while (uri.equals(""));

                

                Logger.logInfo("Run Monitoring Service ID: " + monitoringServiceID);
                Logger.logInfo("URI: " + uri);

                DataPartitionRequest request = new DataPartitionRequest(monitoringSession.getEdaasName(), monitoringSession.getSessionID(), monitoringSession.getDataAssetID(), "");
                
                String requestXML = "";
                
            try {
                requestXML = JAXBUtils.marshal(request, DataPartitionRequest.class);
            } catch (JAXBException ex) {
              
            }

            double monitoringValue = 0;

            String metricName = getMonitoringMetricName(monitoringServiceID);

            RestfulWSClient ws = new RestfulWSClient(uri);
            monitoringValue = Double.parseDouble(ws.callPutMethod(requestXML));

            MonitoringMetric monitoringMetric = new MonitoringMetric(metricName, monitoringValue);
            listOfMonitoringMetrics.add(monitoringMetric);
            ElasticServiceRegistry.releaseElasticService(uri);
            

        }

        long t2 = System.currentTimeMillis();
        Logger.logInfo("MONITORING RESULT: "+monitoringSession.getSessionID()+" \n");
        String log = "MONITORING RESULT: "+monitoringSession.getSessionID()+" \n";
        
        for (MonitoringMetric monitoringMetric : listOfMonitoringMetrics) {

            Logger.logInfo("Metric: " + monitoringMetric.getMetricName() + " - Value: " + monitoringMetric.getMetricValue());
            log = log + "Metric: " + monitoringMetric.getMetricName() + " - Value: " + monitoringMetric.getMetricValue() + "\n";
            
        }

        ElasticState currentElasticState = determineCurrentElasticState();
        Logger.logInfo("Current Elastic State ...");
        log = log + "Current Elastic State ..." + currentElasticState.geteStateID() +"\n";
        
        
        logElasticState(currentElasticState);
        Logger.logInfo("");
        if (!isExpectedElasticState(currentElasticState)) {
            Logger.logInfo("FAIL VALIDATION");
            log = log + "FAIL VALIDATION" + "\n";
            
            
            DataElasticityController controller = new DataElasticityController(listOfExpectedElasticStates, listOfControlProcesses, monitoringSession, eDaaSType);
            controller.startControlElasticState(currentElasticState);
        } else {
            Logger.logInfo("PASS VALIDATION");
            log = log + "PASS VALIDATION" + "\n";
        }
        Logger.logInfo("MONITORING_RUNTIME: " + (t2 - t1));
        log =  log +"MONITORING_RUNTIME: " + (t2 - t1) + "\n";
        
        
        try {
           
            
            IOUtils iou = new IOUtils("/home/ubuntu/log");
            iou.writeData(log, "depic_monitor.xml");
            
            System.out.println("\n" + log);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DataElasticityMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    
    private ElasticState determineCurrentElasticState(){
        ElasticState currentElasticState = null;
        Logger.logInfo("Determine Current ElasticState");
        Logger.logInfo("NoOf EStates: " + listOfElasticStates.size());
        
        for (ElasticState elasticState : listOfElasticStates) {
            
            List<MetricCondition> conditions = elasticState.getListOfConditions();
            boolean rs = true;
            for (MetricCondition condition: conditions) {
                String metricName  =condition.getMetricName();
                double metricValue = findMetricValue(metricName);
                
                Logger.logInfo("Metric: " + metricName +" - Value: " + metricValue);
                Logger.logInfo("Lower Bound: " + condition.getLowerBound() + " - upper bound: " + condition.getUpperBound() );
                
                if (!(metricValue>=condition.getLowerBound() && metricValue<=condition.getUpperBound())){
                    rs = false;
                }
    
            }   
            
            if (rs){
                currentElasticState = elasticState;
                break;
            }
            
        }
        return currentElasticState;   
    }
    
    private double findMetricValue(String metricName){
        double metricValue=0;
        
        for (MonitoringMetric monitoringMetric : listOfMonitoringMetrics) {
            if (monitoringMetric.getMetricName().equals(metricName)){
                metricValue = monitoringMetric.getMetricValue();
            }
        }
        
        return metricValue;
    }
 
    private boolean isExpectedElasticState(ElasticState elasticState) {
        
        boolean rs= false;
        
        for (ElasticState finalEState: listOfExpectedElasticStates){
            if (elasticState.geteStateID().equals(finalEState.geteStateID())){
                rs=true;
                break;
            }
        }
        
        return rs;
    }
  
    private void config(){
        
//        ElasticityProcessesStore elasticityProcessesStore = new ElasticityProcessesStore(); 
//        ElasticDataAsset eda = elasticityProcessesStore.getElasticDataAsset(monitoringSession.getDataAssetID());
//        ElasticProcess elasticityProcess= eda.getElasticityProcess();
//        ElasticStateSet elasticStateSet = eda.getElasticStateSet();
//        eDaaSType = eda.getType();
//        
//        listOfElasticStates = elasticStateSet.getInitialElasticStateSet();
//       
//        monitorProcess = elasticityProcess.getMonitorProcess(); 
//        listOfControlProcesses = elasticityProcess.getListOfControlProcesses();
// 
//        List<String> expectElasticStateIDs = monitoringSession.getListOfExpectedElasticStates();
//        mappingExpectedEStateIDs(expectElasticStateIDs);
//        
//        metricProcess = elasticityProcessesStore.getMetricProcess(monitoringSession.getEdaasName());
//        
//      

        
        
                
       
    }
    
    private void mappingExpectedEStateIDs(List<String> expectElasticStateIDs){
        
        listOfExpectedElasticStates = new ArrayList<ElasticState>();
        
        for (String eStateID : expectElasticStateIDs){
            ElasticState elasticState = findElasticStateWithID(eStateID);
            listOfExpectedElasticStates.add(elasticState);
            
        }
        
        
    }
    
    
    private ElasticState findElasticStateWithID(String elasticStateID){
        
        for (ElasticState elasticState: listOfElasticStates){
            
            if (elasticState.geteStateID().equals(elasticStateID)){
                return elasticState;
            }
        }
        return null;
    }
    
    
    
    
    private void logElasticState(ElasticState elasticState ){
  
            Logger.logInfo("\n***"); 
            Logger.logInfo("eState ID: " + elasticState.geteStateID());
            List<MetricCondition> conditions = elasticState.getListOfConditions();
            for(MetricCondition condition : conditions){
                Logger.logInfo("\n---"); 
                System.out.print("    metric: " + condition.getMetricName());
                System.out.print("    id: " + condition.getConditionID());
                System.out.print("    lower: " + condition.getLowerBound());
                System.out.print("    uppper: " + condition.getUpperBound());
                
            
            
        }
        
    }
    
    
    public String getMonitoringMetricName(String actionID){
        String metricName="";
      
//        List<MetricElasticityProcess> metricElasticityProcesses = metricProcess.getListOfMetricElasticityProcesses();
//        for (MetricElasticityProcess mp : metricElasticityProcesses) {
//            if (mp.getMonitorAction().getMonitorActionID().equals(actionID)) {
//                metricName=mp.getMetricName();
//                break;
//            }
//        }


        return metricName;
    }
 
    
}
