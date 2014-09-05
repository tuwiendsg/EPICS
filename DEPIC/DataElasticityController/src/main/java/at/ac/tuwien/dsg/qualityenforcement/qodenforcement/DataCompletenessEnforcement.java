/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qualityenforcement.qodenforcement;


import at.ac.tuwien.dsg.common.entity.DataItem;
import at.ac.tuwien.dsg.common.entity.DataObject;
import java.util.List;

/**
 *
 * @author Jun
 */
public class DataCompletenessEnforcement {

   
    public DataCompletenessEnforcement() {
        
    }


    public DataObject improveDataCompleteness(DataObject dataObject, double datacompleteness) {

     
        List<DataItem> listOfDataItems = dataObject.getListOfDataItems();

    
        int n = 0;

        double sum_x = 0;
        double sum_x2 = 0;
        double sum_y = 0;
        double sum_xy = 0;

        for (DataItem item : listOfDataItems) {
            String[] vals = item.getValue().split(";");
            double x = Double.parseDouble(vals[2]);
            double y = Double.parseDouble(vals[3]);
            
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
   
       
                 
            for (int i = 0; i < listOfDataItems.size() * 0.1; i++) {
                DataItem item = listOfDataItems.get(i);
                String[] vals = item.getValue().split(";");
                double x = Double.parseDouble(vals[2]);
                
                if (x==0.0 && i!=0) {
                    DataItem previousItem = listOfDataItems.get(i-1);
                    String[] preVals = previousItem.getValue().split(";");
                    x = Double.parseDouble(preVals[2]);
                }
                
                double y = Double.parseDouble(vals[3]);

                double y_i = x * slope + intercept;
                
                String newVals ="";
                
                for (int z=0;z< vals.length;z++) {
                    
                    if (z==2) {
                        newVals += x;
                    } else if (z==3) {
                        newVals += y_i;
                    } else {
                        newVals += vals[z];
                    }
       
                }
                
                item.setValue(newVals);

            }


        

        return dataObject;
    }
    
    
    

   
}
