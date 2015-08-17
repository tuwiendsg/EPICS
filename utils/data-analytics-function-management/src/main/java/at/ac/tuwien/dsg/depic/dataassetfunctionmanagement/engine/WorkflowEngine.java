/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.engine;

/**
 *
 * @author Jun
 */

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.util.IOUtils;
import br.ufjf.taverna.core.TavernaClient;
import br.ufjf.taverna.model.output.TavernaOutput;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//import org.kie.internal.KnowledgeBase;
//import org.kie.internal.builder.KnowledgeBuilder;
//import org.kie.internal.builder.KnowledgeBuilderFactory;
//import org.kie.internal.io.ResourceFactory;
//import org.kie.api.io.ResourceType;
//import org.kie.internal.logger.KnowledgeRuntimeLogger;
//import org.kie.internal.logger.KnowledgeRuntimeLoggerFactory;
//import org.kie.internal.runtime.StatefulKnowledgeSession;

public class WorkflowEngine {

    private DataAnalyticsFunction daf;
    private String dafID;

    public WorkflowEngine(DataAnalyticsFunction daf, String dafID) {
        this.daf = daf;
        this.dafID = dafID;
    }

    public DataAnalyticsFunction getDaf() {
        return daf;
    }

    public void setDaf(DataAnalyticsFunction daf) {
        this.daf = daf;
    }

    public String getDafID() {
        return dafID;
    }

    public void setDafID(String dafID) {
        this.dafID = dafID;
    }

    
  
    
   
    
    

    public void startWFEngine() {
        
        
        TavernaClient client = new TavernaClient();
        
        //client.setBaseUri("http://localhost:8080/TavernaServer-2.5.4/rest");
        client.setBaseUri("http://128.130.172.214:8080/TavernaServer-2.5.4/rest");
        client.setAuthorization("taverna", "taverna");

        try {
            String tmpFilePath = "/Volumes/DATA/Temp";
            String uuid = UUID.randomUUID().toString();
            String status = "";
            
            IOUtils iou = new IOUtils(tmpFilePath);
            iou.writeData(daf.getDaw(), uuid+".t2flow");
   
            uuid = client.create(tmpFilePath + "/" + uuid+".t2flow");
            //String uuid = client.create("/Users/Jun/Desktop/DEPIC Demo/gps_edaas/daf_gps_1.t2flow");
            
            System.out.println(uuid);           
            
           client.start(uuid);

            do {
                 status = client.getStatus(uuid);
                System.out.println(status);
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            } while (!"Finished".equals(status));
            
            
            
            ArrayList<TavernaOutput> tavernaOutput = client.getOutput(uuid);
            for (TavernaOutput output : tavernaOutput) {
                System.out.println(output.getName());
            }
            
            
            client.destroy(uuid);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
        
//
//        try {
//            // load up the knowledge base
//            System.out.println("Start workflow engine ... ");
//            KnowledgeBase kbase = readKnowledgeBase();
//            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
//            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession, "test", 1000);
//            // start a new process instance
//            Map<String, Object> params = new HashMap<String, Object>();   
//            params.put("formOfData", daf.getDataAssetForm().getDataAssetForm());
//            params.put("dafID", dafID);
//            ksession.startProcess("ac.at.tuwien.dsg.daw1", params);
//          
//            
//            
//            
//            logger.close();
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }

    

//    private KnowledgeBase readKnowledgeBase() throws Exception {
//        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
//        
//        InputStream knowledgeStream = new ByteArrayInputStream(daf.getDaw().getBytes(StandardCharsets.UTF_8));
//   
//        kbuilder.add(ResourceFactory.newInputStreamResource(knowledgeStream), ResourceType.BPMN2);
//        
//        //kbuilder.add(ResourceFactory.newClassPathResource(daw+".bpmn"), ResourceType.BPMN2);
//      
//        return kbuilder.newKnowledgeBase();
//        
//       
//    }
//    
   

}
