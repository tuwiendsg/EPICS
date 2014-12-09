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
import at.ac.tuwien.dsg.common.entity.eda.ep.ParallelGateway;
import at.ac.tuwien.dsg.common.entity.process.MetricElasticityProcess;
import at.ac.tuwien.dsg.common.entity.process.MetricProcess;
import at.ac.tuwien.dsg.common.entity.qor.MetricRange;
import at.ac.tuwien.dsg.common.entity.qor.MetricControlActions;

import at.ac.tuwien.dsg.common.entity.process.ActionDependency;
import at.ac.tuwien.dsg.common.entity.qor.QElement;
import at.ac.tuwien.dsg.common.entity.qor.QoRMetric;
import at.ac.tuwien.dsg.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.common.entity.qor.Range;
import at.ac.tuwien.dsg.depictool.elstore.ElasticityProcessStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Jun
 */
public class ElasticityProcessesGenerator {

    QoRModel qorModel;
    MetricProcess metricProcess;
    List<ActionDependency> ActionDependencySet;
    
    
    public ElasticityProcessesGenerator() {
        config();
    }

    public ElasticityProcessesGenerator(QoRModel qorModel, MetricProcess metricProcess) {
        this.qorModel = qorModel;
        this.metricProcess = metricProcess;
        config();
    }


    public MonitorProcess generateMonitorProcess() {

        List<MonitorAction> listOfMonitorActions = new ArrayList<MonitorAction>();
        List<MetricElasticityProcess> listOfMetricElasticityProcesses = metricProcess.getListOfMetricElasticityProcesses();

        for (MetricElasticityProcess metric : listOfMetricElasticityProcesses) {
            MonitorAction monitorAction = metric.getMonitorAction();
            listOfMonitorActions.add(monitorAction);
        }

        MonitorProcess monitorProcess = new MonitorProcess(listOfMonitorActions);

        return monitorProcess;
    }
    
    
    public List<ElasticState> generateSetOfInitialElasticState(){
        List<QoRMetric> listOfMetrics = qorModel.getListOfMetrics();
        List<MetricElasticityProcess> listOfProcessMetric = metricProcess.getListOfMetricElasticityProcesses();
        List<List> listOfConditionSet = new ArrayList<List>();
        List<ElasticState> listOfInitialElasticStates = new ArrayList<ElasticState>();
        
       
        
        for (QoRMetric metric : listOfMetrics){
            
            String metricName = metric.getName();
            MetricElasticityProcess metricProcess = getMetricLElasticityProcessFromMetricName(metricName, listOfProcessMetric);
            List<MetricCondition> listOfConditions = metricProcess.getListOfConditions();
            listOfConditionSet.add(listOfConditions);

        }

        int noOfMetric = listOfConditionSet.size();
        List<int[]> combinations = new ArrayList<int[]>();

        for (int k = 0; k < 1000; k++) {
            int[] conditionIndice = new int[noOfMetric];

            for (int i = 0; i < noOfMetric; i++) {

                List<MetricCondition> conditionMetric_i = listOfConditionSet.get(i);
                int noOfConditions = conditionMetric_i.size();
                int conditionIndex = randomInt(0, noOfConditions);
                conditionIndice[i] = conditionIndex;

            }

            if (!isDuplicated(combinations, conditionIndice)) {
                combinations.add(conditionIndice);
            }

        }

        for (int[] conbination : combinations) {

            List<MetricCondition> eStateConditions = new ArrayList<MetricCondition>();
            String eStateID = "";

            for (int i = 0; i < conbination.length; i++) {

                List<MetricCondition> conditionMetric_i = listOfConditionSet.get(i);
                MetricCondition metricCondition = conditionMetric_i.get(conbination[i]);
                MetricCondition newMetricCondition = new MetricCondition(metricCondition.getMetricName(), metricCondition.getConditionID(), metricCondition.getLowerBound(), metricCondition.getUpperBound());
                eStateConditions.add(newMetricCondition);
                eStateID = eStateID + metricCondition.getConditionID() + ";";

            }

            ElasticState elasticState = new ElasticState(eStateID, eStateConditions);
            listOfInitialElasticStates.add(elasticState);
        }

        return listOfInitialElasticStates;
    }

