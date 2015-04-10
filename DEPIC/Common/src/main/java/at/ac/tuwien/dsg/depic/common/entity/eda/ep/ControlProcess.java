/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.common.entity.eda.ep;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.ElasticState;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ControlAction;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jun
 */

@XmlRootElement(name = "ControlProcess")
@XmlAccessorType(XmlAccessType.FIELD)
public class ControlProcess {
    
    @XmlElement(name = "eStateID_i", required = true)
    ElasticState eStateID_i;
    
    @XmlElement(name = "eStateID_j", required = true)
    ElasticState eStateID_j;
    
    @XmlElement(name = "listOfControlActions", required = true)
    List<ControlAction> listOfControlActions;
    
    @XmlElement(name = "listOfParallelGateways")
    List<ParallelGateway> listOfParallelGateways;

    public ControlProcess() {
    }

    public ControlProcess(ElasticState eStateID_i, ElasticState eStateID_j, List<ControlAction> listOfControlActions) {
        this.eStateID_i = eStateID_i;
        this.eStateID_j = eStateID_j;
        this.listOfControlActions = listOfControlActions;
    }

    public ElasticState geteStateID_i() {
        return eStateID_i;
    }

    public void seteStateID_i(ElasticState eStateID_i) {
        this.eStateID_i = eStateID_i;
    }

    public ElasticState geteStateID_j() {
        return eStateID_j;
    }

    public void seteStateID_j(ElasticState eStateID_j) {
        this.eStateID_j = eStateID_j;
    }

    public List<ControlAction> getListOfControlActions() {
        return listOfControlActions;
    }

    public void setListOfControlActions(List<ControlAction> listOfControlActions) {
        this.listOfControlActions = listOfControlActions;
    }

    public List<ParallelGateway> getListOfParallelGateways() {
        return listOfParallelGateways;
    }

    public void setListOfParallelGateways(List<ParallelGateway> listOfParallelGateways) {
        this.listOfParallelGateways = listOfParallelGateways;
    }

    
    
}
