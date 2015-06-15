/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.repository;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentCase;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Artifact;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlCase;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlStrategy;
import at.ac.tuwien.dsg.depic.common.utils.Configuration;
import at.ac.tuwien.dsg.depic.common.utils.MySqlConnectionManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class PrimitiveActionMetadataManager {

    MySqlConnectionManager connectionManager;

    public PrimitiveActionMetadataManager() {
        Configuration config = new Configuration();
        String ip = config.getConfig("DB.PAM.IP");
        String port = config.getConfig("DB.PAM.PORT");
        String database = config.getConfig("DB.PAM.DATABASE");
        String username = config.getConfig("DB.PAM.USERNAME");
        String password = config.getConfig("DB.PAM.PASSWORD");

        connectionManager = new MySqlConnectionManager(ip, port, database, username, password);

    }

    public void storeMonitoringAction(MonitoringAction monitoringAction) {

        String sql = "INSERT INTO PrimitiveAction (ActionName, AssociatedQoRMetric, ArtifactName, ActionType) VALUES ('" + monitoringAction.getMonitoringActionName() + "','" + monitoringAction.getAssociatedQoRMetric() + "','" + monitoringAction.getArtifact().getName() + "', 'MonitoringAction')";
        connectionManager.ExecuteUpdate(sql);

        storeArtifact(monitoringAction.getArtifact(), monitoringAction.getMonitoringActionName());
        storeParameters(monitoringAction.getListOfParameters(), monitoringAction.getMonitoringActionName());
    }

    public void storeAdjustmentAction(AdjustmentAction adjustmentAction) {
        String sql = "INSERT INTO PrimitiveAction (ActionName, AssociatedQoRMetric, ArtifactName, ActionType) VALUES ('" + adjustmentAction.getActionName() + "','" + adjustmentAction.getAssociatedQoRMetric() + "','" + adjustmentAction.getArtifact().getName() + "', 'AdjustmentAction')";
        connectionManager.ExecuteUpdate(sql);

        storeArtifact(adjustmentAction.getArtifact(), adjustmentAction.getActionName());
        storePrerequisiteActions(adjustmentAction.getListOfPrerequisiteActionIDs(), adjustmentAction.getActionName());

        storeAdjustmentCases(adjustmentAction.getListOfAdjustmentCases(), adjustmentAction.getActionName());

    }

    public void storeResourceControlAction(ResourceControlAction resourceControlAction) {

        String sql = "INSERT INTO PrimitiveAction (ActionName, AssociatedQoRMetric, ArtifactName, ActionType) VALUES ('" + resourceControlAction.getActionName() + "','" + resourceControlAction.getAssociatedQoRMetric() + "','none', 'ResourceControlAction')";
        connectionManager.ExecuteUpdate(sql);

        List<ResourceControlCase> listOfResourceControlCases = resourceControlAction.getListOfResourceControlCases();
        
        storeResourceControlCases(listOfResourceControlCases, resourceControlAction.getActionName());
    }

    public void storeResourceControlCases(List<ResourceControlCase> listOfResourceControlCases, String actionName) {

        for (int i = 0; i < listOfResourceControlCases.size(); i++) {
            ResourceControlCase resourceControlCase = listOfResourceControlCases.get(0);

            String sql = "INSERT INTO ResourceControlCase (ResourceControlActionName, ResourceControlCase, EstimatedResultFrom, EstimatedResultTo, AnalyticsTaskName) "
                    + "VALUES ('" + actionName + "', '" + i + "' , " + resourceControlCase.getEstimatedResult().getLowerBound() + ", " + resourceControlCase.getEstimatedResult().getUpperBound() + ", '" + resourceControlCase.getAnalyticTask().getTaskName() + "')";
            System.err.println(sql);
            
            connectionManager.ExecuteUpdate(sql);

            List<Parameter> listOfAnalyticTaskParameters = resourceControlCase.getAnalyticTask().getParameters();
            storeAnalyticTaskParameters(listOfAnalyticTaskParameters, actionName, String.valueOf(i), resourceControlCase.getAnalyticTask().getTaskName());

            List<ResourceControlStrategy> listOfResourceControlStrategies = resourceControlCase.getListOfResourceControlStrategies();
            storeResourceControlStrategies(listOfResourceControlStrategies, actionName, String.valueOf(i));
        }

    }

    private void storeResourceControlStrategies(List<ResourceControlStrategy> listOfResourceControlStrategies, String actionName, String resourceControlCase) {

        for (ResourceControlStrategy resourceControlStrategy : listOfResourceControlStrategies) {

            String sql = "INSERT INTO ResourceControlStrategy (ResourceControlActionName,ResourceControlCase,  ControlMetric, EffectedActionName,  ScaleInConditionFrom, ScaleInConditionTo, ScaleOutConditionFrom, ScaleOutConditionTo) "
                    + "VALUES ('" + actionName + "', '" + resourceControlCase + "' , '" + resourceControlStrategy.getControlMetric() + "', '" + resourceControlStrategy.getPrimitiveAction() + "' "
                    + ", " + resourceControlStrategy.getScaleInCondition().getLowerBound() + ", " + resourceControlStrategy.getScaleInCondition().getUpperBound() + ""
                    + ", " + resourceControlStrategy.getScaleOutCondition().getLowerBound() + ", " + resourceControlStrategy.getScaleOutCondition().getLowerBound() + ")";
            
            System.err.println(sql);
            connectionManager.ExecuteUpdate(sql);

        }

    }

    public void storeAdjustmentCases(List<AdjustmentCase> listOfAdjustmentCases, String actionName) {

        for (int i = 0; i < listOfAdjustmentCases.size(); i++) {
            AdjustmentCase adjustmentCase = listOfAdjustmentCases.get(i);
            String sql = "INSERT INTO AdjustmentCase (ActionName, AdjustmentCase, EstimatedResultFrom, EstimatedResultTo, AnalyticsTaskName) "
                    + "VALUES ('" + actionName + "', '" + i + "', " + adjustmentCase.getEstimatedResult().getLowerBound() + ", " + adjustmentCase.getEstimatedResult().getUpperBound() + ", '" + adjustmentCase.getAnalyticTask().getTaskName() + "')";
            connectionManager.ExecuteUpdate(sql);

            List<Parameter> listOfAnalyticTaskParameters = adjustmentCase.getAnalyticTask().getParameters();
            storeAnalyticTaskParameters(listOfAnalyticTaskParameters, actionName, String.valueOf(i), adjustmentCase.getAnalyticTask().getTaskName());

            List<Parameter> listOfAdjustmentCaseParameters = adjustmentCase.getListOfParameters();
            storeAdjustmentCaseParameters(listOfAdjustmentCaseParameters, actionName, String.valueOf(i));
        }

    }

    public void storeAnalyticTaskParameters(List<Parameter> listOfAnalyticTaskParameters, String actionName, String adjustmentCase, String analyticTask) {

        for (Parameter parameter : listOfAnalyticTaskParameters) {

            String sql = "INSERT INTO AnalyticTask (ActionName, AdjustmentCase, AnalyticTask, ParameterName, ParameterType, ParameterValue) "
                    + "VALUES ('" + actionName + "', '" + adjustmentCase + "', '" + analyticTask + "', '" + parameter.getParameterName() + "', '" + parameter.getType() + "', '" + parameter.getValue() + "')";
            System.err.println(sql);
            
            connectionManager.ExecuteUpdate(sql);

        }

    }

    public void storeAdjustmentCaseParameters(List<Parameter> listOfAdjustmentCaseParameters, String actionName, String adjustmentCase) {

        for (Parameter parameter : listOfAdjustmentCaseParameters) {
            String sql = "INSERT INTO AdjustmentActionParameter (ActionName, AdjustmentCase, ParameterName, ParameterType, ParameterValue) "
                    + "VALUES ('" + actionName + "', '" + adjustmentCase + "',  '" + parameter.getParameterName() + "', '" + parameter.getType() + "', '" + parameter.getValue() + "')";
            System.err.println(sql);
            connectionManager.ExecuteUpdate(sql);

        }
    }

    public void storePrerequisiteActions(List<String> prerequisiteActionlist, String dependentAction) {

        for (String prerequisiteAction : prerequisiteActionlist) {
            String sql = "INSERT INTO PrerequisiteAction (ActionName, PrerequisiteAction) VALUES ('" + dependentAction + "', '" + prerequisiteAction + "')";
            connectionManager.ExecuteUpdate(sql);
        }

    }

    public void storeArtifact(Artifact artifact, String actionName) {

        String sql = "INSERT INTO Artifact (ArtifactName, ActionName, ArtifactDescription, Location, Type, RESTfulAPI, HttpMethod) "
                + "VALUES ('" + artifact.getName() + "','" + actionName + "','" + artifact.getDescription() + "','" + artifact.getLocation() + "','" + artifact.getType() + "','" + artifact.getRestfulAPI() + "','" + artifact.getHttpMethod() + "')";
        connectionManager.ExecuteUpdate(sql);
    }

    public void storeParameters(List<Parameter> listOfParameters, String actionName) {

        for (Parameter parameter : listOfParameters) {
            String sql = "INSERT INTO MonitoringActionParameter (ActionName, ParameterName, ParameterType, ParameterValue) "
                    + "VALUES ('" + actionName + "','" + parameter.getParameterName() + "','" + parameter.getType() + "','" + parameter.getValue() + "')";
            connectionManager.ExecuteUpdate(sql);
        }

    }
    
       public List<MonitoringAction> getMonitoringActionList() {
        String sql = "SELECT * from PrimitiveAction WHERE ActionType='MonitoringAction'";
        List<MonitoringAction> listOfActions = new ArrayList<>();

        ResultSet rs = connectionManager.ExecuteQuery(sql);
        try {
            while (rs.next()) {

                String actionName = rs.getString("ActionName");
                String associatedQoRMetric = rs.getString("AssociatedQoRMetric");
                String artifactName = rs.getString("ArtifactName");
                String actionType = rs.getString("ActionType");
                MonitoringAction monitoringAction = new MonitoringAction(actionName, actionName, null, associatedQoRMetric, null);
                listOfActions.add(monitoringAction);
            }
            rs.close();

        } catch (Exception ex) {
            System.err.println(ex);
        }

        return listOfActions;

    }
       
       public List<AdjustmentAction> getAdjustmentActionList() {
        String sql = "SELECT * from PrimitiveAction WHERE ActionType='AdjustmentAction'";
        List<AdjustmentAction> listOfActions = new ArrayList<>();

        ResultSet rs = connectionManager.ExecuteQuery(sql);
        try {
            while (rs.next()) {

                String actionName = rs.getString("ActionName");
                String associatedQoRMetric = rs.getString("AssociatedQoRMetric");
                String artifactName = rs.getString("ArtifactName");
                String actionType = rs.getString("ActionType");
                AdjustmentAction adjustmentAction = new AdjustmentAction(actionName, actionName, null, associatedQoRMetric, null, null);
                listOfActions.add(adjustmentAction);
            }
            rs.close();

        } catch (Exception ex) {
            System.err.println(ex);
        }

        return listOfActions;

    }
       
       
       public List<ResourceControlAction> getResourceControlActionList() {
        String sql = "SELECT * from PrimitiveAction WHERE ActionType='ResourceControlAction'";
        List<ResourceControlAction> listOfActions = new ArrayList<>();

        ResultSet rs = connectionManager.ExecuteQuery(sql);
        try {
            while (rs.next()) {

                String actionName = rs.getString("ActionName");
                String associatedQoRMetric = rs.getString("AssociatedQoRMetric");
                String artifactName = rs.getString("ArtifactName");
                String actionType = rs.getString("ActionType");
                ResourceControlAction resourceControlAction = new ResourceControlAction(actionName, associatedQoRMetric, null);
                listOfActions.add(resourceControlAction);
            }
            rs.close();

        } catch (Exception ex) {
            System.err.println(ex);
        }

        return listOfActions;

    }
       
       

}
