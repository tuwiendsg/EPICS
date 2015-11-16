/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.common.utils;


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
            System.err.println(ex);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.err.println(e);
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
            System.err.println(ex);
        }
        
        return path;
    }
    
    
    public String getConfigPath(){
        String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        
        int index = path.indexOf("/depic/");
        path = path.substring(0, index);
        path = path + "/depic/config" ;
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            System.err.println(ex);
        }
        System.out.println(path);
        return path;
    }
    
    
    
    public String getArtifactPath(){
       

        String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        int index = path.indexOf("/classes/at/ac");
        path = path.substring(0, index);
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
           System.err.println(ex);
        }

        path = path + "/classes/artifact";
        
    
        
        
        
        return path;
    
    }
}
