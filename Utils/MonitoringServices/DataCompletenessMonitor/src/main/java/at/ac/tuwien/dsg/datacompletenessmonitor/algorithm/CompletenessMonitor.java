/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.datacompletenessmonitor.algorithm;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.entity.eda.da.DataAttribute;
import at.ac.tuwien.dsg.common.entity.eda.da.DataItem;
import java.util.List;

/**
 *
 * @author Jun
 */
public class CompletenessMonitor {
    
    DataAsset dataAsset;

    public CompletenessMonitor(DataAsset dataAsset) {
        this.dataAsset = dataAsset;
    }
    
    
    
    public double measureDataCompleteness(){
        
        List<DataItem> listOfDataItems = dataAsset.getListOfDataItems();
        int noOfMissingDataItems = 0;
        
        
        for (DataItem dataItem : listOfDataItems){         
            List<DataAttribute> listOfDataAttributes = dataItem.getListOfAttributes();
            
            for (DataAttribute dataAttribute : listOfDataAttributes) {
                if (dataAttribute.getAttributeValue().equals("") || dataAttribute.getAttributeValue().equals("0")){
                    noOfMissingDataItems++;
                    break;
                }
            }
        }
        double completeness =-1;
        
        System.out.println("Total Data Items: " + listOfDataItems.size());
        System.out.println("Missing Values: " + noOfMissingDataItems);
        
        if (listOfDataItems.size()!=0) {
            completeness = (1 - noOfMissingDataItems*1.0/listOfDataItems.size())*100;
        }
        return completeness;
    }
    
}
