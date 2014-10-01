/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.qoranalytics.batchjobprocessing.analytic;

import at.ac.tuwien.dsg.daas.entities.CreateRowsStatement;
import at.ac.tuwien.dsg.daas.entities.RowColumn;
import at.ac.tuwien.dsg.daas.entities.TableRow;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Jun
 */
public class LeastSquareRegression {

    private static final LeastSquareRegression INSTANCE = new LeastSquareRegression();

    public static LeastSquareRegression getInstance() {
        return INSTANCE;
    }

    public LeastSquareRegression() {
    }

    public void start(int numberOfFolds, int attributeIndex) {
        System.out.println("LeastSquareRegression Starting ...");
        //algorithm
        CreateRowsStatement crs = loadData();
        Collection<TableRow> rows = crs.getRows();

        int noOfRowsInFold = (int) Math.ceil(rows.size() / numberOfFolds);
        int foldIndex = 0;
        int rowIndex = 0;
        double sum_x = 0;
        double sum_x2 = 0;
        double sum_y = 0;
        double sum_xy = 0;

        for (TableRow row : rows) {
            Collection<RowColumn> rowColumns = row.getValues();

            int columnIndex = 0;
            double val = 0;
            for (RowColumn column : rowColumns) {
                if (columnIndex == attributeIndex) {
                    val = Double.parseDouble(column.getValue());
                }

                columnIndex++;
            }

            if (rowIndex >= 0 && rowIndex < noOfRowsInFold) {
                sum_x += rowIndex;
                sum_y += val;
                sum_x2 = sum_x2 + Math.pow(rowIndex, 2);
                sum_xy = sum_xy + rowIndex * val;
            }
            int n= noOfRowsInFold;
            double slope = (1.0 * n * sum_xy - sum_x * sum_y) / (1.0 * n * sum_x2 - sum_x * sum_x);
            double intercept = sum_y / (double) n - slope * (sum_x / (double) n);
            rowIndex++;

        }
        
        writeData(crs);

        System.out.println("LeastSquareRegression Completed ...");
    }

    private CreateRowsStatement loadData() {

        CreateRowsStatement crs = null;
        try {
            FileReader inputFile = new FileReader("temp1");
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
            fstream = new FileWriter("temp2", false);
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
}
