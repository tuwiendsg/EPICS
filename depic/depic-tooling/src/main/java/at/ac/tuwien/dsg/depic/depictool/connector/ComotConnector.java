/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.connector;

import at.ac.tuwien.dsg.depic.common.entity.runtime.DeployAction;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ElasticService;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;

import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.DataElasticityManagementProcess;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.MonitoringProcess;

import static at.ac.tuwien.dsg.comot.common.model.ArtifactTemplate.SingleScriptArtifact;

import static at.ac.tuwien.dsg.comot.common.model.CloudService.ServiceTemplate;

import static at.ac.tuwien.dsg.comot.common.model.EntityRelationship.HostedOnRelation;
import static at.ac.tuwien.dsg.comot.common.model.ServiceTopology.ServiceTopology;
import static at.ac.tuwien.dsg.comot.common.model.SoftwareNode.SingleSoftwareUnit;
import static at.ac.tuwien.dsg.comot.common.model.Strategy.Strategy;

import at.ac.tuwien.dsg.comot.common.model.CloudService;
import at.ac.tuwien.dsg.comot.common.model.CommonOperatingSystemSpecification;

import static at.ac.tuwien.dsg.comot.common.model.CommonOperatingSystemSpecification.OpenstackSmall;
import at.ac.tuwien.dsg.comot.common.model.Constraint;
import at.ac.tuwien.dsg.comot.common.model.Constraint.Metric;

import at.ac.tuwien.dsg.comot.common.model.ElasticityCapability;

import at.ac.tuwien.dsg.comot.common.model.OperatingSystemUnit;
import static at.ac.tuwien.dsg.comot.common.model.OperatingSystemUnit.OperatingSystemUnit;

import at.ac.tuwien.dsg.comot.common.model.ServiceTopology;
import at.ac.tuwien.dsg.comot.common.model.ServiceUnit;


import at.ac.tuwien.dsg.depic.depictool.utils.Configuration;
import at.ac.tuwien.dsg.comot.orchestrator.interraction.COMOTOrchestrator;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.AdjustmentProcess;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ResourceControlPlan;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MetricCondition;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlStrategy;
import at.ac.tuwien.dsg.depic.common.repository.PrimitiveActionMetadataManager;
import at.ac.tuwien.dsg.depic.common.utils.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Jun
 */
public class ComotConnector {

    private String cloudServiceID;
    private String artifactRepo;
    private DataElasticityManagementProcess elasticityProcess;
    private DeployAction eDaaSDeployAction;
    private List<DeployAction> monitoringServices;
    private List<DeployAction> controlServices;

    public ComotConnector() {
    }

    public ComotConnector(DataElasticityManagementProcess elasticityProcesses, DeployAction eDaaSDeployAction) {
        this.elasticityProcess = elasticityProcesses;
        this.eDaaSDeployAction = eDaaSDeployAction;
        config();
        prepareSoftwareArtifact();
    }

    private void config() {

        Configuration cfg = new Configuration();
        artifactRepo = cfg.getConfig("ARTIFACT.REPO");
        cloudServiceID = eDaaSDeployAction.getActionID() + "CloudService";
    }

    public String getCloudServiceID() {
        return cloudServiceID;
    }

    private void prepareSoftwareArtifact() {

        prepareMonitoringServices();
        prepareControlServices();
    }

    private void prepareMonitoringServices() {

        monitoringServices = new ArrayList<DeployAction>();

        PrimitiveActionMetadataManager pamm = new PrimitiveActionMetadataManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

        MonitoringProcess monitorProcess = elasticityProcess.getMonitoringProcess();
        List<MonitoringAction> monitoringActions = monitorProcess.getListOfMonitoringActions();

        for (MonitoringAction ma : monitoringActions) {

            if (!isDeployActionExisting(monitoringServices, ma.getMonitoringActionName())) {
                DeployAction deployAction = pamm.getPrimitiveAction(ma.getMonitoringActionName());
                
                if (deployAction != null) {
                        monitoringServices.add(deployAction);
                    } else {
                        Logger.logInfo("COMOT CONNECTOR - Missing Artifact for Service: " + ma.getMonitoringActionName());
                    }
            }

        }
    }

