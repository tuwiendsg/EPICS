/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.dataelasticitymonitor;

import at.ac.tuwien.dsg.common.entity.eda.ElasticState;
import at.ac.tuwien.dsg.common.entity.eda.MetricCondition;
import at.ac.tuwien.dsg.common.entity.eda.ep.ElasticityProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitoringSession;
import at.ac.tuwien.dsg.common.entity.process.MonitoringMetric;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.orchestrator.configuration.Configuration;
import at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller.DataElasticityController;
import at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore.ElasticityProcessesStore;
import at.ac.tuwien.dsg.orchestrator.registry.MonitoringServiceRegistry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class DataElasticityMonitor implements Runnable{

    List<MonitoringMetric> listOfMonitoringMetrics;
    List<ElasticState> listOfElasticStates;
    List<ElasticState> listOfExpectedElasticStates;
    MonitoringSession monitoringSession;
    MonitorProcess monitorProcess;
    

    public DataElasticityMonitor(MonitoringSession monitoringSession) {
        this.monitoringSession = monitoringSession;
        config();
    }
        
    

    @Override
    public void run() {
        startMonitoringService();
    }
    
    public void startMonitoringService() {
        List<MonitorAction> listOfMonitoringActions = monitorProcess.getListOfMonitorActions();
        
        Configuration configuration = new Configuration();
        long monitoringInterval = Long.parseLong(configuration.getConfig("MONITORING.INTERVAL"));
        
        while (true) {
            
            for (MonitorAction monitorAction : listOfMonitoringActions) {
                String monitoringServiceID = monitorAction.getMonitorActionID();
                MonitoringServiceRegistry monitoringServiceRegistry = new MonitoringServiceRegistry();
                
                String uri = monitoringServiceRegistry.getMonitoringServiceURI(monitoringServiceID);
                RestfulWSClient ws = new RestfulWSClient(uri);
                double monitoringValue = Double.parseDouble(ws.callPutMethod(""));
                String metricName = monitoringServiceRegistry.getMonitoringMetricName(monitoringServiceID);
                
                MonitoringMetric monitoringMetric = new MonitoringMetric(metricName, monitoringValue);
                listOfMonitoringMetrics.add(monitoringMetric);
                ElasticState currentElasticState = determineCurrentElasticState(monitoringMetric);
                
                if (!isExpectedElasticState(currentElasticState)) {
                    DataElasticityController controller = new DataElasticityController();
                    controller.startControlElasticState(currentElasticState);
                }
                
                
            }
            
            try {
                Thread.sleep(monitoringInterval);
            } catch (InterruptedException ex) {
                Logger.getLogger(DataElasticityMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    
    private ElasticState determineCurrentElasticState(MonitoringMetric monitoringMetric){
        ElasticState currentElasticState = null;
        
        for (ElasticState elasticState : listOfElasticStates) {
            
            List<MetricCondition> conditions = elasticState.getListOfConditions();
            
            for (MetricCondition condition: conditions) {
                String metricName  =condition.getMetricName();
                double metricValue = findMetricValue(metricName);
                
                if (metricValue>condition.getLowerBound() && metricValue<condition.getUpperBound()){
                    currentElasticState = elasticState;
                    break;
                }
                
    
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
        
        return listOfExpectedElasticStates.contains(elasticState);
        
    }
    
    private void config(){
        
        ElasticityProcessesStore elasticityProcessesStore = new ElasticityProcessesStore();
        ElasticityProcess elasticityProcess= elasticityProcessesStore.getElasticityProcesses(monitoringSession.getDataAssetID());
       
        monitorProcess = elasticityProcess.getMonitorProcess(); 
        List<String> expectElasticStates = monitoringSession.getListOfExpectedElasticStates();
       
    }
    
}
