/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller;

import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.AdjustmentProcess;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ExecutionSession;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ActionExecutor implements Runnable{
  
    private String url;
    
    private Thread t;

    private ExecutionSession executionSession;

   

    public ActionExecutor(ExecutionSession executionSession, String actionID) {
        this.executionSession = executionSession;
    }
    
    private String callPutMethod(String xmlStr) {
        //put get response data
        Client client = Client.create();
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type("application/xml").accept("application/xml").put(ClientResponse.class, xmlStr);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        String output =  response.getEntity(String.class);
        
        return output;
    }
    private AdjustmentAction findAdjustmentActionFromID(){
        
        List<AdjustmentAction> listOfAdjustmentActions = executionSession.getAdjustmentProcess().getListOfAdjustmentActions();
        
        
        return null;
    }
    
    
    
    public void run() {
        
        
        callPutMethod(xmlStr);
        DEPExecutionEngine.actionExecutionFinished(sessionID, actionID);
        
    }

    public void start() {

        if (t == null) {
            t = new Thread(this, "");
            t.start();
        }
    }
    
}
