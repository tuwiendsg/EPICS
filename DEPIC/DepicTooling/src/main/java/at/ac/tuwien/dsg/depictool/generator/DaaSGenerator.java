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
import at.ac.tuwien.dsg.common.utils.Logger;
import at.ac.tuwien.dsg.depictool.util.ZipUtils;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.lang.WordUtils;

/**
 *
 * @author Jun
 */
public class DaaSGenerator {

    QoRModel qoRModel;
    String rootPath;

    public DaaSGenerator() {

    }

    public DaaSGenerator(QoRModel qoRModel) {
        this.qoRModel = qoRModel;
        configureProjectPath();
        generateProjectTemplate();
    }

    public void generateDaaS() {

        generateMetricConstraintClass();
        generateConstraintConverterClass();
        generateConsumerRequirementClass();
        
        

    }
    
    
    private void generateProjectTemplate(){
        ZipUtils zipUtils = new ZipUtils();
        zipUtils.unZipIt(rootPath + "/classes/project.zip");
        
    }

    private void generateMetricConstraintClass() {

        String packageName = "at.ac.tuwien.dsg.edaas.requirement";

        List<QoRMetric> listOfMetrics = qoRModel.getListOfMetrics();
        String templateConstraintClass = loadTemplateClass("MetricConstraint");

        for (QoRMetric metric : listOfMetrics) {

            String metricName = metric.getName();
            metricName = WordUtils.capitalize(metricName);
            metricName = metricName.trim();

            String className = metricName + "Constraint";

            String classContent = templateConstraintClass.replaceAll("Template", metricName);
            classContent = classContent.replaceAll("template_metric_name", metric.getName());
            writeClass(className, packageName, classContent);

        }

    }

    private void generateConstraintConverterClass() {

        String packageName = "at.ac.tuwien.dsg.edaas.requirement.service";

        String packageMetric = "at.ac.tuwien.dsg.edaas.requirement";

        List<QoRMetric> listOfMetrics = qoRModel.getListOfMetrics();
        String templateConstraintClass = loadTemplateClass("ConstraintConverter");
        Logger.logInfo("class: " + templateConstraintClass);

        String importContent = "";
        String conversionContent = "";

        int metricCounter = 0;

        for (QoRMetric metric : listOfMetrics) {

            String metricName = metric.getName();
            metricName = WordUtils.capitalize(metricName);
            metricName = metricName.trim();
            
            String className = metricName + "Constraint";

            importContent = importContent + "\n" + "import " + packageMetric + "." + className+";";

            String c_line1 = className + " c" + String.valueOf(metricCounter) + " =  consumerRequirement.get" + className + "();";
            String c_line2 = "TemplateConstraint c" + String.valueOf(metricCounter) + "_t = new TemplateConstraint(c" + String.valueOf(metricCounter) + ".getConstraintName(), c" + String.valueOf(metricCounter) + ".getMinValue(), c" + String.valueOf(metricCounter) + ".getMaxValue());";
            String c_line3 = "listOfConstraints.add(c" + String.valueOf(metricCounter) + "_t);";

            conversionContent = conversionContent + "\n" + c_line1 + "\n" + c_line2 + "\n" + c_line3;
            metricCounter++;
        }

        String classContent = templateConstraintClass.replaceAll("#import_content#", importContent);
        classContent = classContent.replaceAll("#conversion_content#", conversionContent);

        writeClass("ConstraintConverter", packageName, classContent);

    }

    private void generateConsumerRequirementClass() {
        
        String packageName = "at.ac.tuwien.dsg.edaas.requirement";

        List<QoRMetric> listOfMetrics = qoRModel.getListOfMetrics();
        String templateConstraintClass = loadTemplateClass("ConsumerRequirement");
        System.out.println("class: " + templateConstraintClass);

        String declarationContent = "";
        String getContent = "";
        String setContent = "";


        for (QoRMetric metric : listOfMetrics) {

            String metricName = metric.getName();
            metricName = WordUtils.capitalize(metricName);
            metricName = metricName.trim();
            String className = metricName + "Constraint";

            String declaration_line1 ="@XmlElement(name = \""+className+"\", required = true)";
            String declaration_line2 =className + " "+className.toLowerCase()+";";
            declarationContent = declarationContent + "\n" + declaration_line1 + "\n" + declaration_line2;
     
            String get_line1 = "public "+className+" get"+className+"() {";
            String get_line2 = "return "+className.toLowerCase()+";";
            String get_line3 = "}";
            
            getContent = getContent + "\n" + get_line1 + "\n" + get_line2 + "\n" + get_line3;
            
            String set_line1 = "public void set"+className+"("+className+" "+className.toLowerCase()+") {";
            String set_line2 = "this."+className.toLowerCase()+" = "+className.toLowerCase()+";";
            String set_line3 = "}";
            
            setContent = setContent + "\n" + set_line1 + "\n" + set_line2 + "\n" + set_line3;
            

        }

        String classContent = templateConstraintClass.replaceAll("#declaration_content#", declarationContent);
        classContent = classContent.replaceAll("#get_content#", getContent);
        classContent = classContent.replaceAll("#set_content#", setContent);
        
        writeClass("ConsumerRequirement", packageName, classContent);
        

    }
    
    
    

    private void writeClass(String className, String packageName, String classContent) {

        String filePath = packageToPath(className, packageName);

        FileWriter fstream;

        try {
            fstream = new FileWriter(filePath, false);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(classContent);

            out.close();
        } catch (IOException ex) {
        }

    }

    private String packageToPath(String className, String packageName) {
        String filePath = "";

        packageName = packageName.replaceAll("\\.", "/");

        filePath = rootPath + "/classes/project/eDaaS/src/main/java/" + packageName + "/" + className + ".java";

        return filePath;
    }

    private String loadTemplateClass(String templateName) {

        String templateConstraintClass = "";

        String filePath = "";

        if (templateName.equals("MetricConstraint")) {
            filePath = rootPath + "/classes/templateclass/" + "TemplateConstraint.tpl";
        } else if (templateName.equals("ConstraintConverter")) {
            filePath = rootPath + "/classes/templateclass/" + "ConstraintConverter.tpl";
        } else if (templateName.equals("ConsumerRequirement")) {
            filePath = rootPath + "/classes/templateclass/" + "ConsumerRequirement.tpl";
        }

        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream(filePath);
            // FileInputStream fstream = new FileInputStream("covertype.csv");
        } catch (FileNotFoundException ex) {
            Logger.logInfo(ex.toString());
        }

        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String strLine = "";

        try {
            while ((strLine = br.readLine()) != null) {
                templateConstraintClass = templateConstraintClass + strLine + "\n";

            }
        } catch (IOException ex) {
           Logger.logInfo(ex.toString());
        }

        return templateConstraintClass;
    }

    private void configureProjectPath() {

        String path = DaaSGenerator.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        int index = path.indexOf("/classes/at/ac");
        path = path.substring(0, index);
        try {
            path = URLDecoder.decode(path, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.logInfo(ex.toString());
        }

        System.out.println("Project Path: -" + path + "-");
        rootPath = path;
    }
    
    
    
    
    
    

}
