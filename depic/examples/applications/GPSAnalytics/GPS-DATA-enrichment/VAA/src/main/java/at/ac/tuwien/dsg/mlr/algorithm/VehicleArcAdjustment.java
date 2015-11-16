/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.mlr.algorithm;



import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class VehicleArcAdjustment {

   DataAsset dataAsset;
    int attributeIndex;
    double lowerThreshold;
    double upperThreshold;

    public VehicleArcAdjustment(DataAsset dataAsset, int attributeIndex) {
        copyDataAsset(dataAsset);
        this.attributeIndex = attributeIndex;
        lowerThreshold = 1;
        upperThreshold = 1;
    }
    
    
      public DataAsset applyControl() {

          
       List<Integer> removedDataItemIndex = new ArrayList<Integer>();
          
       List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();

           for (DataItem dataItem : listOfDataItems) {
            List<DataAttribute> listOfDataAttributes = dataItem.getListOfAttributes();
            if (listOfDataAttributes.size() > attributeIndex) {
                DataAttribute attribute = listOfDataAttributes.get(attributeIndex);
                if (isNumeric(attribute.getAttributeValue())) {
                    double attVal = Double.parseDouble(attribute.getAttributeValue());
                    
                    if (attVal == 0) {
                    
                        removedDataItemIndex.add(listOfDataItems.indexOf(dataItem));
                    }
                }
                
            }
            
        }
           
           
           for (Integer i : removedDataItemIndex) {
               listOfDataItems.remove(i);
           }
           
          
        return dataAsset;
    }

    private void copyDataAsset(DataAsset da) {

        String name = da.getDataAssetID();
        int partition = da.getPartition();
        
        List<DataItem> listOfDataItems = da.getListOfDataItems();
       List<DataItem> listOfDataItems_c = new ArrayList<DataItem>();
        for (DataItem di : listOfDataItems) {
            List<DataAttribute> listOfAttributes = di.getListOfAttributes();
            List<DataAttribute> listOfAttributes_c = new ArrayList<DataAttribute>();
           
            for (DataAttribute att: listOfAttributes){
                String attName = att.getAttributeName();
                String attValue = att.getAttributeValue();
                
                DataAttribute att_c = new DataAttribute(attName, attValue);
                listOfAttributes_c.add(att_c);
            }
            
            DataItem dataItem_c = new DataItem(listOfAttributes_c);
            listOfDataItems_c.add(dataItem_c);
        }
        
        this.dataAsset = new DataAsset(name, partition, listOfDataItems_c);
                
        
    }
    
     public double monitor() {

        List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();
        double accuracy = 0;
        //for (int k = 0; k < 80; k++) {

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
        //}

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
