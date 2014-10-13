/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.jbpmengine.hadoop;

/**
 *
 * @author Jun
 */
public class Clustering {
    private static final Clustering INSTANCE = new Clustering();

    public static Clustering getInstance() {
        return INSTANCE;
    }

    public void start(double param) {
        System.out.println("MultiLinearCurveFitting Starting ...");
        //algorithm

    

        System.out.println("MultiLinearCurveFitting Completed ...");
    }
}