    public List<ElasticState> generateSetOfFinalElasticState(List<ElasticState> listOfInitialElasticStates) {

        List<ElasticState> listOfFinalElasticStates = new ArrayList<ElasticState>();
        List<QElement> listOfQElements = qorModel.getListOfQElements();
        for (QElement qElement : listOfQElements) {

            for (ElasticState elasticState : listOfInitialElasticStates) {

                List<MetricCondition> listOfMetricConditions = elasticState.getListOfConditions();

                
                boolean isMatching = true;

                System.out.println("qElement: " + qElement.getqElementID());
                
                
                for (MetricCondition metricCondition : listOfMetricConditions) {

                    String metricName = metricCondition.getMetricName();
                    Range range = findMatchingRange(qElement, metricName);
                    System.out.println("    metric: " + metricName);
                    System.out.println("    from range: " + range.getFromValue() + "    to range: " + range.getToValue()) ;
                    System.out.println("    condition from: " + metricCondition.getLowerBound() + "    condition to: " + metricCondition.getUpperBound()) ;
                    
                    if (!((metricCondition.getLowerBound() >= range.getFromValue())
                            && (metricCondition.getUpperBound() <= range.getToValue()))) {
                        isMatching = false;
                        System.out.println("FALSE CASE");
                        break;
                    }

                }

                if (isMatching) {
                    ElasticState newElasticState = initializeElasticState(elasticState, qElement);
                    listOfFinalElasticStates.add(newElasticState);
                }

            }

        }
        return listOfFinalElasticStates;
    }

    public List<ControlProcess> generateControlProcesses(List<ElasticState> listOfInitialElasticStates, List<ElasticState> listOfFinalElasticStates) {

       List<ControlProcess> listOfControlProcesses = new ArrayList<ControlProcess>();
        
        for (ElasticState elasticState_in : listOfInitialElasticStates) {
            for (ElasticState elasticState_fi : listOfFinalElasticStates){
                ControlProcess controlProcess = findControlProcess(elasticState_in, elasticState_fi);
                
                if (controlProcess!=null) {
                    listOfControlProcesses.add(controlProcess);
                }
                
                
            }
            
        }

//        for (ControlProcess controlProcess : listOfControlProcesses) {
//            sortControlActionOrder(controlProcess);
//        }
        return listOfControlProcesses;
    }
    
    

    private ControlProcess findControlProcess(ElasticState elasticState_in, ElasticState elasticState_fi) {

        ControlProcess controlProcess = null;
        boolean isMovingEStatePossible = true;
  
        List<ControlAction> listOfControlActions = new ArrayList<ControlAction>(); 
        
        List<MetricCondition> listOfConditions_in = elasticState_in.getListOfConditions();
        List<MetricCondition> listOfConditions_fi = elasticState_fi.getListOfConditions();
     
        for (MetricCondition metricCondition_in : listOfConditions_in){
            
            String metricName_in = metricCondition_in.getMetricName();
            MetricCondition metricCondition_fi = findMetricConditionByMetricName(metricName_in, listOfConditions_fi);
            
            List<ControlAction> controlActions = findControlAction(metricName_in, metricCondition_in, metricCondition_fi);
           
            if (controlActions != null) {
                listOfControlActions.addAll(controlActions);
            } else {
                isMovingEStatePossible=false;
            }
           
        }
        
//        
//        List<MetricRange> listOfMetricRanges_i = qE_i.getListOfMetricRanges();
//        List<MetricRange> listOfMetricRanges_j = qE_j.getListOfMetricRanges();
//        
//        for (MetricRange metricRange_i : listOfMetricRanges_i) {
//            String metricName_i = metricRange_i.getMetricName();
//            String rangeVal_i = metricRange_i.getRange();
//            String rangeVal_j = findRangeValueFromMetricName(metricName_i, listOfMetricRanges_j);
//            ControlAction controlAction = findControlAction(metricName_i, rangeVal_i, rangeVal_j);
//
//            if (controlAction == null && !rangeVal_i.equals(rangeVal_j)) {
//                listOfControlActions.clear();
//                break;
//            }
//
//            if (controlAction != null) {
//                listOfControlActions.add(controlAction);
//            }
//        }

        if (isMovingEStatePossible) {
       
            controlProcess = new ControlProcess(elasticState_in, elasticState_fi, listOfControlActions);
            buildWorkflowForControlProcess(controlProcess);
          
        }
        
        return controlProcess;
    }
    
