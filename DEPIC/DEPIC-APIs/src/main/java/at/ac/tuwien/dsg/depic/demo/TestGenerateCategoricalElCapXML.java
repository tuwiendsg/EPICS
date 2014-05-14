/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depic.demo;

import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.CategoricalElasticityValue;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.DataSourceValue;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.NumericalElasticityValue;
import at.ac.tuwien.dsg.depic.utility.CategoricalElCapCostDB;
import at.ac.tuwien.dsg.depic.utility.DataSourceCostDB;
import at.ac.tuwien.dsg.depic.utility.NumericalElCapCostDB;

import at.ac.tuwien.dsg.depic.utility.Utilities;

/**
 *
 * @author Jun
 */
public class TestGenerateCategoricalElCapXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        testNumericalXML();
    }
    
    public static void testCategoricalXML(){
        CategoricalElasticityValue cal1 = new CategoricalElasticityValue(1, 1);
        CategoricalElasticityValue cal2 = new CategoricalElasticityValue(2, 2);
        CategoricalElasticityValue cal3 = new CategoricalElasticityValue(3, 3);
     
        CategoricalElCapCostDB obj = new CategoricalElCapCostDB();
        obj.setName("DataProcessingSpeedLevel");
        obj.setLowerBound(1);
        obj.setUpperBound(3);
        obj.addNewValue(cal1);
        obj.addNewValue(cal2);
        obj.addNewValue(cal3);
        
        Utilities util = new Utilities();
        String xmlString = util.convertCategoricalElCostToXML(obj);
        //System.out.println(xmlString);
    }
    
    public static void testNumericalXML(){
        NumericalElasticityValue cal1 = new NumericalElasticityValue(10, 30, 1.5);
        NumericalElasticityValue cal2 = new NumericalElasticityValue(31, 70, 2.5);
        NumericalElasticityValue cal3 = new NumericalElasticityValue(71, 90, 3.5);
     
        NumericalElCapCostDB obj = new NumericalElCapCostDB();
        obj.setName("DataAccuracy");
        obj.setLowerBound(10);
        obj.setUpperBound(90);
        obj.addNewValue(cal1);
        obj.addNewValue(cal2);
        obj.addNewValue(cal3);
        
        Utilities util = new Utilities();
        String xmlString = util.convertNumericalElCostToXML(obj);
        //System.out.println(xmlString);
    }
    
    public static void testDataSourceXML(){
        DataSourceValue cal1 = new DataSourceValue("shop1", 0.5);
        DataSourceValue cal2 = new DataSourceValue("shop2", 1.0);
        DataSourceValue cal3 = new DataSourceValue("shop3", 0.5);
        DataSourceValue cal4 = new DataSourceValue("shop4", 0.5);
        DataSourceValue cal5 = new DataSourceValue("shop5", 0.5);
        
        
        
        DataSourceCostDB obj = new DataSourceCostDB();
       // categoricalCost.setName("Coop");
  
        obj.addNewValue(cal1);
        obj.addNewValue(cal2);
        obj.addNewValue(cal3);
        obj.addNewValue(cal4);
        obj.addNewValue(cal5);
        
        Utilities util = new Utilities();
        String xmlString = util.convertDataSourceCostToXML(obj);
        //System.out.println(xmlString);
    }
    
    
    public static void unmarsharlXML2DataSourceCostDB(){
        
        Utilities util = new Utilities();
      //  util.unMarshalXML2DataSourceCostDB();
        
               
    }
    
     public static void unmarsharlXML2NumericalElCapCostDB(){
        
        Utilities util = new Utilities();
      //  util.unMarshalXML2NumericalElCalCostDB();
        
               
    }
     public static void unMarsharlXML2CategoricalElCapCostDB(){
        
        Utilities util = new Utilities();
   
        
               
    }
    
}
