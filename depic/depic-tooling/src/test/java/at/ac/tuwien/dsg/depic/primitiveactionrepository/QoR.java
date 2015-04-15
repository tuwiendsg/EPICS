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
public class QoR {

    public QoRModel sampleQoRModel() {

        List<QoRMetric> listOfQoRMetrics = sampleListOfQoRMetrics();
        List<QElement> listOfQElements = sampleListOfQElements();
        QoRModel qorModel = new QoRModel(listOfQoRMetrics, listOfQElements, DataAssetForm.XML);

        return qorModel;

    }

     private  List<QoRMetric> sampleListOfQoRMetrics(){
        
        List<QoRMetric> listOfQoRMetrics = new ArrayList<QoRMetric>();
        
        
        // data completeness metric
        Range dc1 = new Range("dc1",0, 75);
        Range dc2 = new Range("dc2",76,100);

        List<Range> listOfRanges_dc = new ArrayList<Range>();
        listOfRanges_dc.add(dc1);
        listOfRanges_dc.add(dc2);
        
        String name_dc = "columnCompleteness";
        String unit_dc = "%";
        
        QoRMetric metric_dc = new QoRMetric(name_dc, unit_dc, listOfRanges_dc);
        listOfQoRMetrics.add(metric_dc);
        
        // data accuracy metric
        Range da1 = new Range("da1",0, 75);
        Range da2 = new Range("da2",76,100);

        List<Range> listOfRanges_da = new ArrayList<Range>();
        listOfRanges_da.add(da1);
        listOfRanges_da.add(da2);

        String name_da = "dataAccuracyForVotage";
        String unit_da = "%";
        
        QoRMetric metric_da = new QoRMetric(name_da, unit_da, listOfRanges_da);
        listOfQoRMetrics.add(metric_da);
        
   
        // throughput metric
        
        Range th1 = new Range("th1", 0,0.49);
        Range th2 = new Range("th2",0.50,Double.MAX_VALUE);
     
        List<Range> listOfRanges_th = new ArrayList<Range>();
        listOfRanges_th.add(th1);
        listOfRanges_th.add(th2);

        String name_th = "throughputOfDataItemPerSecond";
        String unit_th = "d/s";
        
        QoRMetric metric_th = new QoRMetric(name_th, unit_th, listOfRanges_th);
        listOfQoRMetrics.add(metric_th);
        
        
        return listOfQoRMetrics;
    }
    
    private  List<QElement> sampleListOfQElements(){
        
        List<QElement> listOfQElements = new ArrayList<QElement>();
        
        List<String> listOfMetricRanges1 = new ArrayList<String>();
        listOfMetricRanges1.add("dc1");
        listOfMetricRanges1.add("da1");
        listOfMetricRanges1.add("th1");
        double price1=0.05;

        QElement qElement1 = new QElement("qElement1", listOfMetricRanges1, price1);
        
        listOfQElements.add(qElement1);
        
        List<String> listOfMetricRanges2 = new ArrayList<String>();
        listOfMetricRanges2.add("dc1");
        listOfMetricRanges2.add("da1");
        listOfMetricRanges2.add("th2");
        double price2=0.06;
        
        QElement qElement2 = new QElement("qElement2", listOfMetricRanges2, price2);
        listOfQElements.add(qElement2);
        
        List<String> listOfMetricRanges3 = new ArrayList<String>();
        listOfMetricRanges3.add("dc1");
        listOfMetricRanges3.add("da2");
        listOfMetricRanges3.add("th2");
        double price3=0.08;
        
        QElement qElement3 = new QElement("qElement3", listOfMetricRanges3, price3);
        listOfQElements.add(qElement3);
        
        List<String> listOfMetricRanges4 = new ArrayList<String>();
        listOfMetricRanges4.add("dc2");
        listOfMetricRanges4.add("da1");
        listOfMetricRanges4.add("th2");
        double price4=0.08;
        
        QElement qElement4 = new QElement("qElement4", listOfMetricRanges4, price4);
        listOfQElements.add(qElement4);
        
        List<String> listOfMetricRanges5 = new ArrayList<String>();
        listOfMetricRanges5.add("dc2");
        listOfMetricRanges5.add("da2");
        listOfMetricRanges5.add("th2");
        double price5=0.1;
        
        QElement qElement5 = new QElement("qElement5", listOfMetricRanges5, price5);
        listOfQElements.add(qElement5);
  
        
        return listOfQElements;
        
        
    }
}
