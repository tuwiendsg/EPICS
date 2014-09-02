/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.services;

import at.ac.tuwien.dsg.common.rest.EDODelivery2RepoAccessREST;



/**
 *
 * @author Jun
 */
public class DeliveryService {

    public String getDeliveryEDO(String key) {
        String edoXML = "";
        EDODelivery2RepoAccessREST eDODelivery2RepoAccessREST = new EDODelivery2RepoAccessREST("128.130.172.216", "8080");
        edoXML = eDODelivery2RepoAccessREST.getEDO(key);
        
   
        return edoXML;

    }

}
