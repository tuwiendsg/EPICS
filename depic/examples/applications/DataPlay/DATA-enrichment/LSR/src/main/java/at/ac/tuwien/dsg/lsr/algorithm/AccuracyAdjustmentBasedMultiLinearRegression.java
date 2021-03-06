/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.lsr.algorithm;

import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAsset;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataAttribute;
import at.ac.tuwien.dsg.depic.common.entity.eda.dataasset.DataItem;
import java.util.List;

/**
 *
 * @author Jun
 */
public class AccuracyAdjustmentBasedMultiLinearRegression {
    
    private int attributeIndex;
    private double thresholdAlpha;
    private double thresholdBeta;
    private int noOfSegments;

    public AccuracyAdjustmentBasedMultiLinearRegression() {
        attributeIndex = 1;
        thresholdAlpha = 5;
        thresholdBeta = 10;
        noOfSegments = 10;

    }
    
    public DataAsset adjustAccuracy(DataAsset dataAsset) {
     
        double avgAccuracy = 0;

        List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();

        int segmentMaxElement = listOfDataItems.size() / noOfSegments;

    

        for (int s = 0; s < noOfSegments; s++) {
            
            int startIndex = segmentMaxElement*s;
            int endIndex = startIndex + segmentMaxElement-1;
            //System.out.println("start-end: " + startIndex + "-" + endIndex);

                 int n = 0;

            double sum_x = 0;
            double sum_x2 = 0;
            double sum_y = 0;
            double sum_xy = 0;
                int validObjCounter = 0;
            

            for (int i = 0; i < listOfDataItems.size(); i++) {
                
           
                if (i>= startIndex && i<=endIndex) {
                
                DataItem item = listOfDataItems.get(i);

                List<DataAttribute> dataAttributes = item.getListOfAttributes();

                DataAttribute dataAttributeX = new DataAttribute(dataAttributes.get(0).getAttributeName(), String.valueOf(i));

                DataAttribute dataAttributeY = dataAttributes.get(attributeIndex);

                if (isNumeric(dataAttributeY.getAttributeValue())) {

                    double x = Double.parseDouble(dataAttributeX.getAttributeValue());
                    double y = Double.parseDouble(dataAttributeY.getAttributeValue());

                    if ((x == 0.0) && (y == 0.0)) {

                    } else if (y == 0.0) {

                    } else {
                        n++;
                        sum_x += x;
                        sum_y += y;
                        sum_x2 += x * x;
                        sum_xy += x * y;

                    }
                }
                }
                
            }

            double slope = (1.0 * n * sum_xy - sum_x * sum_y) / (1.0 * n * sum_x2 - sum_x * sum_x);
            double intercept;
            intercept = sum_y / n - slope * (sum_x / n);

            int outlier = 0;
            for (int i = 0; i < listOfDataItems.size(); i++) {
                
                if (i>= startIndex && i<=endIndex) {
                
                
                DataItem item = listOfDataItems.get(i);

                List<DataAttribute> dataAttributes = item.getListOfAttributes();
                DataAttribute dataAttributeX = new DataAttribute(dataAttributes.get(0).getAttributeName(), String.valueOf(i));
                DataAttribute dataAttributeY = dataAttributes.get(attributeIndex);

                if (isNumeric(dataAttributeY.getAttributeValue())) {

                    double x = Double.parseDouble(dataAttributeX.getAttributeValue());

                    if (x == 0.0 && i != 0) {
                        DataItem previousItem = listOfDataItems.get(i - 1);

                        List<DataAttribute> preDataAttributes = previousItem.getListOfAttributes();
                        dataAttributeX = preDataAttributes.get(0);

                        x = Double.parseDouble(dataAttributeX.getAttributeValue());
                    }

                    double y = Double.parseDouble(dataAttributeY.getAttributeValue());

                    if (y != 0) {
                        double y_i = x * slope + intercept;

                        System.out.println(y+","+y_i);
                        
                        if (Math.abs(y_i - y) > thresholdBeta) {
                            outlier++;
  
                            dataAttributeY.setAttributeValue(String.valueOf(y_i));
                        
                            
                        }
                        
                        validObjCounter++;
                        
                    }
                }
                
                }
                

            }
            
        }
        
   
        
        return dataAsset;
    }
    
    
    public boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    
    private DataAttribute getAttributeFromName(List<DataAttribute> dataAttributes, String attributeName){
        
        DataAttribute attr = null;
        
        for (DataAttribute dataAttribute : dataAttributes){
            if (dataAttribute.getAttributeName().equals(attributeName)) {
                attr = dataAttribute;
                break;
            }
            
        }
        
        return  attr;
    }
}
