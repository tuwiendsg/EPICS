/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.batchjobprocessing.analytic;

/**
 *
 * @author Jun
 */
public class StabilityEvaluation {

    private static final StabilityEvaluation INSTANCE = new StabilityEvaluation();
    
    
    public StabilityEvaluation() {
    }
    public static StabilityEvaluation getInstance() {
        return INSTANCE;
    }
    
    
    public void start(double expectedStabilityCoefficient){
        System.out.println("StabilityEvaluation Starting ...");
         
        System.out.println("StabilityEvaluation Completed ...");
    }
    
    
    
}
