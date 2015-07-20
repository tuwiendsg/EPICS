/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.process.generator;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticState;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ResourceControlPlan;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MetricCondition;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlCase;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlStrategy;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.depic.repository.PrimitiveActionMetadataManager;
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
