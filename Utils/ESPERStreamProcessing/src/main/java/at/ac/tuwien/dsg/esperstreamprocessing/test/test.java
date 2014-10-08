/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.esperstreamprocessing.test;

import at.ac.tuwien.dsg.edasich.entity.stream.Task;

import at.ac.tuwien.dsg.esperstreamprocessing.utils.JSONUtils;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.RestfulWSClient;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;



/**
 *
 * @author Jun
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        Task task = new Task(null, "test door1", "close door", "DOOR", Task.SeverityLevel.EMERGENCY);
        String xmlStr = "";
        
        try {
            xmlStr = JSONUtils.writeJSON(task);
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("task: " + xmlStr );
        
        String ip="smartsoc.infosys.tuwien.ac.at";
        String resouce="/rest/api/task";
        
        RestfulWSClient rs = new RestfulWSClient(ip, "8080", resouce);
        rs.callPostMethod(xmlStr);
        
        
        /*
      MoMClient c = new MoMClient();
      c.run();

                */
                
    }
    
}
