/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.dataaccuracymonitor.algorithm;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAttribute;
import at.ac.tuwien.dsg.common.entity.eda.da.DataItem;
import java.util.List;



/**
 *
 * @author Jun
 */
public class DataAccuracyMonitor {

    DataAsset dataAsset;
    int attributeIndex;
    double lowerThreshold;
    double upperThreshold;

    public DataAccuracyMonitor(DataAsset dataAsset) {
        this.dataAsset = dataAsset;
        attributeIndex=4;
        lowerThreshold=220;
        upperThreshold=245;
    }
    
    
   public double measureDataAccuracy(){
       
        List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();
        
        int outlierCounter=0;
        for (DataItem dataItem : listOfDataItems){         
            List<DataAttribute> listOfDataAttributes = dataItem.getListOfAttributes();
            
            DataAttribute attribute = listOfDataAttributes.get(attributeIndex);
            
            System.out.println("Att: " + attribute.getAttributeName() + " - " + attribute.getAttributeValue());
            double attVal = Double.parseDouble(attribute.getAttributeValue());
            
            if(attVal<lowerThreshold || attVal>upperThreshold){
                outlierCounter++;
            }
            
            
        }
       
        double accuracy = 100- outlierCounter*1.0/listOfDataItems.size()*100;
       
       
       return accuracy;
   }
}
