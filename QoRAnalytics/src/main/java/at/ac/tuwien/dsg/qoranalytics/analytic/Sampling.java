/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qoranalytics.analytic;

import at.ac.tuwien.dsg.daas.entities.CreateRowsStatement;
import at.ac.tuwien.dsg.daas.entities.Keyspace;
import at.ac.tuwien.dsg.daas.entities.RowColumn;
import at.ac.tuwien.dsg.daas.entities.Table;
import at.ac.tuwien.dsg.daas.entities.TableRow;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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
        System.out.println("Rows Size: " + rows.size() + " -  sRs " + samplingRows);

        List<Integer> samplingIndice = new ArrayList<>();
        samplingIndice = getSamplingIndice(rows.size(), samplingRows);

        Collection<TableRow> sampleRowsCollection = new ArrayList<>();
        Integer rowIndex = 0;
        for (TableRow row : rows) {
            if (samplingIndice.contains(rowIndex)) {
                sampleRowsCollection.add(row);
            }
            rowIndex++;
        }

        testSamplingResult(sampleRowsCollection);

        CreateRowsStatement crsRs = new CreateRowsStatement();
        crsRs.setTable(crs.getTable());
        crsRs.setRows(sampleRowsCollection);
        writeData(crsRs);

        System.out.println("Sampling Completed ...");
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

    private void writeData(CreateRowsStatement crs) {

        FileWriter fstream;
        try {
            String xmlData = marshal(crs, CreateRowsStatement.class);
            fstream = new FileWriter("temp1", false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(xmlData);
            out.close();
        } catch (Exception ex) {
        }
    }

    private <T> String marshal(Object source, Class<T> configurationClass) throws JAXBException {
        JAXBContext jAXBContext = JAXBContext.newInstance(configurationClass);
        StringWriter writer = new StringWriter();
        jAXBContext.createMarshaller().marshal(source, writer);
        return writer.toString();
    }

    private static int randInt(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    private List<Integer> getSamplingIndice(int noOfRows, int samplingRows) {

        List<Integer> samplingIndice = new ArrayList<>();

        for (int i = 0; i < samplingRows; i++) {
            Integer rdI = randInt(0, noOfRows - 1);
            if (!samplingIndice.contains(rdI)) {
                samplingIndice.add(rdI);
            }
        }

        return samplingIndice;
    }

    private void testSamplingResult(Collection<TableRow> sampleRowsCollection) {

        for (TableRow row : sampleRowsCollection) {

            System.out.println("Test RS - " + row.getValues().toString());
        }
    }

}