    private void prepareControlServices() {

        controlServices = new ArrayList<DeployAction>();

        PrimitiveActionMetadataManager pamm = new PrimitiveActionMetadataManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

        List<AdjustmentProcess> listOfAdjustmentProcesses = elasticityProcess.getListOfAdjustmentProcesses();

        for (AdjustmentProcess adjustmentProcess : listOfAdjustmentProcesses) {

            List<AdjustmentAction> controlActions = adjustmentProcess.getListOfAdjustmentActions();

            for (AdjustmentAction ca : controlActions) {

                if (!isDeployActionExisting(controlServices, ca.getActionName())) {
                    DeployAction deployAction = pamm.getPrimitiveAction(ca.getActionName());

                    if (deployAction != null) {
                        controlServices.add(deployAction);
                    } else {
                        Logger.logInfo("COMOT CONNECTOR - Missing Artifact for Service: " + ca.getActionName());
                    }

                }

            }

        }

    }

    private boolean isDeployActionExisting(List<DeployAction> listOfDeployActions, String deployActionID) {

        System.out.println("CHECK: " + deployActionID);
        
        
        
        for (int i = 0; i < listOfDeployActions.size(); i++) {
            DeployAction da = listOfDeployActions.get(i);

            if (da.getActionID().equals(deployActionID)) {
                return true;
            }
        }

        return false;
    }

    
    public void deployCloudSevices() {

        // monitoring_services_topology 
        ServiceTopology monitoringServicesTopology = ServiceTopology("Monitoring_Services_Topology");

        // control_services_topology
        ServiceTopology controlServicesTopology = ServiceTopology("Control_Services_Topology");

        // edaas_topology
        ServiceTopology edaasServiceTopology = ServiceTopology("eDaaS_Services_Topology");
        // add topology
        Configuration cfg = new Configuration();
        String compositionRules = cfg.getConfigPath() + "/compositionRules.xml";
        CloudService cloudService = ServiceTemplate(eDaaSDeployAction.getActionID() + "CloudService")
                .consistsOfTopologies(monitoringServicesTopology)
                .consistsOfTopologies(controlServicesTopology)
                .consistsOfTopologies(edaasServiceTopology)
                //             .withDefaultMetrics();
                .withMetricCompositonRulesFile(compositionRules);

        int i = 0;
        for (DeployAction dpa : monitoringServices) {

            // add VM + monitoring service units
            OperatingSystemUnit monitoringVM = OperatingSystemUnit(dpa.getActionID() + "_VM")
                    .providedBy(OpenstackSmall()
                            .withBaseImage("a82e054f-4f01-49f9-bc4c-77a98045739c")
                            .addSoftwarePackage("tomcat7")
                            .addSoftwarePackage("ganglia-monitor")
                            .addSoftwarePackage("gmetad")
                    );
            monitoringServicesTopology.addServiceUnit(monitoringVM);

//            ElasticityCapability eventProcessingUnitScaleIn = ElasticityCapability.ScaleIn().withPrimitiveOperations("scalein");
//            ElasticityCapability eventProcessingUnitScaleOut = ElasticityCapability.ScaleOut().withPrimitiveOperations("scaleout");

            ElasticityCapability eventProcessingUnitScaleIn = ElasticityCapability.ScaleIn();
            ElasticityCapability eventProcessingUnitScaleOut = ElasticityCapability.ScaleOut();

            
            Logger.logInfo("DEPLOY: Monitoring Action: " + dpa.getActionID());
            ServiceUnit serviceUnit = SingleSoftwareUnit(dpa.getActionID() + "_SU")
                    .deployedBy(SingleScriptArtifact(dpa.getActionID() + "Artifact", artifactRepo + dpa.getActionID() + ".sh"))
               //     .withMinInstances(0)
                    .provides(eventProcessingUnitScaleIn, eventProcessingUnitScaleOut)
                    .controlledBy(
                            Strategy("EP_ST1_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO1_" + (i++), new Metric(getControlMetric(dpa.getActionID()), "%"))
                                    .lessThan(String.valueOf(getScaleInCondition(dpa.getActionID()))))
                            .enforce(eventProcessingUnitScaleIn),
                            Strategy("EP_ST2_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO2_" + (i++), new Metric(getControlMetric(dpa.getActionID()), "%"))
                                    .greaterThan(String.valueOf(getScaleOutCondition(dpa.getActionID()))))
                            .enforce(eventProcessingUnitScaleOut));

