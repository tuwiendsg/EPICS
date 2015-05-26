/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.mela;


import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import com.esotericsoftware.yamlbeans.YamlWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        /*
        
        IOUtils io = new IOUtils();
        
        
        String dataAssetFunctionXML = io.readData("daw3.bpmn");
        
        DataAssetFunction daf = new DataAssetFunction("pc100", "batch-data", dataAssetFunctionXML);
        
        try {
            String dafXML = JAXBUtils.marshal(daf, DataAssetFunction.class);
            
            IOUtils iou = new IOUtils();
            iou.writeData(dafXML, daf.getName());
            
        } catch (JAXBException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        */
        
        
        /*
        List<MetricElasticityProcess> meps = new ArrayList<MetricElasticityProcess>();
        
        MetricElasticityProcess m1 = sampleEPDataCompleteness();
        MetricElasticityProcess m2 = sampleEPThroughput();
        meps.add(m1);
        meps.add(m2);
        
        
        MetricProcess mp = new MetricProcess(meps);
        
        
        
        toYaml(mp, "/Volumes/DATA/Temp/MetricElasticityProcess.yml");
        */
        
  //      SampleData_DaaS2 sampleData = new SampleData_DaaS2();
           //SampleData_DaaS_KDD sampleData = new SampleData_DaaS_KDD();
        
    //    SampleData_DaaS_KDD_5 sampleData = new SampleData_DaaS_KDD_5();
//      
//        SampleData sampleData = new SampleData();
//        
//        
//        QoRModel qor = sampleData.sampleQoRModel();
//        toYaml(qor, "/Volumes/DATA/Temp/qor.yml");
//        
//        
     //   MetricProcess metricProcess = sampleData.sampleElasticityProcess();
     //   toYaml(metricProcess, "/Volumes/DATA/Temp/metricProcess.yml");
        
    }
    
      
    public static void toYaml(Object obj,String filePath) {
        try {
            YamlWriter writer = new YamlWriter(new FileWriter(filePath));
            writer.write(obj);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
  
}