    private void buildWorkflowForControlProcess(ControlProcess controlProcess){
        
        List<ControlAction> listOfControlActions =  controlProcess.getListOfControlActions();
        
        List<ParallelGateway> listOfParallelGateways = new ArrayList<ParallelGateway>();
        
        
        for (ControlAction controlAction : listOfControlActions) {
            UUID controlActionID = UUID.randomUUID();
            controlAction.setControlActionID(controlActionID.toString());
        }
        
        
        
        
        for (ControlAction controlAction : listOfControlActions) {
            List<ActionDependency> listOfActionDependencies = findDependencyActionFromID(controlAction.getControlActionName());
            
            if (listOfActionDependencies.size()>1) {
                
                ParallelGateway parallelGateway = new ParallelGateway();
                List<String> incomingList = new ArrayList<String>();
                List<String> outgoingList = new ArrayList<String>();
                
                outgoingList.add(controlAction.getControlActionName());
                
                UUID parallelGatewayID = UUID.randomUUID();
                parallelGateway.setId(parallelGatewayID.toString());
                
                for (ActionDependency actionDependency : listOfActionDependencies){
                    int prerequisiteControlActionIndex = findControlActionIndex(listOfControlActions, actionDependency.getPrerequisiteActionID());
                    ControlAction prerequisiteControlAction = listOfControlActions.get(prerequisiteControlActionIndex);
                    prerequisiteControlAction.setOutgoing(parallelGateway.getId());
                    incomingList.add(prerequisiteControlAction.getControlActionName());
                    
                }
                
                parallelGateway.setIncomming(incomingList);
                parallelGateway.setOutgoing(outgoingList);
                listOfParallelGateways.add(parallelGateway);
                
                
               
                
            } else if (listOfActionDependencies.size()==1) {
                ActionDependency actionDependency = listOfActionDependencies.get(0);
                int prerequisiteControlActionIndex = findControlActionIndex(listOfControlActions, actionDependency.getPrerequisiteActionID());
                ControlAction prerequisiteControlAction = listOfControlActions.get(prerequisiteControlActionIndex);
                
                controlAction.setIncomming(prerequisiteControlAction.getControlActionName());
                prerequisiteControlAction.setOutgoing(controlAction.getControlActionName());
                
            } 
            
            
            
            
            
        }
        
        controlProcess.setListOfParallelGateways(listOfParallelGateways);
        
        
    }
    
    private int findControlActionIndex(List<ControlAction> listOfControlActions,String prerequisiteActionID){
        
        int index=0;
        
        for (ControlAction ca : listOfControlActions){
            if (ca.getControlActionName().equals(prerequisiteActionID)){
                index = listOfControlActions.indexOf(ca);
                break;
            }
            
        }
        
        return index;
    }
    
    private MetricCondition findMetricConditionByMetricName(String metricName, List<MetricCondition> listOfConditions){
        MetricCondition rs = null;
        
        for (MetricCondition condition : listOfConditions){
            if (condition.getMetricName().equals(metricName)){
                rs = condition;
                break;
            }
            
        }
        return rs;
    }

