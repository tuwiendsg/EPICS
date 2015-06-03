/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.test;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAssetForm;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticProcess;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.common.utils.YamlUtils;
import static at.ac.tuwien.dsg.depic.common.utils.YamlUtils.toYaml;
import at.ac.tuwien.dsg.depic.elastic.process.generator.ElasticProcessesGenerator;


/**
 *
 * @author Jun
 */
public class ElasticProcessesGeneratorTester {

    private ElasticProcessesGeneratorTester() {
    }
    
    
    
    public static void main(String[] args) {
        // folder where files are put
        String rootPath = args[0];
        
        // qor file
        String qorFile = args[1];
        
        // daf file
        String dafFile = args[2];
        
        // primitive action metadata file
        String primitiveActionMetadataFile = args[3];
        
        startGenerating(rootPath, qorFile, dafFile, primitiveActionMetadataFile);
        
        
    }
    
    

    
    
    public static void startGenerating(String rootFolder, String qorFile, String dafFile, String primitiveActionMetadataFile){
        
       IOUtils iou = new IOUtils(rootFolder);
       YamlUtils.setFilePath(rootFolder);
       String dafStr = iou.readData(dafFile);
       String qorStr = iou.readData(qorFile);
       String primitiveAction_str = iou.readData(primitiveActionMetadataFile);

       DataAnalyticsFunction daf = new DataAnalyticsFunction("daf-gps", DataAssetForm.CSV, DBType.CASSANDRA_NEAR_REAL_TIME, dafStr);
       QoRModel qor = YamlUtils.unmarshallYaml(QoRModel.class, qorStr);
       PrimitiveActionMetadata pam = YamlUtils.unmarshallYaml(PrimitiveActionMetadata.class, primitiveAction_str);


       ElasticProcessesGenerator elasticProcessesGenerator = new ElasticProcessesGenerator(daf, qor, pam, rootFolder);
       ElasticProcess elasticProcess = elasticProcessesGenerator.generateElasticProcesses();
       toYaml(elasticProcess, "elastic_process.yml");
        
        
    }
    
    
}
