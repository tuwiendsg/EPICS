/**
 * Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup
 * E184. This work was partially supported by the European Commission in terms
 * of the CELAR FP7 project (FP7-ICT-2011-8 #317790).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller;

import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticState;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MetricCondition;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.AdjustmentProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.DataElasticityManagementProcess;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.MonitoringProcess;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlAction;

import at.ac.tuwien.dsg.depic.common.entity.runtime.MonitoringSession;

import at.ac.tuwien.dsg.depic.common.entity.runtime.MonitoringMetric;
import at.ac.tuwien.dsg.depic.common.repository.PrimitiveActionMetadataManager;

import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.Logger;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.orchestrator.configuration.Configuration;
import at.ac.tuwien.dsg.orchestrator.elasticityprocessesstore.ElasticityProcessesStore;

import at.ac.tuwien.dsg.orchestrator.registry.ElasticServiceRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.xml.bind.JAXBException;

public class DataElasticityMonitor implements Runnable {
    
    private Thread t;
    
    List<MonitoringMetric> listOfMonitoringMetrics;
    List<ElasticState> listOfElasticStates;
    List<ElasticState> listOfExpectedElasticStates;
    MonitoringSession monitoringSession;
    MonitoringProcess monitorProcess;
    List<AdjustmentProcess> listOfAdjustmentProcess;
    DBType eDaaSType;
    PrimitiveActionMetadata primitiveActionMetadata;
    String dataAssetIndex;
    
    public DataElasticityMonitor(MonitoringSession monitoringSession) {
        this.monitoringSession = monitoringSession;
        listOfMonitoringMetrics = new ArrayList<MonitoringMetric>();
        dataAssetIndex = "";
        config();
    }
    
    public void run() {
        
        do {
            
            startMonitoringService();
            
            try {
                Thread.sleep(10000);
                
            } catch (InterruptedException ex) {
                
            }
        } while (true);
    }
    
    public void start() {
        if (t == null) {
            t = new Thread(this, monitoringSession.getSessionID());
            t.start();
        }
    }
    
    public void startMonitoringService() {

        //copy data
        monitoringSession.setDataAssetIndex(dataAssetIndex);
        String monitoringSessionXML = "";
        
        try {
            monitoringSessionXML = JAXBUtils.marshal(monitoringSession, MonitoringSession.class);
        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(DataElasticityMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Configuration cfg = new Configuration();
        String uriDataAssetCopy = cfg.getConfig("DATA.ASSET.COPY.ENDPOINT");
        RestfulWSClient wsCP = new RestfulWSClient(uriDataAssetCopy);
        String latestDataAssetIndex = wsCP.callPutMethod(monitoringSessionXML);
        
        if (dataAssetIndex.equals(latestDataAssetIndex)) {
            System.out.println("WAITING FOR LATEST DATA ASSET WINDOW");
        } else {
            dataAssetIndex = latestDataAssetIndex;
            
            System.out.println("LATEST DATA ASSET WINDOW: " + dataAssetIndex);
            
            List<MonitoringAction> listOfMonitoringActions = monitorProcess.getListOfMonitoringActions();
            
            Logger.logInfo("Execute Monitoring Process ...");
            
            for (MonitoringAction monitorAction : listOfMonitoringActions) {
                String monitoringServiceName = monitorAction.getMonitoringActionName();
                Logger.logInfo("Monitoring Process - Action: " + monitoringServiceName);
            }
            
            long t1 = System.currentTimeMillis();
            
            for (MonitoringAction monitorAction : listOfMonitoringActions) {
                
                String monitoringServiceName = monitorAction.getMonitoringActionName();
                
                Logger.logInfo("Get monitoring service info: " + monitoringServiceName);
                
                String uri = "";
                
                do {
                    
                    uri = ElasticServiceRegistry.getElasticServiceURI(monitoringServiceName, monitoringSession.geteDaaSType());
                    if (uri.equals("")) {
                        Logger.logInfo("Waiting_for_Active_Elastic_Serivce ... " + monitoringSession.getSessionID() + " - " + monitoringServiceName);
                    } else {
                        Logger.logInfo("Ready_Service: " + monitoringSession.getSessionID() + " - " + uri);
                        ElasticServiceRegistry.occupyElasticService(uri);
                        
                        Logger.logInfo("Run Monitoring Service ID: " + monitoringServiceName);
                        Logger.logInfo("AAA URI: " + uri);
                        
                        DataPartitionRequest request = new DataPartitionRequest(
                                monitoringSession.getEdaasName(),
                                monitoringSession.getSessionID(),
                                monitoringSession.getDataAssetID(),
                                dataAssetIndex);
                        
                        String requestXML = "";
                        
                        try {
                            requestXML = JAXBUtils.marshal(request, DataPartitionRequest.class);
                        } catch (JAXBException ex) {
                            System.err.println(ex);
                        }
                        
                        double monitoringValue = 0;
                        
                        String metricName = getMonitoringMetricName(monitoringServiceName);
                        
                        Logger.logInfo("DATA ASSET REQUEST: " + requestXML);
                        
                        RestfulWSClient ws = new RestfulWSClient(uri);
                        
                        String monitoringStr = ws.callPutMethod(requestXML);
                        Logger.logInfo("Monitoring Str: " + monitoringStr);
                        
                        monitoringValue = Double.parseDouble(monitoringStr);
                        Logger.logInfo("Monitoring Value: " + monitoringValue);
                        MonitoringMetric monitoringMetric = new MonitoringMetric(metricName, monitoringValue);
                        listOfMonitoringMetrics.add(monitoringMetric);
                        ElasticServiceRegistry.releaseElasticService(uri);
                    }
                    
                    try {
                        Thread.sleep(10000);
                        
                    } catch (InterruptedException ex) {
                        System.err.println(ex);
                    }
                    
                } while (uri.equals(""));
                
            }
            
            Logger.logInfo("MONITORING RESULT: " + monitoringSession.getSessionID() + " \n");
            String log = System.currentTimeMillis() + "\t" + monitoringSession.getSessionID() + "\t" + monitoringSession.getDataAssetID() + "\t";
            
            for (MonitoringMetric monitoringMetric : listOfMonitoringMetrics) {
                
                Logger.logInfo("Metric: " + monitoringMetric.getMetricName() + " - Value: " + monitoringMetric.getMetricValue());
                log = log + monitoringMetric.getMetricValue() + "\t";
                
            }
            
            ElasticState currentElasticState = determineCurrentElasticState();
            
            if (currentElasticState == null) {
                
                Logger.logInfo("FAIL VALIDATION");
                log = log + "FAIL" + "\t";
//            AdjustmentProcessExecution controller = new AdjustmentProcessExecution(listOfElasticStates, listOfAdjustmentProcess, monitoringSession, eDaaSType);
//            controller.startControlElasticState(currentElasticState);

                ProcessExecutor processExecutor = new ProcessExecutor(listOfElasticStates, listOfAdjustmentProcess, monitoringSession, currentElasticState, dataAssetIndex);
                processExecutor.start();
                
            } else {
                Logger.logInfo("PASS VALIDATION");
                log = log + "PASS" + "\t";
                Logger.logInfo("Current Elastic State ...");
                
            }
            long t2 = System.currentTimeMillis();
            Logger.logInfo("MONITORING_RUNTIME: " + (t2 - t1));
            log = log + (t2 - t1) + "\n";
            
            try {
                
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(DataElasticityMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    private ElasticState determineCurrentElasticState() {
        ElasticState currentElasticState = null;
        Logger.logInfo("Determine Current ElasticState");
        Logger.logInfo("NoOf EStates: " + listOfElasticStates.size());
        
        for (ElasticState elasticState : listOfElasticStates) {
            
            List<MetricCondition> conditions = elasticState.getListOfConditions();
            boolean rs = true;
            for (MetricCondition condition : conditions) {
                String metricName = condition.getMetricName();
                
                double metricValue = findMetricValue(metricName);
                
                Logger.logInfo("Metric: " + metricName + " - Value: " + metricValue);
                Logger.logInfo("Lower Bound: " + condition.getLowerBound() + " - upper bound: " + condition.getUpperBound());
                
                if (!(metricValue >= condition.getLowerBound() && metricValue <= condition.getUpperBound())) {
                    rs = false;
                }
                
            }
            
            if (rs) {
                currentElasticState = elasticState;
                break;
            }
            
        }
        return currentElasticState;
    }
    
    private double findMetricValue(String metricName) {
        double metricValue = 0;
        
        for (MonitoringMetric monitoringMetric : listOfMonitoringMetrics) {
            if (monitoringMetric.getMetricName().equals(metricName)) {
                metricValue = monitoringMetric.getMetricValue();
            }
        }
        
        return metricValue;
    }
    
    private boolean isExpectedElasticState(ElasticState elasticState) {
        
        boolean rs = false;
        
        for (ElasticState finalEState : listOfElasticStates) {
            if (elasticState.geteStateID().equals(finalEState.geteStateID())) {
                rs = true;
                break;
            }
        }
        
        return rs;
    }
    
    private void config() {
        
        ElasticityProcessesStore elasticityProcessesStore = new ElasticityProcessesStore();
        ElasticDataAsset eda = elasticityProcessesStore.getElasticDataAsset(monitoringSession.getEdaasName());
        
        DataElasticityManagementProcess elasticityProcess = eda.getElasticProcess();
        
        monitoringSession.seteDaaSType(DBType.MYSQL);
        
        listOfElasticStates = eda.getListOfFinalElasticState();
        
        monitorProcess = elasticityProcess.getMonitoringProcess();
        listOfAdjustmentProcess = elasticityProcess.getListOfAdjustmentProcesses();

        //List<String> expectElasticStateIDs = monitoringSession.getListOfExpectedElasticStates();
        //mappingExpectedEStateIDs(expectElasticStateIDs);
        loadPrimitiveActionMetadata();
        
    }
    
    private void loadPrimitiveActionMetadata() {
        
        PrimitiveActionMetadataManager pamm = new PrimitiveActionMetadataManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        List<MonitoringAction> listOfMonitoringActions = pamm.getMonitoringActionList();
        List<AdjustmentAction> listOfAdjustmentActions = pamm.getAdjustmentActionList();
        List<ResourceControlAction> listOfResourceControlActions = pamm.getResourceControlActionList();
        
        primitiveActionMetadata = new PrimitiveActionMetadata(listOfAdjustmentActions, listOfMonitoringActions, listOfResourceControlActions);
        
    }
    
    public String getMonitoringMetricName(String actionName) {
        String metricName = "";
        
        List<MonitoringAction> listOfMonitoringActions = primitiveActionMetadata.getListOfMonitoringActions();
        
        for (MonitoringAction ma : listOfMonitoringActions) {
            if (ma.getMonitoringActionName().equals(actionName)) {
                metricName = ma.getAssociatedQoRMetric();
                break;
            }
        }
        return metricName;
    }
    
}
