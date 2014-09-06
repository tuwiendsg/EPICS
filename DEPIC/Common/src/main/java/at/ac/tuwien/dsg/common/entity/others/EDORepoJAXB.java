/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.common.entity.others;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Jun
 */
public class EDORepoJAXB {
    public String marshallingObject(EDORepo obj) {


        String xmlString = "";

        try {

            StringWriter objWriter = new StringWriter();

            JAXBContext jaxbContext = JAXBContext.newInstance(EDORepo.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(obj, objWriter);
 
            //jaxbMarshaller.marshal(eState, System.out);
           xmlString = objWriter.toString();

        } catch (JAXBException e) {
            System.out.println("Ex: " + e.toString());
        }

        return xmlString;
    }
    
    public EDORepo unmarshallingObject(String xmlStr) {

        EDORepo javaObj = null;
         try {
            
                StringReader reader = new StringReader(xmlStr);   
		JAXBContext jaxbContext = JAXBContext.newInstance(EDORepo.class);
 		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		javaObj = (EDORepo) jaxbUnmarshaller.unmarshal(reader);
 
	  } catch (JAXBException e) {
	
	  }
 
         return javaObj;
         
        
    }
}
