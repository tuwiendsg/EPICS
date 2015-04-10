/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.entity.eda.ep;

/**
 *
 * @author Jun
 */
public class QueueTask {
    String taskID;
    String property;

    public QueueTask(String taskID, String property) {
        this.taskID = taskID;
        this.property = property;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
    
    
}
