/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qualityevaluator.datacompleteness;

import at.ac.tuwien.dsg.common.entity.DataItem;
import at.ac.tuwien.dsg.common.entity.DataObject;
import java.util.List;

/**
 *
 * @author Jun
 */
public class DataCompletenessMeasurement {
    
    int[] activeAttrs = {2,3,4,5};
    
    public double evaluateDataCompleteness(DataObject dataObject){
        
    
        
        List<DataItem> listOfDataItems = dataObject.getListOfDataItems();
        int noOfMissingValues =0;
        
        for (DataItem dataItem : listOfDataItems ) {
            
            String[] vals = dataItem.getValue().split(";");
            for (int i=0;i<vals.length;i++) {
                
                if (isActiveAttr(i)) {
                    double attrVal = Double.parseDouble(vals[i]);
                    if (attrVal==0) {
                        noOfMissingValues++;
                    }
                }
                
                
            }
            
            
        }
        
        double rs = 100.0*(listOfDataItems.size()-noOfMissingValues)/listOfDataItems.size();
        
        
        return rs;
    }
    
    private boolean isActiveAttr(int attrIndex){
       
        boolean rs = false;
        
        for (int i=0;i<activeAttrs.length;i++) {
            if (activeAttrs[i]==attrIndex) {
                rs = true;
                break;
            }
            
        }
        
        return  rs;
    }
    
    
}
