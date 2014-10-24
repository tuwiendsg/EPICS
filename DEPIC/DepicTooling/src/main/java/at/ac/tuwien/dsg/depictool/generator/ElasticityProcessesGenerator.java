/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.generator;

import at.ac.tuwien.dsg.common.deployment.DeployAction;
import at.ac.tuwien.dsg.common.deployment.DeploymentDescription;
import at.ac.tuwien.dsg.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.common.entity.eda.ElasticState;
import at.ac.tuwien.dsg.common.entity.eda.MetricCondition;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.ElasticityProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorProcess;
import at.ac.tuwien.dsg.common.entity.process.MetricElasticityProcess;
import at.ac.tuwien.dsg.common.entity.process.MetricProcess;
import at.ac.tuwien.dsg.common.entity.qor.MetricRange;
import at.ac.tuwien.dsg.common.entity.qor.TriggerActions;

import at.ac.tuwien.dsg.common.entity.process.ActionDependency;
import at.ac.tuwien.dsg.common.entity.qor.QElement;
import at.ac.tuwien.dsg.common.entity.qor.QoRMetric;
import at.ac.tuwien.dsg.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.common.entity.qor.Range;
import at.ac.tuwien.dsg.depictool.elstore.ElasticityProcessStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ElasticityProcessesGenerator {

    QoRModel qorModel;
    MetricProcess metricProcess;
    
    public ElasticityProcessesGenerator() {
    }

    public ElasticityProcessesGenerator(QoRModel qorModel, MetricProcess metricProcess) {
        this.qorModel = qorModel;
        this.metricProcess = metricProcess;
    }


    public MonitorProcess generateMonitorProcess() {

        List<MonitorAction> listOfMonitorActions = new ArrayList<>();
        List<MetricElasticityProcess> listOfMetricElasticityProcesses = metricProcess.getListOfMetricElasticityProcesses();

        for (MetricElasticityProcess metric : listOfMetricElasticityProcesses) {
            MonitorAction monitorAction = metric.getMonitorAction();
            listOfMonitorActions.add(monitorAction);
        }

        MonitorProcess monitorProcess = new MonitorProcess(listOfMonitorActions);

        return monitorProcess;
    }

    public List<ControlProcess> generateControlProcesses() {

        List<ControlProcess> listOfControlProcesses = new ArrayList<>();
        List<QElement> listOfQElements = qorModel.getListOfQElements();

        for (QElement qE_i : listOfQElements) {
            for (QElement qE_j : listOfQElements) {
                if (!qE_i.equals(qE_j)) {
                    ControlProcess controlProcess = findControlProcess(qE_i, qE_j);
                    if (controlProcess != null) {
                        listOfControlProcesses.add(controlProcess);
                    }
                }
            }
        }

        for (ControlProcess controlProcess : listOfControlProcesses) {
            sortControlActionOrder(controlProcess);
        }
        return listOfControlProcesses;
    }
    
    

    private ControlProcess findControlProcess(QElement qE_i, QElement qE_j) {

        ControlProcess controlProcess = null;
        List<ControlAction> listOfControlActions = new ArrayList<>(); 
        List<MetricRange> listOfMetricRanges_i = qE_i.getListOfMetricRanges();
        List<MetricRange> listOfMetricRanges_j = qE_j.getListOfMetricRanges();
        
        for (MetricRange metricRange_i : listOfMetricRanges_i) {
            String metricName_i = metricRange_i.getMetricName();
            String rangeVal_i = metricRange_i.getRange();
            String rangeVal_j = findRangeValueFromMetricName(metricName_i, listOfMetricRanges_j);
            ControlAction controlAction = findControlAction(metricName_i, rangeVal_i, rangeVal_j);

            if (controlAction == null && !rangeVal_i.equals(rangeVal_j)) {
                listOfControlActions.clear();
                break;
            }

            if (controlAction != null) {
                listOfControlActions.add(controlAction);
            }
        }

        if (listOfControlActions.size() != 0) {
            ElasticState eState_i = eStateMap(qE_i);
            ElasticState eState_j = eStateMap(qE_j);          
            controlProcess = new ControlProcess(eState_i, eState_j, listOfControlActions);
        }
        return controlProcess;
    }

    private void sortControlActionOrder(ControlProcess controlProcess) {

        List<ControlAction> listOfActions = controlProcess.getListOfControlActions();
        
        ElasticityProcessStore epRepo = new ElasticityProcessStore();

        for (int i=0;i<listOfActions.size();i++) {
            ControlAction controlAction_i = listOfActions.get(i);
            List<ActionDependency> listOfActionDependencies = epRepo.getControlActionDependencyDB(controlAction_i.getControlActionID());
                    
            for (int j=i+1;j<listOfActions.size();j++) {
            
                    ControlAction controlAction_j = listOfActions.get(j);
            
                    for (ActionDependency actionDependency : listOfActionDependencies) {  
                        String rerequisiteActionID= actionDependency.getPrerequisiteActionID();
                        if (controlAction_j.getControlActionID().equals(rerequisiteActionID)){                
                            Collections.swap(listOfActions, i, j);               
                        }              
                    }
            }
        }
    }

    private ControlAction findControlAction(String metricName, String rangeVal_i, String rangeVal_j) {
        ControlAction returnControlAction = null;
        List<MetricElasticityProcess> listOfMetricElasticityProcesses = metricProcess.getListOfMetricElasticityProcesses();
        for (MetricElasticityProcess elasticityProcess : listOfMetricElasticityProcesses) {
            if (elasticityProcess.getMetricName().equals(metricName)) {
                List<TriggerActions> listOfTriggerActions = elasticityProcess.getListOfTriggerActions();
                for (TriggerActions triggerAction : listOfTriggerActions) {
                    if (triggerAction.getFromRange().equals(rangeVal_i) && triggerAction.getToRange().equals(rangeVal_j)) {
                        returnControlAction = triggerAction.getListOfControlActions().get(0);
                    }
                }
            }
        }

        return returnControlAction;
    }

    private String findRangeValueFromMetricName(String metricName, List<MetricRange> listOfMetricRanges) {

        String rangeVal = "";

        for (MetricRange metricRange : listOfMetricRanges) {
            if (metricRange.getMetricName().equals(metricName)) {
                rangeVal = metricRange.getRange();
            }
        }
        return rangeVal;
    }
    
    private ElasticState eStateMap(QElement qElement) {
        
        List<QoRMetric> listOfMetrics = qorModel.getListOfMetrics();
        List<MetricRange> listOfMetricRanges =  qElement.getListOfMetricRanges();
        
        List<MetricCondition> listOfMetricConditions = new ArrayList<>();
        
        for (MetricRange metricRange : listOfMetricRanges) {
            String metricName = metricRange.getMetricName();
            String rangeID = metricRange.getRange();
            
            for (QoRMetric metric : listOfMetrics) {
                if (metric.equals(metricName)){
                   List<Range> listOfRanges =  metric.getListOfRanges();              
                   for (Range r : listOfRanges) {
                       if (r.getRangeID().equals(rangeID)){
                           double lowerBound = r.getFromValue();
                           double upperBound = r.getToValue();
                           MetricCondition metricCondition = new MetricCondition(metricName, upperBound, lowerBound);
                           listOfMetricConditions.add(metricCondition);
                           break;
                       }              
                   }        
                }           
            }           
        }
        
        String eStateID = qElement.getqElementID();
        ElasticState elasticState = new ElasticState(eStateID, listOfMetricConditions);
        return  elasticState;
    }
    
    
    
     /*
    public void deployElasticityProcess(ElasticityProcess elasticityProcesses){
        
        MonitorProcess monitorProcess = elasticityProcesses.getMonitorProcess();
        
        // deploy monitor actions
        deployMonitorActions(monitorProcess);
        List<ControlProcess> listOfControlProcesses = elasticityProcesses.getListOfControlProcesses();
        //deploy control actions
        deployControlActions(listOfControlProcesses);
        
        
        
        
    }
    
   
    private void deployMonitorActions(MonitorProcess monitorProcess){
        List<MonitorAction> listOfMonitorActions = monitorProcess.getListOfMonitorActions();
        List<DeployAction> listOfDeployActions = new ArrayList<>();
        
        for (MonitorAction monitorAction : listOfMonitorActions) {
            
            String actionID = monitorAction.getMonitorActionID();
            ElasticityProcessRepositorty elasticityProcessRepositorty = new ElasticityProcessRepositorty();
        
            DeployAction deployAction = elasticityProcessRepositorty.getPrimitiveAction(actionID);
            listOfDeployActions.add(deployAction);
            
        }
        
        DeploymentDescription deploymentDescription = new DeploymentDescription(listOfDeployActions);
        
        DeploymentDescriptionJAXB descriptionJAXB = new DeploymentDescriptionJAXB();
        String xmlDescription = descriptionJAXB.marshallingObject(deploymentDescription);
        
        RestfulWSClient restfulWSClient = new RestfulWSClient("localhost", "8080", "/Orchestrator/webresources/DeploymentDescription");
        restfulWSClient.callRestfulWebService(xmlDescription);

    }
    
    */


    
}
