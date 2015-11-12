/**
 * Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup E184. This work was partially supported by the European Commission in terms of the
 * CELAR FP7 project (FP7-ICT-2011-8 #317790).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package at.ac.tuwien.dsg.mlr.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class Configuration {

    static final String CURRENT_DIR = System.getProperty("user.dir");
    static final String CONFIGURATION_FILE="/GPSAnalytics.properties";

    public Configuration() {
        System.out.println("Current dir is: " + CURRENT_DIR);
        // whatever just create needed folders
         (new File(getWorkingDir())).mkdirs();
         (new File(getDataDir())).mkdirs();
    }

    private static String getDefautConfigPath() {
        String path = Configuration.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        int index = path.indexOf("/classes/at/ac");
        path = path.substring(0, index);
        try {
            return URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Configuration.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    // Get available parameters in files. If not found, return the default
    public static String getGenericParameter(String key, String theDefault) {
        String fileNames[] = {CURRENT_DIR + CONFIGURATION_FILE, getDefautConfigPath() + "config.properties"};
        return getGenericParameterFromFile(fileNames, key, theDefault);
    }

    private static String getGenericParameterFromFile(String[] fileNames, String key, String theDefault) {
        Properties prop = new Properties();
        for (String file : fileNames) {
            File f = new File(file);
            try {
                if (!f.exists()) {
                    f.createNewFile();
                } 
                prop.load(new FileReader(f));
                String param = prop.getProperty(key);
                if (param != null) {
                    System.out.println("Read a property in " + file + " and use " + key + "=" + param);
                    return param;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Read a property and use the default " + key + "=" + theDefault);
        return theDefault;
    }

    public static String getMOMIP() {
        return getGenericParameter("ACTIVEMQ.IP", "localhost");
    }

    public static String getMOMTopic(){
        return getGenericParameter("ACTIVEMQ.TOPIC", "GPSAnalytics_1");
    }

    public static String getWorkingDir() {
        return getGenericParameter("WORKING.DIR", "/tmp/GPSAnalytics");
    }
    
    public static String getDataDir() {
        return getWorkingDir() + "/data";
    }
    
    public static String getMySQL_IP() {
        return getGenericParameter("MYSQL.IP", "localhost");
    }
    
    public static String getMySQL_User(){
        return getGenericParameter("MYSQL.USER", "root");
    }
    
    public static String getMySQL_Password(){
        return getGenericParameter("MYSQL.PASSWORD", "123");
    }

}
