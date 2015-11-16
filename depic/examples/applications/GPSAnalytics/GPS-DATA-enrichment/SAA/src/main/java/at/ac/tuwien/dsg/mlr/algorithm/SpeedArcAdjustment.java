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
public class SpeedArcAdjustment {

   DataAsset dataAsset;
    int speedIndex;
    double lowerThreshold;
    double upperThreshold;

    public SpeedArcAdjustment(DataAsset dataAsset, int attributeIndex) {
        copyDataAsset(dataAsset);
        speedIndex = 3;
        lowerThreshold = 1;
        upperThreshold = 1;
    }
    
    
      public DataAsset applyControl() {

        for (int k=0;k<80;k++){  
          
        List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();
        
        int n = 0;

        double sum_x = 0;
        double sum_x2 = 0;
        double sum_y = 0;
        double sum_xy = 0;

        
        for (int i = 0; i < listOfDataItems.size(); i++) {
                DataItem item = listOfDataItems.get(i);
            
            List<DataAttribute> dataAttributes = item.getListOfAttributes();
            
            DataAttribute dataAttributeX = new DataAttribute(dataAttributes.get(0).getAttributeName(), String.valueOf(i));
            DataAttribute dataAttributeY = dataAttributes.get(speedIndex);
            
            double x = Double.parseDouble(dataAttributeX.getAttributeValue());
            double y = Double.parseDouble(dataAttributeY.getAttributeValue());
            
            if ((x == 0.0) && (y == 0.0)) {

            } else {
                n++;
                sum_x += x;
                sum_y += y;
                sum_x2 += x * y;
                sum_xy += x * y;

            }
        }

        double slope = (1.0 * n * sum_xy - sum_x * sum_y) / (1.0 * n * sum_x2 - sum_x * sum_x);
        double intercept;
        intercept = sum_y / n - slope * (sum_x / n);

        
        int outlier=0;
        for (int i = 0; i < listOfDataItems.size(); i++) {
            DataItem item = listOfDataItems.get(i);

            List<DataAttribute> dataAttributes = item.getListOfAttributes();
            DataAttribute dataAttributeX = new DataAttribute(dataAttributes.get(0).getAttributeName(), String.valueOf(i));
            DataAttribute dataAttributeY = dataAttributes.get(speedIndex);

            double x = Double.parseDouble(dataAttributeX.getAttributeValue());

            if (x == 0.0 && i != 0) {
                DataItem previousItem = listOfDataItems.get(i-1);
                    
                    List<DataAttribute> preDataAttributes = previousItem.getListOfAttributes();
                    dataAttributeX = preDataAttributes.get(0);
                    
                    x = Double.parseDouble(dataAttributeX.getAttributeValue());
                }
                
                double y = Double.parseDouble(dataAttributeY.getAttributeValue());

                double y_i = x * slope + intercept;
                
  
                dataAttributeY.setAttributeValue(String.valueOf(y_i));
                       
            }
        
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
    
    
   

   
}
