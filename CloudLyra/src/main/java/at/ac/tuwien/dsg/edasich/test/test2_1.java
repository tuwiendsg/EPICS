/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.test;


import at.ac.tuwien.dsg.edasich.entity.daf.DafDelegator;
import at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing.AnalyticResultDelegate;
import at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing.DafAnalyticCep;
import at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing.DafInputCep;
import at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing.DafOutputCep;
import at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing.DataAnalyticFunctionCep;
import at.ac.tuwien.dsg.edasich.entity.daf.dataformat.Table;
import at.ac.tuwien.dsg.edasich.entity.daf.dataformat.TableAttribute;
import at.ac.tuwien.dsg.edasich.entity.daf.dataformat.TableSchema;
import at.ac.tuwien.dsg.edasich.entity.daf.datasource.DataSourceMOM;
import at.ac.tuwien.dsg.edasich.entity.daf.datasource.DataSourceMySQL;
import at.ac.tuwien.dsg.edasich.utils.IOUtils;
import at.ac.tuwien.dsg.edasich.utils.JAXBUtils;
import at.ac.tuwien.dsg.salam.Task;


import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Jun
 */
public class test2_1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws JAXBException {
        
 
        
        DafInputCep dafInputCep = inputDataSource();
        DafOutputCep dafOutputCep = outputDataSouce();
        DafAnalyticCep dafAnalyticCep = sampleAnalytic();
        AnalyticResultDelegate analyticResultDelegate = sampleAnalyticResultDelegate();
        
        DataAnalyticFunctionCep daf = new DataAnalyticFunctionCep("fcu_ff1_space_temp", dafInputCep, dafOutputCep, dafAnalyticCep, analyticResultDelegate);
        
        
        String xml = JAXBUtils.marshal(daf, DataAnalyticFunctionCep.class);
        
        IOUtils.writeData(xml, "fcu_ff1_space_temp");
        
        System.out.println(xml);
        
        
        
    }

   
    
    
    public static DafInputCep inputDataSource(){
        
        
        TableAttribute att1 = new TableAttribute("name", "String");
        TableAttribute att2 = new TableAttribute("value", "Double");
        TableAttribute att3 = new TableAttribute("timeOfReading", "Date");
        
        List<TableAttribute> listOfAttributes = new ArrayList<>();
        listOfAttributes.add(att1);
        listOfAttributes.add(att2);
        listOfAttributes.add(att3);
        
        Table tab = new Table("SensorEvent", listOfAttributes);
        
        List<Table> listOfTables = new ArrayList<>();
        
        listOfTables.add(tab);
        
        TableSchema inputDataFormat = new TableSchema(listOfTables);
        DataSourceMOM inputDataSource = new DataSourceMOM("10.99.0.53", "9124", "DB_LOG");
        
        DafInputCep input = new DafInputCep(inputDataFormat, inputDataSource);
        
        return input;
        
    }
    
    
    public static DafOutputCep outputDataSouce(){
        
        
        TableAttribute att1 = new TableAttribute("name", "String");
        TableAttribute att2 = new TableAttribute("value", "Double");
        TableAttribute att3 = new TableAttribute("timeOfReading", "Date");
        
        List<TableAttribute> listOfAttributes = new ArrayList<>();
        listOfAttributes.add(att1);
        listOfAttributes.add(att2);
        listOfAttributes.add(att3);
        
        Table tab = new Table("SensorEvent", listOfAttributes);
        
        List<Table> listOfTables = new ArrayList<>();
        
        listOfTables.add(tab);
        
        TableSchema outputDataFormat = new TableSchema(listOfTables);
        DataSourceMySQL outDataSource = new DataSourceMySQL("localhost", "3306", "EDASICH", "root", "123");   
        DafOutputCep dafOutputCep = new DafOutputCep(outputDataFormat, outDataSource);
        
        
        return dafOutputCep;
        
    }
    
    public static DafAnalyticCep sampleAnalytic(){
        
        String epl = "select * from SensorEvent match_recognize ( measures A as val1 pattern (A) define A as A.value < 24 and A.value > 23 and A.name ='fcu_ff1_space_temp')";
        String enrichmentURI = "http://128.130.172.230:8080/DataEnrichment/rest/sensor/";
        DafAnalyticCep dafAnalyticCep = new DafAnalyticCep(epl, enrichmentURI);
        
        return dafAnalyticCep;
    }
    
    public static AnalyticResultDelegate sampleAnalyticResultDelegate(){
        
       // String delegateMessage="<task><taskID>id01</taskID><taskName>CLEAN EVAPORATOR.</taskName><taskContent>Evaporator is fouling.</taskContent><tag>IMPLEMENTATION</tag><severity>WARNING</severity></task>";
        DafDelegator delegator = new DafDelegator("128.130.172.191", "8080", "http://128.130.172.191:8080/rest/api/task");
        
        
        Task delegateMessage = new Task(1, "CLEAN EVAPORATOR", "Evaporator is fouling", "IMPLEMENTATION", Task.SeverityLevel.WARNING);
        AnalyticResultDelegate analyticResultDelegate = new AnalyticResultDelegate(delegateMessage, delegator);
        
        
        
        
        
        
        return  analyticResultDelegate;
    }
    
   
    
}
