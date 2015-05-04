/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.primitiveactionrepository;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import at.ac.tuwien.dsg.depic.common.utils.YamlUtils;
import com.esotericsoftware.yamlbeans.YamlReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Jun
 */
public class fromFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String filePath = "/Volumes/DATA/Temp/primitiveActionRepository.yml";
        PrimitiveActionMetadata primitiveActionRepository = fromYaml(PrimitiveActionMetadata.class, filePath);
        
        System.out.println("rs: " + primitiveActionRepository.getListOfMonitoringActions().get(0).getAssociatedQoRMetric());
        
        
        String yStr = YamlUtils.marshallYaml(PrimitiveActionMetadata.class, primitiveActionRepository);
        
        System.out.println("SSS: " + yStr);
        
        PrimitiveActionMetadata rp = YamlUtils.unmarshallYaml(PrimitiveActionMetadata.class, yStr);
        
        System.out.println("AAA: " +  rp.getListOfMonitoringActions().get(0).getAssociatedQoRMetric());
    }
    
    public static <T> T fromYaml(Class<T> className, String filePath){
        T obj = null;
        try {
            YamlReader reader = new YamlReader(new FileReader(filePath));
            obj = reader.read(className);
 
        } catch (Exception ex) {
            Logger.getLogger(fromFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  obj;
    }
    
}
