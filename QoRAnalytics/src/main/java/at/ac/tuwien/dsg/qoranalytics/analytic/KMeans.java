/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qoranalytics.analytic;

import at.ac.tuwien.dsg.daas.entities.CreateRowsStatement;
import at.ac.tuwien.dsg.daas.entities.TableRow;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Jun
 */
public class KMeans {

    private int k;
    private List<TableRow> clusterCenters;
    private Collection<TableRow> rows;

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
        CreateRowsStatement crs = loadData();
        this.rows =  crs.getRows();
        selectKCenters();
        
        do {
            
            clusteringObjects();
            findNearestClusters();
            
        } while (meetStopCondition()) ;
     
        System.out.println("K-Means Completed ...");
    }
    
    private void findNearestClusters(){
        
    }
    
    private void selectKCenters(){
        
       int rowIndex=0;
       for (TableRow row: clusterCenters) {
           //TableRow ta
       }
        
    }
    
    private void clusteringObjects(){
        
    }
    
    private boolean meetStopCondition(){
        
        
        return true;
    }
    
    private CreateRowsStatement loadData() {

        CreateRowsStatement crs = null;
        try {
            FileReader inputFile = new FileReader("temp");
            BufferedReader bufferReader = new BufferedReader(inputFile);
            String dataStr = "";
            String line = "";
            while ((line = bufferReader.readLine()) != null) {
                System.out.println(line);
                dataStr += line;
            }

            JAXBContext bContext = JAXBContext.newInstance(CreateRowsStatement.class);
            Unmarshaller um = bContext.createUnmarshaller();
            crs = (CreateRowsStatement) um.unmarshal(new StringReader(dataStr));

            bufferReader.close();
        } catch (Exception e) {

        }

        return crs;

    }

}
