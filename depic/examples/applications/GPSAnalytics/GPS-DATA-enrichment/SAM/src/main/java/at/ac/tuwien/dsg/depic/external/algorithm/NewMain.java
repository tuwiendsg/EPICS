/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.external.algorithm;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.utils.JAXBUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
         for (int i = 0; i < 100; i++) {
           
            MySqlDataAssetStore msdas = new MySqlDataAssetStore();
            
            String dataAssetID = "kmeans-daf-gps-" +String.valueOf(i);
      
            
            String daXML = msdas.getDataPartition(dataAssetID, "0");
            
            try {
                DataAsset da = JAXBUtils.unmarshal(daXML, DataAsset.class);
                //  System.out.println("" + da.getDataAssetID());
                
                SpeedAccuracyMonitor sam = new SpeedAccuracyMonitor(da);
                
                
                double accuracy = sam.monitor();
                System.out.println(accuracy);
            } catch (JAXBException ex) {
                Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }
    
}
