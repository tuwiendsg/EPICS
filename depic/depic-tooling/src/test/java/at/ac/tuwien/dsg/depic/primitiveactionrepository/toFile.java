/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.primitiveactionrepository;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.entity.qor.QoRModel;
import at.ac.tuwien.dsg.mela.test;
import static at.ac.tuwien.dsg.mela.test.toYaml;
import com.esotericsoftware.yamlbeans.YamlWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class toFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        PrimitiveActionRepositoryGenerator generator = new PrimitiveActionRepositoryGenerator();
        PrimitiveActionMetadata primitiveActionRepository = generator.getData();
        
        toYaml(primitiveActionRepository, "/Volumes/DATA/Temp/primitiveActionRepository.yml");
        
        
        QoR_GPS qoR = new QoR_GPS();
        QoRModel qoRModel = qoR.sampleQoRModel();
        toYaml(qoRModel, "/Volumes/DATA/Temp/qor_xml.yml");
        
        
        
      
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
