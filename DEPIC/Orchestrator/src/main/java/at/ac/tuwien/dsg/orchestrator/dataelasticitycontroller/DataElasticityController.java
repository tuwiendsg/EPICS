/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller;

import at.ac.tuwien.dsg.common.entity.eda.ElasticState;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlProcess;
import java.util.ArrayList;
import java.util.List;

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
        
        
    }
    
    
    
    
    
    
}
