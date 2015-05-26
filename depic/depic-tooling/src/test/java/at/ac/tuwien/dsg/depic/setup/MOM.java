package at.ac.tuwien.dsg.depic.setup;

import static at.ac.tuwien.dsg.comot.common.model.ArtifactTemplate.SingleScriptArtifact;

import static at.ac.tuwien.dsg.comot.common.model.CloudService.ServiceTemplate;

import static at.ac.tuwien.dsg.comot.common.model.EntityRelationship.HostedOnRelation;
import static at.ac.tuwien.dsg.comot.common.model.ServiceTopology.ServiceTopology;
import static at.ac.tuwien.dsg.comot.common.model.SoftwareNode.SingleSoftwareUnit;

import at.ac.tuwien.dsg.comot.common.model.CloudService;

import static at.ac.tuwien.dsg.comot.common.model.CommonOperatingSystemSpecification.OpenstackSmall;

import at.ac.tuwien.dsg.comot.common.model.OperatingSystemUnit;
import static at.ac.tuwien.dsg.comot.common.model.OperatingSystemUnit.OperatingSystemUnit;

import at.ac.tuwien.dsg.comot.common.model.ServiceTopology;
import at.ac.tuwien.dsg.comot.common.model.ServiceUnit;

import at.ac.tuwien.dsg.comot.orchestrator.interraction.COMOTOrchestrator;

/**
 *
 * @author http://dsg.tuwien.ac.at
 */
public class MOM {

    public static void main(String[] args) {
        //specify service units in terms of software

        String salsaRepo = "http://128.130.172.215/salsa/upload/files/DaaS_Cost/";

        OperatingSystemUnit momVM = OperatingSystemUnit("MoMVM")
                .providedBy(OpenstackSmall()
                        .withBaseImage("a82e054f-4f01-49f9-bc4c-77a98045739c")
                        .addSoftwarePackage("openjdk-7-jre")
                        .addSoftwarePackage("ganglia-monitor")
                        .addSoftwarePackage("gmetad")
                );

        //add the service units belonging to the event processing topology
        ServiceUnit momUnit = SingleSoftwareUnit("MOMUnit")

                .deployedBy(SingleScriptArtifact("deployMOMArtifact", salsaRepo + "deployQueue.sh"));

        //define event processing unit topology
        ServiceTopology eventProcessingTopology = ServiceTopology("EventProcessingTopology")
                .withServiceUnits(momUnit, momVM);

        //describe the service template which will hold more topologies
        CloudService serviceTemplate = ServiceTemplate("MOM-Depic")
                .consistsOfTopologies(eventProcessingTopology)
                .andRelationships(
                        HostedOnRelation("momToVM")
                        .from(momUnit)
                        .to(momVM)
                )
                // as we have horizontally scalable distributed systems (one service unit can have more instances)
                //metrics must be aggregated among VMs
                .withDefaultMetrics();

        COMOTOrchestrator orchestrator = new COMOTOrchestrator()

                .withSalsaIP("128.130.172.215")
                .withSalsaPort(8380)
                //ifwe have rSYBL elasticity control service and MELA 
                //deployed separately
                .withRsyblIP("128.130.172.215")
                .withRsyblPort(8280);
        
        orchestrator.deploy(serviceTemplate);
        //orchestrator.deployAndControl(serviceTemplate);
    }
}
