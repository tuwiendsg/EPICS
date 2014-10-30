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
public class WindTunnelModel {
    public static void main(String []p)
    {
        String uri="http://windtunnel.com#";
        String objectName="WindTunnel";
        
        Model model=ModelFactory.createDefaultModel();
        Resource tunnel=model.createResource(uri+objectName);
        
        Property name=model.createProperty(uri+"name");
        tunnel.addProperty(name, "WindTunnel");
        
        Property volume=model.createProperty(uri+"chambervolume");
        Resource chamberVolume=model.createResource(uri+"ChamberVolume");
        Property length=model.createProperty(uri+"length");
        Property width=model.createProperty(uri+"width");
        Property heigth=model.createProperty(uri+"heigth");
        chamberVolume.addProperty(heigth, "0.4 meter");
        chamberVolume.addProperty(width,"1.2 meter");
        chamberVolume.addProperty(length,"2.5 meter");
        tunnel.addProperty(volume, chamberVolume);
        
        Property facilityProperty=model.createProperty(uri+"facilityElement");
        Resource facilityResource=model.createResource(uri+"FacilityElement");
        Property facilityName=model.createProperty(uri+"facilityname");
        facilityResource.addProperty(facilityName, "Fan");
        facilityResource.addProperty(RDFS.seeAlso,model.createResource("http://windtunnel.com#Fan"));
        facilityResource.addProperty(facilityName, "SensoryModule");
        facilityResource.addProperty(RDFS.seeAlso,model.createResource("http://windtunnel.com#SensoryModule"));
        facilityResource.addProperty(facilityName, "AnalyticGas");
        facilityResource.addProperty(RDFS.seeAlso,model.createResource("http://windtunnel.com#AnalyticGas"));
        facilityResource.addProperty(facilityName, "ChemicalSource");
        facilityResource.addProperty(RDFS.seeAlso,model.createResource("http://windtunnel.com#ChemicalSource"));
        tunnel.addProperty(facilityProperty,facilityResource);
        
        
        Property proposedBy=model.createProperty(uri+"proposedby");
        tunnel.addProperty(proposedBy, "Department Of Defence");
        
        
        
        
        
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
