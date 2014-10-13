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
public class Sampling {
    private static final Sampling INSTANCE = new Sampling();

    public static Sampling getInstance() {
        return INSTANCE;
    }

    public void start(double samplingPercentage) {
        System.out.println("Sampling Starting ...");
        //algorithm

    

        System.out.println("Sampling Completed ...");
    }
}