            monitoringServicesTopology.addServiceUnit(serviceUnit);
            cloudService.andRelationships(HostedOnRelation(serviceUnit.getId() + "To" + monitoringVM.getId())
                    .from(serviceUnit)
                    .to(monitoringVM));

        }

        for (DeployAction dpa : controlServices) {


            
            // add VM + control service units
            OperatingSystemUnit controlVM = OperatingSystemUnit(dpa.getActionID() + "_VM")
                    .providedBy(OpenstackSmall()
                            .withBaseImage("a82e054f-4f01-49f9-bc4c-77a98045739c")
                            .addSoftwarePackage("tomcat7")
                            .addSoftwarePackage("ganglia-monitor")
                            .addSoftwarePackage("gmetad")
                    );
            controlServicesTopology.addServiceUnit(controlVM);

            ElasticityCapability eventProcessingUnitScaleIn = ElasticityCapability.ScaleIn();
            ElasticityCapability eventProcessingUnitScaleOut = ElasticityCapability.ScaleOut();

            Logger.logInfo("DEPLOY: Control Action: " + dpa.getActionID());
            ServiceUnit serviceUnit = SingleSoftwareUnit(dpa.getActionID() + "_SU")
                    .deployedBy(SingleScriptArtifact(dpa.getActionID() + "Artifact", artifactRepo + dpa.getActionID() + ".sh"))
             //       .withMinInstances(0)
                    .provides(eventProcessingUnitScaleIn, eventProcessingUnitScaleOut)
                    .controlledBy(Strategy("EP_ST1_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO1_" + (i++), new Metric(getControlMetric(dpa.getActionID()), "%"))
                                    .lessThan(String.valueOf(getScaleInCondition(dpa.getActionID()))))
                            .enforce(eventProcessingUnitScaleIn),
                            Strategy("EP_ST2_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO2_" + (i++), new Metric(getControlMetric(dpa.getActionID()), "%"))
                                    .greaterThan(String.valueOf(getScaleOutCondition(dpa.getActionID()))))
                            .enforce(eventProcessingUnitScaleOut));
            ;

            controlServicesTopology.addServiceUnit(serviceUnit);
            cloudService.andRelationships(HostedOnRelation(serviceUnit.getId() + "To" + controlVM.getId())
                    .from(serviceUnit)
                    .to(controlVM));
        
        }

        // add VM + eDaaS
        OperatingSystemUnit vm_edaas = OperatingSystemUnit(eDaaSDeployAction.getActionID() + "_VM")
                .providedBy(OpenstackSmall()
                        .withBaseImage("a82e054f-4f01-49f9-bc4c-77a98045739c")
                            .addSoftwarePackage("tomcat7")
                            .addSoftwarePackage("ganglia-monitor")
                            .addSoftwarePackage("gmetad")
                );
        ServiceUnit serviceUnit_edaas = SingleSoftwareUnit(eDaaSDeployAction.getActionID() + "_SU")
                .deployedBy(SingleScriptArtifact(eDaaSDeployAction.getActionID(), eDaaSDeployAction.getArtifact()));

        edaasServiceTopology.addServiceUnit(serviceUnit_edaas);
        edaasServiceTopology.addServiceUnit(vm_edaas);

        cloudService.andRelationships(HostedOnRelation(serviceUnit_edaas.getId() + "To" + vm_edaas.getId())
                .from(serviceUnit_edaas)
                .to(vm_edaas));
        // deployment
        COMOTOrchestrator orchestrator = new COMOTOrchestrator(cfg.getConfig("SALSA.IP"));
                //we have SALSA as cloud management tool
                //curently deployed separately

