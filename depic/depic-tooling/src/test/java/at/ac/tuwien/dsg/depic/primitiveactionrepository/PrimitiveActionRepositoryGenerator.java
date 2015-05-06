/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.primitiveactionrepository;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Artifact;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MetricCondition;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentCase;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlCase;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Jun
 */
public class PrimitiveActionRepositoryGenerator {

    public PrimitiveActionRepositoryGenerator() {
    }

    public PrimitiveActionMetadata getData() {

        List<MonitoringAction> listOfMonitoringActions = getMonitoringActionData();
        List<AdjustmentAction> listOfControlActions = getControlActionData();
        List<ResourceControlAction> listOfResourceControls = getResourceControlData();
        PrimitiveActionMetadata primitiveActionRepository
                = new PrimitiveActionMetadata(listOfControlActions, listOfMonitoringActions, listOfResourceControls);

        return primitiveActionRepository;
    }

    private List<MonitoringAction> getMonitoringActionData() {

        List<MonitoringAction> listOfMonitoringActions = new ArrayList<MonitoringAction>();

        MonitoringAction ma1 = monitoringActionColumnCompletenessMeasurement();
        listOfMonitoringActions.add(ma1);
        
        MonitoringAction ma2 = monitoringActionDataAccuracyForVotageMeasurement();
        listOfMonitoringActions.add(ma2);
        
        MonitoringAction ma3 = monitoringActionThroughputDataItemMeasurement();
        listOfMonitoringActions.add(ma3);
        
        MonitoringAction ma4 = monitoringActionDataAccuracyForImpressionMeasurement();
        listOfMonitoringActions.add(ma4);
        
        MonitoringAction ma5 = monitoringActionDataAccuracyForPositionMeasurement();
        listOfMonitoringActions.add(ma5);
        
        MonitoringAction ma6 = monitoringActionThroughputForDataAssetPerHour();
        listOfMonitoringActions.add(ma6);
        

        return listOfMonitoringActions;
    }

    private List<AdjustmentAction> getControlActionData() {

        List<AdjustmentAction> listOfControlActions = new ArrayList<AdjustmentAction>();

        AdjustmentAction ca1 = controlActionLSR();
        listOfControlActions.add(ca1);
        
        AdjustmentAction ca2 = controlActionDIR();
        listOfControlActions.add(ca2);
        
        AdjustmentAction ca3 = controlActionThroughputDataItemsPerSecond();
        listOfControlActions.add(ca3);
        
        AdjustmentAction ca4 = controlActionAIC();
        listOfControlActions.add(ca4);
        
        AdjustmentAction ca5 = controlActionAPC();
        listOfControlActions.add(ca5);
        
        AdjustmentAction ca6 = controlActionSTC();
        listOfControlActions.add(ca6);
        

        return listOfControlActions;
    }
    
    private List<ResourceControlAction> getResourceControlData() {
        
        
        List<ResourceControlAction> listResourceCotrols = new ArrayList<ResourceControlAction>();
        
        List<ResourceControlCase> listOfResourceControlStrategys = new ArrayList<ResourceControlCase>();
        listOfResourceControlStrategys.add(resourceControlStrategy());
        ResourceControlAction rc = new ResourceControlAction("throughput", listOfResourceControlStrategys);
        
        listResourceCotrols.add(rc);
                
         return listResourceCotrols;
    } 

    /////////////////////////////
    //
    //
    //  Monitoring Actions 
    //
    //
    /////////////////////////////
    private MonitoringAction monitoringActionColumnCompletenessMeasurement() {

        // power consumption - data completeness
        String monitorActionID = "columnCompletenessMeasurement";

        String monitoringActionName = "columnCompletenessMeasurement";

        Artifact artifact = new Artifact(
                monitorActionID,
                "artifact to monitor column completeness",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/datacompletenessMeasurement.sh",
                "sh",
                "/datacompletenessMeasurement/rest/completeness");

        double costPerHour = 0.35;

        String associatedQoRMetric = "columnCompleteness";

        Parameter param1 = new Parameter("attributeIndex", "int", "");
        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        listOfParameters.add(param1);

        MonitoringAction monitoringAction = new MonitoringAction(
                monitorActionID,
                monitoringActionName,
                artifact,
                costPerHour,
                associatedQoRMetric,
                listOfParameters);

        return monitoringAction;

    }

