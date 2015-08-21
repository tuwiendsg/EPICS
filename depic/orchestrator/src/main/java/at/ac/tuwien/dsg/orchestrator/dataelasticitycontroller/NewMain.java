/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.orchestrator.dataelasticitycontroller;

import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
                    DataPartitionRequest request = new DataPartitionRequest("edaas1", "c00", "daf-gps-46", "");

                    String requestXML = "";

                    try {
                        requestXML = JAXBUtils.marshal(request, DataPartitionRequest.class);
                    } catch (JAXBException ex) {
                        System.err.println(ex);
                    }

        RestfulWSClient ws = new RestfulWSClient("http://128.130.172.191:8080/SAM/rest/monitor");
        ws.callPutMethod(requestXML);
    }
    
}
