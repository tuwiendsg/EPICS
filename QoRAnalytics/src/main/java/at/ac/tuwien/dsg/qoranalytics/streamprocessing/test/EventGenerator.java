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
                    SensorEvent s11 = new SensorEvent("Sensor11",randomInteger(200,500), new Date());
                    SensorEvent s12 = new SensorEvent("Sensor12",randomInteger(200,500), new Date());
                    SensorEvent s13 = new SensorEvent("Sensor13",randomInteger(400,700), new Date());
                    
                    /*
                    SensorEvent s14 = new SensorEvent("Sensor14",randomInteger(0,100), new Date());
                    SensorEvent s15 = new SensorEvent("Sensor15",randomInteger(0,100), new Date());
                    SensorEvent s16 = new SensorEvent("Sensor16",randomInteger(0,100), new Date());
                    SensorEvent s17 = new SensorEvent("Sensor17",randomInteger(0,100), new Date());
                    SensorEvent s18 = new SensorEvent("Sensor18",randomInteger(0,100), new Date());
                    
                    
                    SensorEvent s21 = new SensorEvent("Sensor21",randomInteger(0,100), new Date());
                    SensorEvent s22 = new SensorEvent("Sensor22",randomInteger(0,100), new Date());
                    SensorEvent s23 = new SensorEvent("Sensor23",randomInteger(0,100), new Date());
                    SensorEvent s24 = new SensorEvent("Sensor24",randomInteger(0,100), new Date());
                    SensorEvent s25 = new SensorEvent("Sensor25",randomInteger(0,100), new Date());
                    SensorEvent s26 = new SensorEvent("Sensor26",randomInteger(0,100), new Date());
                    SensorEvent s27 = new SensorEvent("Sensor27",randomInteger(0,100), new Date());
                    SensorEvent s28 = new SensorEvent("Sensor28",randomInteger(0,100), new Date());
                    
                    */
                    sensorEventHandler.handle(s11);
                    sensorEventHandler.handle(s12);
                    sensorEventHandler.handle(s13);
                    /*
                    sensorEventHandler.handle(s14);
                    sensorEventHandler.handle(s15);
                    sensorEventHandler.handle(s16);
                    sensorEventHandler.handle(s17);
                    sensorEventHandler.handle(s18);
                    
                    sensorEventHandler.handle(s21);
                    sensorEventHandler.handle(s22);
                    sensorEventHandler.handle(s23);
                    sensorEventHandler.handle(s24);
                    sensorEventHandler.handle(s25);
                    sensorEventHandler.handle(s26);
                    sensorEventHandler.handle(s27);
                    sensorEventHandler.handle(s28);
                    */
                    
                    
                    
                    count++;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    
                    }
                }

            }
        });
    }

    private int randomInteger(int min, int max) {

        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }

    private String getStartingMessage(){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n************************************************************");
        sb.append("\n* STARTING - GENERATING SENSOR DATA");
        sb.append("\n************************************************************\n");
        return sb.toString();
    }
}