/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.orchestrator.properties;

/**
 *
 * @author Jun
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
 import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesConfiguration {

    
    
    
    public PropertiesConfiguration() {
    }
    
    public void writeProperties(){
        
        Properties prop = new Properties();
	OutputStream output = null;
        
        
        try {
 
		output = new FileOutputStream("config.properties");

		// set the properties value
		prop.setProperty("cluster1.ip", "128.130.172.216");
		prop.setProperty("cluster1.port", "8080");
                
                prop.setProperty("cluster2.ip", "127.0.0.1");
		prop.setProperty("cluster2.port", "8080");
                
                prop.setProperty("cluster3.ip", "127.0.0.1");
		prop.setProperty("cluster3.port", "8080");

 
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
    
    
    public List<VMCluster> getProperties(){
        
        
        Properties prop = new Properties();
	InputStream input = null;
        List<VMCluster> ls = new ArrayList<>();
 
	try {
 
		input = new FileInputStream("config.properties");
 
		// load a properties file
		prop.load(input);
 
		// get the property value and print it out
                
                String c1_ip = prop.getProperty("cluster1.ip");
                String c1_port =  prop.getProperty("cluster1.port");
                VMCluster vMCluster1 = new VMCluster(c1_ip, c1_port);
                
                String c2_ip = prop.getProperty("cluster2.ip");
                String c2_port =  prop.getProperty("cluster2.port");
                VMCluster vMCluster2 = new VMCluster(c2_ip, c2_port);
                
                String c3_ip = prop.getProperty("cluster3.ip");
                String c3_port =  prop.getProperty("cluster3.port");
                VMCluster vMCluster3 = new VMCluster(c3_ip, c3_port);
          
                
                ls.add(vMCluster1);
                ls.add(vMCluster2);
                ls.add(vMCluster3);
                
                
 
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
        
        
        return ls;
        
    }
    
    
}
