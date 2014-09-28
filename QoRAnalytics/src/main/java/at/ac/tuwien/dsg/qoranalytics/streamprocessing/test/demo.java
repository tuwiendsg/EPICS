/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qoranalytics.streamprocessing.test;

import at.ac.tuwien.dsg.qoranalytics.connector.MOMConnector;

/**
 *
 * @author Jun
 */
public class demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        /*
        EventGenerator generator = new EventGenerator();
        generator.startSendingData();
         */
        MOMConnector connector = new MOMConnector();
        connector.openConnection();
                
    }
    
}
