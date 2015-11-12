/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.test;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAssetForm;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.DataElasticityManagementProcess;

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

    public static void main(String[] args) {
        System.out.println("EXAMPLES !!!");
        // folder where files are put                

        String rootPath = "/home/hungld/workspace/EPICS/depic/examples/common/demo";

        // qor file
        String qorFile = "qor_xml.yml";

        // daf file
        String dafFile = "daf_gps_1.t2flow";

        // primitive action metadata file
        String primitiveActionMetadataFile = "primitiveActionRepository.yml";

        startGenerating(rootPath, qorFile, dafFile, primitiveActionMetadataFile);

    }

//    public static void main_old(String[] args) {
//        // folder where files are put
//        String rootPath = args[0];
//
//        // qor file
//        String qorFile = args[1];
//
//        // daf file
//        String dafFile = args[2];
//
//        // primitive action metadata file
//        String primitiveActionMetadataFile = args[3];
//
//        startGenerating(rootPath, qorFile, dafFile, primitiveActionMetadataFile);
//
//    }

    public static void startGenerating(String rootFolder, String qorFile, String dafFile, String primitiveActionMetadataFile) {

        System.out.println("rootFolder: " + rootFolder);
        System.out.println("QoR file: " + qorFile);
        System.out.println("dafFile: " + dafFile);
        System.out.println("primitiveActionMetadataFile: " + primitiveActionMetadataFile);
        
        IOUtils iou = new IOUtils(rootFolder);
        YamlUtils.setFilePath(rootFolder);
        String daf_str = iou.readData(dafFile);
        
        System.out.println("daf_str: " + daf_str);
        System.out.println(" ______________________________________________");
        String qor_str = iou.readData(qorFile);
        
        System.out.println("qor_str: " + qor_str);
        System.out.println(" ______________________________________________");
        
        String primitiveAction_str = iou.readData(primitiveActionMetadataFile);;

        DataAnalyticsFunction daf = new DataAnalyticsFunction("daf-gps", DataAssetForm.CSV, DBType.CASSANDRA_NEAR_REAL_TIME, daf_str);
        QoRModel qor = YamlUtils.unmarshallYaml(QoRModel.class, qor_str);
        
        System.out.println("QOR Access Form: "  + qor.getDataAssetForm().toString());
        PrimitiveActionMetadata pam = YamlUtils.unmarshallYaml(PrimitiveActionMetadata.class, primitiveAction_str);
        System.out.println("QOR: " + qor);
        ElasticProcessesGenerator elasticProcessesGenerator = new ElasticProcessesGenerator(daf, qor, pam, rootFolder);
        DataElasticityManagementProcess elasticProcess = elasticProcessesGenerator.generateElasticProcesses();
        toYaml(elasticProcess, "elastic_process.yml");

    }

}
