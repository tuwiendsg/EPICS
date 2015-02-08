/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.utils;


import at.ac.tuwien.dsg.common.entity.process.MetricProcess;
import at.ac.tuwien.dsg.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.common.entity.qor.QoRModel;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

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
        T obj = null;
        
        
        try {
          
           
            YamlReader reader = new YamlReader(yml);
           // YamlReader reader = new YamlReader(new FileReader(filePath));
            obj = reader.read(className);
 
        } catch (Exception ex) {
            Logger.getLogger(YamlUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  obj;
    }
    
    
    public static <T> String marshallYaml(Class<T> className, Object obj){
       
        String yml="";
        
        StringWriter stringWriter = new StringWriter();
        try {
            YamlWriter writer = new YamlWriter(stringWriter);
            writer.write(obj);
            yml = stringWriter.toString();
            
            stringWriter.close();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(YamlUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return yml;
    }
    
   
}
