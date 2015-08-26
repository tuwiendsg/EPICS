/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.utils;

import at.ac.tuwien.dsg.depic.common.utils.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;


/**
 *
 * @author Jun
 */
public class Configuration {
    public Configuration() {
    }

    public String getConfig(String configureName) {

        String path = getConfigPath();

        Properties prop = new Properties();
        String configString = "";
        InputStream input = null;

        try {
            input = new FileInputStream(path + "/config.properties");
            prop.load(input);
            configString = prop.getProperty(configureName);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return configString;
    }
    
    public String getPrimitiveActionMetadata(){
        String path = getConfigPath();
        IOUtils iou = new IOUtils(path);
  
        String primitiveAction_str = iou.readData("primitiveActionRepository.yml");
        return primitiveAction_str;
    }
    
    public String getCurrentPath(){
        String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        int index = path.indexOf("/WEB-INF/classes/at/ac");
        path = path.substring(0, index);
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
           
        }
        
        return path;
    }
    
    
    public String getConfigPath(){
        String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        int index = path.indexOf("/classes/at/ac");
        path = path.substring(0, index);
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            
        }
        
        return path;
    }
    
    
    
    public String getArtifactPath(){
       

        String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        int index = path.indexOf("/classes/at/ac");
        path = path.substring(0, index);
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
           
        }

        path = path + "/classes/artifact";
        
      
        
        
        return path;
    
    }
}
