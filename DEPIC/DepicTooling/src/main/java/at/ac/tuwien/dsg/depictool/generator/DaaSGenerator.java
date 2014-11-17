/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.depictool.generator;



import at.ac.tuwien.dsg.common.entity.qor.QoRMetric;
import at.ac.tuwien.dsg.common.entity.eda.DataAssetFunction;
import at.ac.tuwien.dsg.common.entity.process.DataSource;
import at.ac.tuwien.dsg.common.entity.eda.ElasticDataAsset;
import at.ac.tuwien.dsg.common.entity.process.MetricProcess;
import at.ac.tuwien.dsg.common.entity.qor.QoRModel;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;
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
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jun
 */
public class DaaSGenerator {
    
    ElasticDataAsset elasticDataObject;
    String templateFolder;
    

    public DaaSGenerator() {
    }

    public DaaSGenerator(ElasticDataAsset elasticDataObject) {
        this.elasticDataObject = elasticDataObject;
        templateFolder="template/eDaaS/src/main/java/at/ac/tuwien/dsg/";
    }
    
    public void generateDaaS(){
        
        //genrate metric class 
        generateMetricClass();
        
       
        // get ResultSetMetaData -> generate DataItems
        genrateDataItemClass();
        
  
        
        
    } 

    
    private void genrateDataItemClass() {
    /*
        DataSource dataSource = elasticDataObject.getDataSource();
        List<DataAssetFunction> listOfDataObjectFunctions = elasticDataObject.getListOfDataObjectFunctions();

        for (DataAssetFunction dataObjectFunction : listOfDataObjectFunctions) {

            dataObjectFunction.getName();
            dataObjectFunction.getNumberOfDataItems();
            String query = dataObjectFunction.getQuery();
            query += " LIMIT 1";

            MySqlConnectionManager connectionManager = new MySqlConnectionManager(dataSource.getIp(), dataSource.getPort(), dataSource.getDatabase(), dataSource.getUser(), dataSource.getPassword());
            PrintStream out = System.out;
            ResultSet rs = connectionManager.ExecuteQuery(query);
            try {

                while (rs.next()) {

                    ResultSetMetaData rsmd = rs.getMetaData();
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        if (i > 1) {
                            out.print(",");
                        }

                        int type = rsmd.getColumnType(i);
                       
                        out.print("column " + i + "; type : " + type);
                        if (type == Types.VARCHAR || type == Types.CHAR) {
                            out.print(rs.getString(i));
                        } else if (type == Types.DOUBLE) {
                            
                        } else if (type == Types.INTEGER) {
                            
                        } else {
                            out.print(rs.getLong(i));
                        }
                    }

                out.println();
                }

            } catch (Exception ex) {

            }
        }

            */
    }
    
   
    

    
    private void generateMetricClass(){
        
     //   List<QoRMetric> listOfDataElasticityMetrics = elasticDataObject.getListOfDataElasticityMetrics();
          List<QoRMetric> listOfDataElasticityMetrics = null;
        
        
        String templateConstraintClass = loadTemplateConstraintClass();
        System.out.println("class: " + templateConstraintClass);
        
        for (QoRMetric metric : listOfDataElasticityMetrics) {
            
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
            Logger.getLogger(DaaSGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
     
        
        String strLine="";

        try {
            while ((strLine = br.readLine()) != null) {
                templateConstraintClass = templateConstraintClass + strLine +"\n";
                
            }
        } catch (IOException ex) {
            Logger.getLogger(DaaSGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
      
        
        
        return  templateConstraintClass;
    }
    
    
}
