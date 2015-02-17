/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.common.deployment;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "PrimitiveAction")
@XmlAccessorType(XmlAccessType.FIELD)
public class PrimitiveAction {
    
    @XmlElement(name = "listOfPrimitiveActions", required = true)
    List<DeployAction> listOfPrimitiveActions;

    public PrimitiveAction() {
    }

    public PrimitiveAction(List<DeployAction> listOfPrimitiveActions) {
        this.listOfPrimitiveActions = listOfPrimitiveActions;
    }

    public List<DeployAction> getListOfPrimitiveActions() {
        return listOfPrimitiveActions;
    }

    public void setListOfPrimitiveActions(List<DeployAction> listOfPrimitiveActions) {
        this.listOfPrimitiveActions = listOfPrimitiveActions;
    }
    
    
}
