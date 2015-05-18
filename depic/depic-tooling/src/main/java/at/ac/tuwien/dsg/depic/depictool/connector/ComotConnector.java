/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.connector;

import at.ac.tuwien.dsg.depic.common.entity.runtime.DeployAction;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ElasticService;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;

import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticProcess;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.MonitoringProcess;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depic.common.utils.YamlUtils;
import at.ac.tuwien.dsg.comot.common.model.ArtifactTemplate;
import static at.ac.tuwien.dsg.comot.common.model.ArtifactTemplate.SingleScriptArtifact;
import static at.ac.tuwien.dsg.comot.common.model.ArtifactTemplate.WarArtifact;
import static at.ac.tuwien.dsg.comot.common.model.BASHAction.BASHAction;
import static at.ac.tuwien.dsg.comot.common.model.CloudService.ServiceTemplate;
import static at.ac.tuwien.dsg.comot.common.model.CommonOperatingSystemSpecification.DockerDefault;
import static at.ac.tuwien.dsg.comot.common.model.DockerUnit.DockerUnit;
import static at.ac.tuwien.dsg.comot.common.model.EntityRelationship.ConnectToRelation;
import static at.ac.tuwien.dsg.comot.common.model.EntityRelationship.HostedOnRelation;
import static at.ac.tuwien.dsg.comot.common.model.ServiceTopology.ServiceTopology;
import static at.ac.tuwien.dsg.comot.common.model.SoftwareNode.SingleSoftwareUnit;
import static at.ac.tuwien.dsg.comot.common.model.Strategy.Strategy;
import at.ac.tuwien.dsg.comot.common.model.Capability;
import at.ac.tuwien.dsg.comot.common.model.CloudService;
import static at.ac.tuwien.dsg.comot.common.model.CommonOperatingSystemSpecification.LocalDocker;
import static at.ac.tuwien.dsg.comot.common.model.CommonOperatingSystemSpecification.OpenstackSmall;
import at.ac.tuwien.dsg.comot.common.model.Constraint;
import at.ac.tuwien.dsg.comot.common.model.Constraint.Metric;
import at.ac.tuwien.dsg.comot.common.model.DockerUnit;
import at.ac.tuwien.dsg.comot.common.model.ElasticityCapability;
import at.ac.tuwien.dsg.comot.common.model.EntityRelationship;
import at.ac.tuwien.dsg.comot.common.model.OperatingSystemUnit;
import static at.ac.tuwien.dsg.comot.common.model.OperatingSystemUnit.OperatingSystemUnit;
import at.ac.tuwien.dsg.comot.common.model.LifecyclePhase;
import at.ac.tuwien.dsg.comot.common.model.Requirement;
import at.ac.tuwien.dsg.comot.common.model.ServiceTopology;
import at.ac.tuwien.dsg.comot.common.model.ServiceUnit;
import at.ac.tuwien.dsg.comot.common.model.SoftwareNode;
import at.ac.tuwien.dsg.depic.depictool.repository.ElasticProcessRepositoryManager;
import at.ac.tuwien.dsg.depic.depictool.utils.Configuration;
import at.ac.tuwien.dsg.comot.orchestrator.interraction.COMOTOrchestrator;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.AdjustmentProcess;
import at.ac.tuwien.dsg.depic.common.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class ComotConnector {

    private String cloudServiceID;
    private String artifactRepo;
    private ElasticProcess elasticityProcess;
    private DeployAction eDaaSDeployAction;
    private List<DeployAction> monitoringServices;
    private List<DeployAction> controlServices;

    public ComotConnector() {
    }

    public ComotConnector(ElasticProcess elasticityProcesses, DeployAction eDaaSDeployAction) {
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

        ElasticProcessRepositoryManager epStore = new ElasticProcessRepositoryManager();

        MonitoringProcess monitorProcess = elasticityProcess.getMonitoringProcess();
        List<MonitoringAction> monitoringActions = monitorProcess.getListOfMonitoringActions();

        for (MonitoringAction ma : monitoringActions) {

            if (!isDeployActionExisting(monitoringServices, ma.getMonitoringActionName())) {
                DeployAction deployAction = epStore.getPrimitiveAction(ma.getMonitoringActionName());
                
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

        ElasticProcessRepositoryManager epStore = new ElasticProcessRepositoryManager();

        List<AdjustmentProcess> listOfAdjustmentProcesses = elasticityProcess.getListOfAdjustmentProcesses();

        for (AdjustmentProcess adjustmentProcess : listOfAdjustmentProcesses) {

            List<AdjustmentAction> controlActions = adjustmentProcess.getListOfAdjustmentActions();

            for (AdjustmentAction ca : controlActions) {

                if (!isDeployActionExisting(controlServices, ca.getActionName())) {
                    DeployAction deployAction = epStore.getPrimitiveAction(ca.getActionName());

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
//    
//    public String deployCloudSevices(){
//        
//        // monitoring_services_topology 
//        ServiceTopology monitoringServicesTopology = ServiceTopology("Monitoring_Services_Topology");
// 
//        // control_services_topology
//        ServiceTopology controlServicesTopology = ServiceTopology("Control_Services_Topology");
//    
//        
//        // edaas_topology
//        ServiceTopology edaasServiceTopology = ServiceTopology("eDaaS_Services_Topology");
//
//        
//        // add topology
//        CloudService cloudService = ServiceTemplate(cloudServiceID)
//                .consistsOfTopologies(monitoringServicesTopology)
//                .consistsOfTopologies(controlServicesTopology)
//                .consistsOfTopologies(edaasServiceTopology)
//                .withDefaultMetrics();
//        
//        
//        // add VM + monitoring service units
//        for (DeployAction dpa : monitoringServices) {
//
//            Logger.logInfo("DEPLOY: Monitoring Action: " +dpa.getActionID());
//   
//            OperatingSystemUnit virtualMachine = OperatingSystemUnit(dpa.getActionID() + "_VM")
//                    .providedBy(OpenstackSmall()
//                            .addSoftwarePackage("openjdk-7-jre")
//                            .addSoftwarePackage("ganglia-monitor")
//                            .addSoftwarePackage("gmetad")
//                    );
//
//
//            ServiceUnit serviceUnit = SingleSoftwareUnit(dpa.getActionID()+"_SU")
//                    .deployedBy(SingleScriptArtifact(dpa.getActionID()+"Artifact", artifactRepo + dpa.getActionID() + ".sh"));
//        /*
//            ServiceUnit serviceUnit = SoftwareNode.SingleWarUnit(dpa.getActionID()+"_SU")
//                    .deployedBy(WarArtifact(dpa.getActionID()+"Artifact", artifactRepo + dpa.getActionID() +".war"));
//                   // .deployedBy(WarArtifact(dpa.getActionID(), dpa.getArtifact()));
//  */
//            monitoringServicesTopology.addServiceUnit(virtualMachine);
//            monitoringServicesTopology.addServiceUnit(serviceUnit);
//            
//            
//            
//            cloudService.andRelationships(HostedOnRelation(serviceUnit.getId()+"To"+virtualMachine.getId())
//                        .from(serviceUnit)
//                        .to(virtualMachine));
//
//        }
//        
//        // add VM + control service units
//        for (DeployAction dpa : controlServices) {
//
//            Logger.logInfo("DEPLOY: Control Action: " + dpa.getActionID());
//
//            OperatingSystemUnit virtualMachine = OperatingSystemUnit(dpa.getActionID() + "_VM")
//                    .providedBy(OpenstackSmall()
//                            .addSoftwarePackage("openjdk-7-jre")
//                            .addSoftwarePackage("ganglia-monitor")
//                            .addSoftwarePackage("gmetad")
//                    );
//
//            ServiceUnit serviceUnit = SingleSoftwareUnit(dpa.getActionID() + "_SU")
//                    .deployedBy(SingleScriptArtifact(dpa.getActionID() + "Artifact", artifactRepo + dpa.getActionID() + ".sh"));
////            ServiceUnit serviceUnit = SoftwareNode.SingleWarUnit(dpa.getActionID()+"_SU")
////                    .deployedBy(WarArtifact(dpa.getActionID()+"Artifact", artifactRepo + dpa.getActionID() +".war"));
//
//            controlServicesTopology.addServiceUnit(virtualMachine);
//            controlServicesTopology.addServiceUnit(serviceUnit);
//
//            cloudService.andRelationships(HostedOnRelation(serviceUnit.getId() + "To" + virtualMachine.getId())
//                    .from(serviceUnit)
//                    .to(virtualMachine));
//
//        }
//
//        
//        
//        
//        // add VM + eDaaS
//        
//        OperatingSystemUnit vm_edaas = OperatingSystemUnit(eDaaSDeployAction.getActionID() + "_VM")
//                .providedBy(OpenstackSmall()
//                            .addSoftwarePackage("openjdk-7-jre")
//                            .addSoftwarePackage("ganglia-monitor")
//                            .addSoftwarePackage("gmetad")
//                    );
//
//        ServiceUnit serviceUnit_edaas = SingleSoftwareUnit(eDaaSDeployAction.getActionID()+"_SU")
//                .deployedBy(SingleScriptArtifact(eDaaSDeployAction.getActionID(), artifactRepo + "deployCassandraNode.sh"));
//        
//        edaasServiceTopology.addServiceUnit(serviceUnit_edaas);
//        edaasServiceTopology.addServiceUnit(vm_edaas);
//        
//        cloudService.andRelationships(HostedOnRelation(serviceUnit_edaas.getId() + "To"+vm_edaas.getId())
//                .from(serviceUnit_edaas)
//                .to(vm_edaas));
//
//        
//        
//        // deployment
//        
//        Configuration cfg = new Configuration();
//       
//      
//       
//        
//
//         COMOTOrchestrator orchestrator = new COMOTOrchestrator()
//                //we have SALSA as cloud management tool
//                //curently deployed separately
// 
//                .withSalsaIP(cfg.getConfig("SALSA.IP"))
//                .withSalsaPort(Integer.parseInt(cfg.getConfig("SALSA.PORT")))
//                .withRsyblIP(cfg.getConfig("RSYBL.IP"))
//                .withRsyblPort(Integer.parseInt(cfg.getConfig("RSYBL.PORT")));
//         
//        //deploy, monitor and control
//        orchestrator.deployAndControl(cloudService);
//        
//        
//        
//        
//        
//        return cloudService.getId();
//        
//        
//    }

    public void deployCloudSevices2() {

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
                // .withDefaultMetrics()
                .withMetricCompositonRulesFile(compositionRules);

        // add VM + monitoring service units
        OperatingSystemUnit monitoringVM = OperatingSystemUnit("MonitoringServices_VM")
                .providedBy(OpenstackSmall()
                        .addSoftwarePackage("tomcat7")
                        .addSoftwarePackage("ganglia-monitor")
                        .addSoftwarePackage("gmetad")
                );
        monitoringServicesTopology.addServiceUnit(monitoringVM);
        int i = 0;
        for (DeployAction dpa : monitoringServices) {

            ElasticityCapability eventProcessingUnitScaleIn = ElasticityCapability.ScaleIn().withPrimitiveOperations("scalein");
            ElasticityCapability eventProcessingUnitScaleOut = ElasticityCapability.ScaleOut().withPrimitiveOperations("scaleout");

            Logger.logInfo("DEPLOY: Monitoring Action: " + dpa.getActionID());
            ServiceUnit serviceUnit = SingleSoftwareUnit(dpa.getActionID() + "_SU")
                    .deployedBy(SingleScriptArtifact(dpa.getActionID() + "Artifact", artifactRepo + dpa.getActionID() + ".sh"))
                    .provides(eventProcessingUnitScaleIn, eventProcessingUnitScaleOut)
                    .controlledBy(
                            Strategy("EP_ST1_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO1_" + (i++), new Metric("cpuUsage", "%")).lessThan("6"))
                            .enforce(eventProcessingUnitScaleIn),
                            Strategy("EP_ST2_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO2_" + (i++), new Metric("cpuUsage", "%")).greaterThan("10"))
                            .enforce(eventProcessingUnitScaleOut))
                    
                    ;

            monitoringServicesTopology.addServiceUnit(serviceUnit);
            cloudService.andRelationships(HostedOnRelation(serviceUnit.getId() + "To" + monitoringVM.getId())
                    .from(serviceUnit)
                    .to(monitoringVM));

        }

        // add VM + control service units
        OperatingSystemUnit controlVM = OperatingSystemUnit("ControlServices_VM")
                .providedBy(OpenstackSmall()
                        .addSoftwarePackage("tomcat7")
                        .addSoftwarePackage("ganglia-monitor")
                        .addSoftwarePackage("gmetad")
                );
        controlServicesTopology.addServiceUnit(controlVM);

        for (DeployAction dpa : controlServices) {

            ElasticityCapability eventProcessingUnitScaleIn = ElasticityCapability.ScaleIn().withPrimitiveOperations("scalein");
            ElasticityCapability eventProcessingUnitScaleOut = ElasticityCapability.ScaleOut().withPrimitiveOperations("scaleout");

            Logger.logInfo("DEPLOY: Control Action: " + dpa.getActionID());
            ServiceUnit serviceUnit = SingleSoftwareUnit(dpa.getActionID() + "_SU")
                    .deployedBy(SingleScriptArtifact(dpa.getActionID() + "Artifact", artifactRepo + dpa.getActionID() + ".sh"))
                    .provides(eventProcessingUnitScaleIn, eventProcessingUnitScaleOut)
                    .controlledBy(
                            Strategy("EP_ST1_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO1_" + (i++), new Metric("cpuUsage", "%")).lessThan("6"))
                            .enforce(eventProcessingUnitScaleIn),
                            Strategy("EP_ST2_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO2_" + (i++), new Metric("cpuUsage", "%")).greaterThan("20"))
                            .enforce(eventProcessingUnitScaleOut))
                    
                    ;
            ;

            controlServicesTopology.addServiceUnit(serviceUnit);
            cloudService.andRelationships(HostedOnRelation(serviceUnit.getId() + "To" + controlVM.getId())
                    .from(serviceUnit)
                    .to(controlVM));

        }

        // add VM + eDaaS
//        OperatingSystemUnit vm_edaas = OperatingSystemUnit(eDaaSDeployAction.getActionID() + "_VM")
//                .providedBy(OpenstackSmall()
//                        .addSoftwarePackage("openjdk-7-jre")
//                        .addSoftwarePackage("ganglia-monitor")
//                        .addSoftwarePackage("gmetad")
//                );
//        ServiceUnit serviceUnit_edaas = SingleSoftwareUnit(eDaaSDeployAction.getActionID() + "_SU")
//                .deployedBy(SingleScriptArtifact(eDaaSDeployAction.getActionID(), eDaaSDeployAction.getArtifact()));
//
//        edaasServiceTopology.addServiceUnit(serviceUnit_edaas);
//        edaasServiceTopology.addServiceUnit(vm_edaas);
//
//        cloudService.andRelationships(HostedOnRelation(serviceUnit_edaas.getId() + "To" + vm_edaas.getId())
//                .from(serviceUnit_edaas)
//                .to(vm_edaas));
        // deployment
        COMOTOrchestrator orchestrator = new COMOTOrchestrator()
                //we have SALSA as cloud management tool
                //curently deployed separately

                .withSalsaIP(cfg.getConfig("SALSA.IP"))
                .withSalsaPort(Integer.parseInt(cfg.getConfig("SALSA.PORT")))
                .withRsyblIP(cfg.getConfig("RSYBL.IP"))
                .withRsyblPort(Integer.parseInt(cfg.getConfig("RSYBL.PORT")));

        //deploy, monitor and control
        orchestrator.deployAndControl(cloudService);

        //  orchestrator.controlExisting(cloudService);
        //  return cloudService.getId();
    }

    public void deployCloudSevices() {

        // monitoring_services_topology 
        ServiceTopology monitoringServicesTopology = ServiceTopology("Monitoring_Services_Topology");

        // control_services_topology
        ServiceTopology controlServicesTopology = ServiceTopology("Control_Services_Topology");

        // edaas_topology
        //      ServiceTopology edaasServiceTopology = ServiceTopology("eDaaS_Services_Topology");
        // add topology
        Configuration cfg = new Configuration();
        String compositionRules = cfg.getConfigPath() + "/compositionRules.xml";
        CloudService cloudService = ServiceTemplate(eDaaSDeployAction.getActionID() + "CloudService")
                .consistsOfTopologies(monitoringServicesTopology)
                .consistsOfTopologies(controlServicesTopology)
                //   .consistsOfTopologies(edaasServiceTopology)
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
                    .withMinInstances(0)
                    .provides(eventProcessingUnitScaleIn, eventProcessingUnitScaleOut)
                    .controlledBy(
                            Strategy("EP_ST1_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO1_" + (i++), new Metric("cpuUsage", "%")).lessThan("20"))
                            .enforce(eventProcessingUnitScaleIn),
                            Strategy("EP_ST2_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO2_" + (i++), new Metric("cpuUsage", "%")).greaterThan("50"))
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
                    .withMinInstances(0)
                    .provides(eventProcessingUnitScaleIn, eventProcessingUnitScaleOut)
                    .controlledBy(Strategy("EP_ST1_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO1_" + (i++), new Metric("cpuUsage", "%")).lessThan("20"))
                            .enforce(eventProcessingUnitScaleIn),
                            Strategy("EP_ST2_" + (i++))
                            .when(Constraint.MetricConstraint("EP_ST1_CO2_" + (i++), new Metric("cpuUsage", "%")).greaterThan("50"))
                            .enforce(eventProcessingUnitScaleOut));
            ;

            controlServicesTopology.addServiceUnit(serviceUnit);
            cloudService.andRelationships(HostedOnRelation(serviceUnit.getId() + "To" + controlVM.getId())
                    .from(serviceUnit)
                    .to(controlVM));
        
        }

        // add VM + eDaaS
//        OperatingSystemUnit vm_edaas = OperatingSystemUnit(eDaaSDeployAction.getActionID() + "_VM")
//                .providedBy(OpenstackSmall()
//                        .addSoftwarePackage("openjdk-7-jre")
//                        .addSoftwarePackage("ganglia-monitor")
//                        .addSoftwarePackage("gmetad")
//                );
//        ServiceUnit serviceUnit_edaas = SingleSoftwareUnit(eDaaSDeployAction.getActionID() + "_SU")
//                .deployedBy(SingleScriptArtifact(eDaaSDeployAction.getActionID(), eDaaSDeployAction.getArtifact()));
//
//        edaasServiceTopology.addServiceUnit(serviceUnit_edaas);
//        edaasServiceTopology.addServiceUnit(vm_edaas);
//
//        cloudService.andRelationships(HostedOnRelation(serviceUnit_edaas.getId() + "To" + vm_edaas.getId())
//                .from(serviceUnit_edaas)
//                .to(vm_edaas));
        // deployment
        COMOTOrchestrator orchestrator = new COMOTOrchestrator()
                //we have SALSA as cloud management tool
                //curently deployed separately

                .withSalsaIP(cfg.getConfig("SALSA.IP"))
                .withSalsaPort(Integer.parseInt(cfg.getConfig("SALSA.PORT")))
                .withRsyblIP(cfg.getConfig("RSYBL.IP"))
                .withRsyblPort(Integer.parseInt(cfg.getConfig("RSYBL.PORT")));

        //deploy, monitor and control
        //orchestrator.deployAndControl(cloudService);
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

}
