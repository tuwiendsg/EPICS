/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.tooling.edo.test;

import at.ac.tuwien.dsg.common.tooling.edo.metric.ControlAction;
import at.ac.tuwien.dsg.common.tooling.edo.metric.ControlProcess;
import at.ac.tuwien.dsg.common.tooling.edo.metric.ControlProcessMatrix;
import at.ac.tuwien.dsg.common.tooling.edo.metric.ElasticityMetricConf;
import at.ac.tuwien.dsg.common.tooling.edo.metric.GeneralInfo;
import at.ac.tuwien.dsg.common.tooling.edo.metric.MonitorConf;
import at.ac.tuwien.dsg.common.tooling.edo.metric.Parameter;
import at.ac.tuwien.dsg.common.tooling.edo.metric.PricingPlan;
import at.ac.tuwien.dsg.common.tooling.edo.metric.PricingScheme;
import at.ac.tuwien.dsg.common.tooling.edo.metric.Range;
import at.ac.tuwien.dsg.common.tooling.edo.metric.TriggerValues;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class NumericalMetricGenerator {
     public ElasticityMetricConf getNumericalMetricConfig(){
        
        
         System.out.println( "Start ..." );
        
        Range r1 = new Range("r1",0,70);
        Range r2 = new Range("r2",71,85);
        Range r3 = new Range("r3",86,100);
        List listOfRanges = new ArrayList();
        listOfRanges.add(r1);
        listOfRanges.add(r2);
        listOfRanges.add(r3);
       
        GeneralInfo info = new GeneralInfo("DataCompleteness", listOfRanges);
        
        PricingPlan plan1 = new PricingPlan("r1", 0.03);
        PricingPlan plan2 = new PricingPlan("r2", 0.05);
        PricingPlan plan3 = new PricingPlan("r3", 0.08);
        
        List listOfPricingPlans  = new ArrayList();
        listOfPricingPlans.add(plan1);
        listOfPricingPlans.add(plan2);
        listOfPricingPlans.add(plan3);
      
        PricingScheme pricingScheme = new PricingScheme(listOfPricingPlans);
        
       
        MonitorConf monitorConf = new MonitorConf("/RestWS/DataCompleteness",30);
        
        
        
        Parameter param1 = new Parameter("upperBound", 12.8);
        Parameter param2 = new Parameter("lowerBound", 7.9);
        
        Parameter param3 = new Parameter("upperBound", 11.5);
        Parameter param4 = new Parameter("lowerBound", 10.9);
       
        List listOfParams1 = new ArrayList();
        
        List listOfParams2 = new ArrayList();
        listOfParams2.add(param1);
        listOfParams2.add(param2);
        
        List listOfParams3 = new ArrayList();
        listOfParams3.add(param3);
        listOfParams3.add(param4);
        
        
        
        ControlAction ac1 = new ControlAction("/RestWS/LinearCurveFitting", listOfParams1);
        ControlAction ac2 = new ControlAction("/RestWS/LeastSquaresCurveFitting", listOfParams2);
       ControlAction ac3 = new ControlAction("/RestWS/LeastSquaresCurveFitting", listOfParams3); 
       
       
        TriggerValues triggerValue1 = new TriggerValues("r1", "r2");
        TriggerValues triggerValue2 = new TriggerValues("r2", "r3");
        //TriggerValues triggerValue3 = new TriggerValues("r2", "r3");
        TriggerValues triggerValue3 = new TriggerValues("r1", "r3");
        
        
       
        List listAc1 = new ArrayList();
        listAc1.add(ac1);
        
        List listAc2 = new ArrayList();
        listAc2.add(ac2);
        List listAc3 = new ArrayList();
        listAc3.add(ac3);
        
        
        
        
        ControlProcess controlProcess1 = new ControlProcess(triggerValue1, listAc1);
        ControlProcess controlProcess2 = new ControlProcess(triggerValue2, listAc2);
        ControlProcess controlProcess3 = new ControlProcess(triggerValue3, listAc3);
        
        List listCPs = new ArrayList();
        listCPs.add(controlProcess1);
        listCPs.add(controlProcess2);
        listCPs.add(controlProcess3);
        
        ControlProcessMatrix controlProcessMatrix = new ControlProcessMatrix(listCPs);
        
        
        
        
        
      
        ElasticityMetricConf elasticityMetricConf = new ElasticityMetricConf(info, pricingScheme, monitorConf, controlProcessMatrix);
        
        return elasticityMetricConf;
        
    }
    
}
