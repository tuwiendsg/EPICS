/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.sybl.controller;

import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.CategoricalElasticityBoundary;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.CategoricalElasticityCapability;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.CategoricalElasticityValue;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.Cost;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.DataElasticityCapability;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.DataSource;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.DataSourceValue;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.NumericalElasticityBoundary;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.NumericalElasticityCapability;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.NumericalElasticityValue;
import at.ac.tuwien.dsg.depic.dataelasticityrequirement.DataElasticityRequirement;
import at.ac.tuwien.dsg.sybl.elasticstate.CategoricalElCapState;
import at.ac.tuwien.dsg.sybl.elasticstate.ElasticState;
import at.ac.tuwien.dsg.sybl.elasticstate.NumericalElCapState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Jun
 */
public class DataElasticityController {

    List dataElCapSet;
    List lowerElCap;
    List higherElCap;
    ElasticState eState;

    public DataElasticityController() {
        dataElCapSet = new ArrayList();
        lowerElCap = new ArrayList<ElCapPriority>();
        higherElCap = new ArrayList<ElCapPriority>();
        eState = new ElasticState();
    }

    public ElasticState geteState() {
        return eState;
    }

    public void seteState(ElasticState eState) {
        this.eState = eState;
    }

    
    
    public void findElasticState(DataElasticityRequirement dataElReq) {

        System.out.println("---- FIND ELASTIC STATE ----------------------------- ");
        dataElCapSet.addAll(dataElReq.getDataElCapSet());

        setupStartingState();

        int costPriority = getCostPriority();
        System.out.println("---- Current Total Cost: " + eState.getTotalCost() + "-----");
        System.out.println("---- Cost Priority: " + costPriority + "-----");

        classifyElCapPriorityOrder(costPriority);
        sortElCapPriorityOrder();

        controlLowerElcap();
        controlHigherElcap();
        
        eState.setCurrentBudget(getMaxCost());
        
        
       
        printEState();
        
        System.out.println("*** Status: " + eState.resetStatus());

    }

    private void controlHigherElcap() {

        // process higherElCap
        for (int i = 0; i < higherElCap.size(); i++) {

            ElCapPriority elCapPriority = (ElCapPriority) higherElCap.get(i);
            int elCapID = elCapPriority.getIndex();

            System.out.println("@Current Total Cost: " + eState.getTotalCost());
            System.out.println("@Current Total Budget: " + eState.getCurrentBudget());

            if (eState.getTotalCost() <= eState.getCurrentBudget()) {
                eState.setCurrentBudget(eState.getTotalCost());

            } else if ((eState.getTotalCost() > eState.getCurrentBudget()) && (eState.getTotalCost() <= getMaxCost())) {

                eState.setCurrentBudget(eState.getTotalCost());

            } else {

                while (eState.getTotalCost() > getMaxCost()) {

                    if (dataElCapSet.get(elCapID) instanceof CategoricalElasticityCapability) {

                        CategoricalElasticityCapability elCap = (CategoricalElasticityCapability) dataElCapSet.get(elCapID);

                        String elName = elCap.getElasticityCapabilityName();
                        int currentElCapValue = (int) eState.getCurrentElCapValueFromElCapName(elName);

                        if (currentElCapValue > elCap.getElastictyBoundary().getLowerBoundary()) {

                            double elCapCost = eState.getCurrentElCapCostFromElCapName(elName);
                            eState.subtractCost(elCapCost);
                            eState.setCurrentElValFromElCapName(elName, (currentElCapValue - 1));

                            System.out.println("elName: " + elName);
                            System.out.println("After Decrease to Level : " + eState.getCurrentElCapValueFromElCapName(elName));

                            elCapCost = getCostFromElCapValue(elCap, (int) eState.getCurrentElCapValueFromElCapName(elName));
                            eState.addCost(elCapCost);
                            eState.setCurrentValCostFromElCapName(elName, elCapCost);

                            System.out.println("Cost Now: " + eState.getTotalCost());

                        } else {
                            break;
                        }

                    } else if (dataElCapSet.get(elCapID) instanceof NumericalElasticityCapability) {

                        NumericalElasticityCapability elCap = (NumericalElasticityCapability) dataElCapSet.get(elCapID);

                        String elName = elCap.getElasticityCapabilityName();
                        double currentElCapValue = eState.getCurrentElCapValueFromElCapName(elName);
                        int currentElValIndex  = getNumericalElCapIndex(elCap.getElasticityValues(),currentElCapValue);
                        

                        if (currentElValIndex>0) {

                            currentElValIndex -= 1;
                            currentElCapValue = getNumericalElCapValueFromIndex(elCap.getElasticityValues(),currentElValIndex);
                            
                            double elCapCost = eState.getCurrentElCapCostFromElCapName(elName);
                            eState.subtractCost(elCapCost);
                            
                            eState.setCurrentElValFromElCapName(elName, currentElCapValue);

                            System.out.println("elName: " + elName);
                            System.out.println("After Decrease to Value : " + eState.getCurrentElCapValueFromElCapName(elName));

                            elCapCost = getCostFromElCapValue(elCap,  eState.getCurrentElCapValueFromElCapName(elName));
                            eState.addCost(elCapCost);
                            eState.setCurrentValCostFromElCapName(elName, elCapCost);

                            System.out.println("Cost Now: " + eState.getTotalCost());

                        } else {
                            break;
                        }

                    } else {
                        break;
                    }

                }

            //    
            }

        }
    }

