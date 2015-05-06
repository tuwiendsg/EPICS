/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.generator;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.Action;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticState;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MetricCondition;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.AdjustmentProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.DirectedAcyclicalGraph;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.MonitoringProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ParallelGateway;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentCase;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.qor.QElement;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRMetric;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.depic.common.entity.qor.Range;
import at.ac.tuwien.dsg.depic.depictool.repository.ElasticProcessRepositoryManager;
import at.ac.tuwien.dsg.depic.common.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Jun
 */
public class ElasticProcessesGenerator {

    DataAnalyticsFunction daf;
    QoRModel qorModel;
    PrimitiveActionMetadata primitiveActionRepository;

    public ElasticProcessesGenerator() {
        // config();
    }

    public ElasticProcessesGenerator(DataAnalyticsFunction daf, QoRModel qorModel, PrimitiveActionMetadata primitiveActionRepository) {
        this.daf = daf;
        this.qorModel = qorModel;
        this.primitiveActionRepository = primitiveActionRepository;
        // config();
    }

    public ElasticDataAsset generateElasticProcesses() {
        Logger.logInfo("Start generate Elastic Processes ... ");

        MonitoringProcess monitorProcess = generateMonitoringProcess();

//   List<ElasticState> initialElasticStateSet = elasticityProcessGenerator.generateSetOfInitialElasticState();
//        
//        Logger logger = new Logger();
//        Logger.logInfo("Inital eState Set");
//        logger.logElasticState(initialElasticStateSet);
//        
//        
//        List<ElasticState> finalElasticStateSet = elasticityProcessGenerator.generateSetOfFinalElasticState(initialElasticStateSet);
//        Logger.logInfo("");
//        Logger.logInfo("Final eState Set");
//        logger.logElasticState(finalElasticStateSet);
//        
//        ElasticStateSet elasticStateSet = new ElasticStateSet(initialElasticStateSet, finalElasticStateSet);
//        
//        List<ControlProcess> listOfControlProcesses = elasticityProcessGenerator.generateControlProcesses(initialElasticStateSet, finalElasticStateSet);
//        ElasticProcess elasticityProcesses = new ElasticProcess(monitorProcess, listOfControlProcesses);
//
//        ElasticDataAsset elasticDataAsset = new ElasticDataAsset(eDaaSName,dbType, elasticityProcesses, elasticStateSet);
//        //log
//       
//        
//        logger.logMonitorProcesses(monitorProcess);
//        logger.logControlProcesses(listOfControlProcesses);
//
//        return elasticDataAsset;
        return null;
    }

    ///////////////////////////////////////
    ///                                 ///
    /// Monitoring Process              ///
    ///                                 ///
    ///////////////////////////////////////
    private MonitoringProcess generateMonitoringProcess() {

        List<MonitoringAction> listOfMonitoringActions = new ArrayList<MonitoringAction>();

        List<QoRMetric> listOfQoRMetrics = qorModel.getListOfMetrics();

        for (QoRMetric metric : listOfQoRMetrics) {
            String qorMetricName = metric.getName();
            MonitoringAction monitoringAction = findCorrespondingMonitoringActionFromQoRMetric(qorMetricName);
            listOfMonitoringActions.add(monitoringAction);
        }

        MonitoringProcess monitorProcess = parallelizeMonitoringActions(listOfMonitoringActions);

        return monitorProcess;
    }

    private MonitoringAction findCorrespondingMonitoringActionFromQoRMetric(String qorMetricName) {
        List<MonitoringAction> listOfMonitoringActions = primitiveActionRepository.getListOfMonitoringActions();

        MonitoringAction foundMonitoringAction = null;
        for (MonitoringAction ma : listOfMonitoringActions) {
            if (qorMetricName.equals(ma.getAssociatedQoRMetric())) {
                foundMonitoringAction = copyMonitoringActionInstance(ma);
            }
        }

        return foundMonitoringAction;
    }

    private MonitoringAction copyMonitoringActionInstance(MonitoringAction ma) {

        return ma;
    }

