/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller;

import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.Action;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.AdjustmentProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.DirectedAcyclicalGraph;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ParallelGateway;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author Jun
 */
public class TestDEPExecutionEngine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        AdjustmentProcess adjustmentProcess = sampleData3();
        DEPExecutionEngine dEPExecutionEngine = new DEPExecutionEngine(adjustmentProcess);
        dEPExecutionEngine.executeAdjustmentProcess();
        
    }
    
    
    private static AdjustmentProcess sampleData1(){
        
        
        DirectedAcyclicalGraph dag = new DirectedAcyclicalGraph();
        
        Action a1 = new Action("a1", "a1");      
        Action a2 = new Action("a2", "a2");
        Action a3 = new Action("a3", "a3");
        Action a4 = new Action("a4", "a4");
        
        a1.setIncomming(null);
        a1.setOutgoing("pg1");
        
        a2.setIncomming("pg1");
        a2.setOutgoing("pg2");
        
        a3.setIncomming("pg1");
        a3.setOutgoing("pg2");
        
        a4.setIncomming("pg2");
        a4.setOutgoing(null);
        
        
        
        List<String> pg1Incomming =new ArrayList<String>();
        List<String> pg1Outgoing =new ArrayList<String>();
    
        pg1Incomming.add("a1");
        pg1Outgoing.add("a2");
        pg1Outgoing.add("a3");
        
 
        ParallelGateway pg1 = new ParallelGateway("pg1", pg1Incomming, pg1Outgoing);
        
        
        List<String> pg2Incomming =new ArrayList<String>();
        List<String> pg2Outgoing =new ArrayList<String>();
        
        pg2Incomming.add("a2");
        pg2Incomming.add("a3");
        
        pg2Outgoing.add("a4");
        
        
        
        ParallelGateway pg2 = new ParallelGateway("pg2", pg2Incomming, pg2Outgoing);
        
        
       
        List<Action> listOfActions = new ArrayList<Action>();
        listOfActions.add(a1);
        listOfActions.add(a2);
        listOfActions.add(a3);
        listOfActions.add(a4);
        
        
        List<ParallelGateway> listOfParallelGateways = new ArrayList<ParallelGateway>();
        listOfParallelGateways.add(pg1);
        listOfParallelGateways.add(pg2);
        
        dag.setListOfActions(listOfActions);
        dag.setListOfParallelGateways(listOfParallelGateways);
      
        
        AdjustmentProcess adjustmentProcess = new AdjustmentProcess(null, null, dag);
        
        return adjustmentProcess;
    }
    
    
    private static AdjustmentProcess sampleData2(){
        
        
        DirectedAcyclicalGraph dag = new DirectedAcyclicalGraph();
        
        Action a1 = new Action("a1", "a1");      
        Action a2 = new Action("a2", "a2");
        Action a3 = new Action("a3", "a3");
        
        
        a1.setIncomming("pg1");
        a1.setOutgoing("pg2");
        
        a2.setIncomming("pg1");
        a2.setOutgoing("pg2");
        
        a3.setIncomming("pg1");
        a3.setOutgoing("pg2");
      
        
        
        List<String> pg1Incomming =new ArrayList<String>();
        List<String> pg1Outgoing =new ArrayList<String>();
    
        pg1Outgoing.add("a1");
        pg1Outgoing.add("a2");
        pg1Outgoing.add("a3");
        
 
        ParallelGateway pg1 = new ParallelGateway("pg1", pg1Incomming, pg1Outgoing);
        
        
        List<String> pg2Incomming =new ArrayList<String>();
        List<String> pg2Outgoing =new ArrayList<String>();
        
        pg2Incomming.add("a1");
        pg2Incomming.add("a2");
        pg2Incomming.add("a3");
        
      
        
        ParallelGateway pg2 = new ParallelGateway("pg2", pg2Incomming, pg2Outgoing);
        
        
       
        List<Action> listOfActions = new ArrayList<Action>();
        listOfActions.add(a1);
        listOfActions.add(a2);
        listOfActions.add(a3);
       
        
        
        List<ParallelGateway> listOfParallelGateways = new ArrayList<ParallelGateway>();
        listOfParallelGateways.add(pg1);
        listOfParallelGateways.add(pg2);
        
        dag.setListOfActions(listOfActions);
        dag.setListOfParallelGateways(listOfParallelGateways);
      
        
        AdjustmentProcess adjustmentProcess = new AdjustmentProcess(null, null, dag);
        
        return adjustmentProcess;
    }
    
    private static AdjustmentProcess sampleData3(){
        
        
        DirectedAcyclicalGraph dag = new DirectedAcyclicalGraph();
        
        Action a1 = new Action("a1", "a1");      
        Action a2 = new Action("a2", "a2");
        Action a3 = new Action("a3", "a3");
        Action a4 = new Action("a4", "a4");
        
        a1.setIncomming("pg1");
        a1.setOutgoing("pg2");
        
        a2.setIncomming("pg2");
        a2.setOutgoing("pg3");
        
        a3.setIncomming("pg2");
        a3.setOutgoing("pg3");
        
        a4.setIncomming("pg1");
        a4.setOutgoing("pg4");
        
        
        
        List<String> pg1Incomming =new ArrayList<String>();
        List<String> pg1Outgoing =new ArrayList<String>();
    
        pg1Outgoing.add("a1");
        pg1Outgoing.add("a4");
        
 
        ParallelGateway pg1 = new ParallelGateway("pg1", pg1Incomming, pg1Outgoing);
        
        
        List<String> pg2Incomming =new ArrayList<String>();
        List<String> pg2Outgoing =new ArrayList<String>();
        
        pg2Incomming.add("a1");
        pg2Outgoing.add("a2");
        pg2Outgoing.add("a3");
        
        ParallelGateway pg2 = new ParallelGateway("pg2", pg2Incomming, pg2Outgoing);
        
        
        List<String> pg3Incomming =new ArrayList<String>();
        List<String> pg3Outgoing =new ArrayList<String>();
        
        pg3Incomming.add("a2");
        pg3Incomming.add("a3");
        
        pg3Outgoing.add("pg4");
        
        
        ParallelGateway pg3 = new ParallelGateway("pg3", pg3Incomming, pg3Outgoing);
        
        
        List<String> pg4Incomming =new ArrayList<String>();
        List<String> pg4Outgoing =new ArrayList<String>();
        
        pg4Incomming.add("pg3");
        
        ParallelGateway pg4 = new ParallelGateway("pg4", pg4Incomming, pg4Outgoing);
        
        
       
        List<Action> listOfActions = new ArrayList<Action>();
        listOfActions.add(a1);
        listOfActions.add(a2);
        listOfActions.add(a3);
        listOfActions.add(a4);
        
        
        List<ParallelGateway> listOfParallelGateways = new ArrayList<ParallelGateway>();
        listOfParallelGateways.add(pg1);
        listOfParallelGateways.add(pg2);
        listOfParallelGateways.add(pg3);
        listOfParallelGateways.add(pg4);
        
        dag.setListOfActions(listOfActions);
        dag.setListOfParallelGateways(listOfParallelGateways);
      
        
        AdjustmentProcess adjustmentProcess = new AdjustmentProcess(null, null, dag);
        
        return adjustmentProcess;
    }
    
}