    private void controlLowerElcap() {
        
        // process lowerElCap
        for (int i = 0; i < lowerElCap.size(); i++) {

            ElCapPriority elCapPriority = (ElCapPriority)lowerElCap.get(i);
            int elCapID = elCapPriority.getIndex();

          
            while (eState.getTotalCost() > 0) {

                if (dataElCapSet.get(elCapID) instanceof CategoricalElasticityCapability) {
                    CategoricalElasticityCapability elCap = (CategoricalElasticityCapability) dataElCapSet.get(elCapID);
                    String elName = elCap.getElasticityCapabilityName();
                    int currentElCapValue = (int)eState.getCurrentElCapValueFromElCapName(elName);

                    if (currentElCapValue > elCap.getElastictyBoundary().getLowerBoundary()) {

                        double elCapCost = eState.getCurrentElCapCostFromElCapName(elName);
                            eState.subtractCost(elCapCost);
                            eState.setCurrentElValFromElCapName(elName, (currentElCapValue - 1));

                            System.out.println("elName: " + elName);
                            System.out.println("After Decrease to Level : " + eState.getCurrentElCapValueFromElCapName(elName));

                            elCapCost = getCostFromElCapValue(elCap, (int) eState.getCurrentElCapValueFromElCapName(elName));
                            eState.addCost(elCapCost);
                            eState.setCurrentValCostFromElCapName(elName, elCapCost);

                            System.out.println("Cost Now: " + eState.getTotalCost());
                    

                    } else {
                        break;
                    }

                } else if (dataElCapSet.get(elCapID) instanceof NumericalElasticityCapability) {

                    NumericalElasticityCapability elCap = (NumericalElasticityCapability) dataElCapSet.get(elCapID);

                        String elName = elCap.getElasticityCapabilityName();
                        double currentElCapValue = eState.getCurrentElCapValueFromElCapName(elName);
                        int currentElValIndex  = getNumericalElCapIndex(elCap.getElasticityValues(),currentElCapValue);
                        

                        if (currentElValIndex>0) {

                            currentElValIndex -= 1;
                            currentElCapValue = getNumericalElCapValueFromIndex(elCap.getElasticityValues(),currentElValIndex);
                            
                            double elCapCost = eState.getCurrentElCapCostFromElCapName(elName);
                            eState.subtractCost(elCapCost);
                            
                            eState.setCurrentElValFromElCapName(elName, currentElCapValue);

                            System.out.println("elName: " + elName);
                            System.out.println("After Decrease to Value : " + eState.getCurrentElCapValueFromElCapName(elName));

                            elCapCost = getCostFromElCapValue(elCap,  eState.getCurrentElCapValueFromElCapName(elName));
                            eState.addCost(elCapCost);
                            eState.setCurrentValCostFromElCapName(elName, elCapCost);

                            System.out.println("Cost Now: " + eState.getTotalCost());
                        

                    } else {
                        break;
                    }

                }

            }
        }
        
        

    }

    private void classifyElCapPriorityOrder(int costPriority) {

        for (int i = 0; i < dataElCapSet.size(); i++) {

            if (dataElCapSet.get(i) instanceof CategoricalElasticityCapability) {
                CategoricalElasticityCapability elCap = (CategoricalElasticityCapability) dataElCapSet.get(i);
                int elPriority = elCap.getPriorityOrder();

                ElCapPriority elCapPriority = new ElCapPriority(i, elPriority);

                if (elPriority < costPriority) {
                    higherElCap.add(elCapPriority);
                } else {
                    lowerElCap.add(elCapPriority);
                }

            } else if (dataElCapSet.get(i) instanceof NumericalElasticityCapability) {

                NumericalElasticityCapability elCap = (NumericalElasticityCapability) dataElCapSet.get(i);

                int elPriority = elCap.getPriorityOrder();

                ElCapPriority elCapPriority = new ElCapPriority(i, elPriority);

                if (elPriority < costPriority) {
                    higherElCap.add(elCapPriority);
                } else {
                    lowerElCap.add(elCapPriority);
                }

            }
        }

    }

