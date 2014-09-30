/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.entity.others;

/**
 *
 * @author Jun
 */
public class ActionDependency {
    String actionID;
    String prerequisiteActionID;

    public ActionDependency() {
    }

    public ActionDependency(String actionID, String prerequisiteActionID) {
        this.actionID = actionID;
        this.prerequisiteActionID = prerequisiteActionID;
    }

    public String getActionID() {
        return actionID;
    }

    public void setActionID(String actionID) {
        this.actionID = actionID;
    }

    public String getPrerequisiteActionID() {
        return prerequisiteActionID;
    }

    public void setPrerequisiteActionID(String prerequisiteActionID) {
        this.prerequisiteActionID = prerequisiteActionID;
    }
    
    
}
