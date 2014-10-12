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
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author dsg
 */
public class FanModel {
  public static void main(String []p)
  {
      String uri="http://windtunnel.com#";
      String objectName="Fan";
      
      Model model=ModelFactory.createDefaultModel();
      Resource tunnelResource=model.createResource(uri+"WindTunnel");
      Resource fanResource=model.createResource(uri+objectName);
      fanResource.addProperty(RDF.type,tunnelResource);
      Property name=model.createProperty(uri+"name");
      fanResource.addProperty(name,"Fan");
      
      Property speedProperty=model.createProperty(uri+"fanspeed");
      Property maxSpeed=model.createProperty(uri+"maxspeed");
      Property minSpeed=model.createProperty(uri+"minspeed");
      Property mediumSpeed=model.createProperty(uri+"mediumspeed");
      Resource speedResource=model.createResource(uri+"FanSpeed");
      speedResource.addProperty(minSpeed, "1500 rpm (25Hz)");
      speedResource.addProperty(mediumSpeed, "3900 rpm (65 Hz)");
      speedResource.addProperty(maxSpeed,"5500 rpm (91.66 Hz)");
      fanResource.addProperty(speedProperty, speedResource);
      
      Property locationProperty=model.createProperty(uri+"fanlocation");
      Property positionX=model.createProperty(uri+"positionX");
      Property positionY=model.createProperty(uri+"positionY");
      Property positionZ=model.createProperty(uri+"positionZ");
      Resource locationResource=model.createResource(uri+"FanLocation");
      locationResource.addProperty(positionX, "2.5 (1 cell value=1 meter)");
      locationResource.addProperty(positionY, "1.2 (1 cell value=1 meter)");
      locationResource.addProperty(positionZ,"0.4 (1 cell value=1 meter)");
      fanResource.addProperty(locationProperty, locationResource);
      
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