    private void sortElCapPriorityOrder() {
        // sorting vector
        for (int i = 0; i < higherElCap.size(); i++) {

            for (int j = i + 1; j < higherElCap.size(); j++) {

                ElCapPriority elCapPrio1 = (ElCapPriority) higherElCap.get(i);
                ElCapPriority elCapPrio2 = (ElCapPriority) higherElCap.get(j);
                if (elCapPrio1.getPriority() < elCapPrio2.getPriority()) {
                    Collections.swap(higherElCap, i, j);
                }

            }

        }

        for (int i = 0; i < lowerElCap.size(); i++) {

            for (int j = 0; j < lowerElCap.size(); j++) {

                ElCapPriority elCapPrio1 = (ElCapPriority) lowerElCap.get(i);
                ElCapPriority elCapPrio2 = (ElCapPriority) lowerElCap.get(j);
                if (elCapPrio1.getPriority() > elCapPrio2.getPriority()) {
                    Collections.swap(lowerElCap, i, j);
                }

            }

        }

        // test sorting
        System.out.println(" --- higherElCap ------------------ ");

        for (int i = 0; i < higherElCap.size(); i++) {

            ElCapPriority elCapPriority = (ElCapPriority) higherElCap.get(i);
            System.out.println("ElCapID: " + elCapPriority.getIndex() + " - ElCapPriority: " + elCapPriority.priority);

        }

        System.out.println(" --- lowerElCap ------------------ ");

        for (int i = 0; i < lowerElCap.size(); i++) {

            ElCapPriority elCapPriority = (ElCapPriority) lowerElCap.get(i);
            System.out.println("ElCapID: " + elCapPriority.getIndex() + " - ElCapPriority: " + elCapPriority.priority);

        }
    }

    private double getCostFromElCapValue(CategoricalElasticityCapability elCap, int elVal) {

        double elCost = 0;

        List<CategoricalElasticityValue> elasticityValues = elCap.getElasticityValues();

        for (int i = 0; i < elasticityValues.size(); i++) {
            CategoricalElasticityValue el = elasticityValues.get(i);
            if (el.getElValue() == elVal) {
                elCost = el.getCost();
                break;
            }

        }

        return elCost;
    }

    private double getCostFromElCapValue(NumericalElasticityCapability elCap, double currentElVal) {

        double elCost = 0;
        
 
         List<NumericalElasticityValue> elasticityValues = elCap.getElasticityValues();

        for (int i = 0; i < elasticityValues.size(); i++) {
            NumericalElasticityValue elVal = elasticityValues.get(i);

            if ((elVal.getFromValue() <= currentElVal) && (currentElVal <= elVal.getToValue())) {
                elCost = elVal.getCost();
                break;
            }

        }

    
        return elCost;
    }

    private void setupStartingState() {

        for (int i = 0; i < dataElCapSet.size(); i++) {

            if (dataElCapSet.get(i) instanceof CategoricalElasticityCapability) {
                CategoricalElasticityCapability elCap = (CategoricalElasticityCapability) dataElCapSet.get(i);

                CategoricalElasticityBoundary elCapBoundary = elCap.getElastictyBoundary();
                List<CategoricalElasticityValue> elasticityValues = elCap.getElasticityValues();
                String elName = elCap.getElasticityCapabilityName();

                int constraintMin = elCap.getGreaterThanLevel();
                int constraintMax = elCap.getLessThanLevel();

                if (constraintMin == 0) {
                    System.out.println("No elMIN - set  Min to lowerBound ");
                    elCap.setGreaterThanLevel(elCapBoundary.getLowerBoundary());
                }

                if (constraintMax == 0) {
                    System.out.println("No elMAX - set  Max to upperBound ");
                    elCap.setLessThanLevel(elCapBoundary.getUpperBoundary());
                    constraintMax = elCap.getLessThanLevel();
                }

                int elIndex = getCategoricalElCapIndex(elasticityValues, constraintMax);
                CategoricalElasticityValue currentElVal = elasticityValues.get(elIndex);

                CategoricalElCapState elCapState = new CategoricalElCapState(elName, currentElVal.getCost(), constraintMax);
                eState.addElCapValue(elCapState);
                eState.addCost(currentElVal.getCost());

                printElCapStateLog(elCapState);

            } else if (dataElCapSet.get(i) instanceof NumericalElasticityCapability) {

                NumericalElasticityCapability elCap = (NumericalElasticityCapability) dataElCapSet.get(i);

                NumericalElasticityBoundary elCapBoundary = elCap.getElastictyBoundary();
                List<NumericalElasticityValue> elasticityValues = elCap.getElasticityValues();
                String elName = elCap.getElasticityCapabilityName();

                double constraintMin = elCap.getGreaterThanValue();
                double constraintMax = elCap.getLessThanValue();

                if (constraintMin == 0) {
                    System.out.println("No elMIN - set  Min to lowerBound ");
                    elCap.setGreaterThanValue(elCapBoundary.getLowerBoundary());
                }

                if (constraintMax == 0) {
                    System.out.println("No elMAX - set  Max to upperBound ");
                    elCap.setLessThanValue(elCapBoundary.getUpperBoundary());
                    constraintMax = elCap.getLessThanValue();
                }

                int elIndex = getNumericalElCapIndex(elasticityValues, constraintMax);
                NumericalElasticityValue currentElVal = elasticityValues.get(elIndex);

                NumericalElCapState elCapState = new NumericalElCapState(elName, currentElVal.getCost(), constraintMax);
                eState.addElCapValue(elCapState);
                eState.addCost(currentElVal.getCost());

                printElCapStateLog(elCapState);

            } else if (dataElCapSet.get(i) instanceof DataSource) {

                DataSource elCap = (DataSource) dataElCapSet.get(i);
                List dataSourceVales = elCap.getDataSourceValues();

                for (int j = 0; j < dataSourceVales.size(); j++) {
                    DataSourceValue dsVal = (DataSourceValue) dataSourceVales.get(j);
                    eState.addCost(dsVal.getCost());

                }

            } else if (dataElCapSet.get(i) instanceof Cost) {

                Cost elCap = (Cost) dataElCapSet.get(i);
                eState.setCurrentBudget(1);
               // costPriority = elCap.getPriorityOrder();
                // printElCapStateLog(elCap);

            }

        }

    }

