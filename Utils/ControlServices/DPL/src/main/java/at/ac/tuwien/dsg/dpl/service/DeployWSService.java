/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dpl.service;

import at.ac.tuwien.dsg.dpl.action.DeployWSAction;

/**
 *
 * @author Jun
 */
public class DeployWSService {
    
    public void requestDeployWS(String serviceID){
        DeployWSAction deployWSAction = new DeployWSAction();
        deployWSAction.deployWS(serviceID);
    }
    
}
