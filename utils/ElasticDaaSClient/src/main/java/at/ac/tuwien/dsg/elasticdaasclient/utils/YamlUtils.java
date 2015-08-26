/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.elasticdaasclient.utils;


import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Jun
 */
public class YamlUtils {
    
    
    public static void toYaml(Object obj,String filePath) {
        try {
            YamlWriter writer = new YamlWriter(new FileWriter(filePath));
            writer.write(obj);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(YamlUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static <T> T fromYaml(Class<T> className, String filePath){
        T obj = null;
        try {
            YamlReader reader = new YamlReader(new FileReader(filePath));
            obj = reader.read(className);
 
        } catch (Exception ex) {
            Logger.getLogger(YamlUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  obj;
    }
    
    
    
    
    public static <T> T unmarshallYaml(Class<T> className, String yml){
        
        IOUtils iou = new IOUtils("/home/ubuntu/log");
        iou.overWriteData(yml, "tmp.yaml");
        
        T obj = null;
        try {
            YamlReader reader = new YamlReader(new FileReader("/home/ubuntu/log/tmp.yaml"));
            obj = reader.read(className);
 
        } catch (Exception ex) {
            Logger.getLogger(YamlUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return  obj;
    }
    
    
    public static <T> String marshallYaml(Class<T> className, Object obj){
       
        try {
            //YamlWriter writer = new YamlWriter(new FileWriter("/home/ubuntu/log/tmp.yaml"));
            YamlWriter writer = new YamlWriter(new FileWriter("/Volumes/DATA/Temp/tmp.yaml"));
            
            writer.write(obj);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(YamlUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       // IOUtils iou = new IOUtils("/home/ubuntu/log");
        IOUtils iou = new IOUtils("/Volumes/DATA/Temp");
        String yml =iou.readData("tmp.yaml");
        
        return yml;
    }
    
   
}