    private MonitoringProcess parallelizeMonitoringActions(List<MonitoringAction> listOfMonitoringActions) {

        List<Action> listOfAction = new ArrayList<Action>();

        for (MonitoringAction ma : listOfMonitoringActions) {
            
            String actionID = getUDID();
            ma.setMonitorActionID(actionID);
          
            Action action = new Action(actionID, ma.getMonitoringActionName());
            listOfAction.add(action);
        }

        String parallelGateway_in_id = getUDID();
        String parallelGateway_out_id = getUDID();
        List<String> listOfIncommings = new ArrayList<String>();
        List<String> listOfOutgoings = new ArrayList<String>();

        for (Action action : listOfAction) {
            action.setIncomming(parallelGateway_in_id);
            action.setOutgoing(parallelGateway_out_id);
        }

        ParallelGateway pg_in = new ParallelGateway(parallelGateway_in_id, null, listOfOutgoings);
        ParallelGateway pg_out = new ParallelGateway(parallelGateway_out_id, listOfIncommings, null);

        List<ParallelGateway> listOfParallelGateways = new ArrayList<ParallelGateway>();
        listOfParallelGateways.add(pg_in);
        listOfParallelGateways.add(pg_out);

        DirectedAcyclicalGraph dag = new DirectedAcyclicalGraph();
        dag.setListOfActions(listOfAction);
        dag.setListOfParallelGateways(listOfParallelGateways);

        MonitoringProcess monitoringProcess = new MonitoringProcess(listOfMonitoringActions, dag);

        return monitoringProcess;
    }

    private String getUDID() {
        UUID actionID = UUID.randomUUID();
        return actionID.toString();
    }

    ///////////////////////////////////////
    ///                                 ///
    /// Final EState Set                ///
    ///                                 ///
    ///////////////////////////////////////
    public List<ElasticState> generateFinalElasticStateSet(List<ElasticState> listOfInitialElasticStates) {

        List<ElasticState> listOfFinalElasticStates = new ArrayList<ElasticState>();
        List<QElement> listOfQElements = qorModel.getListOfQElements();

        for (QElement qElement : listOfQElements) {
            List<ElasticState> listOfFinalElasticState_qelement = decomposeQElement(qElement);
            listOfFinalElasticStates.addAll(listOfFinalElasticState_qelement);
        }
        return listOfFinalElasticStates;
    }

    private List<ElasticState> decomposeQElement(QElement qElement) {

        //List<String> listOfRangeID =  qElement.getListOfRanges();
        List<QoRMetric> listOfQoRMetrics = qorModel.getListOfMetrics();

        List<List> listOfConditionSet = new ArrayList<List>();

        for (QoRMetric qorMetric : listOfQoRMetrics) {
            List<MetricCondition> listOfConditions = findEstimatedResultForQoRMetric(qorMetric);
            Range r = findMatchingRange(qElement, qorMetric);

            List<MetricCondition> unitConditions = new ArrayList<MetricCondition>();

            for (MetricCondition condition : listOfConditions) {
                if (condition.getLowerBound() >= r.getFromValue() && condition.getUpperBound() <= r.getToValue()) {
                    unitConditions.add(condition);
                }
            }

            listOfConditionSet.add(unitConditions);
        }

        List<ElasticState> listOfFinalElasticStates = new ArrayList<ElasticState>();

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
            listOfFinalElasticStates.add(elasticState);
        }

