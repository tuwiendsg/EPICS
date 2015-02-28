/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller;

import at.ac.tuwien.dsg.common.entity.eda.EDaaSType;
import at.ac.tuwien.dsg.common.entity.eda.ElasticState;
import at.ac.tuwien.dsg.common.entity.eda.MetricCondition;
import at.ac.tuwien.dsg.common.entity.eda.da.DataControlRequest;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitoringSession;
import at.ac.tuwien.dsg.common.entity.eda.ep.ParallelGateway;
import at.ac.tuwien.dsg.common.entity.eda.ep.QueueTask;
import at.ac.tuwien.dsg.common.entity.process.Parameter;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.orchestrator.registry.ElasticServiceRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import org.eclipse.persistence.internal.core.helper.CoreClassConstants;

/**
 *
 * @author Jun
 */
public class DataElasticityController {

    List<ElasticState> listOfExpectedElasticStates;
    List<ControlProcess> listOfControlProcesses;
    List<QueueTask> listOfTasks;
    List<String> traversedGatewayList;
    EDaaSType eDaaSType;
    MonitoringSession monitoringSession;

    public DataElasticityController(List<ElasticState> listOfExpectedElasticStates, List<ControlProcess> listOfControlProcesses, MonitoringSession monitoringSession, EDaaSType eDaaSType) {
        this.listOfExpectedElasticStates = listOfExpectedElasticStates;
        this.listOfControlProcesses = listOfControlProcesses;
        listOfTasks = new ArrayList<QueueTask>();
        traversedGatewayList = new ArrayList<String>();
        this.monitoringSession = monitoringSession;
        this.eDaaSType = eDaaSType;
    }

    public void startControlElasticState(ElasticState currentElasticState) {

        List<ControlProcess> listOfPotentialControlProcesses = new ArrayList<ControlProcess>();

        for (ElasticState expectedElasticState : listOfExpectedElasticStates) {

            ControlProcess cp = determineAppropriateControlProcess(currentElasticState.geteStateID(), expectedElasticState.geteStateID());
            if (cp != null) {
               // System.out.println("Potential Next eState FOUND");
               // logElasticState(cp.geteStateID_j());

                listOfPotentialControlProcesses.add(cp);
            }

        }

        ControlProcess controlProcess = determineTheBestControlProcess(listOfPotentialControlProcesses);
        System.out.println("BEST CONTROL PROCESS FOUND");
      //  logControlProcesses(controlProcess);
        System.out.println("FINAL Elastic STATE");
        logElasticState(controlProcess.geteStateID_j());

      //  startControlProcess(controlProcess);

    }

    private ControlProcess determineAppropriateControlProcess(String elasticStateID_i, String elasticStateID_j) {

        ControlProcess cp = null;
        for (ControlProcess controlProcess : listOfControlProcesses) {
            if (controlProcess.geteStateID_i().geteStateID().equals(elasticStateID_i)
                    && controlProcess.geteStateID_j().geteStateID().equals(elasticStateID_j)) {
                cp = controlProcess;
            }
        }

        return cp;
    }

    private ControlProcess determineTheBestControlProcess(List<ControlProcess> potentialControlProcesses) {

        int minControlActions = Integer.MAX_VALUE;
        int cpIndex = 0;
        for (ControlProcess cp : potentialControlProcesses) {
            if (cp.getListOfControlActions().size() < minControlActions) {
                minControlActions = cp.getListOfControlActions().size();
                cpIndex = potentialControlProcesses.indexOf(cp);
            }

        }

        return potentialControlProcesses.get(cpIndex);
    }

    private void startControlProcess(ControlProcess cp) {

        System.out.println("PLANNING Control Process ...");

        planControlProcess(cp);
        System.out.println("EXECUTE Control Process ...");
        executeControlProcess(cp);

//        for (ControlAction ca : listOfControlActions){
//            String controlActionID = ca.getControlActionID();
//            List<Parameter> listOfParameters = ca.getListOfParameters();
//            ControlServiceRegistry controlServiceRegistry = new ControlServiceRegistry();
//            String uri = controlServiceRegistry.getControlServiceURI(controlActionID);
//            String paramStr ="";
//            try {
//                paramStr  = JAXBUtils.marshal(listOfParameters, List.class);
//            } catch (JAXBException ex) {
//                Logger.getLogger(DataElasticityController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            
//            RestfulWSClient ws = new RestfulWSClient(uri);
//            ws.callPutMethod(paramStr);
//     
//        }
    }

