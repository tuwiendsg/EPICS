/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.depicinstall;

/**
 *
 * @author Jun
 */
public class install {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ComotConnector comot = new ComotConnector();
        comot.setupDepic();
                
    }
    
}