//                .withSalsaIP(cfg.getConfig("SALSA.IP"))
//                .withSalsaPort(Integer.parseInt(cfg.getConfig("SALSA.PORT")))
//                .withRsyblIP(cfg.getConfig("RSYBL.IP"))
//                .withRsyblPort(Integer.parseInt(cfg.getConfig("RSYBL.PORT")));
//
//        //deploy, monitor and control
//        //orchestrator.deployAndControl(cloudService);
        orchestrator.deployAndControl(cloudService);

        //   orchestrator.controlExisting(cloudService);
        //  return cloudService.getId();
    }
   

    public void deployCloudSevicesFlexiant() {

        // monitoring_services_topology 
        ServiceTopology monitoringServicesTopology = ServiceTopology("Monitoring_Services_Topology");

        // control_services_topology
        ServiceTopology controlServicesTopology = ServiceTopology("Control_Services_Topology");

        // edaas_topology
        ServiceTopology edaasServiceTopology = ServiceTopology("eDaaS_Services_Topology");
        // add topology
        Configuration cfg = new Configuration();
        String compositionRules = cfg.getConfigPath() + "/compositionRules.xml";
        CloudService cloudService = ServiceTemplate(eDaaSDeployAction.getActionID() + "CloudService")
                .consistsOfTopologies(monitoringServicesTopology)
                .consistsOfTopologies(controlServicesTopology)
                   .consistsOfTopologies(edaasServiceTopology)
                //             .withDefaultMetrics();
                .withMetricCompositonRulesFile(compositionRules);

        int i = 0;
        for (DeployAction dpa : monitoringServices) {

            // add VM + monitoring service units
            OperatingSystemUnit monitoringVM = OperatingSystemUnit(dpa.getActionID() + "_VM")
                    .providedBy(CommonOperatingSystemSpecification.FlexiantSmall()
                            .withBaseImage("422a6e60-611c-304d-9317-75ed5ca949b5")
                            .addSoftwarePackage("tomcat7")
                            .addSoftwarePackage("ganglia-monitor")
                            .addSoftwarePackage("gmetad")
                    );
            monitoringServicesTopology.addServiceUnit(monitoringVM);

//            ElasticityCapability eventProcessingUnitScaleIn = ElasticityCapability.ScaleIn().withPrimitiveOperations("scalein");
//            ElasticityCapability eventProcessingUnitScaleOut = ElasticityCapability.ScaleOut().withPrimitiveOperations("scaleout");

            ElasticityCapability eventProcessingUnitScaleIn = ElasticityCapability.ScaleIn();
            ElasticityCapability eventProcessingUnitScaleOut = ElasticityCapability.ScaleOut();

            
            Logger.logInfo("DEPLOY: Monitoring Action: " + dpa.getActionID());
            ServiceUnit serviceUnit = SingleSoftwareUnit(dpa.getActionID() + "_SU")
                    .deployedBy(SingleScriptArtifact(dpa.getActionID() + "Artifact", artifactRepo + dpa.getActionID() + ".sh"))
                   // .withMinInstances(0)
                    .provides(eventProcessingUnitScaleIn, eventProcessingUnitScaleOut)
                    .controlledBy(
                            Strategy("EP_ST1_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO1_" + (i++), new Metric(getControlMetric(dpa.getActionID()), "%"))
                                    .lessThan(String.valueOf(getScaleInCondition(dpa.getActionID()))))
                            .enforce(eventProcessingUnitScaleIn),
                            Strategy("EP_ST2_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO2_" + (i++), new Metric(getControlMetric(dpa.getActionID()), "%"))
                                    .greaterThan(String.valueOf(getScaleOutCondition(dpa.getActionID()))))
                            .enforce(eventProcessingUnitScaleOut));

            monitoringServicesTopology.addServiceUnit(serviceUnit);
            cloudService.andRelationships(HostedOnRelation(serviceUnit.getId() + "To" + monitoringVM.getId())
                    .from(serviceUnit)
                    .to(monitoringVM));

        }

        for (DeployAction dpa : controlServices) {


            
            // add VM + control service units
            OperatingSystemUnit controlVM = OperatingSystemUnit(dpa.getActionID() + "_VM")
                    .providedBy(CommonOperatingSystemSpecification.FlexiantSmall()
                            .withBaseImage("422a6e60-611c-304d-9317-75ed5ca949b5")
                            .addSoftwarePackage("tomcat7")
                            .addSoftwarePackage("ganglia-monitor")
                            .addSoftwarePackage("gmetad")
                    );
            controlServicesTopology.addServiceUnit(controlVM);

            ElasticityCapability eventProcessingUnitScaleIn = ElasticityCapability.ScaleIn();
            ElasticityCapability eventProcessingUnitScaleOut = ElasticityCapability.ScaleOut();

            Logger.logInfo("DEPLOY: Control Action: " + dpa.getActionID());
            ServiceUnit serviceUnit = SingleSoftwareUnit(dpa.getActionID() + "_SU")
                    .deployedBy(SingleScriptArtifact(dpa.getActionID() + "Artifact", artifactRepo + dpa.getActionID() + ".sh"))
               //     .withMinInstances(0)
                    .provides(eventProcessingUnitScaleIn, eventProcessingUnitScaleOut)
                    .controlledBy(Strategy("EP_ST1_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO1_" + (i++), new Metric(getControlMetric(dpa.getActionID()), "%"))
                                    .lessThan(String.valueOf(getScaleInCondition(dpa.getActionID()))))
                            .enforce(eventProcessingUnitScaleIn),
                            Strategy("EP_ST2_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO2_" + (i++), new Metric(getControlMetric(dpa.getActionID()), "%"))
                                    .greaterThan(String.valueOf(getScaleOutCondition(dpa.getActionID()))))
                            .enforce(eventProcessingUnitScaleOut));
            ;

            controlServicesTopology.addServiceUnit(serviceUnit);
            cloudService.andRelationships(HostedOnRelation(serviceUnit.getId() + "To" + controlVM.getId())
                    .from(serviceUnit)
                    .to(controlVM));
        
        }

        // add VM + eDaaS
        OperatingSystemUnit vm_edaas = OperatingSystemUnit(eDaaSDeployAction.getActionID() + "_VM")
                .providedBy(OpenstackSmall()
                        .addSoftwarePackage("openjdk-7-jre")
                        .addSoftwarePackage("ganglia-monitor")
                        .addSoftwarePackage("gmetad")
                );
        ServiceUnit serviceUnit_edaas = SingleSoftwareUnit(eDaaSDeployAction.getActionID() + "_SU")
                .deployedBy(SingleScriptArtifact(eDaaSDeployAction.getActionID(), eDaaSDeployAction.getArtifact()));

        edaasServiceTopology.addServiceUnit(serviceUnit_edaas);
        edaasServiceTopology.addServiceUnit(vm_edaas);

        cloudService.andRelationships(HostedOnRelation(serviceUnit_edaas.getId() + "To" + vm_edaas.getId())
                .from(serviceUnit_edaas)
                .to(vm_edaas));
        // deployment
        COMOTOrchestrator orchestrator = new COMOTOrchestrator(cfg.getConfig("SALSA.IP"));
                //we have SALSA as cloud management tool
                //curently deployed separately

