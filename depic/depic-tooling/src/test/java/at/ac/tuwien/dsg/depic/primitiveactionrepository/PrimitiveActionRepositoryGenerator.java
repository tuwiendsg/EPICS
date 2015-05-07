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
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlStrategy;
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
        
        MonitoringAction ma3 = monitoringActionSpeedAcuracy();
        listOfMonitoringActions.add(ma3);
        
        MonitoringAction ma4 = monitoringActionLocationAccuracy();
        listOfMonitoringActions.add(ma4);
        
        MonitoringAction ma5 = monitoringActionVehicleAcuracy();
        listOfMonitoringActions.add(ma5);
        

        return listOfMonitoringActions;
    }

    private List<AdjustmentAction> getControlActionData() {

        List<AdjustmentAction> listOfControlActions = new ArrayList<AdjustmentAction>();

        AdjustmentAction ca1 = controlActionLSR();
        listOfControlActions.add(ca1);
        
        AdjustmentAction ca2 = controlActionDIR();
        listOfControlActions.add(ca2);
        
        AdjustmentAction ca3 = controlActionAdjustSpeedAcuracy();
        listOfControlActions.add(ca3);
        
        AdjustmentAction ca4 = controlActionLocationAccuracy();
        listOfControlActions.add(ca4);
        
        AdjustmentAction ca5 = controlActionVehicleAccuracy();
        listOfControlActions.add(ca5);

        

        return listOfControlActions;
    }
    
    private List<ResourceControlAction> getResourceControlData() {
        
        
        List<ResourceControlAction> listResourceCotrols = new ArrayList<ResourceControlAction>();
      
        ResourceControlAction throughputControl = resourceControlForThroughput();
        ResourceControlAction diliveryTimeControl = resourceControlForDeliveryTime();
        
        listResourceCotrols.add(throughputControl);
        listResourceCotrols.add(diliveryTimeControl);
                
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

        String associatedQoRMetric = "columnCompleteness";

        Parameter param1 = new Parameter("attributeIndex", "int", "");
        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        listOfParameters.add(param1);

        MonitoringAction monitoringAction = new MonitoringAction(
                monitorActionID,
                monitoringActionName,
                artifact,
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
                associatedQoRMetric,
                listOfParameters);

        return monitoringAction;

    }
    
    
    

    private MonitoringAction monitoringActionSpeedAcuracy() {

        // power consumption - throughput
        String monitorActionID = "SAM";
        String monitoringActionName = "SAM";

        Artifact artifact = new Artifact(
                monitorActionID,
                "measure speed accuracy",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/SAM.sh",
                "sh",
                "/SAM/rest/monitor");

        String associatedQoRMetric = "speedArc";

        Parameter param1 = new Parameter("speedIndex", "int", "2");

      
        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        listOfParameters.add(param1);

        MonitoringAction monitoringAction = new MonitoringAction(
                monitorActionID,
                monitoringActionName,
                artifact,
                associatedQoRMetric,
                listOfParameters);

        return monitoringAction;

    }

    private MonitoringAction monitoringActionLocationAccuracy() {

        // kdd - Ads impression accuracy
        String monitorActionID = "LAM";

        String monitoringActionName = "LAM";

        Artifact artifact = new Artifact(
                monitorActionID,
                "monitor location accuracy",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/LAM.sh",
                "sh",
                "/LAM/rest/monitor");

    
        String associatedQoRMetric = "locationArc";

        Parameter param1 = new Parameter("longtitudeIndex", "int", "0");
        Parameter param2 = new Parameter("latitudeIndex", "int", "1");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        listOfParameters.add(param1);
        listOfParameters.add(param2);

        MonitoringAction monitoringAction = new MonitoringAction(
                monitorActionID,
                monitoringActionName,
                artifact,
                associatedQoRMetric,
                listOfParameters);

        return monitoringAction;

    }

    private MonitoringAction monitoringActionVehicleAcuracy() {

        // kdd - Ads position accuracy
        String monitorActionID = "VAM";

        String monitoringActionName = "VAM";

        Artifact artifact = new Artifact(
                monitorActionID,
                "monitor vehicle accuracy",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/VAM.sh",
                "sh",
                "/VAM/rest/monitor");

        String associatedQoRMetric = "vehicleArc";

        Parameter param1 = new Parameter("longtitudeIndex", "int", "0");
        Parameter param2 = new Parameter("latitudeIndex", "int", "1");
        Parameter param3 = new Parameter("speedIndex", "int", "2");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        listOfParameters.add(param1);
        listOfParameters.add(param2);
        listOfParameters.add(param3);

        MonitoringAction monitoringAction = new MonitoringAction(
                monitorActionID,
                monitoringActionName,
                artifact,
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
      

        List<AdjustmentCase> listOfTransitions = new ArrayList<AdjustmentCase>();
        listOfTransitions.add(transition1);


        AdjustmentAction controlAction = new AdjustmentAction(
                controlActionID,
                controlActionName,
                artifact,
                associatedQoRMetric,
                listOfPrerequisiteActionIDs,
                listOfTransitions);

        return controlAction;

    }

    private AdjustmentAction controlActionAdjustSpeedAcuracy() {

        // power - throughput  
        String controlActionID = "SAA";
        String controlActionName = "SAA";
        Artifact artifact = new Artifact(
                controlActionName,
                "adjust speed accuracy",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/SAA.sh",
                "sh",
                "/SAA/rest/control");

        String associatedQoRMetric = "speedArc";

        List<String> listOfPrerequisiteActionIDs = new ArrayList<String>();
        listOfPrerequisiteActionIDs.add("LAA");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        Parameter param1 = new Parameter("speedIndex", "int", "2");

        listOfParameters.add(param1);

     //   MetricCondition c1 = new MetricCondition(associatedQoRMetric, "speedArc_c1", 0, 80);
     //   MetricCondition c2 = new MetricCondition(associatedQoRMetric, "speedArc_c2", 81, 90);
        MetricCondition c3 = new MetricCondition(associatedQoRMetric, "speedArc_c1", 91, 100);

        AdjustmentCase transition1 = new AdjustmentCase(c3, listOfParameters);
     

        List<AdjustmentCase> listOfTransitions = new ArrayList<AdjustmentCase>();
        listOfTransitions.add(transition1);
   

        AdjustmentAction controlAction = new AdjustmentAction(
                controlActionID,
                controlActionName,
                artifact,
                associatedQoRMetric,
                listOfPrerequisiteActionIDs,
                listOfTransitions);

        return controlAction;

    }

    private AdjustmentAction controlActionLocationAccuracy() {

        // kdd - impression accuracy  
        
        
        String controlActionID = "LAA";
        String controlActionName = "LAA";
        Artifact artifact = new Artifact(
                controlActionName,
                "adjust location accuracy",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/LAA.sh",
                "sh",
                "/LAA/rest/control");

        String associatedQoRMetric = "locationArc";
        List<String> listOfPrerequisiteActionIDs = new ArrayList<String>();
        listOfPrerequisiteActionIDs.add("VAM");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        Parameter param1 = new Parameter("longtitudeIndex", "int", "0");
        Parameter param2 = new Parameter("latitudeIndex", "int", "1");
        listOfParameters.add(param1);
        listOfParameters.add(param2);

     //   MetricCondition c1 = new MetricCondition(associatedQoRMetric, "c1", 0, 75);
    //    MetricCondition c2 = new MetricCondition(associatedQoRMetric, "c2", 76, 90);
        MetricCondition c3 = new MetricCondition(associatedQoRMetric, "locationArc_c1", 91, 100);

        AdjustmentCase transition1 = new AdjustmentCase( c3, listOfParameters);


        List<AdjustmentCase> listOfTransitions = new ArrayList<AdjustmentCase>();
        listOfTransitions.add(transition1);


        AdjustmentAction controlAction = new AdjustmentAction(
                controlActionID,
                controlActionName,
                artifact,
                associatedQoRMetric,
                listOfPrerequisiteActionIDs,
                listOfTransitions);

        return controlAction;

    }

    private AdjustmentAction controlActionVehicleAccuracy() {

    // kdd - position accuracy  
        
        String controlActionID = "VAM";
        String controlActionName = "VAM";
        Artifact artifact = new Artifact(
                controlActionName,
                "control Ads Position Accuracy",
                "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/VAM.sh",
                "sh",
                "/VAM/rest/control");

        String associatedQoRMetric = "vehicleArc";
        List<String> listOfPrerequisiteActionIDs = new ArrayList<String>();
    //listOfPrerequisiteActionIDs.add("");

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        Parameter param1 = new Parameter("longtitudeIndex", "int", "0");
        Parameter param2 = new Parameter("latitudeIndex", "int", "1");
        Parameter param3 = new Parameter("speedIndex", "int", "2");
        listOfParameters.add(param1);
        listOfParameters.add(param2);
        listOfParameters.add(param3);

  //      MetricCondition c1 = new MetricCondition(associatedQoRMetric, "c1", 0, 75);
 //       MetricCondition c2 = new MetricCondition(associatedQoRMetric, "c2", 76, 90);
        MetricCondition c3 = new MetricCondition(associatedQoRMetric, "vehicleArc_c1", 91, 100);

        AdjustmentCase transition1 = new AdjustmentCase( c3, listOfParameters);


        List<AdjustmentCase> listOfTransitions = new ArrayList<AdjustmentCase>();
        listOfTransitions.add(transition1);
    

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

    
    private ResourceControlAction resourceControlForThroughput() {
        
        String associatedQoRMetric = "throughput";
        
       
        
        
        // case
        
        
      
        MetricCondition c1 = new MetricCondition(associatedQoRMetric, "throughput_c1", 301, Double.MAX_VALUE);
  
        MetricCondition dataSize = new MetricCondition("dataSize", "c", 30, 80);
        
        MetricCondition scaleIn_co = new MetricCondition("cpuUsage", "c_in", 0, 15);
         MetricCondition scaleOut_co = new MetricCondition("cpuUsage", "c_out", 85, 100);
        ResourceControlStrategy resourceControlStrategy1 = new ResourceControlStrategy(scaleIn_co, scaleOut_co, "cpuUsage", "SAM");
        ResourceControlStrategy resourceControlStrategy2 = new ResourceControlStrategy(scaleIn_co, scaleOut_co, "cpuUsage", "LAM");
        ResourceControlStrategy resourceControlStrategy3 = new ResourceControlStrategy(scaleIn_co, scaleOut_co, "cpuUsage", "VAM");
        ResourceControlStrategy resourceControlStrategy4 = new ResourceControlStrategy(scaleIn_co, scaleOut_co, "cpuUsage", "SAA");
        ResourceControlStrategy resourceControlStrategy5 = new ResourceControlStrategy(scaleIn_co, scaleOut_co, "cpuUsage", "LAA");
        ResourceControlStrategy resourceControlStrategy6 = new ResourceControlStrategy(scaleIn_co, scaleOut_co, "cpuUsage", "VAA");
        
        List<ResourceControlStrategy> listOfResourceControlStrategys = new ArrayList<ResourceControlStrategy>();
        listOfResourceControlStrategys.add(resourceControlStrategy1);
        listOfResourceControlStrategys.add(resourceControlStrategy2);
        listOfResourceControlStrategys.add(resourceControlStrategy3);
        listOfResourceControlStrategys.add(resourceControlStrategy4);
        listOfResourceControlStrategys.add(resourceControlStrategy5);
        listOfResourceControlStrategys.add(resourceControlStrategy6);
        
        ResourceControlCase resourceControlCase = new ResourceControlCase(c1, dataSize , listOfResourceControlStrategys);
         List<ResourceControlCase> listOfResourceControlCases = new ArrayList<ResourceControlCase>();
         listOfResourceControlCases.add(resourceControlCase);
        
        ResourceControlAction resourceControlAction = new ResourceControlAction(
                associatedQoRMetric,listOfResourceControlCases);

        return resourceControlAction;
        
        
    }
    
     private ResourceControlAction resourceControlForDeliveryTime() {
        
        String associatedQoRMetric = "deliveryTime";
        
        
        // case
        
      
        MetricCondition c1 = new MetricCondition(associatedQoRMetric, "deliveryTime_c1", 0, 0.1);
  
        MetricCondition dataSize = new MetricCondition("dataSize", "c", 30, 80);
        
        MetricCondition scaleIn_co = new MetricCondition("cpuUsage", "c_in", 0, 20);
         MetricCondition scaleOut_co = new MetricCondition("cpuUsage", "c_out", 75, 100);
        ResourceControlStrategy resourceControlStrategy1 = new ResourceControlStrategy(scaleIn_co, scaleOut_co, "cpuUsage", "SAM");
        ResourceControlStrategy resourceControlStrategy2 = new ResourceControlStrategy(scaleIn_co, scaleOut_co, "cpuUsage", "LAM");
        ResourceControlStrategy resourceControlStrategy3 = new ResourceControlStrategy(scaleIn_co, scaleOut_co, "cpuUsage", "VAM");
        ResourceControlStrategy resourceControlStrategy4 = new ResourceControlStrategy(scaleIn_co, scaleOut_co, "cpuUsage", "SAA");
        ResourceControlStrategy resourceControlStrategy5 = new ResourceControlStrategy(scaleIn_co, scaleOut_co, "cpuUsage", "LAA");
        ResourceControlStrategy resourceControlStrategy6 = new ResourceControlStrategy(scaleIn_co, scaleOut_co, "cpuUsage", "VAA");
        
        List<ResourceControlStrategy> listOfResourceControlStrategys = new ArrayList<ResourceControlStrategy>();
        listOfResourceControlStrategys.add(resourceControlStrategy1);
        listOfResourceControlStrategys.add(resourceControlStrategy2);
        listOfResourceControlStrategys.add(resourceControlStrategy3);
        listOfResourceControlStrategys.add(resourceControlStrategy4);
        listOfResourceControlStrategys.add(resourceControlStrategy5);
        listOfResourceControlStrategys.add(resourceControlStrategy6);
        
        ResourceControlCase resourceControlCase = new ResourceControlCase(c1, dataSize , listOfResourceControlStrategys);
         List<ResourceControlCase> listOfResourceControlCases = new ArrayList<ResourceControlCase>();
         listOfResourceControlCases.add(resourceControlCase);
        
        ResourceControlAction resourceControlAction = new ResourceControlAction(
                associatedQoRMetric,listOfResourceControlCases);

        return resourceControlAction;
        
        
    }
}