    private MonitoringAction monitoringActionDataAccuracyForVotageMeasurement() {

        // power consumption - data accuracy
        String monitorActionID = "distanceBasedOutlierMeasurement";
        String monitoringActionName = "distanceBasedOutlierMeasurement";

        Artifact artifact = new Artifact(
                monitorActionID,
                "measure outliers to determine data accuracy",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/dataaccuracyMeasurement.sh",
                "sh",
                "/dataaccuracyMeasurement/rest/dataaccuracy");

        double costPerHour = 0.35;

        String associatedQoRMetric = "dataAccuracyForVotage";

        Parameter param1 = new Parameter("attributeIndex", "int", "");
        Parameter param2 = new Parameter("lowerThreshold", "int", "220");
        Parameter param3 = new Parameter("upperThreshold", "int", "240");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        listOfParameters.add(param1);
        listOfParameters.add(param2);
        listOfParameters.add(param3);

        MonitoringAction monitoringAction = new MonitoringAction(
                monitorActionID,
                monitoringActionName,
                artifact,
                costPerHour,
                associatedQoRMetric,
                listOfParameters);

        return monitoringAction;

    }

    private MonitoringAction monitoringActionThroughputDataItemMeasurement() {

        // power consumption - throughput
        String monitorActionID = "TPM";
        String monitoringActionName = "TPM";

        Artifact artifact = new Artifact(
                monitorActionID,
                "measure throughput - data items per second",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/TPM.sh",
                "sh",
                "/TPM/rest/monitor");

        double costPerHour = 0.35;

        String associatedQoRMetric = "throughputOfDataItemPerSecond";

        Parameter param1 = new Parameter("unit", "String", "dataItems/second");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        listOfParameters.add(param1);

        MonitoringAction monitoringAction = new MonitoringAction(
                monitorActionID,
                monitoringActionName,
                artifact,
                costPerHour,
                associatedQoRMetric,
                listOfParameters);

        return monitoringAction;

    }

    private MonitoringAction monitoringActionDataAccuracyForImpressionMeasurement() {

        // kdd - Ads impression accuracy
        String monitorActionID = "AIM";

        String monitoringActionName = "AIM";

        Artifact artifact = new Artifact(
                monitorActionID,
                "monitor impression accuracy",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/AIM.sh",
                "sh",
                "/AIM/rest/monitor");

        double costPerHour = 0.35;

        String associatedQoRMetric = "adsImpressionAccuracy";

        Parameter param1 = new Parameter("attributeIndex", "int", "");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        listOfParameters.add(param1);

        MonitoringAction monitoringAction = new MonitoringAction(
                monitorActionID,
                monitoringActionName,
                artifact,
                costPerHour,
                associatedQoRMetric,
                listOfParameters);

        return monitoringAction;

    }

    private MonitoringAction monitoringActionDataAccuracyForPositionMeasurement() {

        // kdd - Ads position accuracy
        String monitorActionID = "APM";

        String monitoringActionName = "APM";

        Artifact artifact = new Artifact(
                monitorActionID,
                "monitor Ads position accuracy",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/APM.sh",
                "sh",
                "/APM/rest/monitor");

        double costPerHour = 0.35;

        String associatedQoRMetric = "adsPositionAccuracy";

        Parameter param1 = new Parameter("attributeIndex", "int", "");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        listOfParameters.add(param1);

        MonitoringAction monitoringAction = new MonitoringAction(
                monitorActionID,
                monitoringActionName,
                artifact,
                costPerHour,
                associatedQoRMetric,
                listOfParameters);

        return monitoringAction;

    }

