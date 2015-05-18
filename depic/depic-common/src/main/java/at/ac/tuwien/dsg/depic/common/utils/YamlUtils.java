/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.common.utils;


import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.UUID;
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
        
        UUID tempID = UUID.randomUUID(); 
        
        IOUtils iou = new IOUtils("/home/ubuntu/log");
        iou.overWriteData(yml, tempID.toString()+".yaml");
        
        T obj = null;
        try {
            YamlReader reader = new YamlReader(new FileReader("/home/ubuntu/log/"+tempID.toString()+".yaml"));
            obj = reader.read(className);
 
        } catch (Exception ex) {
            Logger.getLogger(YamlUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        return  obj;
    }
    
    
    public static <T> String marshallYaml(Class<T> className, Object obj){
       
        UUID tempID = UUID.randomUUID();
        
        try {
            YamlWriter writer = new YamlWriter(new FileWriter("/home/ubuntu/log/"+tempID.toString()+".yaml"));
    
            
            writer.write(obj);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(YamlUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        IOUtils iou = new IOUtils("/home/ubuntu/log");

        String yml =iou.readData(tempID.toString()+".yaml");
        
        return yml;
    }
    
   
}
