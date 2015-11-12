/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.testws.tasktest;

import at.ac.tuwien.dsg.task.MySQLOutputer;

/**
 *
 * @author Jun
 */
public class TestMySQLOutputer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        MySQLOutputer mySQLOutputer = new MySQLOutputer();
        mySQLOutputer.start();
        
    }
    
}
