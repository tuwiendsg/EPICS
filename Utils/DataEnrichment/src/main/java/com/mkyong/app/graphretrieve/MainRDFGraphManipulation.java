/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.app.graphretrieve;

import com.mkyong.app.graphretrieve.DataModelManipulation1;
import java.util.LinkedList;

/**
 *
 * @author dsg
 */
public class MainRDFGraphManipulation {
    public static void main(String []p)
    {
        String subject="FanSpeed";
        //String predicate="MonitoredObjectName";
        //String predicate="MonitoredObjectInformation";
        LinkedList<String> monitoringInformation2=new DataModelManipulation1().queryResult(subject);
        for(int i=0;i<monitoringInformation2.size();i++)
        {
            System.out.println("monitoring object="+monitoringInformation2.get(i));
        }
    }
    
}
