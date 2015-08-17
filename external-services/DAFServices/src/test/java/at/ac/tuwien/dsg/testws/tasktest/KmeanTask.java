/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.testws.tasktest;

import at.ac.tuwien.dsg.task.KMeans;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jun
 */
public class KmeanTask {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        List<Integer> listOfAttibuteIndice = new ArrayList<Integer>();
        listOfAttibuteIndice.add(1);
        listOfAttibuteIndice.add(2);
        
        KMeans kMeans = new KMeans(10, 20, 0.00001, 3, listOfAttibuteIndice);
        kMeans.start();
        
    }
    
}
