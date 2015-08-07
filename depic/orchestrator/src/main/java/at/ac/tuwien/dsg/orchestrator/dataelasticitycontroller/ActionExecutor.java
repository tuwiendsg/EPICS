/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller;

import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.AdjustmentProcess;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ExecutionSession;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ExternalServiceRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.Logger;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.orchestrator.registry.ElasticServiceRegistry;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class ActionExecutor implements Runnable {

    private String url;

    private Thread t;

    private ExecutionSession executionSession;

    private String actionID;

    public ActionExecutor(ExecutionSession executionSession, String actionID) {
        this.executionSession = executionSession;
        this.actionID = actionID;
    }

    private String callPutMethod(String xmlStr) {
        //put get response data
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type("application/xml").accept("application/xml").put(ClientResponse.class, xmlStr);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        String output = response.getEntity(String.class);

        return output;
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

        ExternalServiceRequest controlRequest = new ExternalServiceRequest(
                executionSession.getMonitoringSession().getEdaasName(),
                executionSession.getMonitoringSession().getSessionID(),
                executionSession.getMonitoringSession().getDataAssetID(), null);

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

                callPutMethod(requestXML);

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
