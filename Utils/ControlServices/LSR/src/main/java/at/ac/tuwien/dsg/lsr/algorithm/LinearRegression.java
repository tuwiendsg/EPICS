/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.lsr.algorithm;



import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAttribute;
import at.ac.tuwien.dsg.common.entity.eda.da.DataItem;
import java.util.List;

/**
 *
 * @author Jun
 */
public class LinearRegression {

   
    public LinearRegression() {
        
    }

    public DataAsset improveDataCompleteness(DataAsset dataAsset, double datacompleteness,String[] attributes) {
     
        List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();

        int noOfAttributes = attributes.length;
        
        for (int attCounter=1;attCounter<noOfAttributes;attCounter++) {
        
        int n = 0;

        double sum_x = 0;
        double sum_x2 = 0;
        double sum_y = 0;
        double sum_xy = 0;

        for (DataItem item : listOfDataItems) {
            
            List<DataAttribute> dataAttributes = item.getListOfAttributes();
            
            DataAttribute dataAttributeX = getAttributeFromName(dataAttributes,attributes[0]);
            DataAttribute dataAttributeY = getAttributeFromName(dataAttributes,attributes[attCounter]);
            
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
          
            for (int i = 0; i < listOfDataItems.size(); i++) {
                DataItem item = listOfDataItems.get(i);
                
                List<DataAttribute> dataAttributes = item.getListOfAttributes();
                DataAttribute dataAttributeX = getAttributeFromName(dataAttributes,attributes[0]);
                DataAttribute dataAttributeY = getAttributeFromName(dataAttributes,attributes[attCounter]);
                
                double x = Double.parseDouble(dataAttributeX.getAttributeValue());
                
                if (x==0.0 && i!=0) {
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
