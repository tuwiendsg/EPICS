/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.generator;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAssetForm;
import at.ac.tuwien.dsg.depic.common.entity.eda.elasticprocess.ElasticProcess;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.YamlUtils;

import at.ac.tuwien.dsg.depic.depictool.generator.ElasticProcessesGenerator;
import static at.ac.tuwien.dsg.depic.primitiveactionrepository.toFile.toYaml;

/**
 *
 * @author Jun
 */
public class Generator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        IOUtils iou = new IOUtils("/Volumes/DATA/Temp");
        
        String daf_str = iou.readData("daf_gps_1.t2flow");
        String qor_str = iou.readData("qor_xml.yml");;
        String primitiveAction_str = iou.readData("primitiveActionRepository.yml");;
        
        DataAnalyticsFunction daf = new DataAnalyticsFunction("daf-gps", DataAssetForm.CSV, DBType.CASSANDRA_NEAR_REAL_TIME, daf_str);
        QoRModel qor = YamlUtils.unmarshallYaml(QoRModel.class, qor_str);   
        PrimitiveActionMetadata pam = YamlUtils.unmarshallYaml(PrimitiveActionMetadata.class, primitiveAction_str);

        
        ElasticProcessesGenerator elasticProcessesGenerator = new ElasticProcessesGenerator(daf, qor, pam);
        ElasticProcess elasticProcess = elasticProcessesGenerator.generateElasticProcesses();
        toYaml(elasticProcess, "/Volumes/DATA/Temp/elastic_process.yml");
        
        
    }
    
}
