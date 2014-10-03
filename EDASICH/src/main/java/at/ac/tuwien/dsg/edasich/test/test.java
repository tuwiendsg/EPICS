/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.test;

/**
 *
 * @author Jun
 */
import at.ac.tuwien.dsg.common.utils.RestfulWSClient;
import at.ac.tuwien.dsg.edasich.configuration.AnalyticEngineConfiguration;
import at.ac.tuwien.dsg.edasich.configuration.Configuration;
import at.ac.tuwien.dsg.edasich.entity.stream.DataAssetFunctionStreamingData;
import at.ac.tuwien.dsg.edasich.entity.stream.EventPattern;
import at.ac.tuwien.dsg.edasich.entity.stream.Task;
import at.ac.tuwien.dsg.edasich.service.engine.AnalyticController;
import at.ac.tuwien.dsg.edasich.utils.IOUtils;
import at.ac.tuwien.dsg.edasich.utils.JAXBUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        
        
      //  RestfulWSClient rs = new RestfulWSClient("localhost", "8080", "/ESPERStreamProcessing/webresources/esper/start");
       // rs.callRestfulWebService("alo");
        
        // sampleDataAssetFunctions();
        
        /*
 AnalyticController controler = new AnalyticController();
 controler.stopAnalyticEngine("ae1");
        */
        
        
        DataAssetFunctionStreamingData daf = getDAF();
        
        AnalyticController controler = new AnalyticController();
        controler.submitDataAssetFunctionStreamingData("ae1", daf);
        Configuration.submitMOMConf("ae1");
        controler.startAnalyticEngine("ae1", daf.getDaFunctionID());
    
   
    }

    public static DataAssetFunctionStreamingData getDAF() {
        String xmlData = IOUtils.readData("daf1");
        System.out.println("" + xmlData);
        DataAssetFunctionStreamingData daf = null;
        try {
            daf = JAXBUtils.unmarshal(xmlData, DataAssetFunctionStreamingData.class);
            System.out.println("" + daf.getDaFunctionName());

        } catch (JAXBException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }

        return daf;
    }

}
