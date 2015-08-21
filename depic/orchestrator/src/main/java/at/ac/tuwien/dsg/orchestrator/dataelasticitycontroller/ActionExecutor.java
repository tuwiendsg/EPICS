/**
 * Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup
 E184.  This work was partially supported by the European Commission in terms
 * of the CELAR FP7 project (FP7-ICT-2011-8 #317790).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller;

import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.AdjustmentProcess;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ExecutionSession;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ExternalServiceRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.Logger;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.orchestrator.registry.ElasticServiceRegistry;
import java.util.ArrayList;

import java.util.List;
import javax.xml.bind.JAXBException;


public class ActionExecutor implements Runnable {

    private String url;

    private Thread t;

    private ExecutionSession executionSession;

    private String actionID;
    
    private String dataAssetIndex;

    public ActionExecutor(ExecutionSession executionSession, String actionID, String dataAssetIndex) {
        this.executionSession = executionSession;
        this.actionID = actionID;
        this.dataAssetIndex = dataAssetIndex;
    }


    private AdjustmentAction findAdjustmentActionFromID() {

        List<AdjustmentAction> listOfAdjustmentActions = executionSession.getAdjustmentProcess().getListOfAdjustmentActions();

        AdjustmentAction foundAdjustmentAction = null;

        for (AdjustmentAction adjustmentAction : listOfAdjustmentActions) {

            if (adjustmentAction.getActionID().equals(actionID)) {
                foundAdjustmentAction = adjustmentAction;
            }
        }

        return foundAdjustmentAction;
    }

    public void run() {

        AdjustmentAction adjustmentAction = findAdjustmentActionFromID();

        List<Parameter> listOfParameters = new ArrayList<Parameter>();
        
        ExternalServiceRequest controlRequest = new ExternalServiceRequest(
                executionSession.getMonitoringSession().getEdaasName(),
                executionSession.getMonitoringSession().getSessionID(),
                executionSession.getMonitoringSession().getDataAssetID(), 
                dataAssetIndex,
                listOfParameters);
        
        
        String requestXML = "";
        try {
            requestXML = JAXBUtils.marshal(controlRequest, ExternalServiceRequest.class);
        } catch (JAXBException ex) {
            System.err.println(ex);
        }

        String uri = "";

        do {

            uri = ElasticServiceRegistry.getElasticServiceURI(
                    adjustmentAction.getActionName(),
                    executionSession.getMonitoringSession().geteDaaSType());
            if (uri.equals("")) {
                Logger.logInfo("Waiting_for_Active_Elastic_Serivce ... "
                        + executionSession.getMonitoringSession().getSessionID() + " - " + adjustmentAction.getActionName());
            } else {
                Logger.logInfo("Ready_Service: " + executionSession.getMonitoringSession().getSessionID() + " - " + uri);
                ElasticServiceRegistry.occupyElasticService(uri);

                Logger.logInfo("RUN: " + uri);
                this.url = uri;

                DEPExecutionEngine.actionExecuting(executionSession.getMonitoringSession().getSessionID(), actionID);

                RestfulWSClient ws = new RestfulWSClient(url);
                ws.callPutMethod(requestXML);
            

                ElasticServiceRegistry.releaseElasticService(uri);

                DEPExecutionEngine.actionExecutionFinished(executionSession.getMonitoringSession().getSessionID(), actionID);

            }

            try {
                Thread.sleep(10000);

            } catch (InterruptedException ex) {
                System.err.println(ex);
            }

        } while (uri.equals(""));

    }

    public void start() {

        if (t == null) {
            t = new Thread(this, "");
            t.start();
        }
    }

}
