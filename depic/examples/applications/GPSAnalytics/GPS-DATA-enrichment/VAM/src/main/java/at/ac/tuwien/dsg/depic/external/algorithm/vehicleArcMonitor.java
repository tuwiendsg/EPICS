/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.external.algorithm;



import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import java.util.List;

/**
 *
 * @author Jun
 */
public class vehicleArcMonitor {
    
    DataAsset dataAsset;
    int attributeIndex;
    double lowerThreshold;
    double upperThreshold;

    public vehicleArcMonitor(DataAsset dataAsset) {
        this.dataAsset = dataAsset;
        this.attributeIndex = 3;
        lowerThreshold=1;
        upperThreshold=1;
    }

  
    
    
    

    public double monitor() {
        double accuracy = 0;
      

        List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();


            int outlierCounter = 0;
           for (DataItem dataItem : listOfDataItems) {
            List<DataAttribute> listOfDataAttributes = dataItem.getListOfAttributes();
            if (listOfDataAttributes.size() > attributeIndex) {
                DataAttribute attribute = listOfDataAttributes.get(attributeIndex);

//System.out.println("Att: " + attribute.getAttributeName() + " - " + attribute.getAttributeValue());
                if (isNumeric(attribute.getAttributeValue())) {
                    double attVal = Double.parseDouble(attribute.getAttributeValue());
                    
                    if (attVal == 0) {
                        outlierCounter++;
                    }
                }
                
            }
            
        }
        accuracy = 100 - outlierCounter * 1.0 / listOfDataItems.size() * 100;
    
        
        return accuracy;

    }

    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