        return listOfFinalElasticStates;

    }

    private List<MetricCondition> findEstimatedResultForQoRMetric(QoRMetric qorMetric) {
        List<AdjustmentAction> listOfAdjustmentActions = primitiveActionRepository.getListOfAdjustmentActions();

        List<AdjustmentCase> listOfAdjustmentCases = null;

        for (AdjustmentAction adjustmentAction : listOfAdjustmentActions) {
            if (qorMetric.getName().equals(adjustmentAction.getAssociatedQoRMetric())) {
                listOfAdjustmentCases = adjustmentAction.getListOfAdjustmentCases();
                break;
            }
        }

        List<MetricCondition> listOfConditions = new ArrayList<MetricCondition>();

        for (AdjustmentCase adjustmentCase : listOfAdjustmentCases) {
            MetricCondition condition = adjustmentCase.getEstimatedResult();
            listOfConditions.add(condition);
        }

        return listOfConditions;
    }

    private Range findMatchingRange(QElement qElement, QoRMetric qorMetric) {

        Range foundRange = null;

        List<String> listOfRangeIDs_qelement = qElement.getListOfRanges();

        List<Range> listOfPreDefinedRange_qormetric = qorMetric.getListOfRanges();

        for (Range range : listOfPreDefinedRange_qormetric) {
            for (String rangeID : listOfRangeIDs_qelement) {
                if (range.getRangeID().equals(rangeID)) {
                    foundRange = new Range(rangeID, range.getFromValue(), range.getToValue());
                }
            }
        }

        return foundRange;

    }

    private boolean isDuplicated(List<int[]> combinations, int[] conditionIndice) {

        boolean rs = false;

        for (int i = 0; i < combinations.size(); i++) {
            int[] condition_i = combinations.get(i);

            String conditionString_i = "";
            String comparedCondition = "";

            for (int j = 0; j < condition_i.length; j++) {
                conditionString_i = conditionString_i + String.valueOf(condition_i[j]) + ";";
                comparedCondition = comparedCondition + String.valueOf(conditionIndice[j]) + ";";
            }

            if (conditionString_i.equals(comparedCondition)) {
                rs = true;
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

    ///////////////////////////////////////
    ///                                 ///
    /// Adjustment Process              ///
    ///                                 ///
    ///////////////////////////////////////
    public List<AdjustmentProcess> generateAdjustmentProcesses(List<ElasticState> listOfFinalElasticStates) {

        List<AdjustmentProcess> listOfAdjustmentProcesses = new ArrayList<AdjustmentProcess>();

        for (ElasticState elasticState : listOfFinalElasticStates) {
            List<MetricCondition> listOfConditions = elasticState.getListOfConditions();

            List<AdjustmentAction> listOfAdjustmentActions = new ArrayList<AdjustmentAction>();
            for (MetricCondition metricCondition : listOfConditions) {
                AdjustmentAction adjustmentAction = findAdjustmentAction(metricCondition);
                listOfAdjustmentActions.add(adjustmentAction);
            }

            AdjustmentProcess adjustmentProcess = new AdjustmentProcess(elasticState, listOfAdjustmentActions, null);
            buildWorkflowForAdjustmentProcess(adjustmentProcess);
            listOfAdjustmentProcesses.add(adjustmentProcess);

        }

        return listOfAdjustmentProcesses;
    }

    private AdjustmentAction findAdjustmentAction(MetricCondition metricCondition) {
        List<AdjustmentAction> listOfAdjustmentActions = primitiveActionRepository.getListOfAdjustmentActions();

        AdjustmentAction foundAdjustmentAction = null;
        for (AdjustmentAction adjustmentAction : listOfAdjustmentActions) {
            if (metricCondition.getMetricName().equals(adjustmentAction.getAssociatedQoRMetric())) {
                List<AdjustmentCase> listOfAdjustmentCases = adjustmentAction.getListOfAdjustmentCases();

                for (AdjustmentCase adjustmentCase : listOfAdjustmentCases) {
                    if (adjustmentCase.getEstimatedResult().getLowerBound() == metricCondition.getLowerBound()
                            && adjustmentCase.getEstimatedResult().getUpperBound() == metricCondition.getUpperBound()) {

                        AdjustmentCase foundAdjustmentCase = new AdjustmentCase(
                                adjustmentCase.getEstimatedResult(),
                                adjustmentCase.getListOfParameters());

                        List<AdjustmentCase> listOfFoundAdjustmentCases = new ArrayList<AdjustmentCase>();
                        listOfFoundAdjustmentCases.add(foundAdjustmentCase);

                        foundAdjustmentAction = new AdjustmentAction(
                                adjustmentAction.getActionID(),
                                adjustmentAction.getActionName(),
                                adjustmentAction.getArtifact(),
                                adjustmentAction.getAssociatedQoRMetric(),
                                adjustmentAction.getListOfPrerequisiteActionIDs(),
                                listOfFoundAdjustmentCases);

                       
                    }

                }

                break;
            }
        }

        return foundAdjustmentAction;

    }

    private void buildWorkflowForAdjustmentProcess(AdjustmentProcess adjustmentProcess) {

        Logger.logInfo("BUILDING WORKFLOW FOR CONTROL PROCESS ................ ");

        int numberOfActionConnection = 0;

        
        List<AdjustmentAction> listOfAdjustmentActions = adjustmentProcess.getListOfAdjustmentActions();
        List<Action> listOfActions = new ArrayList<Action>();
     
        List<ParallelGateway> listOfParallelGateways = new ArrayList<ParallelGateway>();

        for (AdjustmentAction adjustmentAction : listOfAdjustmentActions) {
            String actionID = getUDID();
            adjustmentAction.setActionID(actionID);
            Action action = new Action(actionID, adjustmentAction.getActionName());
            listOfActions.add(action);
        }

        for (Action action : listOfActions) {

          
            List<String> listOfActionDependencies = findDependencyActions(action);

            Logger.logInfo("No of Dependency Actions: " + listOfActionDependencies.size());
            if (listOfActionDependencies.size() > 1) {

                ParallelGateway parallelGateway = new ParallelGateway();
                List<String> incomingList = new ArrayList<String>();
                List<String> outgoingList = new ArrayList<String>();

                outgoingList.add(action.getActionID());

                UUID parallelGatewayID = UUID.randomUUID();
                parallelGateway.setGatewayID(parallelGatewayID.toString());
                action.setIncomming(parallelGateway.getGatewayID());
                numberOfActionConnection++;

                Logger.logInfo("NEW Parallel Gateway ID: " + parallelGateway.getGatewayID());
                Logger.logInfo("PG set outgoing ID : " + action.getActionID());

                for (String actionDependency : listOfActionDependencies) {
                    
                    int prerequisiteActionIndex = findActionIndex(listOfActions, actionDependency);
                    Action prerequisiteAction = listOfActions.get(prerequisiteActionIndex);

                    Logger.logInfo("ActionDependency Name: " + prerequisiteAction.getActionName());
                    Logger.logInfo("ActionDependency ID: " + prerequisiteAction.getActionID());

                    prerequisiteAction.setOutgoing(parallelGateway.getGatewayID());
                    incomingList.add(prerequisiteAction.getActionID());
                    numberOfActionConnection++;

                }

                parallelGateway.setIncomming(incomingList);
                parallelGateway.setOutgoing(outgoingList);
                listOfParallelGateways.add(parallelGateway);

            } else if (listOfActionDependencies.size() == 1) {
                String actionDependency = listOfActionDependencies.get(0);
                int prerequisiteActionIndex = findActionIndex(listOfActions, actionDependency);
                Action prerequisiteAction = listOfActions.get(prerequisiteActionIndex);

                action.setIncomming(prerequisiteAction.getActionID());
                prerequisiteAction.setOutgoing(action.getActionID());
                numberOfActionConnection++;

            }

        }

        // MAKE START ACTIVITY
        List<Action> nullIncommingAdjustmentActions = new ArrayList<Action>();
        List<ParallelGateway> nullIncommingParallelGateways = new ArrayList<ParallelGateway>();

        for (Action ca : listOfActions) {
            if (ca.getIncomming() == null) {
                nullIncommingAdjustmentActions.add(ca);
            }

        }

        for (ParallelGateway pg: listOfParallelGateways){
            if(pg.getIncomming().isEmpty()){
                nullIncommingParallelGateways.add(pg);
            }
        }
        
        if (nullIncommingAdjustmentActions.size() >= 2) {
            List<String> startPGIncomingList = new ArrayList<String>();
            List<String> startPGOutgoingList = new ArrayList<String>();
            ParallelGateway startPG = new ParallelGateway();

            UUID startParallelGatewayID = UUID.randomUUID();
            startPG.setGatewayID(startParallelGatewayID.toString());

            for (Action ca : nullIncommingAdjustmentActions) {
                startPGOutgoingList.add(ca.getActionID());
                ca.setIncomming(startPG.getGatewayID());
                numberOfActionConnection++;
            }

        for (ParallelGateway pg: nullIncommingParallelGateways){
            startPGOutgoingList.add(pg.getGatewayID());
            pg.getIncomming().add(startPG.getGatewayID());
        }
            startPG.setIncomming(startPGIncomingList);
            startPG.setOutgoing(startPGOutgoingList);
            listOfParallelGateways.add(startPG);
        }

        // MAKE END ACTIVITY
        List<Action> nullOutgoingAdjustmentActions = new ArrayList<Action>();
         List<ParallelGateway> nullOutgoingParallelGateways = new ArrayList<ParallelGateway>();
        for (Action ca : listOfActions) {
            if (ca.getOutgoing() == null) {
                nullOutgoingAdjustmentActions.add(ca);
            }

        }

        for (ParallelGateway pg : listOfParallelGateways) {
            if (pg.getOutgoing().isEmpty()) {
                nullOutgoingParallelGateways.add(pg);
            }
        }
        if (nullOutgoingAdjustmentActions.size() >= 2) {

            List<String> endPGIncomingList = new ArrayList<String>();
            List<String> endPGOutgoingList = new ArrayList<String>();
            ParallelGateway endPG = new ParallelGateway();

            UUID endParallelGatewayID = UUID.randomUUID();
            endPG.setGatewayID(endParallelGatewayID.toString());

            for (Action ca : nullOutgoingAdjustmentActions) {
                endPGIncomingList.add(ca.getActionID());
                ca.setOutgoing(endPG.getGatewayID());
                numberOfActionConnection++;
            }

        for (ParallelGateway pg: nullOutgoingParallelGateways){
            endPGIncomingList.add(pg.getGatewayID());
            pg.getOutgoing().add(endPG.getGatewayID());
        }
            endPG.setIncomming(endPGIncomingList);
            endPG.setOutgoing(endPGOutgoingList);
            listOfParallelGateways.add(endPG);
        }

        
        DirectedAcyclicalGraph dag = new DirectedAcyclicalGraph();
        dag.setListOfActions(listOfActions);
        dag.setListOfParallelGateways(listOfParallelGateways);
        numberOfActionConnection += 2;
        int noOfActions = listOfActions.size();

        Logger.logInfo("no_of_connection: " + numberOfActionConnection);
        Logger.logInfo("no_of_action: " + noOfActions);
   
    }

    
        private int findActionIndex(List<Action> listOfActions,String prerequisiteAction){
        
        int index=0;
        
        for (Action ca : listOfActions){
            if (ca.getActionName().equals(prerequisiteAction)){
                index = listOfActions.indexOf(ca);
                break;
            }
            
        }
        
        return index;
    }
    
    private List<String> findDependencyActions(Action action){
        
        List<String> prerequisiteActionNames = new ArrayList<String>();
        List<AdjustmentAction> listOfAdjustmentActions = primitiveActionRepository.getListOfAdjustmentActions();
        
        for (AdjustmentAction adjustmentAction : listOfAdjustmentActions){
            if (adjustmentAction.getActionName().endsWith(action.getActionName())){
                prerequisiteActionNames = adjustmentAction.getListOfPrerequisiteActionIDs();
                break;
            }
        }
        
        return prerequisiteActionNames;
    }
    
//    private ElasticState copyElasticState(ElasticState eState){
//        
//        String eStateID = eState.geteStateID();
//        List<MetricCondition> conditions = eState.getListOfConditions();
//        List<MetricCondition> newConditions = new ArrayList<MetricCondition>();
//        
//        for (MetricCondition c : conditions){
//            MetricCondition newC = new MetricCondition(c.getMetricName(), c.getConditionID(), c.getLowerBound(), c.getUpperBound());
//            newConditions.add(newC);
//        }
//        
//        ElasticState newElasticState = new ElasticState(eStateID, newConditions);
//        return newElasticState;
//    }
//    
//    private List<ControlAction> copyListControlAction(List<ControlAction> listOfControlActions){
//        
//        List<ControlAction> nListCas =new ArrayList<ControlAction>();
//        
//        for (ControlAction ca: listOfControlActions){
//            
//            String caID = ca.getControlActionID();
//            String caName = ca.getControlActionName();
//            String incomming = ca.getIncomming();
//            String outgoing = ca.getOutgoing();
//            List<Parameter> listOfParameters = ca.getListOfParameters();
//            List<Parameter> nList = new ArrayList<Parameter>();
//            for (Parameter p: listOfParameters){
//                
//                Parameter nP = new Parameter(p.getParaName(), p.getType(), p.getValue());
//                nList.add(nP);
//                
//            }
//            ControlAction nCA = new ControlAction(caName, nList);
//            nCA.setControlActionID(caID);
//            nCA.setIncomming(incomming);
//            nCA.setOutgoing(outgoing);
//            nListCas.add(nCA);
//            
//        }
//        return nListCas;
//    }
//    
//    private List<ParallelGateway> copyParallelGateway(List<ParallelGateway> parallelGatewayList){
//        
//        List<ParallelGateway> nPGList = new ArrayList<ParallelGateway>();
//        
//        for (ParallelGateway pg: parallelGatewayList){
//            
//            List<String> inList = pg.getIncomming();
//            List<String> outList = pg.getOutgoing();
//            
//            List<String> nInList = new ArrayList<String>();
//            List<String> nOutList = new ArrayList<String>();
//            
//            for (String link : inList){
//                nInList.add(link);
//            }
//            
//            for (String link : outList){
//                nOutList.add(link);
//            }
//            
//            ParallelGateway nPG = new ParallelGateway(pg.getId(), nInList, nOutList);
//            nPGList.add(nPG);
//        }
//        
//        return nPGList;
//    }
//    

    
    
//     /*
//    public void deployElasticityProcess(ElasticityProcess elasticityProcesses){
//        
//        MonitorProcess monitorProcess = elasticityProcesses.getMonitorProcess();
//        
//        // deploy monitor actions
//        deployMonitorActions(monitorProcess);
//        List<ControlProcess> listOfControlProcesses = elasticityProcesses.getListOfControlProcesses();
//        //deploy control actions
//        deployControlActions(listOfControlProcesses);
//        
//        
//        
//        
//    }
//    
//   
//    private void deployMonitorActions(MonitorProcess monitorProcess){
//        List<MonitorAction> listOfMonitorActions = monitorProcess.getListOfMonitorActions();
//        List<DeployAction> listOfDeployActions = new ArrayList<>();
//        
//        for (MonitorAction monitorAction : listOfMonitorActions) {
//            
//            String actionID = monitorAction.getMonitorActionID();
//            ElasticityProcessRepositorty elasticityProcessRepositorty = new ElasticityProcessRepositorty();
//        
//            DeployAction deployAction = elasticityProcessRepositorty.getPrimitiveAction(actionID);
//            listOfDeployActions.add(deployAction);
//            
//        }
//        
//        DeploymentDescription deploymentDescription = new DeploymentDescription(listOfDeployActions);
//        
//        DeploymentDescriptionJAXB descriptionJAXB = new DeploymentDescriptionJAXB();
//        String xmlDescription = descriptionJAXB.marshallingObject(deploymentDescription);
//        
//        RestfulWSClient restfulWSClient = new RestfulWSClient("localhost", "8080", "/Orchestrator/webresources/DeploymentDescription");
//        restfulWSClient.callRestfulWebService(xmlDescription);
//
//    }
//    
//    */
//
//
//    
//    
//    
//    private Range getMetricRangeFromID(String rangeID){
//        Range rs = null;
//        
//        List<QoRMetric> listOfMetrics = qorModel.getListOfMetrics();
//        
//        for (QoRMetric qoRMetric : listOfMetrics){
//            
//            List<Range> listOfRanges = qoRMetric.getListOfRanges();
//            
//            for (Range range: listOfRanges){
//               // Logger.logInfo("range ID ith: " + range.getRangeID());
//                if (rangeID.equals(range.getRangeID())){
//                    rs = new Range(rangeID, range.getFromValue(), range.getToValue());
//                    //Logger.logInfo("RANGE CHECK: from value " + range.getFromValue() + " - to value: " + range.getToValue());
//                    
//                    break;
//                }
//                
//            }
//        }
//                
//        return rs;
//    }
//    
//  
//    
//    private ElasticState initializeElasticState(ElasticState elasticState, QElement qElement){
//        
//        
//        
//        List<MetricCondition> listOfMetricConditions = elasticState.getListOfConditions();
//        
//        List<MetricCondition> newlistOfMetricConditions = new ArrayList<MetricCondition>();
//        for (MetricCondition condition : listOfMetricConditions){
//
//            MetricCondition newMetricCondition = 
//                    new MetricCondition(
//                            condition.getMetricName(), 
//                            condition.getConditionID(),
//                            condition.getLowerBound(), 
//                            condition.getUpperBound());
//           newlistOfMetricConditions.add(newMetricCondition);
//        }
//
//        
//        
//        MetricCondition costCondition = new MetricCondition("cost",qElement.getqElementID(), qElement.getPrice(), qElement.getPrice());
//        newlistOfMetricConditions.add(costCondition);
//        
//        ElasticState newElasticState = new ElasticState(elasticState.geteStateID(), newlistOfMetricConditions);
//        
//        return newElasticState;
//    }
//    
//    
//    
//    private void config(){
//        ElasticProcessRepositoryManager epRepo = new ElasticProcessRepositoryManager();
//        ActionDependencySet = epRepo.getAllActionDependencies();
//        
//    }

    
    
}