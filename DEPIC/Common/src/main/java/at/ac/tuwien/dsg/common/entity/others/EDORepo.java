/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.others;

import at.ac.tuwien.dsg.common.entity.DataItem;
import at.ac.tuwien.dsg.common.entity.DataObject;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @author Jun
 */


@XmlRootElement (name = "EDORepo")
public class EDORepo {
    int objID;
    int userID;
    
    String dataObject;
    double dataComplenetess;
    double responseTime;

    public EDORepo() {
    }

    public EDORepo(int objID, int userID, String dataObject, double dataComplenetess, double responseTime) {
        this.objID = objID;
        this.userID = userID;
        this.dataObject = dataObject;
        this.dataComplenetess = dataComplenetess;
        this.responseTime = responseTime;
    }

    
    
    
    public int getObjID() {
        return objID;
    }

    @XmlElement (name ="objID")
    public void setObjID(int objID) {
        this.objID = objID;
    }

    public int getUserID() {
        return userID;
    }

    @XmlElement (name ="userID")
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDataObject() {
        return dataObject;
    }

    @XmlElement (name ="dataObject")
    public void setDataObject(String dataObject) {
        this.dataObject = dataObject;
    }

    public double getDataComplenetess() {
        return dataComplenetess;
    }

    @XmlElement (name ="completeness")
    public void setDataComplenetess(double dataComplenetess) {
        this.dataComplenetess = dataComplenetess;
    }

    public double getResponseTime() {
        return responseTime;
    }
    
    @XmlElement (name ="responseTime")
    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }

    
   
    
    
}
