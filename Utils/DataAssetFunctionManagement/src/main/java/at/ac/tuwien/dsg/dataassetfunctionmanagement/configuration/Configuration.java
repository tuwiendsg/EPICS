/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetfunctionmanagement.configuration;

/**
 *
 * @author Jun
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {

    public Configuration() {
    }

    public static String getConfig(String configureName) {

        String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        System.out.println("Path: " + path);
        int index = path.indexOf("/classes/");
        path = path.substring(0, index);
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }

        Properties prop = new Properties();
        String configString = "";
        InputStream input = null;

        try {
            //input = new FileInputStream(path + "/DataAssetFunctionManagement/WEB-INF/config.properties");
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

    
}
