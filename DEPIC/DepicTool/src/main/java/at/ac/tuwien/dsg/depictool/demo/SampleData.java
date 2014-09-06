/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.demo;

import at.ac.tuwien.dsg.depictool.entity.ControlAction;
import at.ac.tuwien.dsg.depictool.entity.DataElasticityMetric;
import at.ac.tuwien.dsg.depictool.entity.DataElasticityProcessConfiguration;
import at.ac.tuwien.dsg.depictool.entity.DataObjectFunction;
import at.ac.tuwien.dsg.depictool.entity.ElasticDataObject;
import at.ac.tuwien.dsg.depictool.entity.ElasticState;
import at.ac.tuwien.dsg.depictool.entity.MetricElasticityProcess;
import at.ac.tuwien.dsg.depictool.entity.MonitorAction;
import at.ac.tuwien.dsg.depictool.entity.Parameter;
import at.ac.tuwien.dsg.depictool.entity.Range;
import at.ac.tuwien.dsg.depictool.entity.TriggerValues;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class SampleData {
    public ElasticDataObject sampleEDO(){
        
        
        List<DataObjectFunction> listOfDataObjectFunctions = sampleListDataObjectFunctions();
        List<DataElasticityMetric> listOfDataElasticityMetrics = sampleListOfDataElasticityMetrics();
        List<ElasticState> listOfElasticStates = sampleListOfElasticStates();
        
        ElasticDataObject edo = new ElasticDataObject(listOfDataObjectFunctions, listOfDataElasticityMetrics, listOfElasticStates);
         
           return edo;
                
    }
    
    
    private List<DataObjectFunction> sampleListDataObjectFunctions(){
        
        String dataFunctionName = "pc-100";
        String query = "Select * from PowerConsumption Where Time = Today";
        int numberOfDataItem = 100;
        
        
        DataObjectFunction dataObjectFunction1 = new DataObjectFunction(dataFunctionName,query,numberOfDataItem);
        DataObjectFunction dataObjectFunction2 = new DataObjectFunction("pc-200",query,200);
        
        List<DataObjectFunction> listOfDataObjectFunctions = new ArrayList<>();
        listOfDataObjectFunctions.add(dataObjectFunction1);
        listOfDataObjectFunctions.add(dataObjectFunction2);
        
        return  listOfDataObjectFunctions;    
    }
    
    
    private List<DataElasticityMetric> sampleListOfDataElasticityMetrics(){
        
        
        String metricName1 = "data completeness";
        String unit1="%";
        Range r1 = new Range("d1", 0, 75);
        Range r2 = new Range("d2", 76, 90);
        Range r3 = new Range("d3", 91, 100);
        List<Range> listOfRanges1 = new ArrayList<>();
        listOfRanges1.add(r1);
        listOfRanges1.add(r2);
        listOfRanges1.add(r3);
        
        DataElasticityMetric metric1= new DataElasticityMetric(metricName1,unit1, listOfRanges1);
        
        String metricName2 = "throughput";
        String unit2="Mbps";
        
        Range t1 = new Range("t1", 0, 0.1);
        Range t2 = new Range("t2", 0.11, 0.29);
        Range t3 = new Range("t3", 0.30, 1);
        

        List<Range> listOfRanges2 = new ArrayList<>();
        listOfRanges2.add(t1);
        listOfRanges2.add(t2);
        listOfRanges2.add(t3);
        
        DataElasticityMetric metric2 = new DataElasticityMetric(metricName2,unit2, listOfRanges2);
        
        
       
        List<DataElasticityMetric> listOfDataElasticityMetrics = new ArrayList<>();
        listOfDataElasticityMetrics.add(metric1);
        listOfDataElasticityMetrics.add(metric2);
        
                
        return  listOfDataElasticityMetrics;
        
    }
    
    private List<ElasticState> sampleListOfElasticStates(){
        
        List<ElasticState> listOfElasticStates = new ArrayList<>();
        
        
        String eStateID1="eState1";
        List<String> listOfMetricRanges1 = new ArrayList<>();
        listOfMetricRanges1.add("d1");
        listOfMetricRanges1.add("t1");
        double price1 =0.025;
        ElasticState elasticState1 = new ElasticState(eStateID1, listOfMetricRanges1, price1);
        listOfElasticStates.add(elasticState1);
        
        
        
        String eStateID2="eState2";
        List<String> listOfMetricRanges2 = new ArrayList<>();
        listOfMetricRanges2.add("d1");
        listOfMetricRanges2.add("t2");
        double price2 =0.035;
        ElasticState elasticState2 = new ElasticState(eStateID2, listOfMetricRanges2, price2);
        listOfElasticStates.add(elasticState2);
        
        String eStateID3="eState3";
        List<String> listOfMetricRanges3 = new ArrayList<>();
        listOfMetricRanges3.add("d1");
        listOfMetricRanges3.add("t3");
        double price3 =0.045;
        ElasticState elasticState3 = new ElasticState(eStateID3, listOfMetricRanges3, price3);
        listOfElasticStates.add(elasticState3);
        
        
        String eStateID4="eState4";
        List<String> listOfMetricRanges4 = new ArrayList<>();
        listOfMetricRanges4.add("d2");
        listOfMetricRanges4.add("t1");
        double price4 =0.035;
        ElasticState elasticState4 = new ElasticState(eStateID4, listOfMetricRanges4, price4);
        listOfElasticStates.add(elasticState4);
        
        
        String eStateID5="eState5";
        List<String> listOfMetricRanges5 = new ArrayList<>();
        listOfMetricRanges5.add("d2");
        listOfMetricRanges5.add("t2");
        double price5 =0.045;
        ElasticState elasticState5 = new ElasticState(eStateID5, listOfMetricRanges5, price5);
        listOfElasticStates.add(elasticState5);
        
        String eStateID6="eState6";
        List<String> listOfMetricRanges6 = new ArrayList<>();
        listOfMetricRanges6.add("d2");
        listOfMetricRanges6.add("t3");
        double price6 =0.055;
        ElasticState elasticState6 = new ElasticState(eStateID6, listOfMetricRanges6, price6);
        listOfElasticStates.add(elasticState6);
        
        String eStateID7="eState7";
        List<String> listOfMetricRanges7 = new ArrayList<>();
        listOfMetricRanges7.add("d3");
        listOfMetricRanges7.add("t1");
        double price7 =0.045;
        
        ElasticState elasticState7 = new ElasticState(eStateID7, listOfMetricRanges7, price7);
        listOfElasticStates.add(elasticState7);
        
        String eStateID8="eState8";
        List<String> listOfMetricRanges8 = new ArrayList<>();
        listOfMetricRanges8.add("d3");
        listOfMetricRanges8.add("t2");
        double price8 =0.055;
        ElasticState elasticState8 = new ElasticState(eStateID8, listOfMetricRanges8, price8);
        listOfElasticStates.add(elasticState8);
        
        String eStateID9="eState9";
        List<String> listOfMetricRanges9 = new ArrayList<>();
        listOfMetricRanges9.add("d3");
        listOfMetricRanges9.add("t3");
        double price9 =0.065;
        ElasticState elasticState9 = new ElasticState(eStateID9, listOfMetricRanges9, price9);
        listOfElasticStates.add(elasticState9);
        
        
        
        
        
        
        return  listOfElasticStates;
    }
    
    public DataElasticityProcessConfiguration sampleDEPConf(){
        
        
        List<MetricElasticityProcess> listOfMetricElasticityProcesses = new ArrayList<>();
        
        
        /////////////// data completeness
        String controlActionID1="LSR";
        TriggerValues triggerValues1 = new TriggerValues("r1", "r2");
        List<Parameter> listOfParameters = new ArrayList<>();
        
        Parameter param1 = new Parameter("expectedDataCompleteness", "double","100");
        listOfParameters.add(param1);  
        
        ControlAction controlAction1 = new ControlAction(controlActionID1, triggerValues1, listOfParameters); 
               
        
        String controlActionID2="LSR";
        TriggerValues triggerValues2 = new TriggerValues("r1", "r3");
        List<Parameter> listOfParameters2 = new ArrayList<>();
        
        Parameter param2 = new Parameter("expectedDataCompleteness", "double","100");
        listOfParameters2.add(param2);  
        
        ControlAction controlAction2 = new ControlAction(controlActionID2, triggerValues2, listOfParameters2); 
        
        List<ControlAction> listOfControlActions = new ArrayList<>();
        listOfControlActions.add(controlAction1);
        listOfControlActions.add(controlAction2);
                
        MonitorAction monitorAction = new MonitorAction("dataCompletenessMeasurement");
        
        MetricElasticityProcess metricControlAction1 = new MetricElasticityProcess("data completeness",monitorAction, listOfControlActions);
        
        listOfMetricElasticityProcesses.add(metricControlAction1);
        
        /////////////// throughput
        
        String controlActionID21="AddCtrl";
        TriggerValues triggerValues21 = new TriggerValues("t1", "t2");
        List<Parameter> listOfParameters21 = new ArrayList<>();
        
        Parameter param21 = new Parameter("numberOfVMs", "integer","1");
        listOfParameters21.add(param21);  
        
        ControlAction controlAction21 = new ControlAction(controlActionID21, triggerValues21, listOfParameters21); 
               
        
        String controlActionID22="AddCtrl";
        TriggerValues triggerValues22 = new TriggerValues("t2", "t3");
        List<Parameter> listOfParameters22 = new ArrayList<>();
        
        Parameter param22 = new Parameter("numberOfVMs", "integer","1");
        listOfParameters22.add(param22);  
        
        ControlAction controlAction22 = new ControlAction(controlActionID22, triggerValues22, listOfParameters22); 
        
         String controlActionID23="RmCtrl";
        TriggerValues triggerValues23 = new TriggerValues("t3", "t2");
        List<Parameter> listOfParameters23 = new ArrayList<>();
        
        Parameter param23 = new Parameter("numberOfVMs", "integer","1");
        listOfParameters23.add(param23);  
        
        ControlAction controlAction23 = new ControlAction(controlActionID23, triggerValues23, listOfParameters23); 
        
   
        List<ControlAction> listOfControlActions2 = new ArrayList<>();
        listOfControlActions2.add(controlAction21);
        listOfControlActions2.add(controlAction22);
        listOfControlActions2.add(controlAction23);
                
        MonitorAction monitorAction2 = new MonitorAction("throughputMeasurement");
        
        MetricElasticityProcess metricControlAction2 = new MetricElasticityProcess("throughput",monitorAction2, listOfControlActions2);
       
        
        
        listOfMetricElasticityProcesses.add(metricControlAction2);
        
        
        
        
        DataElasticityProcessConfiguration dataElasticityProcessConfiguration = new DataElasticityProcessConfiguration(listOfMetricElasticityProcesses);
        
        
        
        
        
        
        return dataElasticityProcessConfiguration;
    }
    
    
}
