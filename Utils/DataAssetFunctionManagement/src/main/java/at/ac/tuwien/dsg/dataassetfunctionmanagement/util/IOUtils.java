/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataassetfunctionmanagement.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Jun
 */
public class IOUtils {
  
    private static String tomcatTempFolder;

    public IOUtils() {
        tomcatTempFolder="/Volumes/DATA/Temp";
     //  tomcatTempFolder = System.getProperty("java.io.tmpdir");
        
    }
  
    

    public void writeData(String data, String fileName) {

        fileName =  tomcatTempFolder +"/" + fileName;
        FileWriter fstream;
        try {
            fstream = new FileWriter(fileName, false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(data);

            out.close();
        } catch (IOException ex) {
        }
    }

    public  String readData(String fileName) {

        fileName =  tomcatTempFolder +"/" + fileName;
        String data = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {

        }
        return data;
    }

}