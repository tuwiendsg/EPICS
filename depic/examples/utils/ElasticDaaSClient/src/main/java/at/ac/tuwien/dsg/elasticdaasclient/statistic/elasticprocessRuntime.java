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
public class elasticprocessRuntime {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        IOUtils iou = new IOUtils("/Volumes/DATA/Research/Cloud Computing/Experiment Result/round 3/61-100");
        String data = iou.readData("depic_monitor_5.xml");
        System.out.println("");
        String[] lines = data.split("\n");

        for (int k = 0; k < 100; k++) {
            int dataAssetIDIndex = 2;
            int executionTimeIndex = 3;
            String calculatedDataAssetID = "daf-gps-" + String.valueOf(k);

            List<Double> avgs = new ArrayList<Double>();

            for (int l = 0; l < lines.length; l++) {
             //   System.out.println("line l: " + lines[l]);
                
               // if (!lines[l].equals("")){
                String[] strs = lines[l].split("\t");
                String dataAssetID = strs[dataAssetIDIndex];
                //System.out.println("-" + strs[executionTimeIndex] + "-");
                double executionTime = Double.valueOf(strs[executionTimeIndex]);

                if (dataAssetID.equals(calculatedDataAssetID)) {
                    avgs.add(executionTime);
                }
               // }
                
            }
            double rs = 0;
            for (Double num : avgs) {
                rs += num;
            }
            rs = rs / avgs.size();
            System.out.println(rs);
            
            //System.out.println(calculatedDataAssetID + "\t" + rs);
        }
    }

}
