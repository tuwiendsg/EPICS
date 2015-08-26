///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package at.ac.tuwien.dsg.elasticdaasclient.test;
//
//import at.ac.tuwien.dsg.common.entity.eda.da.DataControlRequest;
//import at.ac.tuwien.dsg.common.entity.eda.da.DataPartitionRequest;
//import at.ac.tuwien.dsg.common.entity.process.Parameter;
//import at.ac.tuwien.dsg.elasticdaasclient.utils.JAXBUtils;
//import at.ac.tuwien.dsg.elasticdaasclient.utils.RestfulWSClient;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.xml.bind.JAXBException;
//
///**
// *
// * @author Jun
// */
//public class testControl {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        // TODO code application logic here
//        
//        //String uri = "http://localhost:8080/LSR/rest/control";
//        //String uri = "http://localhost:8080/DIR/rest/control";
//        //String uri = "http://localhost:8080/MLR/rest/control";
//
//        String uri = "http://localhost:8080/STC/rest/control";
//
//        
//        
//        Parameter param = new Parameter("attributeIndex", "Integer", "3");
//        List<Parameter> listOfParams = new ArrayList<Parameter>();
//        listOfParams.add(param);
//
//        DataControlRequest dataControlRequest = new DataControlRequest("edaas3", "c01", "daf1", listOfParams);
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
//        System.out.println("Enforce LSR: " + ws.callPutMethod(requestXML));
//    }
//    
//}