    private int getCostPriority() {
        int costPriority = 1;

        for (int i = 0; i < dataElCapSet.size(); i++) {

            if (dataElCapSet.get(i) instanceof Cost) {

                Cost elCap = (Cost) dataElCapSet.get(i);
                costPriority = elCap.getPriorityOrder();
               // printElCapStateLog(elCap);

            }

        }

        return costPriority;
    }

    private double getMaxCost() {
        double maxCost = 0.0;

        for (int i = 0; i < dataElCapSet.size(); i++) {

            if (dataElCapSet.get(i) instanceof Cost) {

                Cost elCap = (Cost) dataElCapSet.get(i);
                maxCost = elCap.getLessThan();

            }

        }

        return maxCost;
    }

    private int getCategoricalElCapIndex(List<CategoricalElasticityValue> elasticityValues, int currentElVal) {
        int elIndex = 0;

        for (int i = 0; i < elasticityValues.size(); i++) {
            CategoricalElasticityValue elVal = elasticityValues.get(i);
            if (elVal.getElValue() == currentElVal) {
                elIndex = i;
                break;
            }

        }

        return elIndex;
    }

    private int getNumericalElCapIndex(List<NumericalElasticityValue> elasticityValues, double currentElVal) {
        int elIndex = 0;

        for (int i = 0; i < elasticityValues.size(); i++) {
            NumericalElasticityValue elVal = elasticityValues.get(i);

            if ((elVal.getFromValue() <= currentElVal) && (currentElVal <= elVal.getToValue())) {
                elIndex = i;
                break;
            }

        }

        return elIndex;
    }
    
    private double getNumericalElCapValueFromIndex(List<NumericalElasticityValue> elasticityValues, int elIndex) {
        
            NumericalElasticityValue elVal = elasticityValues.get(elIndex);


        return elVal.getToValue();
    }
    

    private void printEState(){
        
        List elCapStates = eState.getElCapStates();
        for (int i = 0; i < elCapStates.size(); i++) {

            if (elCapStates.get(i) instanceof CategoricalElCapState) {
                CategoricalElCapState elState = (CategoricalElCapState)elCapStates.get(i);
                
               printElCapStateLog(elState);
            
            } else if (elCapStates.get(i) instanceof NumericalElCapState) {
                NumericalElCapState elState = (NumericalElCapState)elCapStates.get(i);
                printElCapStateLog(elState);
            }
         }
         
    }
    private void printElCapStateLog(CategoricalElCapState elCapState) {

        System.out.println("*********");
        System.out.println("Name: " + elCapState.getName());
        System.out.println("Cost: " + elCapState.getCost());
        System.out.println("Curent Value: " + elCapState.getCurrentValue());

    }

    private void printElCapStateLog(NumericalElCapState elCapState) {

        System.out.println("*********");
        System.out.println("Name: " + elCapState.getName());
        System.out.println("Cost: " + elCapState.getCost());
        System.out.println("Curent Value: " + elCapState.getCurrentValue());

    }

    private void printElCapStateLog(Cost elCapState) {

        System.out.println("*********");
        System.out.println("Max: " + elCapState.getLessThan());
        System.out.println("Priority: " + elCapState.getPriorityOrder());

    }
    
    
    

}
