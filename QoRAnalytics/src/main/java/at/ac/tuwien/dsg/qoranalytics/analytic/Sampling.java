/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qoranalytics.analytic;

import at.ac.tuwien.dsg.daas.entities.CreateRowsStatement;
import at.ac.tuwien.dsg.daas.entities.RowColumn;
import at.ac.tuwien.dsg.daas.entities.Table;
import at.ac.tuwien.dsg.daas.entities.TableRow;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Jun
 */
public class Sampling {

    private static final Sampling INSTANCE = new Sampling();

    public static Sampling getInstance() {
        return INSTANCE;
    }

    public void start(double samplingPercentage) {
        System.out.println("Sampling Starting ...");
        //algorithm

        CreateRowsStatement crs = loadData();
          
        Collection<TableRow> rows = crs.getRows();
        int samplingRows = (int) Math.ceil(rows.size() * samplingPercentage / 100);
        System.out.println("sRs: " + samplingRows);
        
        List<Integer> samplingIndice = new ArrayList<>();
        samplingIndice = getSamplingIndice(rows.size(),samplingRows);
        
        Collection<TableRow> sampleRowsCollection = new ArrayList<>();
        int rowIndex=0;
        for (TableRow row : rows) {
            if (isSampling(rowIndex,samplingIndice)) {
                sampleRowsCollection.add(row);
            }

        }

        System.out.println("Sampling Completed ...");
    }

    public CreateRowsStatement loadData() {

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

    public static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
    
    public List<Integer> getSamplingIndice(int noOfRows, int samplingRows){
        
        List<Integer> samplingIndice = new ArrayList<>();
        
        for (int i=0;i<samplingRows;i++) {
           Integer rdI = randInt(0,noOfRows-1);
           samplingIndice.add(rdI);
        }
        
        
        
        return  samplingIndice;
    }
    
    private boolean isSampling(int rowIndex, List<Integer> samplingIndice) {
        
        
        return true;
    }
    
    

}
