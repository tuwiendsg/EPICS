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
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.kie.internal.KnowledgeBase;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.api.io.ResourceType;
import org.kie.internal.logger.KnowledgeRuntimeLogger;
import org.kie.internal.logger.KnowledgeRuntimeLoggerFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

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

        try {
            // load up the knowledge base
            System.out.println("Start workflow engine ... ");
            KnowledgeBase kbase = readKnowledgeBase();
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
            KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newThreadedFileLogger(ksession, "test", 1000);
            // start a new process instance
            Map<String, Object> params = new HashMap<String, Object>();   
            params.put("formOfData", daf.getDataAssetForm().getDataAssetForm());
            params.put("dafID", dafID);
            ksession.startProcess("ac.at.tuwien.dsg.daw1", params);
          
            
            
            
            logger.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    private KnowledgeBase readKnowledgeBase() throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        
        InputStream knowledgeStream = new ByteArrayInputStream(daf.getDaw().getBytes(StandardCharsets.UTF_8));
   
        kbuilder.add(ResourceFactory.newInputStreamResource(knowledgeStream), ResourceType.BPMN2);
        
        //kbuilder.add(ResourceFactory.newClassPathResource(daw+".bpmn"), ResourceType.BPMN2);
      
        return kbuilder.newKnowledgeBase();
        
       
    }
    
   

}
