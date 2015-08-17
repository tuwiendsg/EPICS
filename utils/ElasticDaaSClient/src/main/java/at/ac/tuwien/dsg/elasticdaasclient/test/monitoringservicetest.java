/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.elasticdaasclient.test;


import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAssetForm;
import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.Parameter;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.entity.runtime.ExternalServiceRequest;
import at.ac.tuwien.dsg.elasticdaasclient.demo.RequestDataAssetFromDAFM;
import at.ac.tuwien.dsg.elasticdaasclient.utils.JAXBUtils;
import at.ac.tuwien.dsg.elasticdaasclient.utils.RestfulWSClient;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class monitoringservicetest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       //openConnection();
     
        //testVAM();
       //testSAA();
       
testVAA();
    //testSAM();
       
       //closeConnection();
    }
    
    private static void testSAM() {
        String uri = "http://128.130.172.230:8080/SAM/rest/monitor";

        try {

            for (int i = 0; i < 100; i++) {

                DataPartitionRequest dataPartitionRequest = new DataPartitionRequest("edaas1", "c01", "daf-gps-" + i, "0");
                String dataPartitionRequestXML = JAXBUtils.marshal(dataPartitionRequest, DataPartitionRequest.class);

                RestfulWSClient ws = new RestfulWSClient(uri);
                System.out.println("SAM monitor: " + ws.callPutMethod(dataPartitionRequestXML));
            }
        } catch (JAXBException ex) {
            Logger.getLogger(RequestDataAssetFromDAFM.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
     private static void testVAM() {
        String uri = "http://128.130.172.230:8080/VAM/rest/monitor";

        try {

            for (int i = 0; i < 100; i++) {

                DataPartitionRequest dataPartitionRequest = new DataPartitionRequest("edaas1", "c01", "daf-gps-" + i, "0");
                String dataPartitionRequestXML = JAXBUtils.marshal(dataPartitionRequest, DataPartitionRequest.class);

                RestfulWSClient ws = new RestfulWSClient(uri);
                System.out.println("VAM monitor: " + ws.callPutMethod(dataPartitionRequestXML));
            }
        } catch (JAXBException ex) {
            Logger.getLogger(RequestDataAssetFromDAFM.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
     private static void testSAA() {
        String uri = "http://128.130.172.230:8080/SAA/rest/control";

        try {

            for (int i = 0; i < 100; i++) {
        
                List<Parameter> listOfParameters = new ArrayList<Parameter>();
                
                Parameter p = new Parameter("attributeIndex", "int", "3");
                listOfParameters.add(p);
                
                ExternalServiceRequest serviceRequest = new ExternalServiceRequest("edaas1", "c01", "daf-gps-"+i, listOfParameters);
                String serviceRequestXML = JAXBUtils.marshal(serviceRequest, ExternalServiceRequest.class);
                
                
                RestfulWSClient ws = new RestfulWSClient(uri);
                System.out.println("SAA adjustment: " + ws.callPutMethod(serviceRequestXML));
            }
        } catch (JAXBException ex) {
            Logger.getLogger(RequestDataAssetFromDAFM.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
     
     
     
     private static void testVAA() {
        String uri = "http://128.130.172.230:8080/VAA/rest/control";

        try {

            for (int i = 0; i < 100; i++) {
        
                List<Parameter> listOfParameters = new ArrayList<Parameter>();
                
                Parameter p = new Parameter("attributeIndex", "int", "3");
                listOfParameters.add(p);
                
                ExternalServiceRequest serviceRequest = new ExternalServiceRequest("edaas1", "c01", "daf-gps-"+i, listOfParameters);
                String serviceRequestXML = JAXBUtils.marshal(serviceRequest, ExternalServiceRequest.class);
                
                
                RestfulWSClient ws = new RestfulWSClient(uri);
                System.out.println("SAA adjustment: " + ws.callPutMethod(serviceRequestXML));
            }
        } catch (JAXBException ex) {
            Logger.getLogger(RequestDataAssetFromDAFM.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
     
    
//    private static void testAIC(){
//         String uri="http://localhost:8080/AIC/rest/control";
//     
//         Parameter param = new Parameter("attributeIndex", "Integer", "1");
//        List<Parameter> listOfParams = new ArrayList<Parameter>();
//        listOfParams.add(param);
//
//        DataControlRequest dataControlRequest = new DataControlRequest("edaas1", "c01", "daf5", listOfParams);
//
//        String requestXML = "";
//
//        try {
//            requestXML = JAXBUtils.marshal(dataControlRequest, DataControlRequest.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(monitoringservicetest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        RestfulWSClient ws = new RestfulWSClient(uri);
//        System.out.println("Enforce Control: " + ws.callPutMethod(requestXML));
//    }
//    
//    private static void testAPM(){
//         String uri="http://localhost:8080/APM/rest/monitor";
//     
//        DataPartitionRequest request = new DataPartitionRequest("edaas1", "c01", "daf5", "");
//        
//        String requestXML ="";
//        
//        
//        try {
//            requestXML = JAXBUtils.marshal(request, DataPartitionRequest.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(monitoringservicetest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//        
//        RestfulWSClient ws = new RestfulWSClient(uri);
//            System.out.println("APM Monitor: " +ws.callPutMethod(requestXML));
//    }
//    
//    private static void testAPC(){
//         String uri="http://localhost:8080/APC/rest/control";
//     
//         Parameter param = new Parameter("attributeIndex", "Integer", "6");
//        List<Parameter> listOfParams = new ArrayList<Parameter>();
//        listOfParams.add(param);
//
//        DataControlRequest dataControlRequest = new DataControlRequest("edaas1", "c01", "daf5", listOfParams);
//
//        String requestXML = "";
//
//        try {
//            requestXML = JAXBUtils.marshal(dataControlRequest, DataControlRequest.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(monitoringservicetest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        RestfulWSClient ws = new RestfulWSClient(uri);
//        System.out.println("Enforce Control: " + ws.callPutMethod(requestXML));
//    }
//    
//    
//    private static void testTPM(){
//         String uri="http://localhost:8080/TPM/rest/monitor";
//     
//        DataPartitionRequest request = new DataPartitionRequest("edaas1", "c01", "daf5", "");
//        
//        String requestXML ="";
//        
//        
//        try {
//            requestXML = JAXBUtils.marshal(request, DataPartitionRequest.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(monitoringservicetest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//        
//        RestfulWSClient ws = new RestfulWSClient(uri);
//            System.out.println("TPM Monitor: " +ws.callPutMethod(requestXML));
//    }
//    
//    private static void testAccuracy(){
//         String uri="http://localhost:8080/dataaccuracyMeasurement/rest/dataaccuracy";
//     
//        DataPartitionRequest request = new DataPartitionRequest("edaas1", "c01", "daf1", "");
//        
//        String requestXML ="";
//        
//        
//        try {
//            requestXML = JAXBUtils.marshal(request, DataPartitionRequest.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(monitoringservicetest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//        
//        RestfulWSClient ws = new RestfulWSClient(uri);
//            System.out.println("Data Accuracy Monitor: " +ws.callPutMethod(requestXML));
//    }
//    
//    
//    private static void testCompleteness(){
//         String uri="http://128.130.172.216:8080/datacompletenessMeasurement/rest/completeness";
//               
//        DataPartitionRequest request = new DataPartitionRequest("edaas1", "c01", "daf2", "");
//        
//        String requestXML ="";
//        
//        
//        try {
//            requestXML = JAXBUtils.marshal(request, DataPartitionRequest.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(monitoringservicetest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//        
//        RestfulWSClient ws = new RestfulWSClient(uri);
//            System.out.println("Data Completeness Monitor: " +ws.callPutMethod(requestXML));
//    }
//    
    
    private static void openConnection(){
      
        String ip = "128.130.172.216";
        String port = "8080";
        String resource = "/DataAssetLoader/rest/dataasset/repo/cassandra/open";
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        ws.callPutMethod("");
    }
    
    private static void closeConnection(){
        

        String ip = "128.130.172.216";
        String port = "8080";
        String resource = "/DataAssetLoader/rest/dataasset/repo/cassandra/close";
        
        RestfulWSClient ws = new RestfulWSClient(ip, port, resource);
        
        ws.callPutMethod("");
    }
    
}
