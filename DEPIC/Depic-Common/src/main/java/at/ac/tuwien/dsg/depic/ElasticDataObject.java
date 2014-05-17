/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic;

import at.ac.tuwien.dsg.depic.dataelasticityrequirement.DataElasticityRequirement;
import at.ac.tuwien.dsg.depic.utility.DataGovernanceRestWS;
import at.ac.tuwien.dsg.depic.utility.MELARestWS;
import at.ac.tuwien.dsg.depic.utility.SYBLRestWS;
import at.ac.tuwien.dsg.depic.utility.Utilities;

import at.ac.tuwien.dsg.sybl.elasticstate.ElasticState;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class ElasticDataObject {
    DataElasticityRequirement dataElReq;
    DataFunctionality dataFuct;
    ElasticState eState;

    public ElasticDataObject() {
    }
    
    public ElasticDataObject withDataElasticityRequirement(DataElasticityRequirement dataElReq) {
        this.dataElReq = dataElReq;
        return this;
    }
    
    public ElasticDataObject withDataFunctionality(DataFunctionality dataFuct) {
        this.dataFuct = dataFuct;
        return this;
    }
    
    public void submitElasticityRequirement(){
        try {
            Utilities util = new Utilities();
            String elReqXML = util.convertElasticityRequirementToXML(dataElReq);
            System.out.println(elReqXML);
           
            
            SYBLRestWS syblWS = new SYBLRestWS();
            syblWS.submitDataElasticityRequirement(elReqXML);
            
            
        } catch (Exception ex) {
            
        }

        
    }
    
    public void startService(){
        // call data governance with eState
        DataGovernanceRestWS restWS = new DataGovernanceRestWS();
        
        
        restWS.startDataService("eState", getDataFuct().getDataFunctionalityURL());
        
    }
    

    public ElasticState geteState() {
        return eState;
    }

    public void seteState(ElasticState eState) {
        this.eState = eState;
    }

    public DataFunctionality getDataFuct() {
        return dataFuct;
    }

    public void setDataFuct(DataFunctionality dataFuct) {
        this.dataFuct = dataFuct;
    }
    
    
    
   
    
    public ElasticState getElasticStateFromMELA() {
        MELARestWS restWS = new MELARestWS();
        String eStateXML = restWS.getElasticStateXML();
        System.out.println("eStateXML: " + eStateXML);
        Utilities util = new Utilities();
        
        ElasticState elasticState = util.unMarshalXML2ElasticState(eStateXML);
        System.out.println("Status: " + elasticState.getStatus());
        return elasticState;
    }
    
}

