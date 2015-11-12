/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.elasticdaasclient.statistic;

import at.ac.tuwien.dsg.depic.common.utils.IOUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class dataAssetCost {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        IOUtils iou = new IOUtils("/Volumes/DATA/Research/Cloud Computing/Experiment Result/round 3/61-100");
        String data = iou.readData("metricValues_5.csv");
        
        // index define
        int vechileAccuracyIndex = 0;
        int speedAccuracyIndex = 1;
        int deliveryTimeIndex = 2;
        
        
        
        
        // cost function define
        int noOfUser = 5;
        double unitCost = 0.0002;
        double dataAnalyticsCost  = unitCost;
        // 2, 3, 5
        // 0.33, 0.33, 0.33
        
        double vehicleAccuracyWeighFactor = 0.25;
        double speedAccuracyWeightFactor = 0.25;
        double deliveryTimeWeightFactor = 0.5;
        double rangeUnit = 20;
        
        
        System.out.println("");
        String[] lines = data.split("\n");
        
    

            for (int l = 0; l < lines.length; l++) {
      
                String[] strs = lines[l].split(",");
                
                double vehicleAccuracy = Double.parseDouble(strs[vechileAccuracyIndex]);
                double speedAccuracy = Double.parseDouble(strs[speedAccuracyIndex]);
                double deliveryTime = Double.parseDouble(strs[deliveryTimeIndex]);

                
                double totalCost = 0;
                
                
                
                
                double remainPart1 = vehicleAccuracy%rangeUnit;

                if (remainPart1>0 && remainPart1 <10) {
                    vehicleAccuracy+=10;
                }
                
                double remainPart2 = speedAccuracy%rangeUnit;

                if (remainPart2>0 && remainPart2 <10) {
                    speedAccuracy+=10;
                }
                
                double vehicleAccuracyCost = Math.round(vehicleAccuracy/rangeUnit)*unitCost*vehicleAccuracyWeighFactor;
                
                double speedAccusracyCost = Math.round(speedAccuracy/rangeUnit)*unitCost*speedAccuracyWeightFactor;
                
                
                
                
                
               
                if (deliveryTime<55000) {
                    double deliveryTimeCost = (unitCost*2 + dataAnalyticsCost/noOfUser)*deliveryTimeWeightFactor;
                    totalCost = vehicleAccuracyCost + speedAccusracyCost + deliveryTimeCost;
                    
                } else {
                    double deliveryTimeCost = (unitCost*1 + dataAnalyticsCost/noOfUser)*deliveryTimeWeightFactor;
                    totalCost = vehicleAccuracyCost + speedAccusracyCost + deliveryTimeCost;
                }
                
                
                System.out.println(String.valueOf(totalCost));
                
              
            }
         
          
            System.out.println("");
            
            //System.out.println(calculatedDataAssetID + "\t" + rs);
        }
    
    
    
    
    
    
}
