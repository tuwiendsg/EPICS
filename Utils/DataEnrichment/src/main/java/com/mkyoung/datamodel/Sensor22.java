/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyoung.datamodel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.RDFS;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author Anindita
 */
public class Sensor22 {
    public static void main(String []p)
    {
        
    
    String uri="http://windtunnel.com#";
      String objectName="Sensor22";
      
      Model model=ModelFactory.createDefaultModel();
      //Resource moduleResource=model.createResource(uri+"SensoryModule");
      Resource sensorResource=model.createResource(uri+objectName);
      //sensorResource.addProperty(RDFS.member, moduleResource);
      
      Property manufactured=model.createProperty(uri+"manufacturedby");
      sensorResource.addProperty(manufactured, "http://www.figarosensor.com/");
      
      Resource gasSensor=model.createResource("http://www.eoc-inc.com/Cambridge/metal%20oxide%20gas%20sensors.htm");
      sensorResource.addProperty(RDFS.seeAlso, gasSensor);
      
      
      Property name=model.createProperty(uri+"name");
      sensorResource.addProperty(name,"Sensor22");
      
      //Property locationProperty=model.createProperty(uri+"location");
      Property position=model.createProperty(uri+"position");
      Property board=model.createProperty(uri+"board");
      Property sile=model.createProperty(uri+"silo");
      //Resource location=model.createResource(uri+"location"+objectName);
      sensorResource.addProperty(position, "3");
      sensorResource.addProperty(board, "1");
      sensorResource.addProperty(sile, "6");
      //sensorResource.addProperty(locationProperty, location);
      
      model.setNsPrefix("chamber", uri);
        model.write(System.out);
        File f=new File("./example6");
            if(!f.exists())
            {
               f.mkdir();
               System.out.println("file created");
            }
          try
            {
                FileWriter fw=new FileWriter("./example6/"+objectName+".rdf");
                model.write(fw);
            }
          catch(Exception e)
            {
                System.out.println("exception occured : "+e);
            }
      
      
    }
    
}
