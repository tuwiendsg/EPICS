/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.process.generator;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.Action;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticState;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MetricCondition;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.AdjustmentProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.DirectedAcyclicalGraph;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.DataElasticityManagementProcess;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.MonitoringProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ParallelGateway;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ResourceControlPlan;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentCase;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AnalyticTask;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Artifact;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlCase;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlStrategy;
import at.ac.tuwien.dsg.depic.common.entity.qor.QElement;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRMetric;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.depic.common.entity.qor.Range;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;

import at.ac.tuwien.dsg.depic.common.utils.Logger;
import static at.ac.tuwien.dsg.depic.common.utils.YamlUtils.toYaml;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class DataElasticityManagementProcessesGenerator {

    DataAnalyticsFunction daf;
    QoRModel qorModel;
    PrimitiveActionMetadata primitiveActionRepository;
    List<ElasticState> finalElasticStates;
    String errorLog;
    String rootPath;

    public DataElasticityManagementProcessesGenerator() {
    }

    public DataElasticityManagementProcessesGenerator(DataAnalyticsFunction daf, QoRModel qorModel, PrimitiveActionMetadata primitiveActionRepository) {
        this.daf = daf;
        this.qorModel = qorModel;
        this.primitiveActionRepository = primitiveActionRepository;
        errorLog = "";
    }
    
    public DataElasticityManagementProcessesGenerator(DataAnalyticsFunction daf, QoRModel qorModel, PrimitiveActionMetadata primitiveActionRepository, String rooPath) {
        this.daf = daf;
        this.qorModel = qorModel;
        this.primitiveActionRepository = primitiveActionRepository;
        errorLog = "";
        this.rootPath = rooPath;
    }

    public DataElasticityManagementProcess generateElasticProcesses() {
        Logger.logInfo("Start generate Elastic Processes ... ");

        MonitoringProcess monitorProcess = generateMonitoringProcess();
        toYaml(monitorProcess, "monitorProcess.yml");

        finalElasticStates = generateFinalElasticStateSet();
        toYaml(finalElasticStates, "finalElasticStates.yml");

        List<AdjustmentProcess> listOfAdjustmentProcesses = generateAdjustmentProcesses(finalElasticStates);
        toYaml(listOfAdjustmentProcesses, "listOfAdjustmentProcesses.yml");

        List<ResourceControlPlan> listOfResourceControlPlans = generateResourceControlPlan(finalElasticStates);
        toYaml(listOfResourceControlPlans, "listOfResourceControlPlans.yml");

        DataElasticityManagementProcess elasticProcess = new DataElasticityManagementProcess(monitorProcess, listOfAdjustmentProcesses, listOfResourceControlPlans);

        IOUtils iou = new IOUtils(rootPath);
        iou.writeData(errorLog, "errorLog.txt");

        return elasticProcess;
    }

    public List<ElasticState> getFinalElasticStates() {
        return finalElasticStates;
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
            if (monitoringAction != null) {
                listOfMonitoringActions.add(monitoringAction);
            }
        }
        System.err.println("No of monitoring actions: " + listOfMonitoringActions.size());
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

        Artifact artifact = new Artifact(
                ma.getArtifact().getName(),
                ma.getArtifact().getDescription(),
                ma.getArtifact().getLocation(),
                ma.getArtifact().getType(),
                ma.getArtifact().getRestfulAPI());

        List<Parameter> listOfParameters = new ArrayList<Parameter>();

        for (Parameter param : ma.getListOfParameters()) {

            Parameter paramI = new Parameter(
                    param.getParameterName(),
                    param.getType(),
                    param.getValue());

            listOfParameters.add(paramI);

        }

        MonitoringAction monitoringAction = new MonitoringAction(
                ma.getMonitorActionID(),
                ma.getMonitoringActionName(),
                artifact,
                ma.getAssociatedQoRMetric(),
                listOfParameters);

        return monitoringAction;
    }

    private MonitoringProcess parallelizeMonitoringActions(List<MonitoringAction> listOfMonitoringActions) {

        List<Action> listOfAction = new ArrayList<Action>();

        for (MonitoringAction ma : listOfMonitoringActions) {

            String actionID = getUDID();
            ma.setMonitorActionID(actionID);

            Action action = new Action(actionID, ma.getMonitoringActionName());
            listOfAction.add(action);
        }

        String parallelGatewayInId = getUDID();
        String parallelGatewayOutId = getUDID();
        List<String> inputListOfIncommings = new ArrayList<String>();
        List<String> inputlistOfOutgoings = new ArrayList<String>();
        List<String> outputListOfIncommings = new ArrayList<String>();
        List<String> outListOfOutgoings = new ArrayList<String>();

        for (Action action : listOfAction) {
            action.setIncomming(parallelGatewayInId);
            action.setOutgoing(parallelGatewayOutId);
            inputlistOfOutgoings.add(action.getActionID());
            outputListOfIncommings.add(action.getActionID());
        }

        ParallelGateway pg_in = new ParallelGateway(parallelGatewayInId, inputListOfIncommings, inputlistOfOutgoings);
        ParallelGateway pg_out = new ParallelGateway(parallelGatewayOutId, outputListOfIncommings, outListOfOutgoings);

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
    public List<ElasticState> generateFinalElasticStateSet() {

        List<ElasticState> listOfFinalElasticStates = new ArrayList<ElasticState>();
        List<QElement> listOfQElements = qorModel.getListOfQElements();

        for (QElement qElement : listOfQElements) {
            List<ElasticState> listOfFinalElasticState_qelement = decomposeQElement(qElement);

            if (listOfFinalElasticState_qelement != null) {
                listOfFinalElasticStates.addAll(listOfFinalElasticState_qelement);
            } else {

            }
        }
        return listOfFinalElasticStates;
    }

    private List<ElasticState> decomposeQElement(QElement qElement) {

        //List<String> listOfRangeID =  qElement.getListOfRanges();
        List<QoRMetric> listOfQoRMetrics = qorModel.getListOfMetrics();

        List<List> listOfConditionSet = new ArrayList<List>();

        int counter = 0;
        for (QoRMetric qorMetric : listOfQoRMetrics) {

            System.err.println("Metric: " + qorMetric.getName());

            List<MetricCondition> listOfConditions = findEstimatedResultForQoRMetric(qorMetric);

            System.err.println("no of conditions: " + listOfConditions.size());

            if (listOfConditions.size() > 0) {
                Range r = findMatchingRange(qElement, qorMetric);

                if (r != null) {

                    List<MetricCondition> unitConditions = new ArrayList<MetricCondition>();

                    for (MetricCondition condition : listOfConditions) {
                        System.err.println("condition: " + condition.getLowerBound() + " - " + condition.getUpperBound());
                        System.err.println("qor: " + r.getFromValue() + " - " + r.getToValue());

                        if (condition.getLowerBound() == r.getFromValue() && condition.getUpperBound() == r.getToValue()) {
                            unitConditions.add(condition);
                        } else if (condition.getLowerBound() > r.getFromValue() && condition.getUpperBound() < r.getToValue()) {

                            MetricCondition condition_pre = new MetricCondition(
                                    condition.getMetricName(),
                                    condition.getConditionID() + "_" + String.valueOf(++counter),
                                    r.getFromValue(),
                                    condition.getLowerBound());

                            MetricCondition condition_post = new MetricCondition(
                                    condition.getMetricName(),
                                    condition.getConditionID() + "_" + String.valueOf(++counter),
                                    condition.getUpperBound(),
                                    r.getToValue());

                            unitConditions.add(condition);
                            unitConditions.add(condition_pre);
                            unitConditions.add(condition_post);

                        } else if (condition.getLowerBound() > r.getFromValue() && condition.getUpperBound() == r.getToValue()) {

                            MetricCondition condition_pre = new MetricCondition(
                                    condition.getMetricName(),
                                    condition.getConditionID() + "_" + String.valueOf(++counter),
                                    r.getFromValue(),
                                    condition.getLowerBound());
                            unitConditions.add(condition);
                            unitConditions.add(condition_pre);

                        } else if (condition.getLowerBound() == r.getFromValue() && condition.getUpperBound() < r.getToValue()) {
                            MetricCondition condition_post = new MetricCondition(
                                    condition.getMetricName(),
                                    condition.getConditionID() + "_" + String.valueOf(++counter),
                                    condition.getUpperBound(),
                                    r.getToValue());

                            unitConditions.add(condition);
                            unitConditions.add(condition_post);
                        } else if (condition.getLowerBound() < r.getFromValue() && condition.getUpperBound() >= r.getToValue()) {
                            errorLog = errorLog + "\n Can not decompose conditions of metric " + qorMetric.getName() + " in " + qElement.getqElementID() + ".";

                            System.err.println(errorLog);

                        } else if (condition.getLowerBound() <= r.getFromValue() && condition.getUpperBound() > r.getToValue()) {
                            errorLog = errorLog + "\n Can not decompose conditions of metric " + qorMetric.getName() + " in " + qElement.getqElementID() + ".";

                            System.err.println(errorLog);

                        } else {
                            MetricCondition condition_u = new MetricCondition(
                                    condition.getMetricName(),
                                    condition.getConditionID() + "_" + String.valueOf(++counter),
                                    r.getFromValue(),
                                    r.getToValue());
                            unitConditions.add(condition_u);

                        }
                    }

                    if (unitConditions.size() > 0) {

                        for (MetricCondition mc : unitConditions) {
                            System.err.println("mc id: " + mc.getConditionID());
                            System.err.println("mc name: " + mc.getMetricName());
                            System.err.println("mc l: " + mc.getLowerBound());
                            System.err.println("mc u: " + mc.getUpperBound());
                        }

                        listOfConditionSet.add(unitConditions);
                    }

                }
            }
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
            System.err.println("aa1: " + adjustmentAction.getAssociatedQoRMetric());

            if (qorMetric.getName().equals(adjustmentAction.getAssociatedQoRMetric())) {
                System.err.println("aa2: " + adjustmentAction.getAssociatedQoRMetric());
                System.err.println("aa3: " + adjustmentAction.getListOfAdjustmentCases().size());
                listOfAdjustmentCases = adjustmentAction.getListOfAdjustmentCases();
                break;
            }
        }
        
        
        if (listOfAdjustmentCases==null && !isAssociatedWithResourceControlAction(qorMetric.getName())) {      
            errorLog = errorLog + "\n No adjustment action found for metric " + qorMetric.getName() + ". Conditions of this metric are not added to eState.";                        
            System.err.println(errorLog);
        }
        
        

        List<MetricCondition> listOfConditions = new ArrayList<MetricCondition>();

        if (listOfAdjustmentCases != null) {

            for (AdjustmentCase adjustmentCase : listOfAdjustmentCases) {

                if (adjustmentCase.getEstimatedResult() != null) {

                    MetricCondition condition = adjustmentCase.getEstimatedResult();
                    System.err.println("a a1: " + condition.getLowerBound());
                    System.err.println("a a2: " + condition.getUpperBound());
                    if (condition != null) {
                        listOfConditions.add(condition);
                    }
                } else {
                    errorLog = errorLog + "\n No estimated results for metric " + qorMetric.getName() + ". Need to customize elasticity actions for this metric in monitoring/adjustment process.";

                    System.err.println(errorLog);
                }

            }
        }

        System.err.println("No of conditions: " + listOfConditions.size());

        /////////////
        List<ResourceControlAction> listOfResourceControlActions = primitiveActionRepository.getListOfResourceControls();

        List<ResourceControlCase> listOfResourceControlCases = null;

        for (ResourceControlAction resourceControlAction : listOfResourceControlActions) {
            if (qorMetric.getName().equals(resourceControlAction.getAssociatedQoRMetric())) {
                listOfResourceControlCases = resourceControlAction.getListOfResourceControlCases();
            }
        }

        if (listOfResourceControlCases != null) {
            for (ResourceControlCase resourceControlCase : listOfResourceControlCases) {
                MetricCondition condition = resourceControlCase.getEstimatedResult();
                listOfConditions.add(condition);
            }
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
                if (adjustmentAction != null) {
                    listOfAdjustmentActions.add(adjustmentAction);
                }
            }

            AdjustmentProcess adjustmentProcess = new AdjustmentProcess(elasticState, listOfAdjustmentActions, null);
            buildWorkflowForAdjustmentProcess(adjustmentProcess);
            listOfAdjustmentProcesses.add(adjustmentProcess);

        }

        return listOfAdjustmentProcesses;
    }

    private boolean isAssociatedWithResourceControlAction(String metricName) {
        boolean rs = false;
        List<ResourceControlAction> listOfResourceControlActions = primitiveActionRepository.getListOfResourceControls();

        for (ResourceControlAction rc : listOfResourceControlActions) {
            if (metricName.equals(rc.getAssociatedQoRMetric())) {
                rs = true;
                break;
            }
        }

        return rs;
    }

    private boolean isAssociatedWithAdjustmentAction(String metricName) {
        boolean rs = false;
        List<AdjustmentAction> listOfAdjustmentActions = primitiveActionRepository.getListOfAdjustmentActions();

        for (AdjustmentAction adjustmentAction : listOfAdjustmentActions) {
            if (metricName.equals(adjustmentAction.getAssociatedQoRMetric())) {
                rs = true;
                break;
            }
        }

        return rs;
    }

    private AdjustmentAction findAdjustmentAction(MetricCondition metricCondition) {
        List<AdjustmentAction> listOfAdjustmentActions = primitiveActionRepository.getListOfAdjustmentActions();

        AdjustmentAction foundAdjustmentAction = null;
        for (AdjustmentAction adjustmentAction : listOfAdjustmentActions) {
            if (metricCondition.getMetricName().equals(adjustmentAction.getAssociatedQoRMetric())) {
                List<AdjustmentCase> listOfAdjustmentCases = adjustmentAction.getListOfAdjustmentCases();

                for (AdjustmentCase adjustmentCase : listOfAdjustmentCases) {

                    if (adjustmentCase.getEstimatedResult() == null) {
                        

                    } else {

                        if (adjustmentCase.getEstimatedResult().getLowerBound() >= metricCondition.getLowerBound()
                                && adjustmentCase.getEstimatedResult().getUpperBound() <= metricCondition.getUpperBound()) {

                            if (adjustmentCase.getAnalyticTask() == null) {
                                MetricCondition estimatedResult = adjustmentCase.getEstimatedResult();
                                MetricCondition estimatedResult_c = new MetricCondition(estimatedResult.getMetricName(), estimatedResult.getConditionID(), estimatedResult.getLowerBound(), estimatedResult.getUpperBound());
                                List<Parameter> listOfParams = adjustmentCase.getListOfParameters();
                                List<Parameter> listOfParams_c = new ArrayList<Parameter>();
                                for (Parameter param : listOfParams) {
                                    Parameter param_c = new Parameter(param.getParameterName(), param.getType(), param.getValue());
                                    listOfParams_c.add(param_c);
                                }

                                AdjustmentCase foundAdjustmentCase = new AdjustmentCase(
                                        estimatedResult_c,
                                        adjustmentCase.getAnalyticTask(),
                                        listOfParams_c);

                                List<AdjustmentCase> listOfFoundAdjustmentCases = new ArrayList<AdjustmentCase>();
                                listOfFoundAdjustmentCases.add(foundAdjustmentCase);

                                foundAdjustmentAction = new AdjustmentAction(
                                        adjustmentAction.getActionID(),
                                        adjustmentAction.getActionName(),
                                        adjustmentAction.getArtifact(),
                                        adjustmentAction.getAssociatedQoRMetric(),
                                        adjustmentAction.getListOfPrerequisiteActionIDs(),
                                        listOfFoundAdjustmentCases);

                                System.err.println("Found Action: " + adjustmentAction.getActionName());
                                System.err.println("Metric Condtidion: " + metricCondition.getMetricName() + " - " + metricCondition.getLowerBound() + " - " + metricCondition.getUpperBound());
                                System.err.println("Estimated Result: " + estimatedResult.getLowerBound() + " - " + estimatedResult.getUpperBound());
                            } else {
                                if (matchingAnalyticTaskFromDAF(adjustmentCase.getAnalyticTask())) {
                                    MetricCondition estimatedResult = adjustmentCase.getEstimatedResult();
                                    MetricCondition estimatedResult_c = new MetricCondition(estimatedResult.getMetricName(), estimatedResult.getConditionID(), estimatedResult.getLowerBound(), estimatedResult.getUpperBound());
                                    List<Parameter> listOfParams = adjustmentCase.getListOfParameters();
                                    List<Parameter> listOfParams_c = new ArrayList<Parameter>();
                                    for (Parameter param : listOfParams) {
                                        Parameter param_c = new Parameter(param.getParameterName(), param.getType(), param.getValue());
                                        listOfParams_c.add(param_c);
                                    }

                                    AdjustmentCase foundAdjustmentCase = new AdjustmentCase(
                                            estimatedResult_c,
                                            adjustmentCase.getAnalyticTask(),
                                            listOfParams_c);

                                    List<AdjustmentCase> listOfFoundAdjustmentCases = new ArrayList<AdjustmentCase>();
                                    listOfFoundAdjustmentCases.add(foundAdjustmentCase);

                                    foundAdjustmentAction = new AdjustmentAction(
                                            adjustmentAction.getActionID(),
                                            adjustmentAction.getActionName(),
                                            adjustmentAction.getArtifact(),
                                            adjustmentAction.getAssociatedQoRMetric(),
                                            adjustmentAction.getListOfPrerequisiteActionIDs(),
                                            listOfFoundAdjustmentCases);
                                } else {
                                    errorLog = errorLog + "\n Analytic Task " + adjustmentCase.getAnalyticTask().getTaskName() 
                                            + " in daf does not match. Please customize elasticity actions for metric "+metricCondition.getMetricName();
                                    System.err.println(errorLog);
                                }
                            }

                        }
                    }

                }

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

                if (!prerequisiteAction.getActionID().equals(action.getOutgoing())) {
                    action.setIncomming(prerequisiteAction.getActionID());
                    prerequisiteAction.setOutgoing(action.getActionID());
                    numberOfActionConnection++;
                }
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

        for (ParallelGateway pg : listOfParallelGateways) {
            if (pg.getIncomming().isEmpty()) {
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

            for (ParallelGateway pg : nullIncommingParallelGateways) {
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

            for (ParallelGateway pg : nullOutgoingParallelGateways) {
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
        adjustmentProcess.setDirectedAcyclicalGraph(dag);

        Logger.logInfo("no_of_connection: " + numberOfActionConnection);
        Logger.logInfo("no_of_action: " + noOfActions);

    }

    private int findActionIndex(List<Action> listOfActions, String prerequisiteAction) {

        int index = 0;

        for (Action ca : listOfActions) {
            if (ca.getActionName().equals(prerequisiteAction)) {
                index = listOfActions.indexOf(ca);
                break;
            }

        }

        return index;
    }

    private List<String> findDependencyActions(Action action) {

        List<String> prerequisiteActionNames = new ArrayList<String>();
        List<AdjustmentAction> listOfAdjustmentActions = primitiveActionRepository.getListOfAdjustmentActions();

        for (AdjustmentAction adjustmentAction : listOfAdjustmentActions) {
            if (adjustmentAction.getActionName().endsWith(action.getActionName())) {
                prerequisiteActionNames = adjustmentAction.getListOfPrerequisiteActionIDs();
                break;
            }
        }

        return prerequisiteActionNames;
    }

    private boolean matchingAnalyticTaskFromDAF(AnalyticTask pamAnalyticTask) {
        AnalyticTask analyticTask = null;
        try {
            String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";

            String daw = daf.getDaw();

            int beginIndex = daw.indexOf("<depic>");
            int endIndex = daw.indexOf("</depic>");
            String analyticTasksStr = header + daw.substring(beginIndex + 7, endIndex);
            analyticTask = JAXBUtils.unmarshal(analyticTasksStr, AnalyticTask.class);

        } catch (JAXBException ex) {
            java.util.logging.Logger.getLogger(DataElasticityManagementProcessesGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean rs = true;
        if (pamAnalyticTask != null && analyticTask != null) {

            if (pamAnalyticTask.getTaskName().equals(analyticTask.getTaskName())) {

                List<Parameter> listOfParameters1 = analyticTask.getParameters();
                List<Parameter> listOfParameters2 = pamAnalyticTask.getParameters();

                for (Parameter pam1 : listOfParameters1) {
                    for (Parameter pam2 : listOfParameters2) {
                        if (pam1.getParameterName().equals(pam2.getParameterName())) {
                            if (!pam1.getValue().equals(pam2.getValue())) {
                                rs = false;
                            }
                        }

                    }

                }

            } else {
                rs = false;
            }

        } else {
            rs = false;
        }

        return rs;

    }

    ///////////////////////////////////////
    ///                                 ///
    /// Resource Control PLan           ///
    ///                                 ///
    ///////////////////////////////////////
    private List<ResourceControlPlan> generateResourceControlPlan(List<ElasticState> listOfFinalElasticStates) {

        List<ResourceControlPlan> listOfFoundResourceControlPlans = new ArrayList<ResourceControlPlan>();

        for (ElasticState elasticState : listOfFinalElasticStates) {
            List<MetricCondition> listOfConditions = elasticState.getListOfConditions();
            
            List<ResourceControlStrategy> resourceControlStrategiesForEState = new ArrayList<ResourceControlStrategy>();
            
            for (MetricCondition metricCondition : listOfConditions) {
                List<ResourceControlStrategy> listOfFoundResourceControlStrategies = findResourceControlStrategy(metricCondition);
                if (listOfFoundResourceControlStrategies.size() > 0) {
                    resourceControlStrategiesForEState.addAll(listOfFoundResourceControlStrategies);
                } 
            }
            
            for (int i=0; i<resourceControlStrategiesForEState.size();i++){
                for (int j=0; j<resourceControlStrategiesForEState.size();j++) {
                    if (i!=j) {
                        if (resourceControlStrategiesForEState.get(i).getPrimitiveAction().equals(resourceControlStrategiesForEState.get(j).getPrimitiveAction())){
                            errorLog = errorLog + "\n Duplicate resource strategy for primitive action " + resourceControlStrategiesForEState.get(i).getPrimitiveAction();
                            System.err.println(errorLog);
                        }
                    }  
                }
            }
            
            
            
            ResourceControlPlan resourceControlPlan = new ResourceControlPlan(elasticState, resourceControlStrategiesForEState);
                    listOfFoundResourceControlPlans.add(resourceControlPlan);

        }

        return listOfFoundResourceControlPlans;
    }

    private List<ResourceControlStrategy> findResourceControlStrategy(MetricCondition metricCondition) {
        List<ResourceControlAction> listOfResourceControls = primitiveActionRepository.getListOfResourceControls();

        List<ResourceControlStrategy> foundListOfResourceControlStrategies = new ArrayList<ResourceControlStrategy>();
        for (ResourceControlAction rc : listOfResourceControls) {
            if (metricCondition.getMetricName().equals(rc.getAssociatedQoRMetric())) {

                List<ResourceControlCase> listOfResourceControlCases = rc.getListOfResourceControlCases();

                for (ResourceControlCase resourceControlCase : listOfResourceControlCases) {
                    if (resourceControlCase.getEstimatedResult().getLowerBound() == metricCondition.getLowerBound()
                            && resourceControlCase.getEstimatedResult().getUpperBound() == metricCondition.getUpperBound()) {

                        foundListOfResourceControlStrategies.addAll(copyListOfResourceControlStrategy(resourceControlCase.getListOfResourceControlStrategies()));

                        break;

                    }

                }

            }
        }

        return foundListOfResourceControlStrategies;

    }

    private List<ResourceControlStrategy> copyListOfResourceControlStrategy(List<ResourceControlStrategy> originalList) {

        List<ResourceControlStrategy> copyList = new ArrayList<ResourceControlStrategy>();

        for (ResourceControlStrategy rca : originalList) {
            MetricCondition scaleOutCo = rca.getScaleOutCondition();
            MetricCondition scaleOutCo_c = new MetricCondition(scaleOutCo.getMetricName(), scaleOutCo.getConditionID(), scaleOutCo.getLowerBound(), scaleOutCo.getUpperBound());
            MetricCondition scaleInCo = rca.getScaleInCondition();
            MetricCondition scaleInCo_c = new MetricCondition(scaleInCo.getMetricName(), scaleInCo.getConditionID(), scaleInCo.getLowerBound(), scaleInCo.getUpperBound());
            String controlMetric = rca.getControlMetric();
            String primitiveAction = rca.getPrimitiveAction();
            ResourceControlStrategy rca_copy = new ResourceControlStrategy(scaleInCo_c, scaleOutCo_c, controlMetric, primitiveAction);
            copyList.add(rca_copy);
        }

        return copyList;
    }

}