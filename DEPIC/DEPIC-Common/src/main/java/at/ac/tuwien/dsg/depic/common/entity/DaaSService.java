/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.common.entity;

import java.util.List;

/**
 *
 * @author Jun
 */
public class DaaSService {
 
    private User user;
    private List listOfEDO;
    
    
    
    public boolean startService(User user){
        
        user.setUserID(validateUser(user));     
        
        
        
        return true; 
    }
    
    private String validateUser(User user) {
        
        
        return "u01";
    }
    
    
}
