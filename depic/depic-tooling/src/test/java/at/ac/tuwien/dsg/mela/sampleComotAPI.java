/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.mela;

/**
 *
 * @author Jun
 */


import static at.ac.tuwien.dsg.comot.common.model.ArtifactTemplate.SingleScriptArtifact;
import static at.ac.tuwien.dsg.comot.common.model.BASHAction.BASHAction;
import at.ac.tuwien.dsg.comot.common.model.Capability;
import static at.ac.tuwien.dsg.comot.common.model.CapabilityEffect.CapabilityEffect;
import static at.ac.tuwien.dsg.comot.common.model.MetricEffect.MetricEffect;
import static at.ac.tuwien.dsg.comot.common.model.CommonOperatingSystemSpecification.OpenstackMicro;
import static at.ac.tuwien.dsg.comot.common.model.CommonOperatingSystemSpecification.OpenstackSmall;
import at.ac.tuwien.dsg.comot.common.model.Constraint;
import at.ac.tuwien.dsg.comot.common.model.Constraint.Metric;
import static at.ac.tuwien.dsg.comot.common.model.EntityRelationship.ConnectToRelation;
import static at.ac.tuwien.dsg.comot.common.model.EntityRelationship.HostedOnRelation;
import at.ac.tuwien.dsg.comot.common.model.OperatingSystemUnit;
import static at.ac.tuwien.dsg.comot.common.model.OperatingSystemUnit.OperatingSystemUnit;
import at.ac.tuwien.dsg.comot.common.model.Requirement;
import at.ac.tuwien.dsg.comot.common.model.CloudService;
import static at.ac.tuwien.dsg.comot.common.model.CloudService.ServiceTemplate;
import at.ac.tuwien.dsg.comot.common.model.ElasticityCapability;
import at.ac.tuwien.dsg.comot.common.model.LifecyclePhase;
import at.ac.tuwien.dsg.comot.common.model.MetricEffect;
import at.ac.tuwien.dsg.comot.common.model.ServiceTopology;
import static at.ac.tuwien.dsg.comot.common.model.ServiceTopology.ServiceTopology;
import at.ac.tuwien.dsg.comot.common.model.ServiceUnit;
import static at.ac.tuwien.dsg.comot.common.model.SoftwareNode.SingleSoftwareUnit;
import static at.ac.tuwien.dsg.comot.common.model.Strategy.Strategy;
import at.ac.tuwien.dsg.comot.orchestrator.interraction.COMOTOrchestrator;

