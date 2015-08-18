/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.runtime;

/**
 *
 * @author Jun
 */
public enum ExecutionStatus {
    
    FINISHED("FINISHED"), WAITING("WAITING"), EXECUTING("EXECUTING");
    
    private String executionStatus;

    private ExecutionStatus(String executionStatus) {
        this.executionStatus = executionStatus;
    }

    public String getExecutionStatus() {
        return executionStatus;
    }
    
    
    
}
