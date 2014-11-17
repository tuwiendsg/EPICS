/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.rmctrl.service;

import at.ac.tuwien.dsg.rmctrl.action.RemoveVMAction;

/**
 *
 * @author Jun
 */
public class RemoveVMService {
    public void requestRmVMService(String noOfVM){
        RemoveVMAction removeVMAction = new RemoveVMAction();
        removeVMAction.removeVM(noOfVM);
    }
}
