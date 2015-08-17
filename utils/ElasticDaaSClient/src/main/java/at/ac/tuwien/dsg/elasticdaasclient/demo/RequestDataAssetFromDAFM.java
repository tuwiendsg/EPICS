/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.elasticdaasclient.demo;

import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAnalyticsFunction;
import at.ac.tuwien.dsg.depic.common.entity.dataanalyticsfunction.DataAssetForm;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DBType;
import at.ac.tuwien.dsg.depic.common.entity.runtime.DataPartitionRequest;
import at.ac.tuwien.dsg.depic.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.elasticdaasclient.utils.YamlUtils;
import at.ac.tuwien.dsg.elasticdaasclient.test.monitoringservicetest;
import at.ac.tuwien.dsg.elasticdaasclient.utils.JAXBUtils;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class RequestDataAssetFromDAFM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      //  getDataAsset("");
      copyDataAsset();
    }
    
    
    private static void getDataAsset(String dataAssetID){
          
        String uri="http://128.130.172.214:8080/data-asset-loader/rest/dataasset/dafmanagement";
    
       
      DataAnalyticsFunction dataAnalyticsFunction = new DataAnalyticsFunction("daf-gps", DataAssetForm.CSV, DBType.MYSQL, "");
      String daf_temp = YamlUtils.marshallYaml(DataAnalyticsFunction.class, dataAnalyticsFunction);
       
        RestfulWSClient ws = new RestfulWSClient(uri);
            System.out.println("SAM Monitor: " +ws.callPutMethod(daf_temp));
    }
    
    
    private static void copyDataAsset(){
          
        try {
            String uri="http://128.130.172.214:8080/data-asset-loader/rest/dataasset/repo/datacopy";
            
             for (int i=0;i<100;i++) {
        
            DataPartitionRequest dataPartitionRequest = new DataPartitionRequest("edaas1", "c01", "daf-gps-"+i, "0");
            String dataPartitionRequestXML = JAXBUtils.marshal(dataPartitionRequest, DataPartitionRequest.class);
            
            RestfulWSClient ws = new RestfulWSClient(uri);
            System.out.println("COPY: " +ws.callPutMethod(dataPartitionRequestXML));
             }
        } catch (JAXBException ex) {
            Logger.getLogger(RequestDataAssetFromDAFM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
