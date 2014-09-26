/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qoranalytics.analytic;

/**
 *
 * @author Jun
 */
public class KMeans {

    private int k;

    private static final KMeans INSTANCE = new KMeans();

    public static KMeans getInstance() {
        return INSTANCE;
    }

    public KMeans() {
    }

    public KMeans(int k) {
        this.k = k;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void start(int k, String attributes) {
        System.out.println("K-Means Starting ...");
        //algorithm
        setK(k);
        
        System.out.println("K-Means Completed ...");
    }

}
