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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.Properties;

public class Configuration {

    public Configuration() {
    }

    public static String getConfig(String configureName) {

        Properties prop = new Properties();
        String configString = "";
        InputStream input = null;

        try {

            input = new FileInputStream("config/config.properties");

            // load a properties file
            prop.load(input);

            switch (configureName) {
                case "MOM.IP":
                    configString = prop.getProperty("MOM.IP");
                    break;
                case "MOM.PORT":
                    configString = prop.getProperty("MOM.PORT");
                    break;
                case "MOM.QUEUE_NAME":
                    configString = prop.getProperty("MOM.QUEUE_NAME");
                    break;
                case "MESSAGE.LIMIT":
                    configString = prop.getProperty("MESSAGE.LIMIT");
                    break;

            }

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
