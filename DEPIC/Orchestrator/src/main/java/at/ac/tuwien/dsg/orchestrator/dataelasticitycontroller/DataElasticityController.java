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
import at.ac.tuwien.dsg.common.utils.IOUtils;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.Logger;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.orchestrator.dataelasticitymonitor.DataElasticityMonitor;
import at.ac.tuwien.dsg.orchestrator.registry.ElasticServiceRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

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
               // Logger.logInfo("Potential Next eState FOUND");
               // logElasticState(cp.geteStateID_j());

                listOfPotentialControlProcesses.add(cp);
            }

        }

        ControlProcess controlProcess = determineTheBestControlProcess(listOfPotentialControlProcesses);
        Logger.logInfo("BEST CONTROL PROCESS FOUND");
      //  logControlProcesses(controlProcess);
        Logger.logInfo("FINAL Elastic STATE");
        
        String log = "";
        log += "FINAL Elastic STATE: " + currentElasticState.geteStateID();
        
        
        logElasticState(controlProcess.geteStateID_j());

          long t1 = System.currentTimeMillis();
        
        
        startControlProcess(controlProcess);
        
          long t2 = System.currentTimeMillis();
        
        System.out.println("CONTROL_PROCESS_RUNTIME: " +monitoringSession.getSessionID() + "  -  " + (t2-t1));
        log = log + "CONTROL_PROCESS_RUNTIME: " +monitoringSession.getSessionID() + "  -  " + (t2-t1);
        
        
        try {
           
            
            IOUtils iou = new IOUtils("/home/ubuntu/log");
            iou.writeData(log, "depic_controller.xml");
            
            System.out.println("\n" + log);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(DataElasticityController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

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

        Logger.logInfo("PLANNING Control Process ...");

        //planControlProcess(cp);
        Logger.logInfo("EXECUTE Control Process ...");
        
      
        
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

        Logger.logInfo("START: " + startTaskID);

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

        for (ControlAction controlAction : listOfControlActions) {

            List<Parameter> listOfParams = controlAction.getListOfParameters();

            DataControlRequest controlRequest = new DataControlRequest(monitoringSession.getEdaasName(), monitoringSession.getSessionID(), monitoringSession.getDataAssetID(), listOfParams);

            String requestXML = "";
            try {
                requestXML = JAXBUtils.marshal(controlRequest, DataControlRequest.class);
            } catch (JAXBException ex) {

            }

            if (!controlAction.getControlActionName().equals("STC")) {

                String uri = ElasticServiceRegistry.getElasticServiceURI(controlAction.getControlActionName(), eDaaSType);
                Logger.logInfo("RUN: " + uri);
                RestfulWSClient ws = new RestfulWSClient(uri);
                ws.callPutMethod(requestXML);
            }
        }

        
        
//        
//        for (QueueTask task : listOfTasks) {
//            Logger.logInfo(task.getProperty() + " : TaskID: " + task.getTaskID());
//            String controlActionName = "";
//            List<Parameter> listOfParams = null;
//            for (ControlAction ca: listOfControlActions){
//                if (ca.getControlActionID().equals(task.getTaskID())){
//                    listOfParams = ca.getListOfParameters();
//                    controlActionName = ca.getControlActionName();
//                    break;
//                }
//            }
//            
//            DataControlRequest controlRequest = new DataControlRequest(monitoringSession.getEdaasName()
//                    , monitoringSession.getSessionID()
//                    , monitoringSession.getDataAssetID()
//                    , listOfParams);
//            
//            String requestXML="";
//            try {
//                requestXML = JAXBUtils.marshal(controlRequest, DataControlRequest.class);
//            } catch (JAXBException ex) {
//               
//            }
//            
//            if (controlActionName.equals("STC")) {
//            
//            String uri = ElasticServiceRegistry.getElasticServiceURI(controlActionName, eDaaSType);
//      
//            RestfulWSClient ws = new RestfulWSClient(uri);
//            ws.callPutMethod(requestXML);
//            }
//        }
        
        
    }

    private void getNextTask(String taskID, List<ParallelGateway> listOfParallelGateways, List<ControlAction> listOfControlActions) {

        boolean isEndTask = false;

        Logger.logInfo("Next Task of : " + taskID);

        
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

        Logger.logInfo("\n***");
        Logger.logInfo("eState ID: " + elasticState.geteStateID());
        List<MetricCondition> conditions = elasticState.getListOfConditions();
        for (MetricCondition condition : conditions) {
            Logger.logInfo("\n---");
            System.out.print("    metric: " + condition.getMetricName());
            System.out.print("    id: " + condition.getConditionID());
            System.out.print("    lower: " + condition.getLowerBound());
            System.out.print("    uppper: " + condition.getUpperBound());

        }

    }

    public void logControlProcesses(ControlProcess controlProcess) {

        Logger.logInfo("\nLOG CONTROL PROCESS");

        Logger.logInfo("\n***");
        List<ControlAction> listOfControlActions = controlProcess.getListOfControlActions();

        Logger.logInfo("PROCESS ----------- ");
        Logger.logInfo("eState in: " + controlProcess.geteStateID_i().geteStateID());
        Logger.logInfo("eState fi: " + controlProcess.geteStateID_j().geteStateID());
        for (ControlAction controlAction : listOfControlActions) {
            Logger.logInfo("control action: " + controlAction.getControlActionName());
        }

        Logger.logInfo(".............");

        List<MetricCondition> conditions_in = controlProcess.geteStateID_i().getListOfConditions();
        for (MetricCondition c : conditions_in) {
            Logger.logInfo("   id: " + c.getConditionID());
            Logger.logInfo("   metric: " + c.getMetricName());
            Logger.logInfo("   lower: " + c.getLowerBound());
            Logger.logInfo("   upper: " + c.getUpperBound());

        }

        Logger.logInfo("eState fi: " + controlProcess.geteStateID_j().geteStateID());
        List<MetricCondition> conditions_fi = controlProcess.geteStateID_j().getListOfConditions();
        for (MetricCondition c : conditions_fi) {
            Logger.logInfo("   id: " + c.getConditionID());
            Logger.logInfo("   metric: " + c.getMetricName());
            Logger.logInfo("   lower: " + c.getLowerBound());
            Logger.logInfo("   upper: " + c.getUpperBound());

        }

        for (ControlAction controlAction : listOfControlActions) {
            Logger.logInfo("control action: " + controlAction.getControlActionID());
            Logger.logInfo("control action: " + controlAction.getControlActionName());
            Logger.logInfo("  incomming: " + controlAction.getIncomming());
            Logger.logInfo("  outgoing: " + controlAction.getOutgoing());
            List<Parameter> listOfParams = controlAction.getListOfParameters();

            for (Parameter param : listOfParams) {
                Logger.logInfo("    parameter: " + param.getParaName());
                Logger.logInfo("    value: " + param.getValue());
            }

        }

        List<ParallelGateway> listOfParallelGateways = controlProcess.getListOfParallelGateways();

        for (ParallelGateway parallelGateway : listOfParallelGateways) {
            Logger.logInfo("parallel gateway: " + parallelGateway.getId());
            Logger.logInfo("  incoming: " + parallelGateway.getIncomming());
            Logger.logInfo("  outgoing: " + parallelGateway.getOutgoing());
        }

    }

}