    private void planControlProcess(ControlProcess cp) {

        List<ControlAction> listOfControlActions = cp.getListOfControlActions();
        List<ParallelGateway> listOfParallelGateways = cp.getListOfParallelGateways();

        // find start task
        String startTaskID = "";
        for (ParallelGateway pg : listOfParallelGateways) {
            if (pg.getIncomming() == null) {
                startTaskID = pg.getId();
            }
        }

        for (ControlAction ca : listOfControlActions) {
            if (ca.getIncomming() == null) {
                startTaskID = ca.getControlActionID();
            }
        }

        System.out.println("START: " + startTaskID);

        if (isActionTask(startTaskID, listOfParallelGateways)) {

            QueueTask startTask = new QueueTask(startTaskID, "S");
            listOfTasks.add(startTask);

        } else {
            //traversedGatewayList.add(startTaskID);
        }

        // plan other tasks
        getNextTask(startTaskID, listOfParallelGateways, listOfControlActions);

        setPriority(listOfControlActions);
    }
    
    private void executeControlProcess(ControlProcess cp){
        
        List<ControlAction> listOfControlActions = cp.getListOfControlActions();
        
        for (QueueTask task : listOfTasks) {
            System.out.println(task.getProperty() + " : TaskID: " + task.getTaskID());
            String controlActionName = "";
            List<Parameter> listOfParams = null;
            for (ControlAction ca: listOfControlActions){
                if (ca.getControlActionID().equals(task.getTaskID())){
                    listOfParams = ca.getListOfParameters();
                    controlActionName = ca.getControlActionName();
                    break;
                }
            }
            
            DataControlRequest controlRequest = new DataControlRequest(monitoringSession.getEdaasName()
                    , monitoringSession.getSessionID()
                    , monitoringSession.getDataAssetID()
                    , listOfParams);
            
            String requestXML="";
            try {
                requestXML = JAXBUtils.marshal(controlRequest, DataControlRequest.class);
            } catch (JAXBException ex) {
                Logger.getLogger(DataElasticityController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
       
            String uri = ElasticServiceRegistry.getElasticServiceURI(controlActionName, eDaaSType);
      
            RestfulWSClient ws = new RestfulWSClient(uri);
            ws.callPutMethod(requestXML);
        }
        
        
    }

    private void getNextTask(String taskID, List<ParallelGateway> listOfParallelGateways, List<ControlAction> listOfControlActions) {

        boolean isEndTask = false;

        System.out.println("Next Task of : " + taskID);

        
        for (ControlAction ca : listOfControlActions) {
            if (ca.getControlActionID().equals(taskID)) {
                if (ca.getOutgoing() == null) {
                    isEndTask = true;
                } else {

                    String outgoing = ca.getOutgoing();
                    if (isActionTask(outgoing, listOfParallelGateways)) {
                        QueueTask actionTask = new QueueTask(outgoing, "S");
                        listOfTasks.add(actionTask);
                        getNextTask(outgoing, listOfParallelGateways, listOfControlActions);
                    } else if (!isTraversed(outgoing)) {
                        getNextTask(outgoing, listOfParallelGateways, listOfControlActions);
                        traversedGatewayList.add(outgoing);
                    }

                }

            }
        }
        
        for (ParallelGateway pg : listOfParallelGateways) {
            if (pg.getId().equals(taskID) && !isTraversed(pg.getId())) {

                if (pg.getOutgoing() == null) {
                    isEndTask = true;

                } else {
                    List<String> outgoingList = pg.getOutgoing();
                    for (String outgoing : outgoingList) {
                        if (isActionTask(outgoing, listOfParallelGateways)) {
                            QueueTask actionTask = new QueueTask(outgoing, "P");
                            listOfTasks.add(actionTask);
                            getNextTask(outgoing, listOfParallelGateways, listOfControlActions);
                        } else if (!isTraversed(outgoing)) {
                            getNextTask(outgoing, listOfParallelGateways, listOfControlActions);
                            traversedGatewayList.add(outgoing);
                        }

                    }
                }

            }
        }

        
        
    }

    private boolean isActionTask(String taskID, List<ParallelGateway> listOfParallelGateways) {
        boolean rs = true;

        for (ParallelGateway pg : listOfParallelGateways) {
            if (pg.getId().equals(taskID)) {
                rs = false;
            }
        }

        return rs;
    }

    private boolean isTraversed(String gatewayID) {
        boolean rs = false;

        for (String traversedPG : traversedGatewayList) {
            if (traversedPG.equals(gatewayID)) {
                rs = true;
            }
        }

        return rs;
    }
    
    private void setPriority(List<ControlAction> listOfControlActions){
        String taskID="";
        
        for (ControlAction ca: listOfControlActions){
               if (ca.getControlActionName().equals("STC")){
                   taskID=ca.getControlActionID();
               }
           }
        
        for (QueueTask task : listOfTasks) {
            if (task.getTaskID().equals(taskID)){
                Collections.swap(listOfTasks, 0, listOfTasks.indexOf(task));
            }
            
        }
    }

    private void logElasticState(ElasticState elasticState) {

        System.out.println("\n***");
        System.out.println("eState ID: " + elasticState.geteStateID());
        List<MetricCondition> conditions = elasticState.getListOfConditions();
        for (MetricCondition condition : conditions) {
            System.out.println("\n---");
            System.out.print("    metric: " + condition.getMetricName());
            System.out.print("    id: " + condition.getConditionID());
            System.out.print("    lower: " + condition.getLowerBound());
            System.out.print("    uppper: " + condition.getUpperBound());

        }

    }

    public void logControlProcesses(ControlProcess controlProcess) {

        System.out.println("\nLOG CONTROL PROCESS");

        System.out.println("\n***");
        List<ControlAction> listOfControlActions = controlProcess.getListOfControlActions();

        System.out.println("PROCESS ----------- ");
        System.out.println("eState in: " + controlProcess.geteStateID_i().geteStateID());
        System.out.println("eState fi: " + controlProcess.geteStateID_j().geteStateID());
        for (ControlAction controlAction : listOfControlActions) {
            System.out.println("control action: " + controlAction.getControlActionName());
        }

        System.out.println(".............");

        List<MetricCondition> conditions_in = controlProcess.geteStateID_i().getListOfConditions();
        for (MetricCondition c : conditions_in) {
            System.out.println("   id: " + c.getConditionID());
            System.out.println("   metric: " + c.getMetricName());
            System.out.println("   lower: " + c.getLowerBound());
            System.out.println("   upper: " + c.getUpperBound());

        }

        System.out.println("eState fi: " + controlProcess.geteStateID_j().geteStateID());
        List<MetricCondition> conditions_fi = controlProcess.geteStateID_j().getListOfConditions();
        for (MetricCondition c : conditions_fi) {
            System.out.println("   id: " + c.getConditionID());
            System.out.println("   metric: " + c.getMetricName());
            System.out.println("   lower: " + c.getLowerBound());
            System.out.println("   upper: " + c.getUpperBound());

        }

        for (ControlAction controlAction : listOfControlActions) {
            System.out.println("control action: " + controlAction.getControlActionID());
            System.out.println("control action: " + controlAction.getControlActionName());
            System.out.println("  incomming: " + controlAction.getIncomming());
            System.out.println("  outgoing: " + controlAction.getOutgoing());
            List<Parameter> listOfParams = controlAction.getListOfParameters();

            for (Parameter param : listOfParams) {
                System.out.println("    parameter: " + param.getParaName());
                System.out.println("    value: " + param.getValue());
            }

        }

        List<ParallelGateway> listOfParallelGateways = controlProcess.getListOfParallelGateways();

        for (ParallelGateway parallelGateway : listOfParallelGateways) {
            System.out.println("parallel gateway: " + parallelGateway.getId());
            System.out.println("  incoming: " + parallelGateway.getIncomming());
            System.out.println("  outgoing: " + parallelGateway.getOutgoing());
        }

    }

}