//                .withSalsaIP(cfg.getConfig("SALSA.IP"))
//                .withSalsaPort(Integer.parseInt(cfg.getConfig("SALSA.PORT")))
//                .withRsyblIP(cfg.getConfig("RSYBL.IP"))
//                .withRsyblPort(Integer.parseInt(cfg.getConfig("RSYBL.PORT")));
//
//        //deploy, monitor and control
//        //orchestrator.deployAndControl(cloudService);
        orchestrator.deployAndControl(cloudService);

        //   orchestrator.controlExisting(cloudService);
        //  return cloudService.getId();
    }

    public List<ElasticService> getCloudServiceInfo() {

        SalsaConnector salsaConnector = new SalsaConnector();
        boolean rs = salsaConnector.updateCloudServiceInfo(cloudServiceID);
        List<ElasticService> listOfElasticServices = null;
        if (rs) {

            listOfElasticServices = salsaConnector.getDeployedElasticServices(controlServices, monitoringServices);
        }
        return listOfElasticServices;

    }
    
    
    private String getControlMetric(String serviceName){
        String rs = "";
        ResourceControlPlan resourceControlPlan = elasticityProcess.getListOfResourceControlPlans().get(0);
        List<ResourceControlStrategy> listOfResourceControlStrategys = resourceControlPlan.getListOfResourceControlStrategies();
        
        for (ResourceControlStrategy rcs : listOfResourceControlStrategys){
            if (rcs.getPrimitiveAction().equals(serviceName)) {             
                rs = rcs.getControlMetric();
            }
        }
        
        return rs;
    }
    
    private double getScaleInCondition(String serviceName){
        double rs =0;
        ResourceControlPlan resourceControlPlan = elasticityProcess.getListOfResourceControlPlans().get(0);
        List<ResourceControlStrategy> listOfResourceControlStrategys = resourceControlPlan.getListOfResourceControlStrategies();
        
        for (ResourceControlStrategy rcs : listOfResourceControlStrategys){
            if (rcs.getPrimitiveAction().equals(serviceName)) {
                MetricCondition condition = rcs.getScaleInCondition();
                rs = condition.getUpperBound();
            }
        }
        
        return rs;
        
    }
    
    private double getScaleOutCondition(String serviceName){
        double rs =0;
        ResourceControlPlan resourceControlPlan = elasticityProcess.getListOfResourceControlPlans().get(0);
        List<ResourceControlStrategy> listOfResourceControlStrategys = resourceControlPlan.getListOfResourceControlStrategies();
        
        for (ResourceControlStrategy rcs : listOfResourceControlStrategys){
            if (rcs.getPrimitiveAction().equals(serviceName)) {
                MetricCondition condition = rcs.getScaleOutCondition();
                rs = condition.getLowerBound();
            }
        }
        
        return rs;
        
    }

}
