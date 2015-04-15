

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package at.ac.tuwien.dsg.mela;
//
//
//
//import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ControlAction;
//import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
//
//import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
//
//import at.ac.tuwien.dsg.depic.common.entity.qor.QElement;
//import at.ac.tuwien.dsg.depic.common.entity.qor.QoRMetric;
//import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
//import at.ac.tuwien.dsg.depic.common.entity.qor.Range;
//
//import java.util.AbstractList;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// * @author Jun
// */
//public class SampleData {
//    
//    
//   
//    
//    
//    public  QoRModel  sampleQoRModel() {
//    
//       List<QoRMetric> listOfQoRMetrics = sampleListOfQoRMetrics();  
//       List<QElement> listOfQElements = sampleListOfQElements();       
//       QoRModel qorModel = new QoRModel(listOfQoRMetrics, listOfQElements);
//       
//        
//        return qorModel;
//                
//    }
//    
//     private  List<QoRMetric> sampleListOfQoRMetrics(){
//        
//        List<QoRMetric> listOfQoRMetrics = new ArrayList<QoRMetric>();
//        
//        
//        // data completeness metric
//        Range dc1 = new Range("dc1",0, 75);
//        Range dc2 = new Range("dc2",76,100);
//
//        List<Range> listOfRanges_dc = new ArrayList<Range>();
//        listOfRanges_dc.add(dc1);
//        listOfRanges_dc.add(dc2);
//        
//        String name_dc = "datacompleteness";
//        String unit_dc = "%";
//        
//        QoRMetric metric_dc = new QoRMetric(name_dc, unit_dc, listOfRanges_dc);
//        listOfQoRMetrics.add(metric_dc);
//        
//        // data accuracy metric
//        Range da1 = new Range("da1",0, 75);
//        Range da2 = new Range("da2",76,100);
//
//        List<Range> listOfRanges_da = new ArrayList<Range>();
//        listOfRanges_da.add(da1);
//        listOfRanges_da.add(da2);
//
//        String name_da = "dataaccuracy";
//        String unit_da = "%";
//        
//        QoRMetric metric_da = new QoRMetric(name_da, unit_da, listOfRanges_da);
//        listOfQoRMetrics.add(metric_da);
//        
//   
//        // throughput metric
//        
//        Range th1 = new Range("th1", 0,0.49);
//        Range th2 = new Range("th2",0.50,Double.MAX_VALUE);
//     
//        List<Range> listOfRanges_th = new ArrayList<Range>();
//        listOfRanges_th.add(th1);
//        listOfRanges_th.add(th2);
//
//        String name_th = "throughput";
//        String unit_th = "das/h";
//        
//        QoRMetric metric_th = new QoRMetric(name_th, unit_th, listOfRanges_th);
//        listOfQoRMetrics.add(metric_th);
//        
//        
//        return listOfQoRMetrics;
//    }
//    
//    private  List<QElement> sampleListOfQElements(){
//        
//        List<QElement> listOfQElements = new ArrayList<QElement>();
//        
//        
//        
//        MetricRange metricRange11 = new MetricRange("datacompleteness", "dc1");
//        MetricRange metricRange12 = new MetricRange("dataaccuracy", "da1");
//        MetricRange metricRange13 = new MetricRange("throughput", "th1");
//        List<MetricRange> listOfMetricRanges1 = new ArrayList<MetricRange>();
//        listOfMetricRanges1.add(metricRange11);
//        listOfMetricRanges1.add(metricRange12);
//        listOfMetricRanges1.add(metricRange13);
//        double price1=0.05;
//        String unit1="eur";
//        
//        QElement qElement1 = new QElement("qElement1", listOfMetricRanges1, price1, unit1);
//        
//        listOfQElements.add(qElement1);
//        
//        
//        MetricRange metricRange21 = new MetricRange("datacompleteness", "dc1");
//        MetricRange metricRange22 = new MetricRange("dataaccuracy", "da1");
//        MetricRange metricRange23 = new MetricRange("throughput", "th2");
//        List<MetricRange> listOfMetricRanges2 = new ArrayList<MetricRange>();
//        listOfMetricRanges2.add(metricRange21);
//        listOfMetricRanges2.add(metricRange22);
//        listOfMetricRanges2.add(metricRange23);
//        double price2=0.06;
//        String unit2="eur";
//        
//        QElement qElement2 = new QElement("qElement2", listOfMetricRanges2, price2, unit2);
//        listOfQElements.add(qElement2);
//        
//        
//        MetricRange metricRange31 = new MetricRange("datacompleteness", "dc1");
//        MetricRange metricRange32 = new MetricRange("dataaccuracy", "da2");
//        MetricRange metricRange33 = new MetricRange("throughput", "th2");
//        List<MetricRange> listOfMetricRanges3 = new ArrayList<MetricRange>();
//        listOfMetricRanges3.add(metricRange31);
//        listOfMetricRanges3.add(metricRange32);
//        listOfMetricRanges3.add(metricRange33);
//        double price3=0.08;
//        String unit3="eur";
//        
//        QElement qElement3 = new QElement("qElement3", listOfMetricRanges3, price3, unit3);
//        listOfQElements.add(qElement3);
//        
//        
//        MetricRange metricRange41 = new MetricRange("datacompleteness", "dc2");
//        MetricRange metricRange42 = new MetricRange("dataaccuracy", "da1");
//        MetricRange metricRange43 = new MetricRange("throughput", "th2");
//        List<MetricRange> listOfMetricRanges4 = new ArrayList<MetricRange>();
//        listOfMetricRanges4.add(metricRange41);
//        listOfMetricRanges4.add(metricRange42);
//        listOfMetricRanges4.add(metricRange43);
//        double price4=0.08;
//        String unit4="eur";
//        
//        QElement qElement4 = new QElement("qElement4", listOfMetricRanges4, price4, unit4);
//        listOfQElements.add(qElement4);
//        
//        
//        MetricRange metricRange51 = new MetricRange("datacompleteness", "dc2");
//        MetricRange metricRange52 = new MetricRange("dataaccuracy", "da2");
//        MetricRange metricRange53 = new MetricRange("throughput", "th2");
//        List<MetricRange> listOfMetricRanges5 = new ArrayList<MetricRange>();
//        listOfMetricRanges5.add(metricRange51);
//        listOfMetricRanges5.add(metricRange52);
//        listOfMetricRanges5.add(metricRange53);
//        double price5=0.1;
//        String unit5="eur";
//        
//        QElement qElement5 = new QElement("qElement5", listOfMetricRanges5, price5, unit5);
//        listOfQElements.add(qElement5);
//        
////        MetricRange metricRange61 = new MetricRange("datacompleteness", "d2");
////        MetricRange metricRange62 = new MetricRange("throughput", "t3");
////        List<MetricRange> listOfMetricRanges6 = new ArrayList<MetricRange>();
////        listOfMetricRanges6.add(metricRange61);
////        listOfMetricRanges6.add(metricRange62);
////        double price6=0.055;
////        String unit6="eur";
//        
////        QElement qElement6 = new QElement("qElementID6", listOfMetricRanges6, price6, unit6);
////        listOfQElements.add(qElement6);
////        
////        MetricRange metricRange71 = new MetricRange("datacompleteness", "d3");
////        MetricRange metricRange72 = new MetricRange("throughput", "t1");
////        List<MetricRange> listOfMetricRanges7 = new ArrayList<MetricRange>();
////        listOfMetricRanges7.add(metricRange71);
////        listOfMetricRanges7.add(metricRange72);
////        double price7=0.045;
////        String unit7="eur";
////        
////        QElement qElement7 = new QElement("qElementID7", listOfMetricRanges7, price7, unit7);
////        listOfQElements.add(qElement7);
////        
////        
////        MetricRange metricRange81 = new MetricRange("datacompleteness", "d3");
////        MetricRange metricRange82 = new MetricRange("throughput", "t2");
////        List<MetricRange> listOfMetricRanges8 = new ArrayList<MetricRange>();
////        listOfMetricRanges8.add(metricRange81);
////        listOfMetricRanges8.add(metricRange82);
////        double price8=0.055;
////        String unit8="eur";
////        
////        QElement qElement8 = new QElement("qElementID8", listOfMetricRanges8, price8, unit8);
////        listOfQElements.add(qElement8);
////        
////        
////        
////        MetricRange metricRange91 = new MetricRange("datacompleteness", "d3");
////        MetricRange metricRange92 = new MetricRange("throughput", "t3");
////        List<MetricRange> listOfMetricRanges9 = new ArrayList<MetricRange>();
////        listOfMetricRanges9.add(metricRange91);
////        listOfMetricRanges9.add(metricRange92);
////        double price9=0.065;
////        String unit9="eur";
////        
////        QElement qElement9 = new QElement("qElementID9", listOfMetricRanges9, price9, unit9);
////        listOfQElements.add(qElement9); 
//       
//        
//        
//        return listOfQElements;
//        
//        
//    }
//    
//    
//    public MetricElasticityProcess sampleEPDataCompleteness(){
//    
//        
//        List<MetricCondition> listOfMetricConditions = new ArrayList<MetricCondition>();
//        
//        MetricCondition m1 = new MetricCondition("datacompleteness","c11", 0, 75);
//        MetricCondition m2 = new MetricCondition("datacompleteness","c21", 76, 90);
//        MetricCondition m3 = new MetricCondition("datacompleteness","c31", 91, 100);
//       
//        listOfMetricConditions.add(m1);
//        listOfMetricConditions.add(m2);
//        listOfMetricConditions.add(m3);
//        
//        
//        /// MetricElasticityProcess - datacompleteness
//        
//        String metricName_d = "datacompleteness";
//        MonitoringAction monitorAction_d = new MonitoringAction("datacompletenessMeasurement");
//        List<MetricControlActions> listOfTriggerActions_d = new ArrayList<MetricControlActions>();
//        
//        
//             
//        
//        //////////////// trigger action 1
//        
//        List<ControlAction> listOfControlActions_d_1 = new ArrayList<ControlAction>();
//        
//        String ca_name_d_1_1 = "LSR";     
//        List<Parameter> listOfParameters_d_1_1 = new ArrayList<Parameter>();
//        Parameter param_d_1_1_1 = new Parameter("attributeIndex", "Integer", "2");
//        listOfParameters_d_1_1.add(param_d_1_1_1);
//        ControlAction ca_d_1_1 = new ControlAction(ca_name_d_1_1, listOfParameters_d_1_1);    
//        listOfControlActions_d_1.add(ca_d_1_1);
//        
//        String ca_name_d_1_2 = "LSR";     
//        List<Parameter> listOfParameters_d_1_2 = new ArrayList<Parameter>();
//        Parameter param_d_1_2_1 = new Parameter("attributeIndex", "Integer", "4");
//        listOfParameters_d_1_2.add(param_d_1_2_1);
//        ControlAction ca_d_1_2 = new ControlAction(ca_name_d_1_2, listOfParameters_d_1_2);    
//        listOfControlActions_d_1.add(ca_d_1_2);
//        
//        
//        MetricControlActions tr_d_1 = new MetricControlActions("c21", "c31", listOfControlActions_d_1);
//        listOfTriggerActions_d.add(tr_d_1);
//        
//    
//        //////// trigger action 2
//        
//        List<ControlAction> listOfControlActions_d_2 = new ArrayList<ControlAction>();
//        
//        ///////////////////
//        String ca_name_d_2_1 = "LSR";
//        List<Parameter> listOfParameters_d_2_1 = new ArrayList<Parameter>();
//        Parameter param_d_2_1_1 = new Parameter("attributeIndex", "Integer", "2");       
//        listOfParameters_d_2_1.add(param_d_2_1_1);        
//        ControlAction ca_d_2_1 = new ControlAction(ca_name_d_2_1, listOfParameters_d_2_1);    
//        listOfControlActions_d_2.add(ca_d_2_1);
//        
//
//        
//        String ca_name_d_2_2 = "LSR"; 
//        List<Parameter> listOfParameters_d_2_2 = new ArrayList<Parameter>();
//        Parameter param_d_2_2_1 = new Parameter("attributeIndex", "Integer", "4");
//        listOfParameters_d_2_2.add(param_d_2_2_1);        
//        ControlAction ca_d_2_2 = new ControlAction(ca_name_d_2_2, listOfParameters_d_2_2);    
//        listOfControlActions_d_2.add(ca_d_2_2);
// 
//        
//        MetricControlActions tr_d_2 = new MetricControlActions("c11", "c31", listOfControlActions_d_2);
//        listOfTriggerActions_d.add(tr_d_2);
//        /////////////////
//
//        
//        
//        MetricElasticityProcess mep1 = new MetricElasticityProcess(metricName_d, listOfMetricConditions, monitorAction_d, listOfTriggerActions_d);
//        
//        
//        
//        return mep1;
//        
//        
//    }
//    
//    public MetricElasticityProcess sampleEPDataAccuracy(){
//    
//        
//        List<MetricCondition> listOfMetricConditions = new ArrayList<MetricCondition>();
//        
//        MetricCondition m1 = new MetricCondition("dataaccuracy","c12", 0, 75);
//        MetricCondition m2 = new MetricCondition("dataaccuracy","c22", 76, 90);
//        MetricCondition m3 = new MetricCondition("dataaccuracy","c32", 91, 100);
//       
//        listOfMetricConditions.add(m1);
//        listOfMetricConditions.add(m2);
//        listOfMetricConditions.add(m3);
//        
//        
//        /// MetricElasticityProcess - datacompleteness
//        
//        String metricName_d = "dataaccuracy";
//        MonitoringAction monitorAction_d = new MonitoringAction("dataaccuracyMeasurement");
//        List<MetricControlActions> listOfTriggerActions_d = new ArrayList<MetricControlActions>();
//        
//        
//             
//        
//        //////////////// trigger action 1
//        
//        List<ControlAction> listOfControlActions_d_1 = new ArrayList<ControlAction>();
//        
//        String ca_name_d_1_1 = "DIR";     
//        List<Parameter> listOfParameters_d_1_1 = new ArrayList<Parameter>();
//        Parameter param_d_1_1_1 = new Parameter("attributeIndex", "Integer", "3");
//        listOfParameters_d_1_1.add(param_d_1_1_1);
//        ControlAction ca_d_1_1 = new ControlAction(ca_name_d_1_1, listOfParameters_d_1_1);    
//        listOfControlActions_d_1.add(ca_d_1_1);
//       
//        
//        MetricControlActions tr_d_1 = new MetricControlActions("c22", "c32", listOfControlActions_d_1);
//        listOfTriggerActions_d.add(tr_d_1);
//        
//    
//        //////// trigger action 2
//        
//        List<ControlAction> listOfControlActions_d_2 = new ArrayList<ControlAction>();
//        
//        ///////////////////
//        String ca_name_d_2_1 = "DIR";
//        List<Parameter> listOfParameters_d_2_1 = new ArrayList<Parameter>();
//        Parameter param_d_2_1_1 = new Parameter("attributeIndex", "Integer", "3");       
//        listOfParameters_d_2_1.add(param_d_2_1_1);        
//        ControlAction ca_d_2_1 = new ControlAction(ca_name_d_2_1, listOfParameters_d_2_1);    
//        listOfControlActions_d_2.add(ca_d_2_1);
//  
// 
//        
//        MetricControlActions tr_d_2 = new MetricControlActions("c12", "c32", listOfControlActions_d_2);
//        listOfTriggerActions_d.add(tr_d_2);
//        /////////////////
//
//        
//        
//        MetricElasticityProcess mep1 = new MetricElasticityProcess(metricName_d, listOfMetricConditions, monitorAction_d, listOfTriggerActions_d);
//        
//        
//        
//        return mep1;
//        
//        
//    }
//    
//    public MetricElasticityProcess sampleEPThroughput(){
//    
//      
//         
//        List<MetricCondition> listOfMetricConditions = new ArrayList<MetricCondition>();
//        
//        MetricCondition m1 = new MetricCondition("throughput","c13", 0, 0.49);
//        MetricCondition m2 = new MetricCondition("throughput","c23", 0.5, 1.19);
//        MetricCondition m3 = new MetricCondition("throughput","c33", 1.2, Double.MAX_VALUE);
//       
//        listOfMetricConditions.add(m1);
//        listOfMetricConditions.add(m2);
//        listOfMetricConditions.add(m3);
//        
//        
//        /// MetricElasticityProcess - datacompleteness
//        
//        String metricName_d = "throughput";
//        MonitoringAction monitorAction_d = new MonitoringAction("TPM");
//        List<MetricControlActions> listOfTriggerActions_d = new ArrayList<MetricControlActions>();
//        
//        
//             
//        
//        //////////////// trigger action 1
//        
//        List<ControlAction> listOfControlActions_d_1 = new ArrayList<ControlAction>();
//        
//        String ca_name_d_1_1 = "STC";     
//        List<Parameter> listOfParameters_d_1_1 = new ArrayList<Parameter>();
//        Parameter param_d_1_1_1 = new Parameter("minThroughput", "Double", "0.5");
//        Parameter param_d_1_1_2 = new Parameter("maxThroughput", "Double", "1.19");
//        listOfParameters_d_1_1.add(param_d_1_1_1);
//        listOfParameters_d_1_1.add(param_d_1_1_2);
//        ControlAction ca_d_1_1 = new ControlAction(ca_name_d_1_1, listOfParameters_d_1_1);    
//        listOfControlActions_d_1.add(ca_d_1_1);
//       
//        
//        MetricControlActions tr_d_1 = new MetricControlActions("c13", "c23", listOfControlActions_d_1);
//        listOfTriggerActions_d.add(tr_d_1);
//        
//    
//        //////// trigger action 2
//        
//        List<ControlAction> listOfControlActions_d_2 = new ArrayList<ControlAction>();
//       
//        String ca_name_d_2_1 = "STC";
//        List<Parameter> listOfParameters_d_2_1 = new ArrayList<Parameter>();
//        Parameter param_d_2_1_1 = new Parameter("minThroughput", "Double", "1.2");   
//        Parameter param_d_2_1_2 = new Parameter("maxThroughput", "Double", "infinite");   
//        listOfParameters_d_2_1.add(param_d_2_1_1);   
//        listOfParameters_d_2_1.add(param_d_2_1_2);   
//        ControlAction ca_d_2_1 = new ControlAction(ca_name_d_2_1, listOfParameters_d_2_1);    
//        listOfControlActions_d_2.add(ca_d_2_1);
//  
// 
//        
//        MetricControlActions tr_d_2 = new MetricControlActions("c23", "c33", listOfControlActions_d_2);
//        listOfTriggerActions_d.add(tr_d_2);
//        /////////////////
//        
//        
//         //////// trigger action 3
//        
//        List<ControlAction> listOfControlActions_d_3 = new ArrayList<ControlAction>();
//       
//        String ca_name_d_3_1 = "STC";
//        List<Parameter> listOfParameters_d_3_1 = new ArrayList<Parameter>();
//        Parameter param_d_3_1_1 = new Parameter("minThroughput", "Double", "0.5");   
//        Parameter param_d_3_1_2 = new Parameter("maxThroughput", "Double", "1.19");   
//        listOfParameters_d_3_1.add(param_d_3_1_1);   
//        listOfParameters_d_3_1.add(param_d_3_1_2);   
//        ControlAction ca_d_3_1 = new ControlAction(ca_name_d_3_1, listOfParameters_d_3_1);    
//        listOfControlActions_d_3.add(ca_d_3_1);
//  
// 
//        
//        MetricControlActions tr_d_3 = new MetricControlActions("c33", "c23", listOfControlActions_d_3);
//        listOfTriggerActions_d.add(tr_d_3);
//        /////////////////
//
//        
//        
//        MetricElasticityProcess mep1 = new MetricElasticityProcess(metricName_d, listOfMetricConditions, monitorAction_d, listOfTriggerActions_d);
//        
//        
//        return mep1;
//        
//        
//    }
//    
//    
//    public MetricProcess sampleElasticityProcess(){
//        
//        MetricElasticityProcess dataCompleteness = sampleEPDataCompleteness();
//        MetricElasticityProcess dataAccuracy = sampleEPDataAccuracy();
//        MetricElasticityProcess throughput = sampleEPThroughput();
//        
//        
//        
//        List<MetricElasticityProcess> listOfMetricElasticityProcesses = new ArrayList<MetricElasticityProcess>();
//        listOfMetricElasticityProcesses.add(dataCompleteness);
//        listOfMetricElasticityProcesses.add(dataAccuracy);
//        listOfMetricElasticityProcesses.add(throughput);
//        
//        
//        MetricProcess mp1 = new MetricProcess(listOfMetricElasticityProcesses);
//        
//        return mp1;
//    }
//    
//    
//   
//    
//   
//    
//    
//    
//    
//    
//    
//
//
//}
