/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.streamprocessing.entity.event;

import java.util.Date;

/**
 *
 * @author Jun
 */
public class SensorEvent {
    
    private int value;
    private Date timeOfReading;

    public SensorEvent() {
    }

    public SensorEvent(int value, Date timeOfReading) {
        this.value = value;
        this.timeOfReading = timeOfReading;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Date getTimeOfReading() {
        return timeOfReading;
    }

    public void setTimeOfReading(Date timeOfReading) {
        this.timeOfReading = timeOfReading;
    }
    
    
    
    @Override
    public String toString() {
        return "SensorEvent [" + value + "]";
    }
}
