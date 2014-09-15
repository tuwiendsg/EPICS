/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Jun
 */
public class Attribute {
    String id;
    Date dateTime;
    double global_active_power;
    double global_reactive_power;
    double voltage;
    double global_intensity;
    double sub_metering_1;
    double sub_metering_2;
    double sub_metering_3;

    public Attribute() {
    }

    public Attribute(String id, Date dateTime, double global_active_power, double global_reactive_power, double voltage, double global_intensity, double sub_metering_1, double sub_metering_2, double sub_metering_3) {
        this.id = id;
        this.dateTime = dateTime;
        this.global_active_power = global_active_power;
        this.global_reactive_power = global_reactive_power;
        this.voltage = voltage;
        this.global_intensity = global_intensity;
        this.sub_metering_1 = sub_metering_1;
        this.sub_metering_2 = sub_metering_2;
        this.sub_metering_3 = sub_metering_3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public double getGlobal_active_power() {
        return global_active_power;
    }

    public void setGlobal_active_power(double global_active_power) {
        this.global_active_power = global_active_power;
    }

    public double getGlobal_reactive_power() {
        return global_reactive_power;
    }

    public void setGlobal_reactive_power(double global_reactive_power) {
        this.global_reactive_power = global_reactive_power;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public double getGlobal_intensity() {
        return global_intensity;
    }

    public void setGlobal_intensity(double global_intensity) {
        this.global_intensity = global_intensity;
    }

    public double getSub_metering_1() {
        return sub_metering_1;
    }

    public void setSub_metering_1(double sub_metering_1) {
        this.sub_metering_1 = sub_metering_1;
    }

    public double getSub_metering_2() {
        return sub_metering_2;
    }

    public void setSub_metering_2(double sub_metering_2) {
        this.sub_metering_2 = sub_metering_2;
    }

    public double getSub_metering_3() {
        return sub_metering_3;
    }

    public void setSub_metering_3(double sub_metering_3) {
        this.sub_metering_3 = sub_metering_3;
    }
    
    
    
    
    
}
