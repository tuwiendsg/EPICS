/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.esperstreamprocessing.test;

import at.ac.tuwien.dsg.esperstreamprocessing.entity.SalamTask;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.JSONUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Jun
 */
public class test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      MoMClient c = new MoMClient();
      c.run();
    }
    
}
