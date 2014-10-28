/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller;

import at.ac.tuwien.dsg.common.entity.eda.ElasticState;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlProcess;
import at.ac.tuwien.dsg.common.entity.process.Parameter;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.orchestrator.registry.ControlServiceRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class DataElasticityController {
    
    List<ElasticState> listOfExpectedElasticStates;
    List<ControlProcess> listOfControlProcesses;
    
    
    public void startControlElasticState (ElasticState currentElasticState){
        
        List<ControlProcess> listOfPotentialControlProcesses = new ArrayList<>();
        
        for (ElasticState expectedElasticState : listOfExpectedElasticStates) {
            
            ControlProcess cp = determineAppropriateControlProcess(currentElasticState, expectedElasticState);
            listOfPotentialControlProcesses.add(cp);
            
        }
        
        ControlProcess controlProcess = determineTheBestControlProcess(listOfPotentialControlProcesses);
        executeControlProcess(controlProcess);
        
    
    }
    
    
    private ControlProcess determineAppropriateControlProcess(ElasticState elasticState_i, ElasticState elasticState_j){
        
        ControlProcess cp=null;
            for (ControlProcess controlProcess : listOfControlProcesses) {
                if (controlProcess.geteStateID_i().equals(elasticState_i) && controlProcess.geteStateID_j().equals(elasticState_j)){
                    cp = controlProcess;
                }
            }
        
        return cp;
    }
    
    private ControlProcess determineTheBestControlProcess(List<ControlProcess> potentialControlProcesses) {
        
        int minControlActions = Integer.MAX_VALUE;
        int cpIndex =0;
        for (ControlProcess cp : potentialControlProcesses) {
            if (cp.getListOfControlActions().size()<minControlActions) {
                minControlActions = cp.getListOfControlActions().size();
                cpIndex = potentialControlProcesses.indexOf(cp);
            }
            
        }
        
        return potentialControlProcesses.get(cpIndex);
    }
    
    private void executeControlProcess(ControlProcess cp) {
        List<ControlAction> listOfControlActions = cp.getListOfControlActions();
        
        for (ControlAction ca : listOfControlActions){
            String controlActionID = ca.getControlActionID();
            List<Parameter> listOfParameters = ca.getListOfParameters();
            ControlServiceRegistry controlServiceRegistry = new ControlServiceRegistry();
            String uri = controlServiceRegistry.getControlServiceURI(controlActionID);
            String paramStr ="";
            try {
                paramStr  = JAXBUtils.marshal(listOfParameters, List.class);
            } catch (JAXBException ex) {
                Logger.getLogger(DataElasticityController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            RestfulWSClient ws = new RestfulWSClient(uri);
            ws.callPutMethod(paramStr);
     
        }
    }
    
    
    
    
    
    
}
