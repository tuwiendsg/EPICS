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
import at.ac.tuwien.dsg.common.entity.qor.MetricRange;
import at.ac.tuwien.dsg.common.entity.qor.QElement;
import at.ac.tuwien.dsg.common.entity.qor.QoRMetric;
import at.ac.tuwien.dsg.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.common.entity.qor.Range;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.orchestrator.configuration.Configuration;
import at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller.DataElasticityController;
import at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore.ElasticityProcessesStore;
import at.ac.tuwien.dsg.orchestrator.registry.MonitoringServiceRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class DataElasticityMonitor{

    List<MonitoringMetric> listOfMonitoringMetrics;
    List<ElasticState> listOfElasticStates;
    List<ElasticState> listOfExpectedElasticStates;
    MonitoringSession monitoringSession;
    MonitorProcess monitorProcess;
    

    public DataElasticityMonitor() {
        this.monitoringSession = monitoringSession;
        listOfMonitoringMetrics = new ArrayList<MonitoringMetric>();
        config();
    }
     

    public void startMonitoringService() {
        List<MonitorAction> listOfMonitoringActions = monitorProcess.getListOfMonitorActions();
        
            for (MonitorAction monitorAction : listOfMonitoringActions) {
                
            
                String monitoringServiceID = monitorAction.getMonitorActionID();
                MonitoringServiceRegistry monitoringServiceRegistry = new MonitoringServiceRegistry();
                
                String uri = monitoringServiceRegistry.getMonitoringServiceURI(monitoringServiceID);
                RestfulWSClient ws = new RestfulWSClient(uri);
                double monitoringValue = Double.parseDouble(ws.callPutMethod(monitoringSession.getDataAssetID()));
                String metricName = monitoringServiceRegistry.getMonitoringMetricName(monitoringServiceID);
                
                MonitoringMetric monitoringMetric = new MonitoringMetric(metricName, monitoringValue);
                listOfMonitoringMetrics.add(monitoringMetric);
                
            }
            
            ElasticState currentElasticState = determineCurrentElasticState();
                
                if (!isExpectedElasticState(currentElasticState)) {
                    DataElasticityController controller = new DataElasticityController();
                    controller.startControlElasticState(currentElasticState);
                }
        
    }
    
    private ElasticState determineCurrentElasticState(){
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
       // mappingEState(monitoringSession.getDataAssetID());
        List<String> expectElasticStateIDs = monitoringSession.getListOfExpectedElasticStates();
        mappingExpectedEStateIDs(expectElasticStateIDs);
       
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
    
    
    
    
 
    
}
