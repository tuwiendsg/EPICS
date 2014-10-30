/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.jbpmengine.hadoop;

import at.ac.tuwien.dsg.utility.DesignChart;

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

        DesignChart chart12=new DesignChart();
        try {
            chart12.chart();
            
        } catch (Exception e) {
            System.out.println("Exception occured in sampling: "+e);
        }
        
        System.out.println("ClassValue=" + samplingPercentage);
        System.out.println("Sampling Completed ...");
    }
}
