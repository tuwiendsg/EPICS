/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.myorg;

import java.util.Vector;
import weka.core.*;

/**
 *
 * @author junnguyen
 */
public class AppConst {
   // public static int noOfLinesInDataChunk = 11621;
    
  //  public static int noOfLinesInDataChunk = 290506;
    public static int noOfLinesInDataChunk = 29050;
    public static int m_MaxIterations = 20;
    public static double e = Math.pow(10, -7);
    
    public static int m_NumClusters = 3;
    public static Vector<Instance> Centers;
    public static Vector<Instance> newCenters;
    
    
    
    public static int InterationNumber =0;
    public static void initializeCenters(){
        Centers = new Vector();
        newCenters = new Vector();
        
        double[] c1={1,1,1723,1,2.215,4082.52216298};
        double[] c2={2,5,1149,1,1.423,254.948322134};
        double[] c3={4,3,3067,1,0.553,617.287130901};
        
        Instance i_c1 = new DenseInstance(1.0, c1);
        Instance i_c2 = new DenseInstance(1.0, c2);
        Instance i_c3 = new DenseInstance(1.0, c3);
        Centers.add(i_c1);
        Centers.add(i_c2);
        Centers.add(i_c3);
    }
}
