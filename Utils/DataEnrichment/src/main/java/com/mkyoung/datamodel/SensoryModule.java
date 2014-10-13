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
public class SensoryModule {
    public static void main(String []p)
    {
        String uri="http://windtunnel.com#";
        
        String objectName="SensoryModule";
        
        Model model=ModelFactory.createDefaultModel();
        Resource r1=model.createResource(uri+objectName);
        Resource tunnelResource=model.createResource(uri+"WindTunnel");
        r1.addProperty(RDFS.member,tunnelResource);
        
        Property number=model.createProperty(uri+"sensoryModuleNumber");
        r1.addProperty(number, "54");
        
        Property location=model.createProperty(uri+"locatedIn");
        Property position=model.createProperty(uri+"positionnumber");
        Property board=model.createProperty(uri+"boardnumber");
        Resource locationResource=model.createResource(uri+"Place");
        locationResource.addProperty(position, "6");
        locationResource.addProperty(board,"9");
        r1.addProperty(location,locationResource);
        
        Property controllername=model.createProperty(uri+"controllerunitname");
        r1.addProperty(controllername, "microprocessor MSP430F247");
        
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
