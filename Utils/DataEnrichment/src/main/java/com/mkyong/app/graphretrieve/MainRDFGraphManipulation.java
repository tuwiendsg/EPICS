/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.app.graphretrieve;

//import com.mkyong.app.graphretrieve.DataModelManipulation1;
import java.util.LinkedList;

/**
 *
 * @author Anindita
 */
public class MainRDFGraphManipulation {
    public static void main(String []p)
    {
        //String subject="FanSpeed";
        String subject="Sensor21";
        //String subject="SensoryModule";
        //String predicate="MonitoredObjectName";
        //String predicate="MonitoredObjectInformation";
        /*LinkedList<String> monitoringInformation2=new DataModelManipulation1().queryResult(subject);
        for(int i=0;i<monitoringInformation2.size();i++)
        {
            System.out.println("monitoring object="+monitoringInformation2.get(i));
        }*/
        
        System.out.println("########subject from main");
        LinkedList<String> monitoringInformation1=new RDFManipulationSubject().queryResultSubject(subject);
        for(int i=0;i<monitoringInformation1.size();i++)
        {
            System.out.println("monitoring object="+monitoringInformation1.get(i));
        }
        
        System.out.println("########predicate from main********");
        LinkedList<String> monitoringInformation2=new RDFManipulationPredicate().queryResultPredicate(subject);
        for(int i=0;i<monitoringInformation2.size();i++)
        {
            System.out.println("monitoring object="+monitoringInformation2.get(i));
        }
        
        System.out.println("########object from main&&&&&&&&&");
        LinkedList<String> monitoringInformation3=new RDFManipulationObject().queryResultObject(subject);
        for(int i=0;i<monitoringInformation3.size();i++)
        {
            System.out.println("monitoring object="+monitoringInformation3.get(i));
        }
    }
    
}
