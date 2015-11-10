/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.testws.tasktest;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import at.ac.tuwien.dsg.mlr.util.JAXBUtils;
import at.ac.tuwien.dsg.mlr.util.IOUtils;
import at.ac.tuwien.dsg.task.KMeans;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class KMeansTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //load data asset
        IOUtils iou = new IOUtils("/Volumes/DATA/BigData/mydb");
        String dataAssetXML = iou.readData("daf-gps-0.data");
        
        //System.out.println(dataAssetXML);
        
        // marshal
        DataAsset dataAsset = null;
        try {
            dataAsset = JAXBUtils.unmarshal(dataAssetXML, DataAsset.class);

        } catch (JAXBException ex) {
            Logger.getLogger(KMeansTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        // clustering
        List<Integer> listOfAttibuteIndice = new ArrayList<Integer>();
        listOfAttibuteIndice.add(1);
        listOfAttibuteIndice.add(2);
        
        KMeans kMeans = new KMeans(10, 20, 0.00001, 3, listOfAttibuteIndice);
        kMeans.clustering(dataAsset);
        
        
        //print result
        List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();
        int i=0;
        for (DataItem dataItem : listOfDataItems){
            List<DataAttribute> listOfDataAttributes = dataItem.getListOfAttributes();
            i++;
            System.out.println(
                    i+ ":\t" +
                    listOfDataAttributes.get(1).getAttributeValue()+ "\t" +
                    listOfDataAttributes.get(2).getAttributeValue()+ "\t" +
                    listOfDataAttributes.get(3).getAttributeValue()+ "\t" +
                    listOfDataAttributes.get(listOfDataAttributes.size()-1).getAttributeValue()+ "\t");
            
        }
        
    }
    
}