    private MonitoringAction monitoringActionThroughputForDataAssetPerHour() {

        // kdd - throughput
        String monitorActionID = "TPM";
        String monitoringActionName = "TPM";

        Artifact artifact = new Artifact(
                monitorActionID,
                "measure throughput - data assets per hour",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/TPM.sh",
                "sh",
                "/TPM/rest/monitor");

        double costPerHour = 0.35;

        String associatedQoRMetric = "throughputOfDataAssetsPerHour";

        Parameter param1 = new Parameter("unit", "String", "dataAsset/hour");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        listOfParameters.add(param1);

        MonitoringAction monitoringAction = new MonitoringAction(
                monitorActionID,
                monitoringActionName,
                artifact,
                costPerHour,
                associatedQoRMetric,
                listOfParameters);

        return monitoringAction;

    }

    /////////////////////////////
    //
    //
    //  Control Actions 
    //
    //
    /////////////////////////////
    private AdjustmentAction controlActionLSR() {

    // power - data completenes    
        String controlActionID = "LSR";
        String controlActionName = "LSR";
        Artifact artifact = new Artifact(
                controlActionName,
                "Linear Least Square Regression to fill missing values",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/LSR.sh",
                "sh",
                "/LSR/rest/control");

        double costPerHour = 0.45;
        String associatedQoRMetric = "columnCompletenessForVoltage";
        List<String> listOfPrerequisiteActionIDs = new ArrayList<String>();
    //listOfPrerequisiteActionIDs.add("");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        Parameter param1 = new Parameter("attributeIndex", "int", "");
        listOfParameters.add(param1);

        MetricCondition c1 = new MetricCondition(associatedQoRMetric, "c1", 0, 75);
        MetricCondition c2 = new MetricCondition(associatedQoRMetric, "c2", 76, 90);
        MetricCondition c3 = new MetricCondition(associatedQoRMetric, "c3", 91, 100);
    
        AdjustmentCase transition1 = new AdjustmentCase( c3, listOfParameters);
        AdjustmentCase transition2 = new AdjustmentCase( c3, listOfParameters);

        List<AdjustmentCase> listOfTransitions = new ArrayList<AdjustmentCase>();
        listOfTransitions.add(transition1);
        listOfTransitions.add(transition2);
      
        
        AdjustmentAction controlAction = new AdjustmentAction(
                controlActionID,
                controlActionName,
                artifact,
                associatedQoRMetric,
                listOfPrerequisiteActionIDs,
                listOfTransitions);

        return controlAction;

    }

    private AdjustmentAction controlActionDIR() {

    // power - data accuracy  
        String controlActionID = "DIR";
        String controlActionName = "DIR";
        Artifact artifact = new Artifact(
                controlActionName,
                "Distance-based Outlier Detection and Removal to improve data accuracy",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/DIR.sh",
                "sh",
                "/DIR/rest/control");

        double costPerHour = 0.45;
        String associatedQoRMetric = "dataAccuracyForVoltage";
        List<String> listOfPrerequisiteActionIDs = new ArrayList<String>();
        //listOfPrerequisiteActionIDs.add("");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        Parameter param1 = new Parameter("attributeIndex", "int", "");
        Parameter param2 = new Parameter("lowerThreshold", "int", "220");
        Parameter param3 = new Parameter("upperThreshold", "int", "240");
        listOfParameters.add(param1);
        listOfParameters.add(param2);
        listOfParameters.add(param3);

        MetricCondition c1 = new MetricCondition(associatedQoRMetric, "c1", 0, 75);
        MetricCondition c2 = new MetricCondition(associatedQoRMetric, "c2", 76, 90);
        MetricCondition c3 = new MetricCondition(associatedQoRMetric, "c3", 91, 100);

        AdjustmentCase transition1 = new AdjustmentCase(c3, listOfParameters);
        AdjustmentCase transition2 = new AdjustmentCase(c3, listOfParameters);

        List<AdjustmentCase> listOfTransitions = new ArrayList<AdjustmentCase>();
        listOfTransitions.add(transition1);
        listOfTransitions.add(transition2);

        AdjustmentAction controlAction = new AdjustmentAction(
                controlActionID,
                controlActionName,
                artifact,
                associatedQoRMetric,
                listOfPrerequisiteActionIDs,
                listOfTransitions);

        return controlAction;

    }

