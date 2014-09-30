/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.deployment;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */
@XmlRootElement (name = "DeploymentDescription")
public class DeploymentDescription {
    List<DeployAction> listOfDeployActions;

    public DeploymentDescription() {
    }

    public DeploymentDescription(List<DeployAction> listOfDeployActions) {
        this.listOfDeployActions = listOfDeployActions;
    }

    public List<DeployAction> getListOfDeployActions() {
        return listOfDeployActions;
    }

    @XmlElement (name ="ListOfDeployActions")
    public void setListOfDeployActions(List<DeployAction> listOfDeployActions) {
        this.listOfDeployActions = listOfDeployActions;
    }

    
    
}
