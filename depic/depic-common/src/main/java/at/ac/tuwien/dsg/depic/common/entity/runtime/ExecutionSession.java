/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.runtime;

import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.AdjustmentProcess;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ExecutionSession {
    
    MonitoringSession monitoringSession;
    List<ExecutionStep> listOfExecutionSteps;
    AdjustmentProcess adjustmentProcess;

    public ExecutionSession(MonitoringSession monitoringSession, List<ExecutionStep> listOfExecutionSteps, AdjustmentProcess adjustmentProcess) {
        this.monitoringSession = monitoringSession;
        this.listOfExecutionSteps = listOfExecutionSteps;
        this.adjustmentProcess = adjustmentProcess;
    }

    
    public MonitoringSession getMonitoringSession() {
        return monitoringSession;
    }

    public void setMonitoringSession(MonitoringSession monitoringSession) {
        this.monitoringSession = monitoringSession;
    }

    public List<ExecutionStep> getListOfExecutionSteps() {
        return listOfExecutionSteps;
    }

    public void setListOfExecutionSteps(List<ExecutionStep> listOfExecutionSteps) {
        this.listOfExecutionSteps = listOfExecutionSteps;
    }

    public AdjustmentProcess getAdjustmentProcess() {
        return adjustmentProcess;
    }

    public void setAdjustmentProcess(AdjustmentProcess adjustmentProcess) {
        this.adjustmentProcess = adjustmentProcess;
    }
    
    
    
    
    
}
