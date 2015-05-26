/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.setup;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class DataAssetLoader_Cassandra {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String restParent = "http://128.130.172.214:8080/data-asset-loader/rest/test/";

        String r_open = restParent + "repo/dataasset/open";
        
        String r_close = restParent + "repo/dataasset/close";
        
        
        String r1 = restParent + "repo/keyspace";

        //table DataAsset
        String r2 = restParent + "repo/dataasset/create";
        String r3 = restParent + "repo/dataasset/insert";
        String r4 = restParent + "repo/dataasset/get";
   
        String r5 = restParent + "repo/dataasset/clear";
        
        String r6 = restParent + "repo/dataasset/nopar";
        
        
        // table DataAssetProcessing
        String r2a = restParent + "repo/processingdataasset/create";
        String r3a = restParent + "repo/processingdataasset/insert";
        String r4a = restParent + "repo/processingdataasset/get";
        String r5a = restParent + "repo/processingdataasset/nopar";
        String r6a = restParent + "repo/processingdataasset/update";
        String r7a = restParent + "repo/processingdataasset/clear";
        
        
        
        
        
        // end url
        
        open(r_open);
      //  doR1(r1);
        
        
        // Data Asset
      //  doR2(r2);
      //  doR3(r3);
      //  doR4(r4);
      //   doR5(r5);
       // doR7a(r6);
        
        
        // Processing Data Asset
      // doR2a(r2a);
       // doR3a(r3a);
      //  doR4a(r4a);
      //  doR5a(r5a);
      //  doR6a(r6a);
       // doR7a(r7a);
        
        close(r_close);
    }
    
    private static void open(String url){
        DataPartitionRequest dpr = new DataPartitionRequest("edaas1", "c03", "daf5", "0");
          String xml = "";
        
        
        try {
            xml = JAXBUtils.marshal(dpr, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetLoader_Cassandra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("Cassandra Open Connection " + ws.callPutMethod(xml));
    }
    
    private static void close(String url){
        
        DataPartitionRequest dpr = new DataPartitionRequest("edaas1", "c03", "daf5", "0");
          String xml = "";
        
        
        try {
            xml = JAXBUtils.marshal(dpr, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetLoader_Cassandra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("Cassandra Close Connection " + ws.callPutMethod(xml));
    }

    private static void doR1(String url) {
        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("Cassandra Test " + ws.callPutMethod(""));
    }

    private static void doR2(String url) {
        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("Cassandra Test " + ws.callPutMethod(""));
    }

    private static void doR3(String url) {
        DataAttribute id = new DataAttribute("id", "1");
        DataAttribute adID = new DataAttribute("adID", "123");
        DataAttribute userID = new DataAttribute("userID", "u01");
        DataAttribute click = new DataAttribute("click", "1");
        DataAttribute data = new DataAttribute("data", "data data data");

        List<DataAttribute> listOfDataAttributes = new ArrayList<DataAttribute>();
        listOfDataAttributes.add(adID);
        listOfDataAttributes.add(userID);
        listOfDataAttributes.add(click);
        listOfDataAttributes.add(data);
        listOfDataAttributes.add(id);

        DataItem item = new DataItem(listOfDataAttributes);
        List<DataItem> listOfDataItems = new ArrayList<DataItem>();
        listOfDataItems.add(item);

        DataAsset dataAsset = new DataAsset("asset1", 0, listOfDataItems);

        String xml = "";
        try {
            xml = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetLoader_Cassandra.class.getName()).log(Level.SEVERE, null, ex);
        }

        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("Cassandra Test " + ws.callPutMethod(xml));

    }

    private static void doR4(String url) {

        
        DataPartitionRequest dpr = new DataPartitionRequest("edaas1", "c01", "daf5", "0");
        
        String xml = "";
        
        
        try {
            xml = JAXBUtils.marshal(dpr, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetLoader_Cassandra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("Cassandra Test " + ws.callPutMethod(xml));

    }

    private static void doR5(String url) {
        
          DataPartitionRequest dpr = new DataPartitionRequest("edaas1", "c03", "daf5", "0");
          String xml = "";
        
        
        try {
            xml = JAXBUtils.marshal(dpr, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetLoader_Cassandra.class.getName()).log(Level.SEVERE, null, ex);
        }

        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("Cassandra Test " + ws.callPutMethod(xml));

    }
    
    private static void doR6(String url) {

        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("NoOfPartitions: " + ws.callPutMethod(""));

    }
    
    
    private static void doR2a(String url) {
        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("Cassandra Test " + ws.callPutMethod(""));
    }
    
    private static void doR3a(String url) {
        int partitionNo = 300;
        
        for (int i=0;i<partitionNo;i++) {

        DataAttribute adID = new DataAttribute("adID", "123");
        DataAttribute userID = new DataAttribute("userID", "u01");
        DataAttribute click = new DataAttribute("click", "1");
        DataAttribute data = new DataAttribute("data", "data data data");

        List<DataAttribute> listOfDataAttributes = new ArrayList<DataAttribute>();
        listOfDataAttributes.add(adID);
        listOfDataAttributes.add(userID);
        listOfDataAttributes.add(click);
        listOfDataAttributes.add(data);

        DataItem item = new DataItem(listOfDataAttributes);
        List<DataItem> listOfDataItems = new ArrayList<DataItem>();
        listOfDataItems.add(item);

        DataAsset dataAsset = new DataAsset("edaas1" + ";c01" + ";daf5" , i, listOfDataItems);

        String xml = "";
        try {
            xml = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetLoader_Cassandra.class.getName()).log(Level.SEVERE, null, ex);
        }

        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("Cassandra Test " + ws.callPutMethod(xml));

        }
    }
    
    private static void doR4a(String url) {

        
        DataPartitionRequest dpr = new DataPartitionRequest("edaas1", "c01", "daf5", "0");
        
        String xml = "";
        
        
        try {
            xml = JAXBUtils.marshal(dpr, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetLoader_Cassandra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("Cassandra Test " + ws.callPutMethod(xml));

    }
    
    private static void doR5a(String url) {

        
        DataPartitionRequest dpr = new DataPartitionRequest("edaas1", "c01", "daf5", "0");
        
        String xml = "";
        
        
        try {
            xml = JAXBUtils.marshal(dpr, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetLoader_Cassandra.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("Cassandra Test " + ws.callPutMethod(xml));

    }
    
    
    
     private static void doR6a(String url) {


        DataAttribute data = new DataAttribute("data", "aaaaaaaaaaaaaaaaaaa");

        List<DataAttribute> listOfDataAttributes = new ArrayList<DataAttribute>();

        listOfDataAttributes.add(data);

        DataItem item = new DataItem(listOfDataAttributes);
        List<DataItem> listOfDataItems = new ArrayList<DataItem>();
        listOfDataItems.add(item);

        DataAsset dataAsset = new DataAsset("edaas" + ";c01" + ";asset1" , 0, listOfDataItems);

        String xml = "";
        try {
            xml = JAXBUtils.marshal(dataAsset, DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetLoader_Cassandra.class.getName()).log(Level.SEVERE, null, ex);
        }

        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("Cassandra Test " + ws.callPutMethod(xml));

    }
     
     private static void doR7a(String url) {
         
           DataPartitionRequest dpr = new DataPartitionRequest("edaas1", "c03", "daf5", "0");
          String xml = "";
        
        
        try {
            xml = JAXBUtils.marshal(dpr, DataPartitionRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(DataAssetLoader_Cassandra.class.getName()).log(Level.SEVERE, null, ex);
        }

        RestfulWSClient ws = new RestfulWSClient(url);
        System.out.println("Cassandra Test " + ws.callPutMethod(xml));

    }
    
}
