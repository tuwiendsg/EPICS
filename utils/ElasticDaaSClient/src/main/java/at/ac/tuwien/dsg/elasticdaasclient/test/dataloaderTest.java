///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package at.ac.tuwien.dsg.elasticdaasclient.test;
//
//
//import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
//import at.ac.tuwien.dsg.common.entity.eda.da.DataPartitionRequest;
//import at.ac.tuwien.dsg.edaas.requirement.edaas.power.DataAssetRequest;
//import at.ac.tuwien.dsg.elasticdaasclient.utils.JAXBUtils;
//import at.ac.tuwien.dsg.elasticdaasclient.utils.RestfulWSClient;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.xml.bind.JAXBException;
//
///**
// *
// * @author Jun
// */
//public class dataloaderTest {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
////        try {
////            // TODO code application logic here
////            String daXML =testget();
////            
////            DataAsset da = JAXBUtils.unmarshal(daXML, DataAsset.class);
////            da.getListOfDataItems().get(0).getListOfAttributes().get(0).setAttributeName("AAAAAAAAAAAA");
////            
////            
////            daXML = JAXBUtils.marshal(da, DataAsset.class);
////            
////            
////            testsave(daXML);
////        } catch (JAXBException ex) {
////            Logger.getLogger(dataloaderTest.class.getName()).log(Level.SEVERE, null, ex);
////        }
//        
//    //    testNoPartition();
//    
//   testrequestdatafromDAFM();
//    
//    
//        //testNoPartition();
//    }
//    
//    
//    private static void testrequestdatafromDAFM(){
//        String ip="128.130.172.216";
//        String port="8080";
//        String copyEndPoint="/DataAssetLoader/rest/dataasset/dafmanagement";
////        
////        DataPartitionRequest partitionRequest = new DataPartitionRequest("edaas1", "c01", "daf5", "");
////        String requestXML="";
////        try {
////            requestXML = JAXBUtils.marshal(partitionRequest, DataPartitionRequest.class);
////        } catch (JAXBException ex) {
////            Logger.getLogger(dataloaderTest.class.getName()).log(Level.SEVERE, null, ex);
////        }
////        
//        
//        
//        RestfulWSClient ws = new RestfulWSClient(ip, port, copyEndPoint);
//        System.out.println("RS: " +ws.callPutMethod("daf5"));
//    }
//    
//    private static void testcopy(){
//        String ip="128.130.172.216";
//        String port="8080";
//        String copyEndPoint="/DataAssetLoader/rest/dataasset/repo/datacopy";
//        
//        DataPartitionRequest partitionRequest = new DataPartitionRequest("edaas1", "c01", "daf5", "");
//        String requestXML="";
//        try {
//            requestXML = JAXBUtils.marshal(partitionRequest, DataPartitionRequest.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(dataloaderTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//        
//        RestfulWSClient ws = new RestfulWSClient(ip, port, copyEndPoint);
//        System.out.println("RS: " +ws.callPutMethod(requestXML));
//    }
//    
//    
//    private static String testget(){
//        String ip="localhost";
//        String port="8080";
//        String copyEndPoint="/DataAssetLoader/rest/dataasset/repo/getpartition";
//        
//        DataPartitionRequest partitionRequest = new DataPartitionRequest("edaas1", "c01", "daw3", "2");
//        String requestXML="";
//        try {
//            requestXML = JAXBUtils.marshal(partitionRequest, DataPartitionRequest.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(dataloaderTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//        
//        RestfulWSClient ws = new RestfulWSClient(ip, port, copyEndPoint);
//        
//        String rs = ws.callPutMethod(requestXML);
//        
//        System.out.println("RS: " +rs);
//        return rs;
//    }
//    
//    
//    private static void testsave(String daXML){
//        
//        String ip="localhost";
//        String port="8080";
//        String copyEndPoint="/DataAssetLoader/rest/dataasset/repo/savepartition";
//        
//         RestfulWSClient ws = new RestfulWSClient(ip, port, copyEndPoint);
//        
//        String rs = ws.callPutMethod(daXML);
//    }
//    
//    private static String testNoPartition(){
//        String ip="128.130.172.216";
//        String port="8080";
//        String copyEndPoint="/DataAssetLoader/rest/dataasset/repo/noofpartition";
//        
//         DataPartitionRequest partitionRequest = new DataPartitionRequest("edaas1", "c01", "daf1", "");
//        String requestXML="";
//        try {
//            requestXML = JAXBUtils.marshal(partitionRequest, DataPartitionRequest.class);
//        } catch (JAXBException ex) {
//            Logger.getLogger(dataloaderTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        
//        
//         RestfulWSClient ws = new RestfulWSClient(ip, port, copyEndPoint);
//        
//        String noOfPartition = ws.callPutMethod(requestXML);
//        
//        System.out.println("RS: " + noOfPartition);
//        return noOfPartition;
//    }
//    
//}
