/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.analytic;

/**
 *
 * @author Jun
 */
public class LeastSquareRegression {

    private static final LeastSquareRegression INSTANCE = new LeastSquareRegression();

    public static LeastSquareRegression getInstance() {
        return INSTANCE;
    }

    public LeastSquareRegression() {
    }
    
    public void start() {
        System.out.println("LeastSquareRegression Starting ...");
        //algorithm
        
        
        System.out.println("LeastSquareRegression Completed ...");
    }
    
}
