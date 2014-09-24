/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.app;

import at.ac.tuwien.dsg.common.deployment.DeployAction;
import at.ac.tuwien.dsg.common.deployment.DeploymentDescription;
import at.ac.tuwien.dsg.common.deployment.DeploymentDescriptionJAXB;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depictool.entity.others.ActionDependency;
import at.ac.tuwien.dsg.depictool.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.depictool.entity.eda.ep.ControlProcess;
import at.ac.tuwien.dsg.depictool.entity.eda.ep.ElasticityProcess;
import at.ac.tuwien.dsg.depictool.entity.process.MetricProcess;
import at.ac.tuwien.dsg.depictool.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.depictool.entity.eda.ElasticState;
import at.ac.tuwien.dsg.depictool.entity.process.MetricElasticityProcess;
import at.ac.tuwien.dsg.depictool.entity.qor.MetricRange;
import at.ac.tuwien.dsg.depictool.entity.eda.ep.MonitorAction;
import at.ac.tuwien.dsg.depictool.entity.eda.ep.MonitorProcess;
import at.ac.tuwien.dsg.depictool.entity.qor.TriggerValues;
import at.ac.tuwien.dsg.depictool.util.ElasticityProcessRepositorty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ElasticityProcessGenerator {

    ElasticDataAsset elasticDataObject;
    MetricProcess elasticityProcessConfiguration;
    
    public ElasticityProcessGenerator() {
    }

    public ElasticityProcessGenerator(ElasticDataAsset elasticDataObject, MetricProcess elasticityProcessConfiguration) {
        this.elasticDataObject = elasticDataObject;
        this.elasticityProcessConfiguration = elasticityProcessConfiguration;
    }
    
     

    public MonitorProcess generateMonitorProcess() {

        List<MonitorAction> listOfMonitorActions = new ArrayList<>();

        List<MetricElasticityProcess> listOfMetricElasticityProcesses = elasticityProcessConfiguration.getListOfMetricElasticityProcesses();

        for (MetricElasticityProcess metric : listOfMetricElasticityProcesses) {
            MonitorAction monitorAction = metric.getMonitorAction();
            listOfMonitorActions.add(monitorAction);

        }

        MonitorProcess monitorProcess = new MonitorProcess(listOfMonitorActions);

        return monitorProcess;
    }

    public List<ControlProcess> generateControlProcesses() {

        List<ControlProcess> listOfControlProcesses = new ArrayList<>();

        List<ElasticState> listOfElasticStates = elasticDataObject.getListOfElasticStates();

        for (ElasticState eState_i : listOfElasticStates) {
            for (ElasticState eState_j : listOfElasticStates) {

                if (!eState_i.equals(eState_j)) {

                    ControlProcess controlProcess = findControlProcess(eState_i, eState_j);
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
    
    
    public void deployElasticityProcess(ElasticityProcess dataElasticityProcess){
        
        MonitorProcess monitorProcess = dataElasticityProcess.getMonitorProcess();
        
        // deploy monitor actions
        deployMonitorActions(monitorProcess);
        
        
        List<ControlProcess> listOfControlProcesses = dataElasticityProcess.getListOfControlProcesses();
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
    
    
    private void deployControlActions(List<ControlProcess> listOfControlProcesses){
        
    }
    
    
    

    private ControlProcess findControlProcess(ElasticState eStatei, ElasticState eStatej) {

        ControlProcess controlProcess = null;

        List<ControlAction> listOfControlActions = new ArrayList<>();

        List<MetricRange> listOfMetricRanges_i = eStatei.getListOfMetricRanges();
        List<MetricRange> listOfMetricRanges_j = eStatej.getListOfMetricRanges();

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
            controlProcess = new ControlProcess(eStatei.geteStateID(), eStatej.geteStateID(), listOfControlActions);
        }
       // System.out.println("from eState i: " + eStatei.geteStateID() + " --- to eState j: " + eStatei.geteStateID());

        return controlProcess;
    }

    private void sortControlActionOrder(ControlProcess controlProcess) {

        List<ControlAction> listOfActions = controlProcess.getListOfControlActions();
        
      /*  
        for (ControlAction c : listOfActions) {
            System.out.println(c.getActionID());
        }
        */
        
        ElasticityProcessRepositorty epRepo = new ElasticityProcessRepositorty();

        for (int i=0;i<listOfActions.size();i++) {
            ControlAction controlAction_i = listOfActions.get(i);
            List<ActionDependency> listOfActionDependencies = epRepo.getControlActionDependencyDB(controlAction_i.getActionID());
                    
            for (int j=i+1;j<listOfActions.size();j++) {
                
            
                    ControlAction controlAction_j = listOfActions.get(j);
                    
     
                    for (ActionDependency actionDependency : listOfActionDependencies) {
                    
                        String rerequisiteActionID= actionDependency.getPrerequisiteActionID();
                        if (controlAction_j.getActionID().equals(rerequisiteActionID)){
                            
                            Collections.swap(listOfActions, i, j);
                           
                        }
                        
                        
                    }
                

            }

        }
        
        
       

    }

    private ControlAction findControlAction(String metricName, String rangeVal_i, String rangeVal_j) {
        ControlAction returnControlAction = null;

        List<MetricElasticityProcess> listOfMetricElasticityProcesses = elasticityProcessConfiguration.getListOfMetricElasticityProcesses();

        for (MetricElasticityProcess elasticityProcess : listOfMetricElasticityProcesses) {
            if (elasticityProcess.getMetricName().equals(metricName)) {

                List<ControlAction> listOfControlActions = elasticityProcess.getListOfControlActions();

                for (ControlAction controlAction : listOfControlActions) {

                    TriggerValues triggerValues = controlAction.getTriggerValues();

                    if (triggerValues.getFromRange().equals(rangeVal_i) && triggerValues.getToRange().equals(rangeVal_j)) {
                        returnControlAction = controlAction;
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
    
}
