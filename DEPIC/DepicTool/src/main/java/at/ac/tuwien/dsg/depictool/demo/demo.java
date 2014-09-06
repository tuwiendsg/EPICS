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
import at.ac.tuwien.dsg.depictool.util.YamlUtils;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        YamlUtils yamlUtils = new YamlUtils();
        yamlUtils.convertElasticDataObject2Yaml(sampleEDO());
        
        yamlUtils.convertElasticProcessConfiguration2Yaml(sampleDEPConf());
        
        //System.out.println("EDO: " +xmlString);
        
    
    }
    
    
    public static ElasticDataObject sampleEDO(){
        
        
        List<DataObjectFunction> listOfDataObjectFunctions = sampleListDataObjectFunctions();
        List<DataElasticityMetric> listOfDataElasticityMetrics = sampleListOfDataElasticityMetrics();
        List<ElasticState> listOfElasticStates = sampleListOfElasticStates();
        
        ElasticDataObject edo = new ElasticDataObject(listOfDataObjectFunctions, listOfDataElasticityMetrics, listOfElasticStates);
         
           return edo;
                
    }
    
    
    public static List<DataObjectFunction> sampleListDataObjectFunctions(){
        
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
    
    
    public static List<DataElasticityMetric> sampleListOfDataElasticityMetrics(){
        
        
        String metricName1 = "data completeness";
        String unit1="%";
        Range r1 = new Range("r1", 0, 75);
        Range r2 = new Range("r2", 76, 90);
        Range r3 = new Range("r3", 91, 100);
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
    
    public static List<ElasticState> sampleListOfElasticStates(){
        
        List<ElasticState> listOfElasticStates = new ArrayList<>();
        
        
        String eStateID1="eState1";
        List<String> listOfMetricRanges1 = new ArrayList<>();
        listOfMetricRanges1.add("r1");
        listOfMetricRanges1.add("t1");
        double price1 =0.05;

        ElasticState elasticState1 = new ElasticState(eStateID1, listOfMetricRanges1, price1);
        listOfElasticStates.add(elasticState1);
        
        String eStateID2="eState2";
        List<String> listOfMetricRanges2 = new ArrayList<>();
        listOfMetricRanges2.add("r3");
        listOfMetricRanges2.add("t2");
        double price2 =0.08;

        ElasticState elasticState2 = new ElasticState(eStateID2, listOfMetricRanges2, price2);
        listOfElasticStates.add(elasticState2);
        
        
        
        
        
        
        return  listOfElasticStates;
    }
    
    public static DataElasticityProcessConfiguration sampleDEPConf(){
        
        String controlActionID1="ca1";
        TriggerValues triggerValues1 = new TriggerValues("r1", "r2");
        List<Parameter> listOfParameters = new ArrayList<>();
        
        Parameter param1 = new Parameter("expectedDataCompleteness", "double");
        listOfParameters.add(param1);  
        
        ControlAction controlAction1 = new ControlAction(controlActionID1, triggerValues1, listOfParameters); 
               
        
        String controlActionID2="ca2";
        TriggerValues triggerValues2 = new TriggerValues("r1", "r3");
        List<Parameter> listOfParameters2 = new ArrayList<>();
        
        Parameter param2 = new Parameter("expectedDataCompleteness", "double");
        listOfParameters2.add(param2);  
        
        ControlAction controlAction2 = new ControlAction(controlActionID2, triggerValues2, listOfParameters2); 
        
        List<ControlAction> listOfControlActions = new ArrayList<>();
        listOfControlActions.add(controlAction1);
        listOfControlActions.add(controlAction2);
                
        MonitorAction monitorAction = new MonitorAction("dataCompletenessMeasurement");
        
        MetricElasticityProcess metricControlAction1 = new MetricElasticityProcess("data completeness",monitorAction, listOfControlActions);
        List<MetricElasticityProcess> listOfMetricControlActions = new ArrayList<>();
        listOfMetricControlActions.add(metricControlAction1);
        
        DataElasticityProcessConfiguration dataElasticityProcessConfiguration = new DataElasticityProcessConfiguration(listOfMetricControlActions);
        
        
        
        
        
        
        return dataElasticityProcessConfiguration;
    }
    
}
