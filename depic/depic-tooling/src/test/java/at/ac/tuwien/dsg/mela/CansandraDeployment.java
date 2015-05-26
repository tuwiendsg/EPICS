///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package at.ac.tuwien.dsg.mela;
//
///**
// *
// * @author Jun
// */
//
//
//import static at.ac.tuwien.dsg.comot.common.model.ArtifactTemplate.SingleScriptArtifact;
//import static at.ac.tuwien.dsg.comot.common.model.BASHAction.BASHAction;
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
//import at.ac.tuwien.dsg.comot.common.model.ElasticityCapability;
//import at.ac.tuwien.dsg.comot.common.model.LifecyclePhase;
//import at.ac.tuwien.dsg.comot.common.model.MetricEffect;
//import at.ac.tuwien.dsg.comot.common.model.ServiceTopology;
//import static at.ac.tuwien.dsg.comot.common.model.ServiceTopology.ServiceTopology;
//import at.ac.tuwien.dsg.comot.common.model.ServiceUnit;
//import static at.ac.tuwien.dsg.comot.common.model.SoftwareNode.SingleSoftwareUnit;
//import static at.ac.tuwien.dsg.comot.common.model.Strategy.Strategy;
//import at.ac.tuwien.dsg.comot.orchestrator.interraction.COMOTOrchestrator;
//
///**
// *
// * @author http://dsg.tuwien.ac.at
// */
//public class CansandraDeployment {
//
//    public static void main(String[] args) {
//        //specify service units in terms of software
//        
//        
//        String cassandraClusterName = "EDARepository";
//        //String cassandraClusterName = "DataProvider";
//
//        createOneDataNode(cassandraClusterName);
//        //createTwoDataNode(cassandraClusterName);
//    }
//    
//    private static void createOneDataNode(String cloudServiceName){
//        
//        
//       // String salsaRepo = "http://128.130.172.215/salsa/upload/files/ElasticIoT/";
//        String salsaRepo = "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/";
//
//        //need to specify details of VM and operating system to deploy the software servide units on
//        OperatingSystemUnit dataControllerVM = OperatingSystemUnit("DataControllerUnitVM")
//                .providedBy(OpenstackSmall()
//                        .addSoftwarePackage("openjdk-7-jre")
//                        .addSoftwarePackage("ganglia-monitor")
//                        .addSoftwarePackage("gmetad")
//                );
//
//        OperatingSystemUnit dataNodeVM = OperatingSystemUnit("DataNodeUnitVM")
//                .providedBy(OpenstackSmall()
//                        .addSoftwarePackage("openjdk-7-jre")
//                        .addSoftwarePackage("ganglia-monitor")
//                        .addSoftwarePackage("gmetad")
//                );
//
//        
//        //start with Data End, and first with Data Controller
//        ServiceUnit dataControllerUnit = SingleSoftwareUnit("DataControllerUnit")
//                //software artifacts needed for unit deployment   = script to deploy Cassandra
//                .deployedBy(SingleScriptArtifact("deployDataControllerArtifact", salsaRepo + "deployCassandraSeed.sh"))
//                //data controller exposed its IP 
//                .exposes(Capability.Variable("DataController_IP_information"));
//
//        ElasticityCapability dataNodeUnitScaleIn = ElasticityCapability.ScaleIn().withPrimitiveOperations("M2MDaaS.decommissionNode", "Salsa.scaleIn");
//        ElasticityCapability dataNodeUnitScaleOut = ElasticityCapability.ScaleOut().withPrimitiveOperations("Salsa.scaleOut");
//
//        //specify data node
//        ServiceUnit dataNodeUnit = SingleSoftwareUnit("DataNodeUnit")
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
//      
//        //Describe a Data End service topology containing the previous 2 software service units
//        ServiceTopology dataEndTopology = ServiceTopology("DataEndTopology")
//                .withServiceUnits(dataControllerUnit, dataNodeUnit //add also OS units to topology
//                        , dataControllerVM, dataNodeVM
//                );
//
//        //specify constraints on the data topology
//        //thus, the CPU usage of all Service Unit instances of the data end Topology must be below 80%
//        dataEndTopology.constrainedBy(Constraint.MetricConstraint("DET_CO1", new Metric("cpuUsage", "%")).lessThan("80"));
//
//       
//        // elasticity capabilities
//        dataNodeUnitScaleOut.withCapabilityEffect(CapabilityEffect(dataNodeUnit)
//                .withMetricEffect(
//                        MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.SUB).withValue(30.0))
//                .withMetricEffect(
//                        MetricEffect().withMetric(new Metric("cost", "$")).withType(MetricEffect.Type.ADD).withValue(0.12)))
//                .withCapabilityEffect(CapabilityEffect(dataControllerUnit)
//                        .withMetricEffect(
//                                MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.SUB).withValue(30.0)))
//                .withCapabilityEffect(CapabilityEffect(dataEndTopology)
//                        .withMetricEffect(
//                                MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.SUB).withValue(30.0)));
//
//        dataNodeUnitScaleIn.withCapabilityEffect(CapabilityEffect(dataNodeUnit)
//                .withMetricEffect(
//                        MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.ADD).withValue(30.0))
//                .withMetricEffect(
//                        MetricEffect().withMetric(new Metric("cost", "$")).withType(MetricEffect.Type.SUB).withValue(0.12)))
//                .withCapabilityEffect(CapabilityEffect(dataControllerUnit)
//                        .withMetricEffect(
//                                MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.ADD).withValue(30.0)))
//                .withCapabilityEffect(CapabilityEffect(dataEndTopology)
//                        .withMetricEffect(
//                                MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.ADD).withValue(30.0)));
//
//       
//        //describe the service template which will hold more topologies
//        CloudService serviceTemplate = ServiceTemplate(cloudServiceName)
//                .consistsOfTopologies(dataEndTopology)
//
//                //defining CONNECT_TO and HOSTED_ON relationships
//                .andRelationships(
//                        //Data Controller IP send to Data Node
//                        ConnectToRelation("dataNodeToDataController")
//                        .from(dataControllerUnit.getContext().get("DataController_IP_information"))
//                        .to(dataNodeUnit.getContext().get("DataController_IP_Data_Node_Req")) //specify which software unit goes to which VM
//                        ,
//     
//                        HostedOnRelation("dataControllerToVM")
//                        .from(dataControllerUnit)
//                        .to(dataControllerVM),
//                        HostedOnRelation("dataNodeToVM")
//                        .from(dataNodeUnit)
//                        .to(dataNodeVM) //add hosted on relatinos
//                        
//                )
//                // as we have horizontally scalable distributed systems (one service unit can have more instances)
//                //metrics must be aggregated among VMs
//                .withDefaultMetrics();
//                //to find scaling actions, one must assume some effects for each action, to understand
//        //if it makes sense or not to execute the action
////                .withDefaultActionEffects();
//
//        //instantiate COMOT orchestrator to deploy, monitor and control the service
//        COMOTOrchestrator orchestrator = new COMOTOrchestrator()
//                //we have SALSA as cloud management tool
//                //curently deployed separately
//                .withSalsaIP("128.130.172.215")
//                .withSalsaPort(8380)
//                .withRsyblIP("128.130.172.215")
//                .withRsyblPort(8280);
//        
//
//        orchestrator.deployAndControl(serviceTemplate);
//        
//    }
//    
//    private static void createTwoDataNode(String cloudServiceName){
//        
//       // String salsaRepo = "http://128.130.172.215/salsa/upload/files/ElasticIoT/";
//        String salsaRepo = "http://128.130.172.215/salsa/upload/files/jun/artifact_sh/";
//
//        //need to specify details of VM and operating system to deploy the software servide units on
//        OperatingSystemUnit dataControllerVM = OperatingSystemUnit("DataControllerUnitVM")
//                .providedBy(OpenstackSmall()
//                        .addSoftwarePackage("openjdk-7-jre")
//                        .addSoftwarePackage("ganglia-monitor")
//                        .addSoftwarePackage("gmetad")
//                );
//
//        OperatingSystemUnit dataNodeVM = OperatingSystemUnit("DataNodeUnitVM")
//                .providedBy(OpenstackMicro()
//                        .addSoftwarePackage("openjdk-7-jre")
//                        .addSoftwarePackage("ganglia-monitor")
//                        .addSoftwarePackage("gmetad")
//                );
//        
//        OperatingSystemUnit dataNodeVM2 = OperatingSystemUnit("DataNodeUnitVM2")
//                .providedBy(OpenstackMicro()
//                        .addSoftwarePackage("openjdk-7-jre")
//                        .addSoftwarePackage("ganglia-monitor")
//                        .addSoftwarePackage("gmetad")
//                );
//
//        
//        //start with Data End, and first with Data Controller
//        ServiceUnit dataControllerUnit = SingleSoftwareUnit("DataControllerUnit")
//                //software artifacts needed for unit deployment   = script to deploy Cassandra
//                .deployedBy(SingleScriptArtifact("deployDataControllerArtifact", salsaRepo + "deployCassandraSeed.sh"))
//                //data controller exposed its IP 
//                .exposes(Capability.Variable("DataController_IP_information"));
//
//        ElasticityCapability dataNodeUnitScaleIn = ElasticityCapability.ScaleIn().withPrimitiveOperations("M2MDaaS.decommissionNode", "Salsa.scaleIn");
//        ElasticityCapability dataNodeUnitScaleOut = ElasticityCapability.ScaleOut().withPrimitiveOperations("Salsa.scaleOut");
//
//        //specify data node
//        ServiceUnit dataNodeUnit = SingleSoftwareUnit("DataNodeUnit")
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
//        
//        //specify data node
//        ServiceUnit dataNodeUnit2 = SingleSoftwareUnit("DataNodeUnit2")
//                .deployedBy(SingleScriptArtifact("deployDataNodeArtifact", salsaRepo + "deployCassandraNode.sh"))
//                //data node MUST KNOW the IP of cassandra seed, to connect to it and join data cluster
//                .requires(Requirement.Variable("DataController_IP_Data_Node_Req").withName("requiringDataNodeIP"))
//                .provides(dataNodeUnitScaleIn, dataNodeUnitScaleOut)
//                //express elasticity strategy: Scale IN Data Node when cpu usage < 40%
//                .controlledBy(Strategy("DN_ST2")
//                        .when(Constraint.MetricConstraint("DN_ST2_CO1", new Metric("cpuUsage", "%")).lessThan("40"))
//                        .enforce(dataNodeUnitScaleIn)
//                );
//
//      
//        //Describe a Data End service topology containing the previous 2 software service units
//        ServiceTopology dataEndTopology = ServiceTopology("DataEndTopology")
//                .withServiceUnits(dataControllerUnit, dataNodeUnit //add also OS units to topology
//                        , dataControllerVM, dataNodeVM, dataNodeUnit2, dataNodeVM2
//                );
//
//        //specify constraints on the data topology
//        //thus, the CPU usage of all Service Unit instances of the data end Topology must be below 80%
//        dataEndTopology.constrainedBy(Constraint.MetricConstraint("DET_CO1", new Metric("cpuUsage", "%")).lessThan("80"));
//
//       
//        // elasticity capabilities
//        dataNodeUnitScaleOut.withCapabilityEffect(CapabilityEffect(dataNodeUnit)
//                .withMetricEffect(
//                        MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.SUB).withValue(30.0))
//                .withMetricEffect(
//                        MetricEffect().withMetric(new Metric("cost", "$")).withType(MetricEffect.Type.ADD).withValue(0.12)))
//                .withCapabilityEffect(CapabilityEffect(dataControllerUnit)
//                        .withMetricEffect(
//                                MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.SUB).withValue(30.0)))
//                .withCapabilityEffect(CapabilityEffect(dataEndTopology)
//                        .withMetricEffect(
//                                MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.SUB).withValue(30.0)));
//
//        dataNodeUnitScaleIn.withCapabilityEffect(CapabilityEffect(dataNodeUnit)
//                .withMetricEffect(
//                        MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.ADD).withValue(30.0))
//                .withMetricEffect(
//                        MetricEffect().withMetric(new Metric("cost", "$")).withType(MetricEffect.Type.SUB).withValue(0.12)))
//                .withCapabilityEffect(CapabilityEffect(dataControllerUnit)
//                        .withMetricEffect(
//                                MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.ADD).withValue(30.0)))
//                .withCapabilityEffect(CapabilityEffect(dataEndTopology)
//                        .withMetricEffect(
//                                MetricEffect().withMetric(new Metric("cpuUsage", "%")).withType(MetricEffect.Type.ADD).withValue(30.0)));
//
//       
//        //describe the service template which will hold more topologies
//        CloudService serviceTemplate = ServiceTemplate(cloudServiceName)
//                .consistsOfTopologies(dataEndTopology)
//
//                //defining CONNECT_TO and HOSTED_ON relationships
//                .andRelationships(
//                        //Data Controller IP send to Data Node
//                        ConnectToRelation("dataNodeToDataController")
//                        .from(dataControllerUnit.getContext().get("DataController_IP_information"))
//                        .to(dataNodeUnit.getContext().get("DataController_IP_Data_Node_Req")) //specify which software unit goes to which VM
//                        ,
//                        ConnectToRelation("dataNode2ToDataController")
//                        .from(dataControllerUnit.getContext().get("DataController_IP_information"))
//                        .to(dataNodeUnit2.getContext().get("DataController_IP_Data_Node_Req")) //specify which software unit goes to which VM
//                        ,
//                        
//     
//                        HostedOnRelation("dataControllerToVM")
//                        .from(dataControllerUnit)
//                        .to(dataControllerVM),
//                        HostedOnRelation("dataNode2ToVM")
//                        .from(dataNodeUnit2)
//                        .to(dataNodeVM2),
//                        HostedOnRelation("dataNodeToVM")
//                        .from(dataNodeUnit)
//                        .to(dataNodeVM) //add hosted on relatinos
//                        
//                )
//                // as we have horizontally scalable distributed systems (one service unit can have more instances)
//                //metrics must be aggregated among VMs
//                .withDefaultMetrics();
//                //to find scaling actions, one must assume some effects for each action, to understand
//        //if it makes sense or not to execute the action
////                .withDefaultActionEffects();
//
//        //instantiate COMOT orchestrator to deploy, monitor and control the service
//        COMOTOrchestrator orchestrator = new COMOTOrchestrator()
//                //we have SALSA as cloud management tool
//                //curently deployed separately
//                .withSalsaIP("128.130.172.215")
//                .withSalsaPort(8380)
//                .withRsyblIP("128.130.172.215")
//                .withRsyblPort(8280);
//        
//
//        orchestrator.deployAndControl(serviceTemplate);
//    }
//}
