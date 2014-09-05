/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qualityenforcement.demo;

import at.ac.tuwien.dsg.qualityenforcement.qodenforcement.QualityEnforcement;

/**
 *
 * @author Jun
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int objID =1;
        String userID = "1"; 
        
        
        QualityEnforcement qualityEnforcement = new QualityEnforcement();
        qualityEnforcement.doQualityEnforcement(objID, userID);
        
    }
    
}
