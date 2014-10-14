/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.esperstreamprocessing.utils;

/**
 *
 * @author Jun
 */

import at.ac.tuwien.dsg.edasich.configuration.MOMConfiguration;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Configuration {

    public Configuration() {
    }

    public static String getConfig(String configureName) {

        
        String configString = "";
        
        try {
            String momXML = IOUtils.readData("momconf");
            MOMConfiguration conf = JAXBUtils.unmarshal(momXML, MOMConfiguration.class);


            switch (configureName) {
                case "MOM.IP":
                    configString = conf.getIp();
                    break;
                case "MOM.PORT":
                    configString = conf.getPort();
                    break;
                case "MOM.QUEUE_NAME":
                    configString = conf.getQueue();
                    break;
                case "MESSAGE.LIMIT":
                    configString = String.valueOf(conf.getLimit());
                    break;

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } 

        return configString;
    }
    
    
    public static String getConfiguration(String configureName) {

        String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        int index = path.indexOf("/classes/at/ac");
        path = path.substring(0, index);
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Logger.getLogger(Configuration.class.getName()).log(Level.INFO,"Configure Path: " + path);
        
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
 
    


}