    private void sortControlActionOrder(ControlProcess controlProcess) {

        List<ControlAction> listOfActions = controlProcess.getListOfControlActions();
        
        ElasticityProcessStore epRepo = new ElasticityProcessStore();

        for (int i=0;i<listOfActions.size();i++) {
            ControlAction controlAction_i = listOfActions.get(i);
            List<ActionDependency> listOfActionDependencies = epRepo.getActionDependencyDB(controlAction_i.getControlActionName());
                    
            for (int j=i+1;j<listOfActions.size();j++) {
            
                    ControlAction controlAction_j = listOfActions.get(j);
            
                    for (ActionDependency actionDependency : listOfActionDependencies) {  
                        String rerequisiteActionID= actionDependency.getPrerequisiteActionID();
                        if (controlAction_j.getControlActionName().equals(rerequisiteActionID)){                
                            Collections.swap(listOfActions, i, j);               
                        }              
                    }
            }
        }
    }

    private List<ControlAction> findControlAction(String metricName, MetricCondition condition_in, MetricCondition condition_fi) {
        List<ControlAction> returnControlActions=null;
        
        List<MetricElasticityProcess> listOfMetricElasticityProcesses = metricProcess.getListOfMetricElasticityProcesses();
      
        
        
        
        for (MetricElasticityProcess elasticityProcess : listOfMetricElasticityProcesses) {
        
            
            if (elasticityProcess.getMetricName().equals(metricName)) {
                List<MetricControlActions> metricControlActionList = elasticityProcess.getListOfMetricControlActions();
              //  String conditionID_in = findConditionID(condition_in, elasticityProcess);
              //  String conditionID_fi = findConditionID(condition_fi, elasticityProcess);
                String conditionID_in = condition_in.getConditionID();
                String conditionID_fi = condition_fi.getConditionID();
                
                
                for (MetricControlActions metricControlAction : metricControlActionList) {
                    
                   
                    if ((metricControlAction.getFromRange().equals(conditionID_in)) && 
                            (metricControlAction.getToRange().equals(conditionID_fi))) {
                        
                        returnControlActions = metricControlAction.getListOfControlActions();
                    
                    }
                }
            }
        }

        return returnControlActions;
    }
    
