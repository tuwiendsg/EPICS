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
public class costVM {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        IOUtils iou = new IOUtils("/Volumes/DATA/Research/Cloud Computing/Experiment Result/5RS/81-100");
        String data1 = iou.readData("depic_monitor_5.xml");
        String data2 = iou.readData("vm_log.xml");
        System.out.println("");
        String[] lines1 = data1.split("\n");
        String[] lines2 = data2.split("\n");
        
     
        String[] tmp = lines1[0].split("\t");
        long markTime = Long.parseLong(tmp[0]);

        for (int k = 0; k < 100; k++) {
            int dataAssetIDIndex = 2;
            int executionTimeIndex = 3;
            int timelineIndex = 0; 
            
            String calculatedDataAssetID = "daf-gps-" + String.valueOf(k);

            List<Double> avgs = new ArrayList<Double>();
            List<Integer> avgVM = new ArrayList<Integer>();

            for (int l = 0; l < lines1.length; l++) {

                String[] strs = lines1[l].split("\t");
                String dataAssetID = strs[dataAssetIDIndex];
                long timeL = Long.parseLong(strs[timelineIndex]);
                long dif = timeL-markTime;
                int vnIndex = Math.round(dif/10000); 
                if (vnIndex>=0) {
                double executionTime = Double.valueOf(strs[executionTimeIndex]);

                if (dataAssetID.equals(calculatedDataAssetID)) {
                    avgs.add(executionTime);
                    avgVM.add(Integer.parseInt(lines2[vnIndex]));
                }
                }
                
            }
            double rs = 0;
            for (Double num : avgs) {
                rs += num;
            }
            
            rs = rs / avgs.size();
            
            
            double rsVM = 0;
            for (Integer num : avgVM){
                rsVM +=num;
            }
            rsVM = rsVM / avgVM.size();
            System.out.println(rsVM);
            
            //System.out.println(calculatedDataAssetID + "\t" + rs);
        }
    }
    
}
