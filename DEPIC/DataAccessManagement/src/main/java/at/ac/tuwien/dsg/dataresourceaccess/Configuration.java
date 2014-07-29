/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.dataresourceaccess;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author Jun
 */
public class Configuration {
    
    
    public void setConf() {
        
        Properties prop = new Properties();
	OutputStream output = null;
 
	try {
 
		output = new FileOutputStream("config.properties");
                
		// set the properties value
		prop.setProperty("MySqlConf", "jdbc:mysql://localhost:3306/shark?useUnicode=true&characterEncoding=UTF-8&useFastDateParsing=false");
		prop.setProperty("Username", "root");
		prop.setProperty("Password", "");
 
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
    
    
    
    public void getConf(){
        
        Properties prop = new Properties();
    	InputStream input = null;
 
    	try {
 
    		String filename = "config.properties";
                
                
                
    		input = new FileInputStream(filename);
    		if(input==null){
    	            System.out.println("Sorry, unable to find " + filename);
    		    return;
    		}
 
    		//load a properties file from class path, inside static method
    		prop.load(input);
 
                //get the property value and print it out
                System.out.println(prop.getProperty("MySqlConf"));
    	        System.out.println(prop.getProperty("Username"));
    	        System.out.println(prop.getProperty("Password"));
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        } finally{
        	if(input!=null){
        		try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	}
        }
 
        
    }
    
    
    public void printThemAll() {
 
	Properties prop = new Properties();
	InputStream input = null;
 
	try {
 
		String filename = "config.properties";
		input = getClass().getClassLoader().getResourceAsStream(filename);
		if (input == null) {
			System.out.println("Sorry, unable to find " + filename);
			return;
		}
 
		prop.load(input);
 
		Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = prop.getProperty(key);
			System.out.println("Key : " + key + ", Value : " + value);
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
 
  }
    
    
    
}
