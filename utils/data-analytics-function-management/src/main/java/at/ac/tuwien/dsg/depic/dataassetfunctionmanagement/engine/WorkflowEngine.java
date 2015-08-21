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
import at.ac.tuwien.dsg.depic.dataassetfunctionmanagement.configuration.Configuration;
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
    private String rootPath;

    public WorkflowEngine(DataAnalyticsFunction daf, String dafID) {
        this.daf = daf;
        this.dafID = dafID;
        rootPath = "/home/ubuntu/log/temp";
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
        
        IOUtils iou = new IOUtils(rootPath);
        iou.writeData(daf.getDaw(), "daf_file_temp.t2flow");

        TavernaClient client = new TavernaClient();

        String tavernaServerEndpoint = Configuration.getConfig("TAVERNA.SERVER.ENDPOINT");
        client.setBaseUri(tavernaServerEndpoint);
        client.setAuthorization("taverna", "taverna");

        try {

            String status = "";
            
            String dafUri = rootPath + "/daf_file_temp.t2flow";
            String uuid = client.create(dafUri);
            System.out.println(uuid);

            client.start(uuid);

            do {
                status = client.getStatus(uuid);
                System.out.println(status);
                try {
                    Thread.sleep(2000);
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

}
