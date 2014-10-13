/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.jbpmengine.hadoop;

/**
 *
 * @author Jun
 */
public class Query {
    private static final Query INSTANCE = new Query();

    public static Query getInstance() {
        return INSTANCE;
    }

    public void start(String param) {
        System.out.println("Query Starting ...");
        //algorithm
        System.out.println("SQL: " + param);

    }
}
