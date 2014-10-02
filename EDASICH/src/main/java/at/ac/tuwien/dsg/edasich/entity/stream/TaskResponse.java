/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.entity.stream;

/**
 *
 * @author Jun
 */
public class TaskResponse {
    String taskID;
    String taskResponse;

    public TaskResponse() {
    }

    public TaskResponse(String taskID, String taskResponse) {
        this.taskID = taskID;
        this.taskResponse = taskResponse;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getTaskResponse() {
        return taskResponse;
    }

    public void setTaskResponse(String taskResponse) {
        this.taskResponse = taskResponse;
    }
    
    
    
}
