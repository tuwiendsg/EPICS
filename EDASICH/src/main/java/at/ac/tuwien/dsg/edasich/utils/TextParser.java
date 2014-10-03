/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.edasich.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class TextParser {
    
   public static List<String> parseSensorName(String statement){
       List<String> listOfSensors = new ArrayList<>();
       
       String[] words = statement.split(",. ");
       
       for (String word : words) {
           if ((word.contains("Sensor")) && !listOfSensors.contains(word)){              
               listOfSensors.add(word);
           }
       }
      
       return listOfSensors;
   }
    
}
