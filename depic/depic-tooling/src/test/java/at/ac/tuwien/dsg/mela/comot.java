///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package at.ac.tuwien.dsg.mela;
//
//import static at.ac.tuwien.dsg.comot.common.model.ArtifactTemplate.SingleScriptArtifact;
//import at.ac.tuwien.dsg.comot.common.model.Capability;
//import static at.ac.tuwien.dsg.comot.common.model.CapabilityEffect.CapabilityEffect;
//import static at.ac.tuwien.dsg.comot.common.model.MetricEffect.MetricEffect;
//import static at.ac.tuwien.dsg.comot.common.model.CommonOperatingSystemSpecification.OpenstackMicro;
//import static at.ac.tuwien.dsg.comot.common.model.CommonOperatingSystemSpecification.OpenstackSmall;
//import at.ac.tuwien.dsg.comot.common.model.Constraint;
//import at.ac.tuwien.dsg.comot.common.model.Constraint.Metric;
//import static at.ac.tuwien.dsg.comot.common.model.EntityRelationship.ConnectToRelation;
//import static at.ac.tuwien.dsg.comot.common.model.EntityRelationship.HostedOnRelation;
//import at.ac.tuwien.dsg.comot.common.model.OperatingSystemUnit;
//import static at.ac.tuwien.dsg.comot.common.model.OperatingSystemUnit.OperatingSystemUnit;
//import at.ac.tuwien.dsg.comot.common.model.Requirement;
//import at.ac.tuwien.dsg.comot.common.model.CloudService;
//import static at.ac.tuwien.dsg.comot.common.model.CloudService.ServiceTemplate;
//import static at.ac.tuwien.dsg.comot.common.model.CommonOperatingSystemSpecification.LocalDocker;
//import at.ac.tuwien.dsg.comot.common.model.ElasticityCapability;
//import at.ac.tuwien.dsg.comot.common.model.MetricEffect;
//import at.ac.tuwien.dsg.comot.common.model.ServiceTopology;
//import static at.ac.tuwien.dsg.comot.common.model.ServiceTopology.ServiceTopology;
//import at.ac.tuwien.dsg.comot.common.model.ServiceUnit;
//import static at.ac.tuwien.dsg.comot.common.model.SoftwareNode.SingleSoftwareUnit;
//import static at.ac.tuwien.dsg.comot.common.model.Strategy.Strategy;
//import at.ac.tuwien.dsg.comot.orchestrator.interraction.COMOTOrchestrator;
///**
// *
// * @author Jun
// */
//public class comot {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        // TODO code application logic here
//        
//        String salsaRepo = "http://128.130.172.215/salsa/upload/files/ElasticIoT/";
//        
//        
//        OperatingSystemUnit dataControllerVM = OperatingSystemUnit("DataControllerUnitVM")
//                .providedBy(OpenstackSmall()
//                        .addSoftwarePackage("openjdk-7-jre")
//                        .addSoftwarePackage("ganglia-monitor")
//                        .addSoftwarePackage("gmetad")
//                );
//        
//        ElasticityCapability dataNodeUnitScaleIn = ElasticityCapability.ScaleIn().withPrimitiveOperations("M2MDaaS.decommissionNode", "Salsa.scaleIn");
//        ElasticityCapability dataNodeUnitScaleOut = ElasticityCapability.ScaleOut().withPrimitiveOperations("Salsa.scaleOut");
//        
//        
//        dataControllerVM.constrainedBy(Constraint.MetricConstraint("DET_CO1", new Metric("cpuUsage", "%")).lessThan("80"));
//        
//        
//         dataNodeUnitScaleOut
//                .withCapabilityEffect(CapabilityEffect(dataControllerVM)
//                        .withMetricEffect(
//                                MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.SUB).withValue(30.0)));
//
//        dataNodeUnitScaleIn
//                .withCapabilityEffect(CapabilityEffect(dataControllerVM)
//                        .withMetricEffect(
//                                MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.ADD).withValue(30.0)));
//        
//        
//        
//        
//         ServiceUnit dataNodeUnit = SingleSoftwareUnit("DataNodeUnit")
//                .deployedBy(SingleScriptArtifact("deployDataNodeArtifact", salsaRepo + "deployCassandraNode.sh"))
//                //data node MUST KNOW the IP of cassandra seed, to connect to it and join data cluster
//                .requires(Requirement.Variable("DataController_IP_Data_Node_Req").withName("requiringDataNodeIP"))
//                .provides(dataNodeUnitScaleIn, dataNodeUnitScaleOut)
//                //express elasticity strategy: Scale IN Data Node when cpu usage < 40%
//                .controlledBy(Strategy("DN_ST1")
//                        .when(Constraint.MetricConstraint("DN_ST1_CO1", new Metric("cpuUsage", "%")).lessThan("40"))
//                        .enforce(dataNodeUnitScaleIn)
//                );
//        
//        ServiceTopology localProcessinTopology = ServiceTopology("Gateway")
//                .withServiceUnits(dataControllerVM, dataNodeUnit );
//        
//        
//       
//        
//         CloudService serviceTemplate = ServiceTemplate("edaas1")
//                .consistsOfTopologies(localProcessinTopology)
//                  .andRelationships(
//                   HostedOnRelation("loadbalancerToDocker")
//                        .from(dataNodeUnit)
//                        .to(dataControllerVM))
//                .withDefaultMetrics();
//        
//         
//         COMOTOrchestrator orchestrator = new COMOTOrchestrator("128.130.172.215");
//
//
//        //deploy, monitor and control
//        orchestrator.deployAndControl(serviceTemplate);
//        
//        
//    }
//    
//}
