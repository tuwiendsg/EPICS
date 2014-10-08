/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.utils;

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

    public static void writeData(String data, String fileName) {

        //String tomcatTempFolder = System.getProperty("java.io.tmpdir");
        String tomcatTempFolder="/Volumes/DATA/BigData";
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

    public static String readData(String fileName) {

        //String tomcatTempFolder = System.getProperty("java.io.tmpdir");
        String tomcatTempFolder="/Volumes/DATA/BigData";
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
