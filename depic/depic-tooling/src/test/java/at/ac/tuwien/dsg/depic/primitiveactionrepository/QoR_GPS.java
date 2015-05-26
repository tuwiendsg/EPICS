/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.primitiveactionrepository;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAssetForm;
import at.ac.tuwien.dsg.depic.common.entity.qor.QElement;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRMetric;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.depic.common.entity.qor.Range;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class QoR_GPS {

    public QoRModel sampleQoRModel() {

        List<QoRMetric> listOfQoRMetrics = sampleListOfQoRMetrics();
        List<QElement> listOfQElements = sampleListOfQElements();
        QoRModel qorModel = new QoRModel(listOfQoRMetrics, listOfQElements, DataAssetForm.XML);

        return qorModel;

    }

     private  List<QoRMetric> sampleListOfQoRMetrics(){
        
        List<QoRMetric> listOfQoRMetrics = new ArrayList<QoRMetric>();
        
        
        // data completeness metric
        Range dc1 = new Range("speedArc_co1",0, 90);
        Range dc2 = new Range("speedArc_co2",91,100);

        List<Range> listOfRanges_dc = new ArrayList<Range>();
        listOfRanges_dc.add(dc1);
        listOfRanges_dc.add(dc2);
        
        String name_dc = "speedArc";
        String unit_dc = "%";
        
        QoRMetric metric_dc = new QoRMetric(name_dc, unit_dc, listOfRanges_dc);
        listOfQoRMetrics.add(metric_dc);
        
       
        
        
        Range vh1 = new Range("vehicleArc_co1",0, 80);
        Range vh2 = new Range("vehicleArc_co2",81,100);

        List<Range> listOfRanges_vh = new ArrayList<Range>();
        listOfRanges_vh.add(vh1);
        listOfRanges_vh.add(vh2);

        String name_vh = "vehicleArc";
        String unit_vh = "%";
        
        QoRMetric metric_vh = new QoRMetric(name_vh, unit_vh, listOfRanges_vh);
        listOfQoRMetrics.add(metric_vh);
        
   
        
        
        Range th1 = new Range("deliveryTime_co1", 54,Double.MAX_VALUE);
        Range th2 = new Range("deliveryTime_co2", 0,53);
        
     
     
        List<Range> listOfRanges_th = new ArrayList<Range>();
        listOfRanges_th.add(th1);
        listOfRanges_th.add(th2);
 

        String name_th = "deliveryTime";
        String unit_th = "s";
        
        QoRMetric metric_th = new QoRMetric(name_th, unit_th, listOfRanges_th);
        listOfQoRMetrics.add(metric_th);
        
        
        return listOfQoRMetrics;
    }
    
    private  List<QElement> sampleListOfQElements(){
        
        List<QElement> listOfQElements = new ArrayList<QElement>();
        
        List<String> listOfMetricRanges1 = new ArrayList<String>();
        listOfMetricRanges1.add("speedArc_co2");
        listOfMetricRanges1.add("vehicleArc_co2");
        listOfMetricRanges1.add("deliveryTime_co2");
        double price1=0.009;

        QElement qElement1 = new QElement("qElement1", listOfMetricRanges1, price1);
        listOfQElements.add(qElement1);
        
        
        
        List<String> listOfMetricRanges2 = new ArrayList<String>();
        listOfMetricRanges2.add("speedArc_co1");
        listOfMetricRanges2.add("vehicleArc_co1");
        listOfMetricRanges2.add("deliveryTime_co1");
        double price2=0.005;
        
        QElement qElement2 = new QElement("qElement2", listOfMetricRanges2, price2);
        listOfQElements.add(qElement2);
        
        
        
        List<String> listOfMetricRanges3 = new ArrayList<String>();
        listOfMetricRanges3.add("speedArc_co2");
        listOfMetricRanges3.add("vehicleArc_co1");
        listOfMetricRanges3.add("deliveryTime_co2");
        double price3=0.007;
        
        QElement qElement3 = new QElement("qElement3", listOfMetricRanges3, price3);
        listOfQElements.add(qElement3);
        
        
        
        List<String> listOfMetricRanges4 = new ArrayList<String>();
        listOfMetricRanges4.add("speedArc_co1");
        listOfMetricRanges4.add("vehicleArc_co2");
        listOfMetricRanges4.add("deliveryTime_co2");
        double price4=0.008;
        
        QElement qElement4 = new QElement("qElement4", listOfMetricRanges4, price4);
        listOfQElements.add(qElement4);
        
        
        
        List<String> listOfMetricRanges5 = new ArrayList<String>();
        listOfMetricRanges5.add("speedArc_co1");
        listOfMetricRanges5.add("vehicleArc_co1");
        listOfMetricRanges5.add("deliveryTime_co2");
        double price5=0.006;
        
        QElement qElement5 = new QElement("qElement5", listOfMetricRanges5, price5);
        listOfQElements.add(qElement5);
      
        
        return listOfQElements;
        
        
    }
}
