/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qualityenforcement.qodenforcement;

import at.ac.tuwien.dsg.common.entity.DataObject;
import at.ac.tuwien.dsg.common.entity.others.EDORepo;
import at.ac.tuwien.dsg.common.entity.others.EDORepoJAXB;
import at.ac.tuwien.dsg.common.jaxb.DataObjectJAXB;
import at.ac.tuwien.dsg.qualityenforcement.repoaccessmanagement.EDODeliveryRepoAccessREST;
import at.ac.tuwien.dsg.qualityenforcement.repoaccessmanagement.EDORepoAccessREST;

/**
 *
 * @author Jun
 */
public class QualityEnforcement {
    
    public void doQualityEnforcement(int objectID, String userID ) {
        
        EDORepoAccessREST repoAccessManagement = new EDORepoAccessREST();
        String xmlString = repoAccessManagement.getElasticDataObjectString(String.valueOf(objectID), userID);
        System.out.println("QualityEnforcement: " + xmlString);
        
        
        EDORepoJAXB jAXB = new EDORepoJAXB();
        EDORepo eDORepo = jAXB.unmarshallingObject(xmlString);
        
        String dataObjectXml = eDORepo.getDataObject();
        DataObjectJAXB dataObjectJAXB = new DataObjectJAXB();
        
        DataObject dataObject = dataObjectJAXB.unmarshallingObject(dataObjectXml);
        double dataCompleteness = eDORepo.getDataComplenetess();
        DataCompletenessEnforcement dataCompletenessEnforcement = new DataCompletenessEnforcement();
        DataObject eDataObject = dataCompletenessEnforcement.improveDataCompleteness(dataObject, dataCompleteness);
        
        eDORepo.setDataObject(dataObjectJAXB.marshallingObject(eDataObject));
        eDORepo.setDataComplenetess(100);
        String deliveryEDOStr ="";
        
        deliveryEDOStr =jAXB.marshallingObject(eDORepo);
        
        
        System.out.println("DeliverEDO: " + deliveryEDOStr);
        
        EDODeliveryRepoAccessREST eDODeliveryRepoAccessREST = new EDODeliveryRepoAccessREST();
        eDODeliveryRepoAccessREST.saveEDO(deliveryEDOStr);
        
        
        
        
        
        
        
        
        
        
    }
    
}
