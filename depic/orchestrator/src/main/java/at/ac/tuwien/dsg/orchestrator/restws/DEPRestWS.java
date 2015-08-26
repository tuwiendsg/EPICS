/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.restws;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.DataElasticityManagementProcess;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlAction;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.repository.ElasticProcessRepositoryManager;
import at.ac.tuwien.dsg.depic.common.repository.PrimitiveActionMetadataManager;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.depic.common.utils.YamlUtils;
import at.ac.tuwien.dsg.depic.elastic.process.generator.ElasticProcessesGenerator;
import at.ac.tuwien.dsg.orchestrator.registry.ElasticServiceMonitor;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */

@Path("dep")
public class DEPRestWS {
    
    @Context
    private UriInfo context;

    public DEPRestWS() {
    }
    
    @PUT
    @Path("get/qor")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String getQoR(String daasName) {

        System.out.println("DaaS Name: " + daasName);
        ElasticProcessRepositoryManager eprm = new ElasticProcessRepositoryManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String qorStr = eprm.getQoR(daasName);

        return qorStr;
    }
    
    @PUT
    @Path("get/daf")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String getDAF(String daasName) {

        System.out.println("DaaS Name: " + daasName);
        ElasticProcessRepositoryManager eprm = new ElasticProcessRepositoryManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String dafStr = eprm.getDAF(daasName);

        return dafStr;
    }
    
    @PUT
    @Path("get/dep")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String getDEP(String daasName) {

        System.out.println("DaaS Name: " + daasName);
        ElasticProcessRepositoryManager eprm = new ElasticProcessRepositoryManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        String depStr = eprm.getElasticityProcesses(daasName);

        return depStr;
    }
    
    
    @PUT
    @Path("submit/daas")
    @Consumes(MediaType.APPLICATION_XML)
    public void insertDaaS(String daasName) {

        System.out.println("DaaS Name: " + daasName);
        ElasticProcessRepositoryManager eprm = new ElasticProcessRepositoryManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        eprm.insertDaaS(daasName);

    }

    @PUT
    @Path("submit/daf/{daasName}")
    @Consumes(MediaType.APPLICATION_XML)
    public void submitDataAnalyticFunction(@PathParam("daasName") String daasName, String daf) {

        System.out.println("DaaS Name: " + daasName);

        ElasticProcessRepositoryManager eprm = new ElasticProcessRepositoryManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        eprm.storeDAF(daasName, daf);
    }

    @PUT
    @Path("submit/qor/{daasName}")
    @Consumes(MediaType.APPLICATION_XML)
    public void submitQoR(@PathParam("daasName") String daasName, String qor) {
        System.out.println("DaaS Name: " + daasName);
        ElasticProcessRepositoryManager eprm = new ElasticProcessRepositoryManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        eprm.storeQoR(daasName, qor);

    }

    @PUT
    @Path("submit/dbtype/{daasName}")
    @Consumes(MediaType.APPLICATION_XML)
    public void submitDBType(@PathParam("daasName") String daasName, String dbType) {

        System.out.println("DaaS Name: " + daasName);
        ElasticProcessRepositoryManager eprm = new ElasticProcessRepositoryManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        eprm.storeDBType(daasName, dbType);

    }

    @GET
    @Path("generate/{daasName}")
    @Consumes(MediaType.APPLICATION_XML)
    public void requestToGenerateDEPProcess(@PathParam("daasName") String daasName) {

        System.out.println("GENERATE DaaS: " + daasName);
        
        ElasticProcessRepositoryManager eprm = new ElasticProcessRepositoryManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        
        String qorStr = eprm.getQoR(daasName);
        String dafStr = eprm.getDAF(daasName);
        String dbtype = eprm.getDBType(daasName);
        
        DataAnalyticsFunction daf = new DataAnalyticsFunction(daasName, null, DBType.valueOf(dbtype), dafStr);
       
        QoRModel qoRModel = YamlUtils.unmarshallYaml(QoRModel.class, qorStr);
        
        
         PrimitiveActionMetadataManager pamm = new PrimitiveActionMetadataManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        List<MonitoringAction> listOfMonitoringActions = pamm.getMonitoringActionList();
        List<AdjustmentAction> listOfAdjustmentActions = pamm.getAdjustmentActionList();
        List<ResourceControlAction> listOfResourceControlActions = pamm.getResourceControlActionList();

        PrimitiveActionMetadata primitiveActionRepository = new PrimitiveActionMetadata(listOfAdjustmentActions, listOfMonitoringActions, listOfResourceControlActions);

        
        ElasticProcessesGenerator elasticProcessesGenerator = new ElasticProcessesGenerator(daf, qoRModel, primitiveActionRepository);
        
        DataElasticityManagementProcess elasticProcess = elasticProcessesGenerator.generateElasticProcesses();
        
      
      
        

    }
    
    
}
