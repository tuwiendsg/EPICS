/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.service.engine;

import at.ac.tuwien.dsg.common.utils.RestfulWSClient;

import at.ac.tuwien.dsg.edasich.configuration.Configuration;
import at.ac.tuwien.dsg.edasich.entity.stream.Task;
import at.ac.tuwien.dsg.edasich.utils.JAXBUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class TaskDistribution {
    
    public void sendTaskSALAM(Task task){
        try {
            String salamIp = "";
            String salamPort = "";
            String salamApi = "";
            
            RestfulWSClient restClient = new RestfulWSClient(salamIp, salamPort, salamApi);
            String taskXML = JAXBUtils.marshal(task, Task.class);
            
            restClient.callRestfulWebService(taskXML);
        } catch (JAXBException ex) {
            Logger.getLogger(TaskDistribution.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void onTaskResponse(){
        
    }
    
}
