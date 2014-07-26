/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.demo;

import at.ac.tuwien.dsg.orchestrator.restapi.QualityEvaluatorREST;

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
        
        // start quality evaluator
        QualityEvaluatorREST evalRest = new QualityEvaluatorREST();
        evalRest.startQualityEvaluator();
        
        
        
        
        // start quality enforcement
        
        // start delivery service
    }
    
}
