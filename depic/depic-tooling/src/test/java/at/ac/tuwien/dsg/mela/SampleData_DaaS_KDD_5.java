///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package at.ac.tuwien.dsg.mela;
//
//import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.MetricCondition;
//import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ControlAction;
//import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
//import at.ac.tuwien.dsg.depic.common.entity.process.MetricElasticityProcess;
//import at.ac.tuwien.dsg.depic.common.entity.process.MetricProcess;
//import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
//import at.ac.tuwien.dsg.depic.common.entity.qor.MetricControlActions;
//import at.ac.tuwien.dsg.depic.common.entity.qor.MetricRange;
//import at.ac.tuwien.dsg.depic.common.entity.qor.QElement;
//import at.ac.tuwien.dsg.depic.common.entity.qor.QoRMetric;
//import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
//import at.ac.tuwien.dsg.depic.common.entity.qor.Range;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// * @author Jun
// */
//public class SampleData_DaaS_KDD_5 {
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
//    private  List<QoRMetric> sampleListOfQoRMetrics(){
//        
//        List<QoRMetric> listOfQoRMetrics = new ArrayList<QoRMetric>();
//        
//        
//        // acrPosition metric
//        Range ap1 = new Range("ap1",0, 60);
//        Range ap2 = new Range("ap2",61,100);
//
//        List<Range> listOfRanges_ap = new ArrayList<Range>();
//        listOfRanges_ap.add(ap1);
//        listOfRanges_ap.add(ap2);
//        
//        String name_ap = "acrPosition";
//        String unit_ap = "%";
//        
//        QoRMetric metric_ap = new QoRMetric(name_ap, unit_ap, listOfRanges_ap);
//        listOfQoRMetrics.add(metric_ap);
//        
//        // acrImpression metric
//        Range ai1 = new Range("ai1",0, 75);
//        Range ai2 = new Range("ai2",76,100);
//
//        List<Range> listOfRanges_ai = new ArrayList<Range>();
//        listOfRanges_ai.add(ai1);
//        listOfRanges_ai.add(ai2);
//
//        String name_ai = "acrImpression";
//        String unit_ai = "%";
//        
//        QoRMetric metric_ai = new QoRMetric(name_ai, unit_ai, listOfRanges_ai);
//        listOfQoRMetrics.add(metric_ai);
//        
//    
//        
//        // throughput metric
//        
//        Range th1 = new Range("th1", 0,0.2);
//        Range th2 = new Range("th2", 0.3,0.5);
//        Range th3 = new Range("th3",0.6,Double.MAX_VALUE);
//     
//        List<Range> listOfRanges_th = new ArrayList<Range>();
//        listOfRanges_th.add(th1);
//        listOfRanges_th.add(th2);
//        listOfRanges_th.add(th3);
//
//        String name_th = "throughput";
//        String unit_th = "das/h";
//        
//        QoRMetric metric_th = new QoRMetric(name_th, unit_th, listOfRanges_th);
//        listOfQoRMetrics.add(metric_th);
//        
//        
//        // arcClick metric
//        
//        Range click1 = new Range("click1", 0,90);
//        Range click2 = new Range("click2", 91,95);
//        Range click3 = new Range("click3", 96,100);
//     
//        List<Range> listOfRanges_click = new ArrayList<Range>();
//        listOfRanges_click.add(click1);
//        listOfRanges_click.add(click2);
//        listOfRanges_click.add(click3);
//
//        String name_click = "arcClick";
//        String unit_click = "%";
//        
//        QoRMetric metric_click = new QoRMetric(name_click, unit_click, listOfRanges_click);
//        listOfQoRMetrics.add(metric_click);
//        
//        
//        
//        // arcDepth metric
//        
//        Range depth1 = new Range("depth1", 0,90);
//        Range depth2 = new Range("depth2", 91,95);
//        Range depth3 = new Range("depth3", 96,100);
//     
//        List<Range> listOfRanges_depth = new ArrayList<Range>();
//        listOfRanges_depth.add(depth1);
//        listOfRanges_depth.add(depth2);
//        listOfRanges_depth.add(depth3);
//
//        String name_depth = "arcDepth";
//        String unit_depth = "%";
//        
//        QoRMetric metric_depth = new QoRMetric(name_depth, unit_depth, listOfRanges_depth);
//        listOfQoRMetrics.add(metric_depth);
//        
//        
//        return listOfQoRMetrics;
//    }
//    
//    
//    private  List<QElement> sampleListOfQElements(){
//        
//        List<QElement> listOfQElements = new ArrayList<QElement>();
//        
//        
//        
//        MetricRange metricRange11 = new MetricRange("acrPosition", "ap1");
//        MetricRange metricRange12 = new MetricRange("acrImpression", "ai1");
//        MetricRange metricRange13 = new MetricRange("throughput", "th1");
//        MetricRange metricRange14 = new MetricRange("arcClick", "click1");
//        MetricRange metricRange15 = new MetricRange("arcDepth", "depth1");
//
//        List<MetricRange> listOfMetricRanges1 = new ArrayList<MetricRange>();
//        listOfMetricRanges1.add(metricRange11);
//        listOfMetricRanges1.add(metricRange12);
//        listOfMetricRanges1.add(metricRange13);
//        listOfMetricRanges1.add(metricRange14);
//        listOfMetricRanges1.add(metricRange15);
//        
//   
//        double price1=4.9;
//        String unit1="eur";
//        
//        QElement qElement1 = new QElement("qElement1", listOfMetricRanges1, price1, unit1);
//        
//        listOfQElements.add(qElement1);
//        
//        
//        MetricRange metricRange21 = new MetricRange("acrPosition", "ap2");
//        MetricRange metricRange22 = new MetricRange("acrImpression", "ai2");
//        MetricRange metricRange23 = new MetricRange("throughput", "th1");
//        MetricRange metricRange24 = new MetricRange("arcClick", "click1");
//        MetricRange metricRange25 = new MetricRange("arcDepth", "depth1");
//        
//
//        List<MetricRange> listOfMetricRanges2 = new ArrayList<MetricRange>();
//        listOfMetricRanges2.add(metricRange21);
//        listOfMetricRanges2.add(metricRange22);
//        listOfMetricRanges2.add(metricRange23);
//        listOfMetricRanges2.add(metricRange24);
//        listOfMetricRanges2.add(metricRange25);
//    
//        double price2=7.9;
//        String unit2="eur";
//        
//        QElement qElement2 = new QElement("qElement2", listOfMetricRanges2, price2, unit2);
//        listOfQElements.add(qElement2);
//        
//        
//        MetricRange metricRange31 = new MetricRange("acrPosition", "ap2");
//        MetricRange metricRange32 = new MetricRange("acrImpression", "ai2");
//        MetricRange metricRange33 = new MetricRange("throughput", "th2");
//        MetricRange metricRange34 = new MetricRange("arcClick", "click2");
//        MetricRange metricRange35 = new MetricRange("arcDepth", "depth2");
//
//        List<MetricRange> listOfMetricRanges3 = new ArrayList<MetricRange>();
//        listOfMetricRanges3.add(metricRange31);
//        listOfMetricRanges3.add(metricRange32);
//        listOfMetricRanges3.add(metricRange33);
//        listOfMetricRanges3.add(metricRange34);
//        listOfMetricRanges3.add(metricRange35);
//
//        double price3=8.9;
//        String unit3="eur";
//        
//        QElement qElement3 = new QElement("qElement3", listOfMetricRanges3, price3, unit3);
//        listOfQElements.add(qElement3);
//        
//        
//        MetricRange metricRange41 = new MetricRange("acrPosition", "ap2");
//        MetricRange metricRange42 = new MetricRange("acrImpression", "ai1");
//        MetricRange metricRange43 = new MetricRange("throughput", "th1");
//        MetricRange metricRange44 = new MetricRange("arcClick", "click2");
//        MetricRange metricRange45 = new MetricRange("arcDepth", "depth2");
//
//        List<MetricRange> listOfMetricRanges4 = new ArrayList<MetricRange>();
//        listOfMetricRanges4.add(metricRange41);
//        listOfMetricRanges4.add(metricRange42);
//        listOfMetricRanges4.add(metricRange43);
//        listOfMetricRanges4.add(metricRange44);
//        listOfMetricRanges4.add(metricRange45);
//   
//        double price4=5.9;
//        String unit4="eur";
//        
//        QElement qElement4 = new QElement("qElement4", listOfMetricRanges4, price4, unit4);
//        listOfQElements.add(qElement4);
//        
//        
//        MetricRange metricRange51 = new MetricRange("acrPosition", "ap2");
//        MetricRange metricRange52 = new MetricRange("acrImpression", "ai2");
//        MetricRange metricRange53 = new MetricRange("throughput", "th2");
//        MetricRange metricRange54 = new MetricRange("arcClick", "click3");
//        MetricRange metricRange55 = new MetricRange("arcDepth", "depth3");
//        
//        List<MetricRange> listOfMetricRanges5 = new ArrayList<MetricRange>();
//        listOfMetricRanges5.add(metricRange51);
//        listOfMetricRanges5.add(metricRange52);
//        listOfMetricRanges5.add(metricRange53);
//        listOfMetricRanges5.add(metricRange54);
//        listOfMetricRanges5.add(metricRange55);
// 
//        
//        double price5=9.9;
//        String unit5="eur";
//        
//        QElement qElement5 = new QElement("qElement5", listOfMetricRanges5, price5, unit5);
//        listOfQElements.add(qElement5);
//        
//        
//        MetricRange metricRange61 = new MetricRange("acrPosition", "ap2");
//        MetricRange metricRange62 = new MetricRange("acrImpression", "ai2");
//        MetricRange metricRange63 = new MetricRange("throughput", "th3");
//        MetricRange metricRange64 = new MetricRange("arcClick", "click3");
//        MetricRange metricRange65 = new MetricRange("arcDepth", "depth3");
//        
//        List<MetricRange> listOfMetricRanges6 = new ArrayList<MetricRange>();
//        listOfMetricRanges6.add(metricRange61);
//        listOfMetricRanges6.add(metricRange62);
//        listOfMetricRanges6.add(metricRange63);
//        listOfMetricRanges6.add(metricRange64);
//        listOfMetricRanges6.add(metricRange65);
// 
//        
//        double price6=10.9;
//        String unit6="eur";
//        
//        QElement qElement6 = new QElement("qElement6", listOfMetricRanges6, price6, unit6);
//        listOfQElements.add(qElement6);
//        
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
//    public MetricProcess sampleElasticityProcess(){
//        
//        MetricElasticityProcess arcPosition = sampleEPArcPosisiton();
//        MetricElasticityProcess arcImpression = sampleEPArcImpression();
//        MetricElasticityProcess throughput = sampleEPThroughput();
//        MetricElasticityProcess arcClick = sampleArcClick();
//        MetricElasticityProcess arcDepth = sampleArcDepth();
//        
//        
//        
//        List<MetricElasticityProcess> listOfMetricElasticityProcesses = new ArrayList<MetricElasticityProcess>();
//        listOfMetricElasticityProcesses.add(arcPosition);
//        listOfMetricElasticityProcesses.add(arcImpression);
//        listOfMetricElasticityProcesses.add(throughput);
//        listOfMetricElasticityProcesses.add(arcClick);
//        listOfMetricElasticityProcesses.add(arcDepth);
//
//        
//        
//        MetricProcess mp1 = new MetricProcess(listOfMetricElasticityProcesses);
//        
//        return mp1;
//    }
//    
//    private MetricElasticityProcess sampleArcDepth(){
//        
//      List<MetricCondition> listOfMetricConditions = new ArrayList<MetricCondition>();
//        
//        MetricCondition m1 = new MetricCondition("arcDepth","c15", 0,90);
//        MetricCondition m2 = new MetricCondition("arcDepth","c25", 91,95);
//        MetricCondition m3 = new MetricCondition("arcDepth","c35", 96,100);
//       
//        listOfMetricConditions.add(m1);
//        listOfMetricConditions.add(m2);
//        listOfMetricConditions.add(m3);
//        
//        
//         String metricName_d = "arcDepth";
//        MonitoringAction monitorAction_d = new MonitoringAction("ARCDEPTH_M");
//        List<MetricControlActions> listOfTriggerActions_d = new ArrayList<MetricControlActions>();
//        
//        
//             
//        
//        //////////////// trigger action 1
//        
//        List<ControlAction> listOfControlActions_d_1 = new ArrayList<ControlAction>();
//        
//        String ca_name_d_1_1 = "ARCDEPTH_C";     
//        List<Parameter> listOfParameters_d_1_1 = new ArrayList<Parameter>();
//        Parameter param_d_1_1_1 = new Parameter("attributeIndex", "Integer", "0");
//        listOfParameters_d_1_1.add(param_d_1_1_1);
//        ControlAction ca_d_1_1 = new ControlAction(ca_name_d_1_1, listOfParameters_d_1_1);    
//        listOfControlActions_d_1.add(ca_d_1_1);
//        
//   
//        
//        MetricControlActions tr_d_1 = new MetricControlActions("c25", "c35", listOfControlActions_d_1);
//        listOfTriggerActions_d.add(tr_d_1);
//        
//    
//        //////// trigger action 2
//        
//        List<ControlAction> listOfControlActions_d_2 = new ArrayList<ControlAction>();
//        
//        ///////////////////
//        String ca_name_d_2_1 = "ARCDEPTH_C";
//        List<Parameter> listOfParameters_d_2_1 = new ArrayList<Parameter>();
//        Parameter param_d_2_1_1 = new Parameter("attributeIndex", "Integer", "0");       
//        listOfParameters_d_2_1.add(param_d_2_1_1);        
//        ControlAction ca_d_2_1 = new ControlAction(ca_name_d_2_1, listOfParameters_d_2_1);    
//        listOfControlActions_d_2.add(ca_d_2_1);
//  
// 
//        
//        MetricControlActions tr_d_2 = new MetricControlActions("c15", "c35", listOfControlActions_d_2);
//        listOfTriggerActions_d.add(tr_d_2);
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
//        
//    }
//    
//    private MetricElasticityProcess sampleArcClick(){
//        
//        List<MetricCondition> listOfMetricConditions = new ArrayList<MetricCondition>();
//        
//        MetricCondition m1 = new MetricCondition("arcClick","c14", 0,90);
//        MetricCondition m2 = new MetricCondition("arcClick","c24", 91,95);
//        MetricCondition m3 = new MetricCondition("arcClick","c34", 96,100);
//       
//        listOfMetricConditions.add(m1);
//        listOfMetricConditions.add(m2);
//        listOfMetricConditions.add(m3);
//        
//        
//        
//         
//        String metricName_d = "arcClick";
//        MonitoringAction monitorAction_d = new MonitoringAction("ARCCLICK_M");
//        List<MetricControlActions> listOfTriggerActions_d = new ArrayList<MetricControlActions>();
//        
//        
//             
//        
//        //////////////// trigger action 1
//        
//        List<ControlAction> listOfControlActions_d_1 = new ArrayList<ControlAction>();
//        
//        String ca_name_d_1_1 = "ARCCLICK_C";     
//        List<Parameter> listOfParameters_d_1_1 = new ArrayList<Parameter>();
//        Parameter param_d_1_1_1 = new Parameter("attributeIndex", "Integer", "0");
//        listOfParameters_d_1_1.add(param_d_1_1_1);
//        ControlAction ca_d_1_1 = new ControlAction(ca_name_d_1_1, listOfParameters_d_1_1);    
//        listOfControlActions_d_1.add(ca_d_1_1);
//        
//   
//        
//        MetricControlActions tr_d_1 = new MetricControlActions("c24", "c34", listOfControlActions_d_1);
//        listOfTriggerActions_d.add(tr_d_1);
//        
//    
//        //////// trigger action 2
//        
//        List<ControlAction> listOfControlActions_d_2 = new ArrayList<ControlAction>();
//        
//        ///////////////////
//        String ca_name_d_2_1 = "ARCCLICK_C";
//        List<Parameter> listOfParameters_d_2_1 = new ArrayList<Parameter>();
//        Parameter param_d_2_1_1 = new Parameter("attributeIndex", "Integer", "0");       
//        listOfParameters_d_2_1.add(param_d_2_1_1);        
//        ControlAction ca_d_2_1 = new ControlAction(ca_name_d_2_1, listOfParameters_d_2_1);    
//        listOfControlActions_d_2.add(ca_d_2_1);
//  
// 
//        
//        MetricControlActions tr_d_2 = new MetricControlActions("c14", "c34", listOfControlActions_d_2);
//        listOfTriggerActions_d.add(tr_d_2);
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
//    
//    private MetricElasticityProcess sampleEPArcPosisiton(){
//         
//        List<MetricCondition> listOfMetricConditions = new ArrayList<MetricCondition>();
//        
//        MetricCondition m1 = new MetricCondition("acrPosition","c11", 0, 60);
//        MetricCondition m2 = new MetricCondition("acrPosition","c21", 61, 79);
//        MetricCondition m3 = new MetricCondition("acrPosition","c31", 80, 100);
//       
//        listOfMetricConditions.add(m1);
//        listOfMetricConditions.add(m2);
//        listOfMetricConditions.add(m3);
//        
//        
//        /// MetricElasticityProcess - acrPosition
//        
//        String metricName_d = "acrPosition";
//        MonitoringAction monitorAction_d = new MonitoringAction("APM");
//        List<MetricControlActions> listOfTriggerActions_d = new ArrayList<MetricControlActions>();
//        
//        
//             
//        
//        //////////////// trigger action 1
//        
//        List<ControlAction> listOfControlActions_d_1 = new ArrayList<ControlAction>();
//        
//        String ca_name_d_1_1 = "APC";     
//        List<Parameter> listOfParameters_d_1_1 = new ArrayList<Parameter>();
//        Parameter param_d_1_1_1 = new Parameter("attributeIndex", "Integer", "6");
//        listOfParameters_d_1_1.add(param_d_1_1_1);
//        ControlAction ca_d_1_1 = new ControlAction(ca_name_d_1_1, listOfParameters_d_1_1);    
//        listOfControlActions_d_1.add(ca_d_1_1);
//        
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
//        String ca_name_d_2_1 = "APC";
//        List<Parameter> listOfParameters_d_2_1 = new ArrayList<Parameter>();
//        Parameter param_d_2_1_1 = new Parameter("attributeIndex", "Integer", "6");       
//        listOfParameters_d_2_1.add(param_d_2_1_1);        
//        ControlAction ca_d_2_1 = new ControlAction(ca_name_d_2_1, listOfParameters_d_2_1);    
//        listOfControlActions_d_2.add(ca_d_2_1);
//  
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
//    }
//    
//    private MetricElasticityProcess sampleEPArcImpression(){
//         List<MetricCondition> listOfMetricConditions = new ArrayList<MetricCondition>();
//        
//        MetricCondition m1 = new MetricCondition("acrImpression","c12", 0, 75);
//        MetricCondition m2 = new MetricCondition("acrImpression","c22", 76, 90);
//        MetricCondition m3 = new MetricCondition("acrImpression","c32", 91, 100);
//       
//        listOfMetricConditions.add(m1);
//        listOfMetricConditions.add(m2);
//        listOfMetricConditions.add(m3);
//        
//        
//        /// MetricElasticityProcess - acrImpression
//        
//        String metricName_d = "acrImpression";
//        MonitoringAction monitorAction_d = new MonitoringAction("AIM");
//        List<MetricControlActions> listOfTriggerActions_d = new ArrayList<MetricControlActions>();
//        
//        
//             
//        
//        //////////////// trigger action 1
//        
//        List<ControlAction> listOfControlActions_d_1 = new ArrayList<ControlAction>();
//        
//        String ca_name_d_1_1 = "AIC";     
//        List<Parameter> listOfParameters_d_1_1 = new ArrayList<Parameter>();
//        Parameter param_d_1_1_1 = new Parameter("attributeIndex", "Integer", "1");
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
//        String ca_name_d_2_1 = "AIC";
//        List<Parameter> listOfParameters_d_2_1 = new ArrayList<Parameter>();
//        Parameter param_d_2_1_1 = new Parameter("attributeIndex", "Integer", "1");       
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
//    }
//    
//    
//    private MetricElasticityProcess sampleEPThroughput(){
//        List<MetricCondition> listOfMetricConditions = new ArrayList<MetricCondition>();
//        
//        MetricCondition m1 = new MetricCondition("throughput","c13", 0, 0.2);
//        MetricCondition m2 = new MetricCondition("throughput","c23", 0.3, 0.5);
//        MetricCondition m3 = new MetricCondition("throughput","c33", 0.6, Double.MAX_VALUE);
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
//        Parameter param_d_1_1_1 = new Parameter("minThroughput", "Double", "0.3");
//        Parameter param_d_1_1_2 = new Parameter("maxThroughput", "Double", "0.5");
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
//        Parameter param_d_2_1_1 = new Parameter("minThroughput", "Double", "0.6");   
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
//        Parameter param_d_3_1_1 = new Parameter("minThroughput", "Double", "0.3");   
//        Parameter param_d_3_1_2 = new Parameter("maxThroughput", "Double", "0.5");   
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
//    }
//}
