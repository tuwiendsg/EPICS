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
import java.sql.ResultSet;
import java.util.Properties;

public class Configuration {

    public Configuration() {
    }

    public static String getConfig(String configureName) {

        String configString = "";
        
        try {
            String momXML = IOUtils.readData("momconf");
            MOMConfiguration conf = at.ac.tuwien.dsg.edasich.utils.JAXBUtils.unmarshal(momXML, MOMConfiguration.class);


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
    
 
    


}
