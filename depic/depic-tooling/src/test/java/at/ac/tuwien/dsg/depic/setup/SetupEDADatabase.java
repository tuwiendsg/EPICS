/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.setup;

import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class SetupEDADatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        openConnection();
        setupEDARepo();
        closeConnection();
        
        
    }
    private static void setupEDARepo(){
        
        DataPartitionRequest dpr = new DataPartitionRequest("edaas1", "c03", "daf5", "0");
          String xml = "";
        
        
        try {
            xml = JAXBUtils.marshal(dpr, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetLoader_Cassandra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String uri = "http://128.130.172.214:8080/data-asset-loader/rest/test/repo/dataasset/setup";
        RestfulWSClient ws = new RestfulWSClient(uri);
        ws.callPutMethod(xml);
    }
    
    private static void openConnection(){
        DataPartitionRequest dpr = new DataPartitionRequest("edaas1", "c03", "daf5", "0");
          String xml = "";
        
        
        try {
            xml = JAXBUtils.marshal(dpr, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetLoader_Cassandra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        String uri = "http://128.130.172.214:8080/data-asset-loader/rest/test/repo/dataasset/open";
        RestfulWSClient ws = new RestfulWSClient(uri);
        ws.callPutMethod(xml);
    }
    
    private static void closeConnection(){
        
        DataPartitionRequest dpr = new DataPartitionRequest("edaas1", "c03", "daf5", "0");
          String xml = "";
        
        
        try {
            xml = JAXBUtils.marshal(dpr, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetLoader_Cassandra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String uri = "http://128.130.172.214:8080/data-asset-loader//rest/test/repo/dataasset/close";
        RestfulWSClient ws = new RestfulWSClient(uri);
        ws.callPutMethod(xml);
    }
    
}
