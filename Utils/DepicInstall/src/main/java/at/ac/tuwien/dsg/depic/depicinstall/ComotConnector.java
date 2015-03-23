/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depicinstall;

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
import at.ac.tuwien.dsg.comot.orchestrator.interraction.COMOTOrchestrator;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class ComotConnector {

    private String artifactRepo;
    private String salsaIP;
    private int salsaPort;
    private String rsyblIP;
    private int rsyblPort;

    public ComotConnector() {
        config();
    }

    private void config() {

        artifactRepo = "http://128.130.172.215/salsa/upload/files/jun/depic_setup/";
        salsaIP ="128.130.172.215";
        salsaPort=8380;
        rsyblIP ="128.130.172.215";
        rsyblPort=8280;
       
    }

    public void setupDepic() {

        
        ServiceTopology depicServicesTopology = ServiceTopology("Depic_Services_Topology");

        CloudService cloudService = ServiceTemplate("DepicCloudService")
                .consistsOfTopologies(depicServicesTopology);

        OperatingSystemUnit depicVM = OperatingSystemUnit("Depic_VM")
                .providedBy(OpenstackSmall()
                        .addSoftwarePackage("tomcat7")
                        .addSoftwarePackage("ganglia-monitor")
                        .addSoftwarePackage("gmetad")
                );
        
        depicServicesTopology.addServiceUnit(depicVM);
        
        ServiceUnit depicTooling_SU = SingleSoftwareUnit("DepicTooling_SU")
                .deployedBy(SingleScriptArtifact("DepicTooling_Artifact", artifactRepo + "DepicTooling_Artifact.sh"));
        depicServicesTopology.addServiceUnit(depicTooling_SU); 
        cloudService.andRelationships(HostedOnRelation(depicTooling_SU.getId() + "_To_" + depicVM.getId())
                .from(depicTooling_SU)
                .to(depicVM));
        
        ServiceUnit orchestrator_SU = SingleSoftwareUnit("Orchestrator_SU")
                .deployedBy(SingleScriptArtifact("Orchestrator_Artifact", artifactRepo + "Orchestrator_Artifact.sh"));
        depicServicesTopology.addServiceUnit(orchestrator_SU);       
        cloudService.andRelationships(HostedOnRelation(orchestrator_SU.getId() + "_To_" + depicVM.getId())
                .from(orchestrator_SU)
                .to(depicVM));
        
        ServiceUnit epStore_SU = SingleSoftwareUnit("ElasticityProcessStore_SU")
                .deployedBy(SingleScriptArtifact("ElasticityProcessStore_Artifact", artifactRepo + "ElasticityProcessStore_Artifact.sh"));
        depicServicesTopology.addServiceUnit(epStore_SU);
        cloudService.andRelationships(HostedOnRelation(epStore_SU.getId() + "_To_" + depicVM.getId())
                .from(epStore_SU)
                .to(depicVM));
        
        ServiceUnit dataAssetFunctionManagement_SU = SingleSoftwareUnit("DataAssetFunctionManagement_SU")
                .deployedBy(SingleScriptArtifact("DataAssetFunctionManagement_Artifact", artifactRepo + "DataAssetFunctionManagement_Artifact.sh"));
        depicServicesTopology.addServiceUnit(dataAssetFunctionManagement_SU);
        cloudService.andRelationships(HostedOnRelation(dataAssetFunctionManagement_SU.getId() + "_To_" + depicVM.getId())
                .from(dataAssetFunctionManagement_SU)
                .to(depicVM));
        
        

        COMOTOrchestrator orchestrator = new COMOTOrchestrator()
                .withSalsaIP(salsaIP)
                .withSalsaPort(salsaPort)
                .withRsyblIP(rsyblIP)
                .withRsyblPort(rsyblPort);

        //orchestrator.deployAndControl(cloudService);
        orchestrator.deploy(cloudService);
  
    }

}
