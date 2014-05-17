/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Jun
 */
public class Configuration {

    public void getConf() {

        Properties prop = new Properties();
	InputStream input = null;
 
	try {
 
		input = new FileInputStream("config.properties");
 
		// load a properties file
		prop.load(input);
 
		// get the property value and print it out
		System.out.println(prop.getProperty("mela.ip"));
		System.out.println(prop.getProperty("mela.port"));
		System.out.println(prop.getProperty("sybl.ip"));
                System.out.println(prop.getProperty("sybl.port"));
                System.out.println(prop.getProperty("salsa.ip"));
                System.out.println(prop.getProperty("salsa.port"));
 
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
    }
    
    public void setConf(){
        
        Properties prop = new Properties();
	OutputStream output = null;
 
	try {
 
		output = new FileOutputStream("config.properties");
 
		// set the properties value
		prop.setProperty("mela.ip", "localhost");
		prop.setProperty("mela.port", "8080");
		prop.setProperty("sybl.ip", "localhost");
                prop.setProperty("sybl.port", "8080");
                prop.setProperty("salsa.ip", "localhost");
                prop.setProperty("salsa.port", "8080");
 
		// save properties to project root folder
		prop.store(output, null);
 
	} catch (IOException io) {
		io.printStackTrace();
	} finally {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
 
	}
        
    }

}
