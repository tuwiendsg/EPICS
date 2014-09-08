/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.app;

import at.ac.tuwien.dsg.depictool.entity.DataElasticityMetric;
import at.ac.tuwien.dsg.depictool.entity.ElasticDataObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class APIGenerator {
    
    ElasticDataObject elasticDataObject;
    String templateFolder;

    public APIGenerator() {
    }

    public APIGenerator(ElasticDataObject elasticDataObject) {
        this.elasticDataObject = elasticDataObject;
        templateFolder="template/eDaaS/src/main/java/at/ac/tuwien/dsg/";
    }
    
    public void generateAPI(){
        
        generateMetricClass();
        
        
        
        
       
       
        
        
    } 
    
    private void genrateDataItemClass(){
        
        
    }
    
    
    private void generateMetricClass(){
        
        List<DataElasticityMetric> listOfDataElasticityMetrics = elasticDataObject.getListOfDataElasticityMetrics();
        
        
        String templateConstraintClass = loadTemplateConstraintClass();
        System.out.println("class: " + templateConstraintClass);
        
        for (DataElasticityMetric metric : listOfDataElasticityMetrics) {
            
            String metricName = metric.getName();
            System.out.println(metricName);
            
            String className = templateFolder + "edaas/userrequirement/" + metricName+"Constraint.java";
            
            String classContent = templateConstraintClass.replaceAll("Template", metricName);
            writeConstraintClass(className,classContent);
            
        } 
        
    }
    
    
    
    private void writeConstraintClass(String className, String classContent){
        
        FileWriter fstream;

        

        try {
            fstream = new FileWriter(className, true);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(classContent);

            
            out.close();
        } catch (IOException ex) {
        }
        
        
    }
    
    
    
    private String loadTemplateConstraintClass(){
        
        String templateConstraintClass="";
        
        
        FileInputStream fstream=null;
        try {
            fstream = new FileInputStream(templateFolder+"edaas/userrequirement/TemplateConstraint.java");
            // FileInputStream fstream = new FileInputStream("covertype.csv");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(APIGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
     
        
        String strLine="";

        try {
            while ((strLine = br.readLine()) != null) {
                templateConstraintClass = templateConstraintClass + strLine +"\n";
                
            }
        } catch (IOException ex) {
            Logger.getLogger(APIGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
        
        
        return  templateConstraintClass;
    }
    
    
}
