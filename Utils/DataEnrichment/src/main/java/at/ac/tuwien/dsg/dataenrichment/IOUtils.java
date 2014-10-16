/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataenrichment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class IOUtils {

    public void writeData(String data, String filePath) {


        FileWriter fstream;
        try {
            fstream = new FileWriter(filePath, false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(data);

            out.close();
        } catch (IOException ex) {
            Logger.getLogger(IOUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
