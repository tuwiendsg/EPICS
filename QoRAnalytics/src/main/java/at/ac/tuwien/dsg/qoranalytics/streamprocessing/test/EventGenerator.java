/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.streamprocessing.test;

import at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event.SensorEvent;
import at.ac.tuwien.dsg.qoranalytics.streamprocessing.handler.SensorEventHandler;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Jun
 */

public class EventGenerator {

   
    private SensorEventHandler sensorEventHandler;

    
    
    public EventGenerator() {
        sensorEventHandler = new SensorEventHandler();
        sensorEventHandler.afterPropertiesSet();
    }

    
    
    
    
    public void startSendingData() {

        ExecutorService xrayExecutor = Executors.newSingleThreadExecutor();

        xrayExecutor.submit(new Runnable() {
            public void run() {

                System.out.println(getStartingMessage());
                
                int count = 0;
                while (count < 1000) {
                    SensorEvent ve = new SensorEvent("",new Random().nextInt(500), new Date());
             
                    sensorEventHandler.handle(ve);
                    count++;
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    
                    }
                }

            }
        });
    }

    
    private String getStartingMessage(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n************************************************************");
        sb.append("\n* STARTING - GENERATING SENSOR DATA");
        sb.append("\n************************************************************\n");
        return sb.toString();
    }
}