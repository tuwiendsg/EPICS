/**
 * Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup
 * E184. This work was partially supported by the European Commission in terms
 * of the CELAR FP7 project (FP7-ICT-2011-8 #317790).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package at.ac.tuwien.dsg.orchestrator.restws;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.AdjustmentAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.MonitoringAction;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.ResourceControlAction;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ElasticService;
import at.ac.tuwien.dsg.depic.common.utils.Configuration;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.common.utils.YamlUtils;
import at.ac.tuwien.dsg.depic.repository.ElasticProcessRepositoryManager;
import at.ac.tuwien.dsg.depic.repository.PrimitiveActionMetadataManager;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/dep")
public class DEPProcessesGeneratorService {

    @GET
    @Path("/submit/pam")
    @Produces(MediaType.TEXT_PLAIN)
    public String importPAM() {
        
        Configuration cfg = new Configuration(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        
        IOUtils iou = new IOUtils(cfg.getConfigPath());
        String pamYAML = iou.readData("primitiveActionRepository.yml");
        
        System.out.println("pam: " + pamYAML);
        
        YamlUtils.setFilePath(cfg.getConfigPath());
       
        PrimitiveActionMetadata primitiveActionMetadata = YamlUtils.unmarshallYaml(PrimitiveActionMetadata.class, pamYAML);
        
        
        
        PrimitiveActionMetadataManager pamm = new PrimitiveActionMetadataManager(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        
        List<MonitoringAction> listOfMonitoringActions = primitiveActionMetadata.getListOfMonitoringActions();
        for (MonitoringAction monitoringAction : listOfMonitoringActions){
            pamm.storeMonitoringAction(monitoringAction);
        }
        
        List<AdjustmentAction> listOfAdjustmentActions = primitiveActionMetadata.getListOfAdjustmentActions();
        for (AdjustmentAction adjustmentAction : listOfAdjustmentActions){
            pamm.storeAdjustmentAction(adjustmentAction);
        }
        
        List<ResourceControlAction> listOfResourceControlActions = primitiveActionMetadata.getListOfResourceControls();
        for (ResourceControlAction resourceControlAction : listOfResourceControlActions){
            pamm.storeResourceControlAction(resourceControlAction);
        }
 
        return pamYAML;
    }

    @PUT
    @Path("/submit/daas")
    @Consumes(MediaType.APPLICATION_XML)
    public void insertDaaS(String daasName) {

        System.out.println("DaaS Name: " + daasName);
        ElasticProcessRepositoryManager eprm = new ElasticProcessRepositoryManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        eprm.insertDaaS(daasName);

    }

    @PUT
    @Path("/submit/daf/{daasName}")
    @Consumes(MediaType.APPLICATION_XML)
    public void submitDataAnalyticFunction(@PathParam("daasName") String daasName, String daf) {

        System.out.println("DaaS Name: " + daasName);

        ElasticProcessRepositoryManager eprm = new ElasticProcessRepositoryManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        eprm.storeDAF(daasName, daf);
    }

    @PUT
    @Path("/submit/qor/{daasName}")
    @Consumes(MediaType.APPLICATION_XML)
    public void submitQoR(@PathParam("daasName") String daasName, String qor) {
        System.out.println("DaaS Name: " + daasName);
        ElasticProcessRepositoryManager eprm = new ElasticProcessRepositoryManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        eprm.storeQoR(daasName, qor);

    }

    @PUT
    @Path("/submit/dbtype/{daasName}")
    @Consumes(MediaType.APPLICATION_XML)
    public void submitDBType(@PathParam("daasName") String daasName, String dbType) {

        System.out.println("DaaS Name: " + daasName);
        ElasticProcessRepositoryManager eprm = new ElasticProcessRepositoryManager(
                getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        eprm.storeDBType(daasName, dbType);

    }

    @GET
    @Path("/generate/{daasName}")
    @Consumes(MediaType.APPLICATION_XML)
    public void requestToGenerateDEPProcess(@PathParam("daasName") String daasName) {

        System.out.println("DaaS Name: " + daasName);

    }

    @GET
    @Path("/start")
    @Produces(MediaType.TEXT_PLAIN)
    public String startDepicService() {

        return "ok";
    }

}