    private AdjustmentAction controlActionThroughputDataItemsPerSecond() {

        // power - throughput  
        String controlActionID = "STC";
        String controlActionName = "STC";
        Artifact artifact = new Artifact(
                controlActionName,
                "control throughput",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/STC.sh",
                "sh",
                "/STC/rest/control");

        double costPerHour = 0.45;
        String associatedQoRMetric = "throughputOfDataItemPerSecond";

        List<String> listOfPrerequisiteActionIDs = new ArrayList<String>();
        //listOfPrerequisiteActionIDs.add("");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        Parameter param1 = new Parameter("unit", "String", "dataItems/second");

        listOfParameters.add(param1);

        MetricCondition c1 = new MetricCondition(associatedQoRMetric, "c1", 0, 0.49);
        MetricCondition c2 = new MetricCondition(associatedQoRMetric, "c2", 0.5, 1.19);
        MetricCondition c3 = new MetricCondition(associatedQoRMetric, "c3", 1.2, Double.MAX_VALUE);

        AdjustmentCase transition1 = new AdjustmentCase(c2, listOfParameters);
        AdjustmentCase transition2 = new AdjustmentCase(c3, listOfParameters);
        AdjustmentCase transition3 = new AdjustmentCase(c1, listOfParameters);

        List<AdjustmentCase> listOfTransitions = new ArrayList<AdjustmentCase>();
        listOfTransitions.add(transition1);
        listOfTransitions.add(transition2);
        listOfTransitions.add(transition3);

        AdjustmentAction controlAction = new AdjustmentAction(
                controlActionID,
                controlActionName,
                artifact,
                associatedQoRMetric,
                listOfPrerequisiteActionIDs,
                listOfTransitions);

        return controlAction;

    }

    private AdjustmentAction controlActionAIC() {

        // kdd - impression accuracy  
        
        
        String controlActionID = "AIC";
        String controlActionName = "AIC";
        Artifact artifact = new Artifact(
                controlActionName,
                "control Ads Impression Accuracy",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/AIC.sh",
                "sh",
                "/AIC/rest/control");

        double costPerHour = 0.45;
        String associatedQoRMetric = "adsImpressionAccuracy";
        List<String> listOfPrerequisiteActionIDs = new ArrayList<String>();
    //listOfPrerequisiteActionIDs.add("");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        Parameter param1 = new Parameter("attributeIndex", "int", "");
        listOfParameters.add(param1);

        MetricCondition c1 = new MetricCondition(associatedQoRMetric, "c1", 0, 75);
        MetricCondition c2 = new MetricCondition(associatedQoRMetric, "c2", 76, 90);
        MetricCondition c3 = new MetricCondition(associatedQoRMetric, "c3", 91, 100);

        AdjustmentCase transition1 = new AdjustmentCase( c3, listOfParameters);
        AdjustmentCase transition2 = new AdjustmentCase(c3, listOfParameters);

        List<AdjustmentCase> listOfTransitions = new ArrayList<AdjustmentCase>();
        listOfTransitions.add(transition1);
        listOfTransitions.add(transition2);

        AdjustmentAction controlAction = new AdjustmentAction(
                controlActionID,
                controlActionName,
                artifact,
                associatedQoRMetric,
                listOfPrerequisiteActionIDs,
                listOfTransitions);

        return controlAction;

    }