    private String findConditionID(MetricCondition condition, MetricElasticityProcess elasticityProcess){
        String conditionID="";
        
        List<MetricCondition> listOfConditions = elasticityProcess.getListOfConditions();
        for (MetricCondition metricCondition : listOfConditions){
            if ((condition.getLowerBound()>=metricCondition.getLowerBound()) 
                    && (condition.getUpperBound()<=metricCondition.getUpperBound())){
                conditionID = metricCondition.getMetricName();
            }
        }
        return conditionID;
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
    
//    private ElasticState eStateMap(QElement qElement) {
//        
//        List<QoRMetric> listOfMetrics = qorModel.getListOfMetrics();
//        List<MetricRange> listOfMetricRanges =  qElement.getListOfMetricRanges();
//        
//        List<MetricCondition> listOfMetricConditions = new ArrayList<MetricCondition>();
//        
//        for (MetricRange metricRange : listOfMetricRanges) {
//            String metricName = metricRange.getMetricName();
//            String rangeID = metricRange.getRange();
//            
//            for (QoRMetric metric : listOfMetrics) {
//                if (metric.equals(metricName)){
//                   List<Range> listOfRanges =  metric.getListOfRanges();              
//                   for (Range r : listOfRanges) {
//                       if (r.getRangeID().equals(rangeID)){
//                           double lowerBound = r.getFromValue();
//                           double upperBound = r.getToValue();
//                           MetricCondition metricCondition = new MetricCondition(metricName, upperBound, lowerBound);
//                           listOfMetricConditions.add(metricCondition);
//                           break;
//                       }              
//                   }        
//                }           
//            }           
//        }
//        
//        String eStateID = qElement.getqElementID();
//        ElasticState elasticState = new ElasticState(eStateID, listOfMetricConditions);
//        return  elasticState;
//    }
    
    private MetricElasticityProcess getMetricLElasticityProcessFromMetricName(String metricName, List<MetricElasticityProcess> listOfMetricElasticityProcesses){
        
        for (MetricElasticityProcess metricElasticityProcess : listOfMetricElasticityProcesses){
            if(metricElasticityProcess.getMetricName().equals(metricName)){
                return metricElasticityProcess;
            }
        }
        return null;
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


    
    
    private boolean isDuplicated(List<int[]> combinations, int[] conditionIndice){
        
        boolean rs=false;
        
        for (int i=0;i<combinations.size();i++){
            int[] condition_i = combinations.get(i);
            
            String conditionString_i = "";
            String comparedCondition = "";
            
            for (int j=0;j<condition_i.length;j++){
                conditionString_i =conditionString_i + String.valueOf(condition_i[j]) + ";";
                comparedCondition = comparedCondition + String.valueOf(conditionIndice[j])+";" ;
            }
            
            if (conditionString_i.equals(comparedCondition)){
                rs=true;
                break;
            }
        }
        
        
        return rs;
    }

    private int randomInt(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }
    
    private Range getMetricRangeFromID(String rangeID){
        Range rs = null;
        
        List<QoRMetric> listOfMetrics = qorModel.getListOfMetrics();
        
        for (QoRMetric qoRMetric : listOfMetrics){
            
            List<Range> listOfRanges = qoRMetric.getListOfRanges();
            
            for (Range range: listOfRanges){
               // System.out.println("range ID ith: " + range.getRangeID());
                if (rangeID.equals(range.getRangeID())){
                    rs = new Range(rangeID, range.getFromValue(), range.getToValue());
                    //System.out.println("RANGE CHECK: from value " + range.getFromValue() + " - to value: " + range.getToValue());
                    
                    break;
                }
                
            }
        }
                
        return rs;
    }
    
  
    
    private ElasticState initializeElasticState(ElasticState elasticState, QElement qElement){
        
        
        
        List<MetricCondition> listOfMetricConditions = elasticState.getListOfConditions();
        
        List<MetricCondition> newlistOfMetricConditions = new ArrayList<MetricCondition>();
        for (MetricCondition condition : listOfMetricConditions){

            MetricCondition newMetricCondition = 
                    new MetricCondition(
                            condition.getMetricName(), 
                            condition.getConditionID(),
                            condition.getLowerBound(), 
                            condition.getUpperBound());
           newlistOfMetricConditions.add(newMetricCondition);
        }

        
        
        MetricCondition costCondition = new MetricCondition("cost",qElement.getqElementID(), qElement.getPrice(), qElement.getPrice());
        newlistOfMetricConditions.add(costCondition);
        
        ElasticState newElasticState = new ElasticState(elasticState.geteStateID(), newlistOfMetricConditions);
        
        return newElasticState;
    }
    
    private Range findMatchingRange(QElement qElement, String metricName){
    
        Range range = null;
        List<MetricRange> listOfMetricRanges = qElement.getListOfMetricRanges();
       
        for (MetricRange metricRange : listOfMetricRanges){
            
           
            if (metricName.equals(metricRange.getMetricName())){
                String rangeID = metricRange.getRange();
            
                range = getMetricRangeFromID(rangeID);
                break;
            }
            
            
        }
        return range;
        
    }
    
    
    private void config(){
        ElasticityProcessStore epRepo = new ElasticityProcessStore();
        ActionDependencySet = epRepo.getAllActionDependencies();
        
    }
    
    private List<ActionDependency> findDependencyActionFromID(String actionID){
        
        List<ActionDependency> dependencyList = new ArrayList<ActionDependency>();
        for (ActionDependency actionDependency : ActionDependencySet){
            if (actionDependency.getActionID().equals(actionID)){
                ActionDependency ad = new ActionDependency(
                        actionDependency.getActionID(), 
                        actionDependency.getPrerequisiteActionID());
                dependencyList.add(ad);
                
            }
            
        }
        
        return dependencyList;
    }
    
}
