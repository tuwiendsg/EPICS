/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.datagovernance.monitoring;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 * @author Jun
 */
public class DataAccuracy {
    
    
    public String getDataAccuracyPrice () {

        
        String str = "";
        try {
            URL oracle = new URL("http://128.130.172.216/elfinder/files/download/dataAccuracyPricing.xml");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                str += inputLine;
            }
            in.close();

        } catch (Exception ex) {

        }

        return str;
   
}

    
}