    private AdjustmentAction controlActionAPC() {

    // kdd - position accuracy  
        
        String controlActionID = "APC";
        String controlActionName = "APC";
        Artifact artifact = new Artifact(
                controlActionName,
                "control Ads Position Accuracy",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/APC.sh",
                "sh",
                "/APC/rest/control");

        double costPerHour = 0.45;
        String associatedQoRMetric = "adsPositionAccuracy";
        List<String> listOfPrerequisiteActionIDs = new ArrayList<String>();
    //listOfPrerequisiteActionIDs.add("");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        Parameter param1 = new Parameter("attributeIndex", "int", "");
        listOfParameters.add(param1);

        MetricCondition c1 = new MetricCondition(associatedQoRMetric, "c1", 0, 75);
        MetricCondition c2 = new MetricCondition(associatedQoRMetric, "c2", 76, 90);
        MetricCondition c3 = new MetricCondition(associatedQoRMetric, "c3", 91, 100);

        AdjustmentCase transition1 = new AdjustmentCase( c3, listOfParameters);
        AdjustmentCase transition2 = new AdjustmentCase(c3, listOfParameters);

        List<AdjustmentCase> listOfTransitions = new ArrayList<AdjustmentCase>();
        listOfTransitions.add(transition1);
        listOfTransitions.add(transition2);

        AdjustmentAction controlAction = new AdjustmentAction(
                controlActionID,
                controlActionName,
                artifact,
 
                associatedQoRMetric,
                listOfPrerequisiteActionIDs,
                listOfTransitions);

        
        
        return controlAction;

    }

    private AdjustmentAction controlActionSTC() {

    // kdd - throughput
         
        String controlActionID = "STC";
        String controlActionName = "STC";
        Artifact artifact = new Artifact(
                controlActionName,
                "control throughput",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/STC.sh",
                "sh",
                "/STC/rest/control");

        double costPerHour = 0.45;
        String associatedQoRMetric = "throughputOfDataAssetsPerHour";

        List<String> listOfPrerequisiteActionIDs = new ArrayList<String>();
        //listOfPrerequisiteActionIDs.add("");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        Parameter param1 = new Parameter("unit", "String", "dataAssets/hour");

        listOfParameters.add(param1);

        MetricCondition c1 = new MetricCondition(associatedQoRMetric, "c1", 0, 0.49);
        MetricCondition c2 = new MetricCondition(associatedQoRMetric, "c2", 0.5, 1.19);
        MetricCondition c3 = new MetricCondition(associatedQoRMetric, "c3", 1.2, Double.MAX_VALUE);

        AdjustmentCase transition1 = new AdjustmentCase(c2, listOfParameters);
        AdjustmentCase transition2 = new AdjustmentCase(c3, listOfParameters);
        AdjustmentCase transition3 = new AdjustmentCase( c1, listOfParameters);

        List<AdjustmentCase> listOfTransitions = new ArrayList<AdjustmentCase>();
        listOfTransitions.add(transition1);
        listOfTransitions.add(transition2);
        listOfTransitions.add(transition3);

        AdjustmentAction controlAction = new AdjustmentAction(
                controlActionID,
                controlActionName,
                artifact,
                associatedQoRMetric,
                listOfPrerequisiteActionIDs,
                listOfTransitions);
        
        return controlAction;

    }
    
    
    /////////////////////////////
    //
    //
    //  Resource Controls 
    //
    //
    /////////////////////////////

    
    private ResourceControlCase resourceControlStrategy() {
       
        MetricCondition condition_in = new MetricCondition("cpuUsage", "co_1", 0, 15);
        MetricCondition condition_out = new MetricCondition("cpuUsage", "co_2", 80, 100);
        
        ResourceControlCase resourceControlStrategy = new ResourceControlCase(condition_in, condition_out, 2500);
        return resourceControlStrategy;
        
        
    }
}
