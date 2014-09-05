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
import at.ac.tuwien.dsg.common.rest.EDORepoMeasuredAccessREST;

/**
 *
 * @author Jun
 */
public class QualityEnforcement {
    
    public void doQualityEnforcement(int objectID, String userID ) {
        
        EDORepoMeasuredAccessREST repoAccessManagement = new EDORepoMeasuredAccessREST("localhost","8080");
        String xmlString = repoAccessManagement.getElasticDataObjectString(String.valueOf(objectID), userID);
        System.out.println("Do QualityEnforcement: " + xmlString);
        
        
        EDORepoJAXB jAXB = new EDORepoJAXB();
        EDORepo eDORepo = jAXB.unmarshallingObject(xmlString);
        
        String dataObjectXml = eDORepo.getDataObject();
        DataObjectJAXB dataObjectJAXB = new DataObjectJAXB();
        
        DataObject dataObject = dataObjectJAXB.unmarshallingObject(dataObjectXml);
        
        double minCompleteness =75;
        
        double dataCompleteness = eDORepo.getDataComplenetess();
        
        if (dataCompleteness<minCompleteness) {
        
            DataCompletenessEnforcement dataCompletenessEnforcement = new DataCompletenessEnforcement();
            DataObject eDataObject = dataCompletenessEnforcement.improveDataCompleteness(dataObject, dataCompleteness);
            
            dataObject = eDataObject;
            
            eDORepo.setDataObject(dataObjectJAXB.marshallingObject(dataObject));
            eDORepo.setDataComplenetess(100);
         }
         
         
         
         
        
        String deliveryEDOStr ="";
        
        deliveryEDOStr =jAXB.marshallingObject(eDORepo);
        
        
        System.out.println("DeliverEDO: " + deliveryEDOStr);
        
        EDODeliveryRepoAccessREST eDODeliveryRepoAccessREST = new EDODeliveryRepoAccessREST();
        eDODeliveryRepoAccessREST.saveEDO(deliveryEDOStr);
        
        
        
        
        
        
        
        
        
        
    }
    
}
