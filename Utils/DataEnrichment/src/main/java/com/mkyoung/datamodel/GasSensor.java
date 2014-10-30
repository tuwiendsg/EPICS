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
import com.hp.hpl.jena.vocabulary.RDFS;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author dsg
 */
public class GasSensor {
    public static void main(String []p)
    {
        String uri="http://windtunnel.com#";
      String objectName="MOXGasSensor";
      
      Model model=ModelFactory.createDefaultModel();
      Resource r1=model.createResource(uri+objectName);
      Resource moduleResource=model.createResource(uri+"SensoryModule");
      r1.addProperty(RDFS.member, moduleResource);
      
      Property p1=model.createProperty(uri+"name");
      r1.addProperty(p1, "MetalOxideGasSensor");
      
      Property p2=model.createProperty(uri+"providedby");
      r1.addProperty(p2, "Figaro Inc.");
      
      r1.addProperty(RDFS.seeAlso, "http://www.figarosensor.com");
      
      model.setNsPrefix("chamber", uri);
        model.write(System.out);
        File f=new File("./example");
            if(!f.exists())
            {
               f.mkdir();
               System.out.println("file created");
            }
          try
            {
                FileWriter fw=new FileWriter("./example/"+objectName+".rdf");
                model.write(fw);
            }
          catch(Exception e)
            {
                System.out.println("exception occured : "+e);
            }
      
    }
}
