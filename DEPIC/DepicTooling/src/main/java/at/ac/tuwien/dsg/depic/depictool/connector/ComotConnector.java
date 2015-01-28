/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.connector;

import at.ac.tuwien.dsg.common.deployment.DeployAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.ControlProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.ElasticityProcess;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorAction;
import at.ac.tuwien.dsg.common.entity.eda.ep.MonitorProcess;
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
import at.ac.tuwien.dsg.depictool.elstore.ElasticityProcessStore;
import at.ac.tuwien.dsg.depictool.util.Configuration;
import at.ac.tuwien.dsg.comot.orchestrator.interraction.COMOTOrchestrator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class ComotConnector {

    private String artifactRepo;
    private ElasticityProcess elasticityProcess;
    private DeployAction eDaaSDeployAction;
    private List<DeployAction> monitoringServices;
    private List<DeployAction> controlServices;
    
    
    public ComotConnector() {
    }

    public ComotConnector(ElasticityProcess elasticityProcesses, DeployAction eDaaSDeployAction) {
        this.elasticityProcess = elasticityProcesses;
        this.eDaaSDeployAction = eDaaSDeployAction;
        config();
        prepareSoftwareArtifact();
    }
    
    
    
    private void config(){
        
        Configuration cfg = new Configuration();
        artifactRepo = cfg.getConfig("ARTIFACT.REPO"); 
    }
    
    private void prepareSoftwareArtifact(){
        
       prepareMonitoringServices();
       prepareControlServices();
    }
    
    private void prepareMonitoringServices(){
         
        monitoringServices = new ArrayList<DeployAction>();

        ElasticityProcessStore epStore = new ElasticityProcessStore();
        
        MonitorProcess monitorProcess = elasticityProcess.getMonitorProcess();
        List<MonitorAction> monitoringActions = monitorProcess.getListOfMonitorActions();

        for (MonitorAction ma : monitoringActions) {
           
            if (!isDeployActionExisting(monitoringServices, ma.getMonitorActionID())) {
                DeployAction deployAction = epStore.getPrimitiveAction(ma.getMonitorActionID());
                monitoringServices.add(deployAction);
            }

        }
    }
    
    private void prepareControlServices(){
        
        controlServices = new ArrayList<DeployAction>();

        ElasticityProcessStore epStore = new ElasticityProcessStore();

        List<ControlProcess> listOfControlProcesses = elasticityProcess.getListOfControlProcesses();

        for (ControlProcess controlProcess : listOfControlProcesses) {

            List<ControlAction> controlActions = controlProcess.getListOfControlActions();

            for (ControlAction ca : controlActions) {

                 if (!isDeployActionExisting(controlServices, ca.getControlActionName())) {
                    DeployAction deployAction = epStore.getPrimitiveAction(ca.getControlActionName());
                    controlServices.add(deployAction);
                }

            }

        }


        
    }
    
    private boolean isDeployActionExisting(List<DeployAction> listOfDeployActions, String deployActionID) {

        for (DeployAction da : listOfDeployActions) {
            if (da.getActionID().equals(deployActionID)) {
                return true;
            }
        }
        return false;
    }
    
    public void deployCloudSevices(){
        
        // monitoring_services_topology 
        ServiceTopology monitoringServicesTopology = ServiceTopology("Monitoring_Services_Topology");
        monitoringServicesTopology.constrainedBy(Constraint.MetricConstraint("DET_CO1", new Metric("cpuUsage", "%")).lessThan("80"));
        // control_services_topology
        ServiceTopology controlServicesTopology = ServiceTopology("Control_Services_Topology");
        controlServicesTopology.constrainedBy(Constraint.MetricConstraint("DET_CO2", new Metric("cpuUsage", "%")).lessThan("80"));
        
        // edaas_topology
      //  ServiceTopology edaasServiceTopology = ServiceTopology("eDaaS_Services_Topology");
        
        // add topology
        CloudService cloudService = ServiceTemplate(eDaaSDeployAction.getActionID()+"CloudService3")
                .consistsOfTopologies(monitoringServicesTopology)
                .consistsOfTopologies(controlServicesTopology)
       //         .consistsOfTopologies(edaasServiceTopology)
                .withDefaultMetrics();
        
        
        // add VM + monitoring service units
        for (DeployAction dpa : monitoringServices) {

            System.out.println("DEPLOY: Monitoring Action: " +dpa.getActionID());
   
            OperatingSystemUnit virtualMachine = OperatingSystemUnit(dpa.getActionID() + "_VM")
                    .providedBy(OpenstackSmall()
                            .addSoftwarePackage("openjdk-7-jre")
                            .addSoftwarePackage("ganglia-monitor")
                            .addSoftwarePackage("gmetad")
                    );


            ServiceUnit serviceUnit = SingleSoftwareUnit(dpa.getActionID()+"_SU")
                    .deployedBy(SingleScriptArtifact(dpa.getActionID()+"Artifact", artifactRepo + "deployCassandraNode.sh"));
            
                   // .deployedBy(WarArtifact(dpa.getActionID(), dpa.getArtifact()));
  
            monitoringServicesTopology.addServiceUnit(virtualMachine);
            monitoringServicesTopology.addServiceUnit(serviceUnit);
            
            
            
            cloudService.andRelationships(HostedOnRelation(serviceUnit.getId()+"To"+virtualMachine.getId())
                        .from(serviceUnit)
                        .to(virtualMachine));

        }
        
        // add VM + control service units
        for (DeployAction dpa : controlServices) {

            System.out.println("DEPLOY: Control Action: " +dpa.getActionID());
            
            OperatingSystemUnit virtualMachine = OperatingSystemUnit(dpa.getActionID() + "_VM")
                    .providedBy(LocalDocker()
                    );

            ServiceUnit serviceUnit = SingleSoftwareUnit(dpa.getActionID())
                    .deployedBy(SingleScriptArtifact(dpa.getActionID()+"Artifact", artifactRepo + "deployCassandraNode.sh"));

            controlServicesTopology.addServiceUnit(virtualMachine);
            controlServicesTopology.addServiceUnit(serviceUnit);
            
            cloudService.andRelationships(HostedOnRelation(serviceUnit.getId()+"To"+virtualMachine.getId())
                        .from(serviceUnit)
                        .to(virtualMachine));

        }
        
        
        /*
        
        // add VM + eDaaS
        
        OperatingSystemUnit vm_edaas = OperatingSystemUnit(eDaaSDeployAction.getActionID() + "_VM")
                .providedBy(LocalDocker()
                );

        ServiceUnit serviceUnit_edaas = SingleSoftwareUnit(eDaaSDeployAction.getActionID())
                .deployedBy(SingleScriptArtifact(eDaaSDeployAction.getActionID(), artifactRepo + "deployCassandraNode.sh"));
        
        edaasServiceTopology.addServiceUnit(serviceUnit_edaas);
        edaasServiceTopology.addServiceUnit(vm_edaas);
        
        cloudService.andRelationships(HostedOnRelation(eDaaSDeployAction.getActionID() + "ToVM")
                .from(serviceUnit_edaas)
                .to(vm_edaas));
*/
        
        
        // deployment
        
        Configuration cfg = new Configuration();
       

         COMOTOrchestrator orchestrator = new COMOTOrchestrator()
                //we have SALSA as cloud management tool
                //curently deployed separately
 
                .withSalsaIP(cfg.getConfig("COMOT.IP"))
                .withSalsaPort(8380);
         
        //deploy, monitor and control
        orchestrator.deployAndControl(cloudService);
        
        
        
        
        
        
        
    } 
    
    
}
