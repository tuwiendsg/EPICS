/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depictool.utils;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAssetForm;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticProcess;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.common.utils.YamlUtils;
import static at.ac.tuwien.dsg.depic.common.utils.YamlUtils.toYaml;
import at.ac.tuwien.dsg.depic.depictool.generator.ElasticProcessesGenerator;

/**
 *
 * @author Jun
 */
public class ElasticProcessesGeneratorTester {
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        String rootPath = "";
        String qorFile = "";
        String dafFile = "";
        String primitiveActionMetadataFile = "";
        
        startGenerating(rootPath, qorFile, dafFile, primitiveActionMetadataFile);
        
        
    }
    
    

    
    
    public static void startGenerating(String rootFolder, String qorFile, String dafFile, String primitiveActionMetadataFile){
        
       IOUtils iou = new IOUtils(rootFolder);
       YamlUtils.setFilePath(rootFolder);
       String daf_str = iou.readData(dafFile);
       String qor_str = iou.readData(qorFile);;
       String primitiveAction_str = iou.readData(primitiveActionMetadataFile);;

       DataAnalyticsFunction daf = new DataAnalyticsFunction("daf-gps", DataAssetForm.CSV, DBType.CASSANDRA_NEAR_REAL_TIME, daf_str);
       QoRModel qor = YamlUtils.unmarshallYaml(QoRModel.class, qor_str);
       PrimitiveActionMetadata pam = YamlUtils.unmarshallYaml(PrimitiveActionMetadata.class, primitiveAction_str);


       ElasticProcessesGenerator elasticProcessesGenerator = new ElasticProcessesGenerator(daf, qor, pam, rootFolder);
       ElasticProcess elasticProcess = elasticProcessesGenerator.generateElasticProcesses();
       toYaml(elasticProcess, "elastic_process.yml");
        
        
    }
    
    
}
