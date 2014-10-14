/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.dataconfiguration;

/**
 *
 * @author dsg
 */
public class Retrive {
    public static void main(String []p)
    {
        System.out.println("virtuoso ip: "+Configuration.getConfig("VIRTUOSO.IP"));
        System.out.println("virtuoso port: "+Configuration.getConfig("VIRTUOSO.PORT"));
        System.out.println("ENHANCEMENT ip: "+Configuration.getConfig("ENHANCEMENT.IP"));
        System.out.println("ENHANCEMENT port: "+Configuration.getConfig("ENHANCEMENT.PORT"));
    }
}
