/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.process.generator;

import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.BinaryRestriction;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.BinaryRestrictionsConjunction;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.Condition;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.Constraint;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.LeftHandSide;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.Monitoring;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.RightHandSide;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.SYBLSpecification;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.Strategy;
import at.ac.tuwien.dsg.csdg.elasticityInformation.elasticityRequirements.ToEnforce;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticState;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ResourceControlPlan;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MetricCondition;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlCase;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlStrategy;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ResourceControlPlanGenerator {
    
    DataAnalyticsFunction daf;
    QoRModel qorModel;
    PrimitiveActionMetadata primitiveActionRepository;
    List<ElasticState> finalElasticStates;
    String errorLog;
    String rootPath;
    
    public ResourceControlPlanGenerator(DataAnalyticsFunction daf, QoRModel qorModel, PrimitiveActionMetadata primitiveActionRepository, List<ElasticState> finalElasticStates, String errorLog, String rootPath) {
        this.daf = daf;
        this.qorModel = qorModel;
        this.primitiveActionRepository = primitiveActionRepository;
        this.finalElasticStates = finalElasticStates;
        this.errorLog = errorLog;
        this.rootPath = rootPath;
    }

    
    public List<ResourceControlPlan> generateResourceControlPlan(List<ElasticState> listOfFinalElasticStates) {

        List<ResourceControlPlan> listOfFoundResourceControlPlans = new ArrayList<ResourceControlPlan>();

        for (ElasticState elasticState : listOfFinalElasticStates) {
            List<MetricCondition> listOfConditions = elasticState.getListOfConditions();
            
            List<ResourceControlStrategy> resourceControlStrategiesForEState = new ArrayList<ResourceControlStrategy>();
            
            for (MetricCondition metricCondition : listOfConditions) {
                List<ResourceControlStrategy> listOfFoundResourceControlStrategies = findResourceControlStrategy(metricCondition);
                if (listOfFoundResourceControlStrategies.size() > 0) {
                    resourceControlStrategiesForEState.addAll(listOfFoundResourceControlStrategies);
                } 
            }
            
            for (int i=0; i<resourceControlStrategiesForEState.size();i++){
                for (int j=0; j<resourceControlStrategiesForEState.size();j++) {
                    if (i!=j) {
                        if (resourceControlStrategiesForEState.get(i).getPrimitiveAction().equals(resourceControlStrategiesForEState.get(j).getPrimitiveAction())){
                            errorLog = errorLog + "\n Duplicate resource strategy for primitive action " + resourceControlStrategiesForEState.get(i).getPrimitiveAction();
                            System.err.println(errorLog);
                        }
                    }  
                }
            }
            
            
            
            ResourceControlPlan resourceControlPlan = new ResourceControlPlan(elasticState, resourceControlStrategiesForEState);
                    listOfFoundResourceControlPlans.add(resourceControlPlan);

        }

        return listOfFoundResourceControlPlans;
    }
    
    
    public SYBLSpecification generateResourceControlPlanSYBL(List<ElasticState> listOfFinalElasticStates) {

        SYBLSpecification sYBLSpecification = new SYBLSpecification();
      
        for (ElasticState elasticState : listOfFinalElasticStates) {
            List<MetricCondition> listOfConditions = elasticState.getListOfConditions();
            
            for (MetricCondition metricCondition : listOfConditions) {
                List<ResourceControlStrategy> listOfFoundResourceControlStrategies = findResourceControlStrategy(metricCondition);
                
                if (listOfFoundResourceControlStrategies.size() > 0) {
                    
                    
                    for (ResourceControlStrategy rcs : listOfFoundResourceControlStrategies){
                        
                        Strategy scaleInStrategy = mappingResourceControlStrategyToSYBLScaleInStrategy(rcs);
                        Strategy scaleOutStrategy = mappingResourceControlStrategyToSYBLScaleOutStrategy(rcs);
                        
                        sYBLSpecification.addStrategy(scaleInStrategy);
                        sYBLSpecification.addStrategy(scaleOutStrategy);
                        
                    }             
                } 
            }
         
            
         
        }

        return sYBLSpecification;
    }
    

    
    
    
    
    
      
        
    private Strategy mappingResourceControlStrategyToSYBLScaleInStrategy(ResourceControlStrategy rcs){
        Strategy strategy = new Strategy();

        ToEnforce toEnforce = new ToEnforce();
        toEnforce.setActionName("scaleIn");
        toEnforce.setParameter(rcs.getPrimitiveAction());
        
        strategy.setToEnforce(toEnforce);
        

        Condition cond = new Condition();

        BinaryRestrictionsConjunction binaryRestrictions = new BinaryRestrictionsConjunction();
        
            {
                // binary restriction 1
                BinaryRestriction binaryRestr1 = new BinaryRestriction();
                binaryRestr1.setType("lessThan");

                LeftHandSide leftHandSide1 = new LeftHandSide();
                leftHandSide1.setMetric(rcs.getControlMetric());

                RightHandSide rightHandSide1 = new RightHandSide();
                rightHandSide1.setNumber(String.valueOf(rcs.getScaleInCondition().getUpperBound()));

                binaryRestr1.setLeftHandSide(leftHandSide1);
                binaryRestr1.setRightHandSide(rightHandSide1);
                binaryRestrictions.add(binaryRestr1);
            }
      
            {
                // binary restriction 2
                BinaryRestriction binaryRestr2 = new BinaryRestriction();
                binaryRestr2.setType("greaterThan");

                LeftHandSide leftHandSide2 = new LeftHandSide();
                leftHandSide2.setMetric(rcs.getControlMetric());

                RightHandSide rightHandSide2 = new RightHandSide();
                rightHandSide2.setNumber(String.valueOf(rcs.getScaleInCondition().getLowerBound()));

                binaryRestr2.setLeftHandSide(leftHandSide2);
                binaryRestr2.setRightHandSide(rightHandSide2);
                binaryRestrictions.add(binaryRestr2);
            }
        
        cond.addBinaryRestrictionConjunction(binaryRestrictions);
        
        
        strategy.setCondition(cond);
        
        return strategy;
    }    
       
    
    private Strategy mappingResourceControlStrategyToSYBLScaleOutStrategy(ResourceControlStrategy rcs){
        Strategy strategy = new Strategy();

        ToEnforce toEnforce = new ToEnforce();
        toEnforce.setActionName("scaleOut");
        toEnforce.setParameter(rcs.getPrimitiveAction());
        
        strategy.setToEnforce(toEnforce);
        

        Condition cond = new Condition();

        BinaryRestrictionsConjunction binaryRestrictions = new BinaryRestrictionsConjunction();
        
            {
                // binary restriction 1
                BinaryRestriction binaryRestr1 = new BinaryRestriction();
                binaryRestr1.setType("lessThan");

                LeftHandSide leftHandSide1 = new LeftHandSide();
                leftHandSide1.setMetric(rcs.getControlMetric());

                RightHandSide rightHandSide1 = new RightHandSide();
                rightHandSide1.setNumber(String.valueOf(rcs.getScaleOutCondition().getUpperBound()));

                binaryRestr1.setLeftHandSide(leftHandSide1);
                binaryRestr1.setRightHandSide(rightHandSide1);
                binaryRestrictions.add(binaryRestr1);
            }
      
            {
                // binary restriction 2
                BinaryRestriction binaryRestr2 = new BinaryRestriction();
                binaryRestr2.setType("greaterThan");

                LeftHandSide leftHandSide2 = new LeftHandSide();
                leftHandSide2.setMetric(rcs.getControlMetric());

                RightHandSide rightHandSide2 = new RightHandSide();
                rightHandSide2.setNumber(String.valueOf(rcs.getScaleOutCondition().getLowerBound()));

                binaryRestr2.setLeftHandSide(leftHandSide2);
                binaryRestr2.setRightHandSide(rightHandSide2);
                binaryRestrictions.add(binaryRestr2);
            }
        
        cond.addBinaryRestrictionConjunction(binaryRestrictions);
        
        
        strategy.setCondition(cond);
        
        return strategy;
    }  

    private List<ResourceControlStrategy> findResourceControlStrategy(MetricCondition metricCondition) {
        
        
        
        
        List<ResourceControlAction> listOfResourceControls = primitiveActionRepository.getListOfResourceControls();

        
       
        List<ResourceControlStrategy> foundListOfResourceControlStrategies = new ArrayList<ResourceControlStrategy>();
        for (ResourceControlAction rc : listOfResourceControls) {
            if (metricCondition.getMetricName().equals(rc.getAssociatedQoRMetric())) {

                List<ResourceControlCase> listOfResourceControlCases = rc.getListOfResourceControlCases();

                for (ResourceControlCase resourceControlCase : listOfResourceControlCases) {
                    if (resourceControlCase.getEstimatedResult().getLowerBound() == metricCondition.getLowerBound()
                            && resourceControlCase.getEstimatedResult().getUpperBound() == metricCondition.getUpperBound()) {

                        foundListOfResourceControlStrategies.addAll(copyListOfResourceControlStrategy(resourceControlCase.getListOfResourceControlStrategies()));

                        break;

                    }

                }

            }
        }

        return foundListOfResourceControlStrategies;

    }

    private List<ResourceControlStrategy> copyListOfResourceControlStrategy(List<ResourceControlStrategy> originalList) {

        List<ResourceControlStrategy> copyList = new ArrayList<ResourceControlStrategy>();

        for (ResourceControlStrategy rca : originalList) {
            MetricCondition scaleOutCo = rca.getScaleOutCondition();
            MetricCondition scaleOutCo_c = new MetricCondition(scaleOutCo.getMetricName(), scaleOutCo.getConditionID(), scaleOutCo.getLowerBound(), scaleOutCo.getUpperBound());
            MetricCondition scaleInCo = rca.getScaleInCondition();
            MetricCondition scaleInCo_c = new MetricCondition(scaleInCo.getMetricName(), scaleInCo.getConditionID(), scaleInCo.getLowerBound(), scaleInCo.getUpperBound());
            String controlMetric = rca.getControlMetric();
            String primitiveAction = rca.getPrimitiveAction();
            ResourceControlStrategy rca_copy = new ResourceControlStrategy(scaleInCo_c, scaleOutCo_c, controlMetric, primitiveAction);
            copyList.add(rca_copy);
        }

        return copyList;
    }

    
    
    
    
    
    
    
}
