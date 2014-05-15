/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.depic.utility;


import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.CategoricalElasticityValue;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.DataSourceValue;
import at.ac.tuwien.dsg.depic.dataelasticitycapabilities.NumericalElasticityValue;
import at.ac.tuwien.dsg.depic.dataelasticityrequirement.DataElasticityRequirement;
import at.ac.tuwien.dsg.sybl.elasticstate.ElasticState;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.FileUtils;
/**
 *
 * @author Jun
 */
public class Utilities {

    public String convertElasticityRequirementToXML(DataElasticityRequirement daElReq) {

        String xmlString = "";

        try {

            StringWriter objWriter = new StringWriter();

            JAXBContext jaxbContext = JAXBContext.newInstance(DataElasticityRequirement.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(daElReq, objWriter);
            jaxbMarshaller.marshal(daElReq, System.out);
            
            xmlString = objWriter.toString();
            
        } catch (Exception e) {
     
        }

        return xmlString;
    }
    
    public String convertCategoricalElCostToXML(CategoricalElCapCostDB javaObj) {

        String xmlString = "";

        try {

            StringWriter objWriter = new StringWriter();

            JAXBContext jaxbContext = JAXBContext.newInstance(CategoricalElCapCostDB.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(javaObj, objWriter);
            jaxbMarshaller.marshal(javaObj, System.out);
          
            xmlString = objWriter.toString();

        } catch (Exception e) {
     
        } 

        return xmlString;
    }
    
    
    public String convertElasticStateToXML(ElasticState eState) {

        System.out.println("eState to XML");
        String xmlString = "";

        try {

            StringWriter objWriter = new StringWriter();

            JAXBContext jaxbContext = JAXBContext.newInstance(ElasticState.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(eState, objWriter);
 
            //jaxbMarshaller.marshal(eState, System.out);
           xmlString = objWriter.toString();

        } catch (Exception e) {
     
        }

        return xmlString;
    }
    
    
    
    
      public String convertNumericalElCostToXML(NumericalElCapCostDB javaObj) {

        String xmlString = "";

        try {

            StringWriter objWriter = new StringWriter();

            JAXBContext jaxbContext = JAXBContext.newInstance(NumericalElCapCostDB.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(javaObj, objWriter);
            jaxbMarshaller.marshal(javaObj, System.out);
            
            xmlString = objWriter.toString();

        } catch (Exception e) {
     
        }

        return xmlString;
    }
      
      public String convertDataSourceCostToXML(DataSourceCostDB javaObj) {

        String xmlString = "";

        try {

            StringWriter objWriter = new StringWriter();

            JAXBContext jaxbContext = JAXBContext.newInstance(DataSourceCostDB.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(javaObj, objWriter);
            jaxbMarshaller.marshal(javaObj, System.out);
            
            xmlString = objWriter.toString();

        } catch (Exception e) {
     
        }

        return xmlString;
    }
      
      public ElasticState unMarshalXML2ElasticState(String xmlStr) {

        ElasticState javaObj = null;
         try {
            
                StringReader reader = new StringReader(xmlStr);   
		JAXBContext jaxbContext = JAXBContext.newInstance(ElasticState.class);
 		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		javaObj = (ElasticState) jaxbUnmarshaller.unmarshal(reader);
 
	  } catch (JAXBException e) {
	
	  }
 
         return javaObj;
         
        
    }
      
      public DataSourceCostDB unMarshalXML2DataSourceCostDB(String urlStr){
        
        DataSourceCostDB javaObj=null;
        
         try {
             
                StringReader reader = new StringReader(urlToString(urlStr));
                
		JAXBContext jaxbContext = JAXBContext.newInstance(DataSourceCostDB.class);
 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		javaObj = (DataSourceCostDB) jaxbUnmarshaller.unmarshal(reader);
                  
                List ls = javaObj.getDataSourceValues();
                
                for (int i=0;i<ls.size();i++){
                    DataSourceValue val = (DataSourceValue)ls.get(i);
                    System.out.println("Name:" + val.getSourceName());
                    System.out.println("Cost:" + val.getCost());
                    
                }

                
		
 
	  } catch (Exception e) {
		
	  }
        
        
        return javaObj;
        
              
    }
      
      
      public NumericalElCapCostDB unMarshalXML2NumericalElCapCostDB(String urlStr){
        
        NumericalElCapCostDB javaObj=null;
        
         try {
 
                StringReader reader = new StringReader(urlToString(urlStr)); 
                
                
		JAXBContext jaxbContext = JAXBContext.newInstance(NumericalElCapCostDB.class);
 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		javaObj = (NumericalElCapCostDB) jaxbUnmarshaller.unmarshal(reader);
                
                String name = javaObj.getName();
                double lowerBound = javaObj.getLowerBound();
                double upperBound = javaObj.getUpperBound();
                System.out.println("Name: " + name);
                System.out.println("lowerBound: " + lowerBound);
                System.out.println("upperBound: " + upperBound);
           
                
                List ls = javaObj.getElasticityValues();
                
                for (int i=0;i<ls.size();i++){
                    NumericalElasticityValue val = (NumericalElasticityValue)ls.get(i);
                    System.out.println("fromValue:" + val.getFromValue());
                    System.out.println("toValue:" + val.getToValue());
                    System.out.println("Cost:" + val.getCost());
                    
                }

                
		
 
	  } catch (Exception e) {
		
	  }
        
        
        return javaObj;
        
              
    }
      
      
      public DataElasticityRequirement unMarsharlXML2DataElasticityRequirement(String xmlStr){
        
        DataElasticityRequirement javaObj=null;
        
         try {
 
          
		StringReader reader = new StringReader(xmlStr);
                
                
		JAXBContext jaxbContext = JAXBContext.newInstance(DataElasticityRequirement.class);
 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		javaObj = (DataElasticityRequirement) jaxbUnmarshaller.unmarshal(reader);
                
            
                
		
 
	  } catch (Exception e) {
		
	  }
        
        
        return javaObj;
        
              
    }
      
      public CategoricalElCapCostDB unMarsharlXML2CategoricalElCapCostDB(String urlStr){
        
        CategoricalElCapCostDB javaObj=null;
        
         try {
 
                StringReader reader = new StringReader(urlToString(urlStr));
                
                
		JAXBContext jaxbContext = JAXBContext.newInstance(CategoricalElCapCostDB.class);
 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		javaObj = (CategoricalElCapCostDB) jaxbUnmarshaller.unmarshal(reader);
                
                String name = javaObj.getName();
                int lowerBound = javaObj.getLowerBound();
                int upperBound = javaObj.getUpperBound();
                System.out.println("Name: " + name);
                System.out.println("lowerBound: " + lowerBound);
                System.out.println("upperBound: " + upperBound);
           
                
                List ls = javaObj.getElasticityValues();
                
                for (int i=0;i<ls.size();i++){
                    CategoricalElasticityValue val = (CategoricalElasticityValue)ls.get(i);
                    System.out.println("Level:" + val.getElValue());
                    System.out.println("Cost:" + val.getCost());
                    
                }

                
		
 
	  } catch (Exception e) {
		
	  }
        
        
        return javaObj;
        
              
    }
      
      
      public void writeElasticStateXMLtoFile(String eStateXML) {
        
        try {
 
			
			File file = new File("eState.xml");
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(eStateXML);
			bw.close();
 
			System.out.println("Done");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        
    }
      
      
    public String urlToString(String urlStr) {

        String xmlString = "";
        try {
            URL website = new URL(urlStr);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            xmlString = response.toString();
        } catch (Exception ex) {

        }

        return xmlString;

    }
    
    public void saveCurrentElasticState(String xmlStr) {

        try {
        String encodedMessage = URLEncoder.encode(xmlStr, "UTF-8");
        String urlStr = "http://128.130.172.216/elfinder/files/download/SaveElasticState.php?content=" + encodedMessage;
        
            URL website = new URL(urlStr);
            URLConnection connection = website.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

          
        } catch (Exception ex) {

        }

 

    }
    
    
    public String getCurrentElasticState() {
        
        return urlToString("http://128.130.172.216/elfinder/files/download/eState.xml");
    }

}