public class sampleComotAPI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        String salsaRepo = "http://128.130.172.215/salsa/upload/files/ElasticIoT/";
        
        
        ///////////////////////////////////////
        // MONITORING SERIVCES TOPOLOGY
        ///////////////////////////////////////

        //need to specify details of VM and operating system to deploy the software servide units on
        OperatingSystemUnit dataCompletenessMeasurement_VM = OperatingSystemUnit("dataCompletenessMeasurement_VM")
                .providedBy(OpenstackSmall()
                        .addSoftwarePackage("openjdk-7-jre")
                        .addSoftwarePackage("ganglia-monitor")
                        .addSoftwarePackage("gmetad")
                );
        
        OperatingSystemUnit dataAccuracyMeasurement_VM = OperatingSystemUnit("dataAccuracyMeasurement_VM")
                .providedBy(OpenstackSmall()
                        .addSoftwarePackage("openjdk-7-jre")
                        .addSoftwarePackage("ganglia-monitor")
                        .addSoftwarePackage("gmetad")
                );
        
        
        OperatingSystemUnit throughputMeasurement_VM = OperatingSystemUnit("throughputMeasurement_VM")
                .providedBy(OpenstackSmall()
                        .addSoftwarePackage("openjdk-7-jre")
                        .addSoftwarePackage("ganglia-monitor")
                        .addSoftwarePackage("gmetad")
                );


        //start with Data End, and first with Data Controller
        ServiceUnit dataCompletenessMeasurement_SU = SingleSoftwareUnit("dataCompleteness_SU")
                .deployedBy(SingleScriptArtifact("deployDataControllerArtifact", salsaRepo + "deployCassandraSeed.sh"));
        
        ServiceUnit dataAccuracyMeasurement_SU = SingleSoftwareUnit("dataAccuracyMeasurement_SU")
                .deployedBy(SingleScriptArtifact("deployDataControllerArtifact", salsaRepo + "deployCassandraSeed.sh"));
        
        ServiceUnit throughputMeasurement_SU = SingleSoftwareUnit("throughputMeasurement_SU")
                .deployedBy(SingleScriptArtifact("deployDataControllerArtifact", salsaRepo + "deployCassandraSeed.sh"));


       

        //Describe a Data End service topology containing the previous 2 software service units
        ServiceTopology monitoringServiceTopology = ServiceTopology("MonitoringServicesTopology")
                .withServiceUnits(dataCompletenessMeasurement_VM, dataAccuracyMeasurement_VM, throughputMeasurement_VM //add also OS units to topology
                        , dataCompletenessMeasurement_SU, dataAccuracyMeasurement_SU, throughputMeasurement_SU
                );

        //specify constraints on the data topology
        //thus, the CPU usage of all Service Unit instances of the data end Topology must be below 80%
        monitoringServiceTopology.constrainedBy(Constraint.MetricConstraint("DET_CO1", new Metric("cpuUsage", "%")).lessThan("80"));

       

        
        
        ///////////////////////////////////////
        // CONTROL SERVICES TOPOLOGY
        ///////////////////////////////////////
        
        OperatingSystemUnit LSR_VM = OperatingSystemUnit("LSR_VM")
                .providedBy(OpenstackSmall()
                        .addSoftwarePackage("openjdk-7-jre")
                        .addSoftwarePackage("ganglia-monitor")
                        .addSoftwarePackage("gmetad")
                );
        
        OperatingSystemUnit MLR_VM = OperatingSystemUnit("MLR_VM")
                .providedBy(OpenstackSmall()
                        .addSoftwarePackage("openjdk-7-jre")
                        .addSoftwarePackage("ganglia-monitor")
                        .addSoftwarePackage("gmetad")
                );
        
        
        OperatingSystemUnit DIR_VM = OperatingSystemUnit("DIR_VM")
                .providedBy(OpenstackSmall()
                        .addSoftwarePackage("openjdk-7-jre")
                        .addSoftwarePackage("ganglia-monitor")
                        .addSoftwarePackage("gmetad")
                );
        
        OperatingSystemUnit STC_VM = OperatingSystemUnit("STC_VM")
                .providedBy(OpenstackSmall()
                        .addSoftwarePackage("openjdk-7-jre")
                        .addSoftwarePackage("ganglia-monitor")
                        .addSoftwarePackage("gmetad")
                );


        //start with Data End, and first with Data Controller
        ServiceUnit LSR_SU = SingleSoftwareUnit("LSR_SU")
                .deployedBy(SingleScriptArtifact("deployDataControllerArtifact", salsaRepo + "deployCassandraSeed.sh"));
        
        ServiceUnit MLR_SU = SingleSoftwareUnit("MLR_SU")
                .deployedBy(SingleScriptArtifact("deployDataControllerArtifact", salsaRepo + "deployCassandraSeed.sh"));
        
        ServiceUnit DIR_SU = SingleSoftwareUnit("DIR_SU")
                .deployedBy(SingleScriptArtifact("deployDataControllerArtifact", salsaRepo + "deployCassandraSeed.sh"));
        
        ServiceUnit STC_SU = SingleSoftwareUnit("STC_SU")
                .deployedBy(SingleScriptArtifact("deployDataControllerArtifact", salsaRepo + "deployCassandraSeed.sh"));


       

        //Describe a Data End service topology containing the previous 2 software service units
        ServiceTopology controlServiceTopology = ServiceTopology("ControlServicesTopology")
                .withServiceUnits(LSR_VM, MLR_VM, DIR_VM, STC_VM //add also OS units to topology
                        , LSR_SU, MLR_SU, DIR_SU, STC_SU
                );

        //specify constraints on the data topology
        //thus, the CPU usage of all Service Unit instances of the data end Topology must be below 80%
        controlServiceTopology.constrainedBy(Constraint.MetricConstraint("DET_CO2", new Metric("cpuUsage", "%")).lessThan("80"));
        
        
        ///////////////////////////////////////
        // EDAAS TOPOLOGY
        ///////////////////////////////////////

        OperatingSystemUnit edaas_VM = OperatingSystemUnit("edaas_VM")
                .providedBy(OpenstackSmall()
                        .addSoftwarePackage("openjdk-7-jre")
                        .addSoftwarePackage("ganglia-monitor")
                        .addSoftwarePackage("gmetad")
                );
        
        ServiceUnit edaas_SU = SingleSoftwareUnit("edaas_SU")
                .deployedBy(SingleScriptArtifact("deployDataControllerArtifact", salsaRepo + "deployCassandraSeed.sh"));
        
        
        //Describe a Data End service topology containing the previous 2 software service units
        ServiceTopology edaasTopology = ServiceTopology("edaasTopology")
                .withServiceUnits(edaas_VM //add also OS units to topology
                        , edaas_SU
                );

        //specify constraints on the data topology
        //thus, the CPU usage of all Service Unit instances of the data end Topology must be below 80%
        edaasTopology.constrainedBy(Constraint.MetricConstraint("DET_CO3", new Metric("cpuUsage", "%")).lessThan("80"));
        
        //describe the service template which will hold more topologies
        CloudService serviceTemplate = ServiceTemplate("edaas")
                .consistsOfTopologies(monitoringServiceTopology)
                .consistsOfTopologies(controlServiceTopology)
                .consistsOfTopologies(edaasTopology)
                //defining CONNECT_TO and HOSTED_ON relationships
                .andRelationships(
                        //Data Controller IP send to Data Node
                        HostedOnRelation("dataCompletenessMeasurement_SUTodataCompletenessMeasurement_VM")
                        .from(dataCompletenessMeasurement_SU)
                        .to(dataCompletenessMeasurement_VM),
                        
                        HostedOnRelation("dataAccuracyMeasurement_SUTodataAccuracyMeasurement_VM")
                        .from(dataAccuracyMeasurement_SU)
                        .to(dataAccuracyMeasurement_VM),
                        
                        HostedOnRelation("throughputMeasurement_SUTothroughputMeasurement_VM")
                        .from(throughputMeasurement_SU)
                        .to(throughputMeasurement_VM),
                        
                        HostedOnRelation("LSR_SUToLSR_VM")
                        .from(LSR_SU)
                        .to(LSR_VM),
                        
                        HostedOnRelation("MLR_SUToMLR_VM")
                        .from(MLR_SU)
                        .to(MLR_VM),
                        
                        HostedOnRelation("DIR_SUToDIR_VM")
                        .from(DIR_SU)
                        .to(DIR_VM),
                        
                        HostedOnRelation("STC_SUToLSR_VM")
                        .from(STC_SU)
                        .to(STC_VM),
                        
                        HostedOnRelation("edaas_SUToedaas_VM")
                        .from(edaas_SU)
                        .to(edaas_VM)
                                
                                
                )
                // as we have horizontally scalable distributed systems (one service unit can have more instances)
                //metrics must be aggregated among VMs
                .withDefaultMetrics();
                //to find scaling actions, one must assume some effects for each action, to understand
        //if it makes sense or not to execute the action
//                .withDefaultActionEffects();

        //instantiate COMOT orchestrator to deploy, monitor and control the service
        COMOTOrchestrator orchestrator = new COMOTOrchestrator()
                //we have SALSA as cloud management tool
                //curently deployed separately
 
                .withSalsaIP("128.130.172.215")
                .withSalsaPort(8380);
 
                //we have rSYBL elasticity control service and MELA 
                //deployed separately
                //                .withRsyblIP("localhost")
                //                .withRsyblIP("localhost")
                //                                .withRsyblIP("localhost")
                //.withRsyblIP("localhost")
                //                .withRsyblIP("localhost")
                //.withRsyblPort(8080);
//                .withRsyblPort(8080);

        //deploy, monitor and control
//        orchestrator.deployAndControl(serviceTemplate);
//        orchestrator.deploy(serviceTemplate);
        orchestrator.deployAndControl(serviceTemplate);
    }
    
}
