/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.addvm.service;

import at.ac.tuwien.dsg.addvm.action.AddVMAction;

/**
 *
 * @author Jun
 */
public class AddVMService {
    
    public String requestAddVMService(String noOfVM){
        AddVMAction addVMAction = new AddVMAction();
        return  addVMAction.addVM(noOfVM);

    }
    
}
