/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.demo;

import at.ac.tuwien.dsg.common.rest.EDORepoCleanRest;

/**
 *
 * @author Jun
 */
public class EDORepoCleanApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        EDORepoCleanRest eDORepoCleanRest = new EDORepoCleanRest("128.130.172.216", "8080");
        eDORepoCleanRest.emptyEDORepo();
    }
    
}