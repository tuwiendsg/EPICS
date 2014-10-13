/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.myorg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.DenseInstance;
import weka.core.Instance;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/**
 *
 * @author junnguyen
 */
public class Utilities {

    
   
    

    
    public  void printLogData(String logMsg) {
        
        
        //sendLogMessage(logMsg);
        System.out.println(logMsg);
        /*
        FileWriter fstream;


        try {
            fstream = new FileWriter("/usr/local/logs/testLog.txt", true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(logMsg + "\n");

            out.close();
        } catch (IOException ex) {
        }
                
                */
    }
    
    public void sendLogMessage(String message) {
        try {
            String encodedMessage = URLEncoder.encode(message, "UTF-8");
            
            String url="http://nguyentiendung.name.vn/dea/log.php?message=" + encodedMessage;
            
            URL oracle = new URL(url); //+word+".html");
            
            System.setProperty("http.agent", "Mozilla/16.0 (Macintosh; U; Intel Mac OS X 10.7; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));
            
            
            String inputLine = "";
            String soundLink="";
            while ((inputLine = in.readLine()) != null) {
                
                System.out.println(inputLine);
            }
            in.close();
            
        } catch (Exception ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
     
    }

 
    public int findNearestClusterIndex(Instance instance, String centersStr) {

        Vector<Instance> Centers = new Vector();

        String[] instancesStr = centersStr.split(";");

     //   printLogData3("FULL STRING" + centersStr);
        
        for (int z = 0; z < instancesStr.length; z++) {
     //        printLogData3("SIGNLE STRING" + instancesStr[z]);

            String[] atts = instancesStr[z].split(",");
            int numOfAttributes = atts.length;
            double[] d_atts = new double[numOfAttributes];

            for (int i = 0; i < numOfAttributes; i++) {
                d_atts[i] = Double.parseDouble(atts[i]);

            }

            Instance newCenterInstance = new DenseInstance(1.0, d_atts);
            Centers.add(newCenterInstance);
        }

        
        
        int nearestIndex = 0;
      //  printLogData3("CHECK POINT A");
        double nearestDistance = getDistanceBetweenInstances(instance, Centers.get(0));

       // printLogData3("CHECK POINT B");
        for (int i = 1; i < Centers.size(); i++) {
       //     printLogData3("CHECK POINT C");
            double distance2centerI = getDistanceBetweenInstances(instance,Centers.get(i));
       //     printLogData3("CHECK POINT D");
            if (distance2centerI < nearestDistance) {
                nearestDistance = distance2centerI;
                nearestIndex = i;
            }

        }


        return nearestIndex;

    }
    
    
    public double getDistanceBetweenInstances(Instance a, Instance b) {

    //    printLogData3("CHECK POINT A1");
        double distance = 0;
      //  printLogData3("CHECK POINT A2");
        for (int i = 0; i < a.numAttributes(); i++) {
            double difference = a.value(i) - b.value(i);
       //     printLogData3("CHECK POINT A Loop");
            distance += Math.pow(difference, 2);
        }

       // printLogData3("CHECK POINT A3");
        //distance = Math.sqrt(distance);
        return distance;
    }
    
    
   public boolean isCenterConverged() {
       
      
       
       
        boolean converged = true;
        
        
        

            for (int i = 0; i < AppConst.Centers.size(); i++) {

                double diff = Math.sqrt(getDistanceBetweenInstances(  AppConst.Centers.get(i), AppConst.newCenters.get(i)));
                if (diff > AppConst.e) {
                    converged = false;
                    break;
                }

            }
        
        
        
        
        return converged;
    }
   
   
   public void copyCenters(){
       
       for (int i=0;i<AppConst.Centers.size();i++){
           AppConst.Centers.set(i, AppConst.newCenters.get(i));
       }
       
   }
}
