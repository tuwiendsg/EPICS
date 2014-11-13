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
public class pacificcontrol_3sensors {

    /**
     * @param args the command line arguments
     */
    
    final static String machine ="";
    
    public static void main(String[] args) throws JAXBException {
        
        String dafName = "evaporator_fouling_analytic";
        
        DafInputCep dafInputCep = inputDataSource();
        DafOutputCep dafOutputCep = outputDataSouce();
        DafAnalyticCep dafAnalyticCep = sampleAnalytic();
        AnalyticResultDelegate analyticResultDelegate = sampleAnalyticResultDelegate();
        
        DataAnalyticFunctionCep daf = new DataAnalyticFunctionCep(dafName, dafInputCep, dafOutputCep, dafAnalyticCep, analyticResultDelegate);
        
        
        String xml = JAXBUtils.marshal(daf, DataAnalyticFunctionCep.class);
        
        IOUtils.writeData(xml, dafName);
        
        System.out.println(xml);
        
        
        
    }

   
    
    
    public static DafInputCep inputDataSource(){
        
        List<Table> listOfTables = new ArrayList<>();
        
        
        
        listOfTables.add(inTable("chw_supply_temp" + machine));
        listOfTables.add(inTable("fcu_ff1_space_temp" + machine));
        listOfTables.add(inTable("fcu_ff1_set_point" + machine));
        
        TableSchema inputDataFormat = new TableSchema(listOfTables);
        DataSourceMOM inputDataSource = new DataSourceMOM("10.99.0.88", "9124", "DB_LOG");
        
        DafInputCep input = new DafInputCep(inputDataFormat, inputDataSource);
        
        return input;
        
    }
    
    public static Table inTable(String tabName){
        
        TableAttribute att1 = new TableAttribute("name", "String");
        TableAttribute att2 = new TableAttribute("value", "Double");
        TableAttribute att3 = new TableAttribute("timeOfReading", "Date");
        
        List<TableAttribute> listOfAttributes = new ArrayList<>();
        listOfAttributes.add(att1);
        listOfAttributes.add(att2);
        listOfAttributes.add(att3);
        
        Table tab = new Table(tabName, listOfAttributes);
     
        return  tab;
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
        
       // String epl = "select * from SensorEvent.win:time(30 sec) match_recognize ( measures A as val1, B as val2 pattern (A+ B+) define A as A.value < 13 and A.name ='chw_supply_temp',  B as B.value > 24 and B.name ='fcu_ff1_space_temp')";
        String epl = "select * from SensorEvent match_recognize ( "
                  + "measures A as val1, B as val2, C as val3 "
                  + "pattern ((A B C)|(A C B)|(B A C)|(B C A)|(C A B)|(C B A))"//interval 1 seconds "
                  + "define A as (A.value < 13 and A.name ='chw_supply_temp"+ machine+"'),  "
                  + "B as (Math.round(B.value) = 23 and B.name ='fcu_ff1_space_temp"+ machine+"'), "
                  + "C as (Math.round(C.value) = 23 and C.name ='fcu_ff1_set_point"+ machine+"') "
                  + ")";
     
        
        String enrichmentURI = "http://128.130.172.230:8080/DataEnrichment/rest/sensor/";
        DafAnalyticCep dafAnalyticCep = new DafAnalyticCep(epl, enrichmentURI);
        
        return dafAnalyticCep;
    }
    
    public static AnalyticResultDelegate sampleAnalyticResultDelegate(){
        
       // String delegateMessage="<task><taskID>id01</taskID><taskName>CLEAN EVAPORATOR.</taskName><taskContent>Evaporator is fouling.</taskContent><tag>IMPLEMENTATION</tag><severity>WARNING</severity></task>";
        DafDelegator delegator = new DafDelegator("128.130.172.191", "8080", "http://128.130.172.191:8080/rest/api/task");
        
        
        Task delegateMessage = new Task(1, "CLEAN EVAPORATOR", "Evaporator is fouling", "IMPLEMENTATION", Task.SeverityLevel.CRITICAL);
        AnalyticResultDelegate analyticResultDelegate = new AnalyticResultDelegate(delegateMessage, delegator);
        
        
        
        
        
        
        return  analyticResultDelegate;
    }
    
   
    
}
