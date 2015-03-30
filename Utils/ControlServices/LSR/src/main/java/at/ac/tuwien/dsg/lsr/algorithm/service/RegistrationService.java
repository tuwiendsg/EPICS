/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.lsr.algorithm.service;

/**
 *
 * @author Jun
 */
public class RegistrationService implements Runnable {

    private Thread t;
    private String threadName;
    private boolean isInterrupt;

    public RegistrationService(String name) {
        threadName = name;
        isInterrupt = false;
    }

    public void run() {
        while (!isInterrupt) {
            System.out.println(System.currentTimeMillis());
        }
    }

    public void stop() {
        isInterrupt = true;
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
