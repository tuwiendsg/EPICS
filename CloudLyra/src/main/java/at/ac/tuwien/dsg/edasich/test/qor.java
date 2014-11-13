/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.test;

import at.ac.tuwien.dsg.edasich.entity.batch.QoRMetric;
import at.ac.tuwien.dsg.edasich.entity.batch.Range;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class qor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       
        
        String str="-5384590581385367\n" +
"-";
        
        
        str = str.replaceAll("\n", "");
        
        System.out.println("*" + str + "*");
    }
    
    void sampleQoRModel(){
        
        List<Range> listOfRange1 = new ArrayList<>();
        
        QoRMetric metric1 = new QoRMetric("samplingSize", "percent", listOfRange1);
        
    }
    
}
