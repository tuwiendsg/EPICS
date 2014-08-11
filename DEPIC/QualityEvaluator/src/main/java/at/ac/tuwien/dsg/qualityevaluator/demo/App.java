/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qualityevaluator.demo;

import at.ac.tuwien.dsg.qualityevaluator.restclient.DataObjectRequest;

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
        
        DataObjectRequest dataObjectRequest = new DataObjectRequest();
        dataObjectRequest.getDataObjectString("0");
    }
    
}
