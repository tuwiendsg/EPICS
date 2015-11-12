/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.mlr;

/**
 *
 * @author Duc-Hung LE
 */
public class Standalone {
     public static void main(String[] args) throws InterruptedException {
        RestWS caller = new RestWS();
        caller.startMOMReader("");
        Thread.sleep(1000);
        caller.startSpeedEstimation("");
        Thread.sleep(1000);
        caller.startOutputer();
    }
}
