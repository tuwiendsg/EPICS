/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.esperstreamprocessing.rest;

/**
 *
 * @author Jun
 */

import at.ac.tuwien.dsg.edasich.entity.daf.complexeventprocessing.DataAnalyticFunctionCep;


import at.ac.tuwien.dsg.esperstreamprocessing.queueclient.QueueClient;

import at.ac.tuwien.dsg.esperstreamprocessing.utils.IOUtils;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.JAXBUtils;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;

//http://localhost:8080/RESTfulExample/rest/message/hello%20world
@Path("/esper")
public class EsperRESTWS {

    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{param}")
    public String printMessage(@PathParam("param") String msg) {
        
        String tempdir = System.getProperty("java.io.tmpdir");
        
        Logger.getLogger(EsperRESTWS.class.getName()).log(Level.INFO, tempdir);
        String log = "DAF";
        Logger.getLogger(EsperRESTWS.class.getName()).log(Level.INFO, log);
        
        
        
        
        return log;

    }
    
    @PUT
    @Path("/start")
    @Consumes(MediaType.APPLICATION_XML)
    public void startEsper(String dafName) {
        
        try {
            
            IOUtils.writeData(dafName+"stop", "1"); 
     
            
            
            String xmlData = IOUtils.readData(dafName);
            
            DataAnalyticFunctionCep daf = JAXBUtils.unmarshal(xmlData, DataAnalyticFunctionCep.class);
      
            
            String log = "STARTING ... " + dafName;
            
            log = log + "\n CQL: " + daf.getDafAnalyticCep().getEplStatement();
            
            Logger.getLogger(EsperRESTWS.class.getName()).log(Level.INFO, log);
            
            QueueClient qc = new QueueClient();
            qc.configureDataAssetFunction(daf);
            
            Thread thread = new Thread(qc, dafName);
            thread.start();
        } catch (JAXBException ex) {
           // Logger.getLogger(EsperRESTWS.class.getName()).log(Level.SEVERE, null, ex);
        }
    
                }


    @PUT
    @Path("/stop")
    @Consumes(MediaType.APPLICATION_XML)
    public void stopEsper(String dafName) {       
          IOUtils.writeData(dafName+"stop", "0"); 
    }

    @PUT
    @Path("/daf")
    @Consumes(MediaType.APPLICATION_XML)
    public void submitDataAssetFunction(String dafXML) {
        try {
        String log= "Recieved:" + dafXML;
        DataAnalyticFunctionCep daf = JAXBUtils.unmarshal(dafXML, DataAnalyticFunctionCep.class);
        
        log = log +  "\n Daf Name:" + daf.getDafName();
        log= log + "\n Daf CPL:" + daf.getDafAnalyticCep().getEplStatement();
        
        
        Logger.getLogger(EsperRESTWS.class.getName()).log(Level.INFO, log);
        
        
        
        IOUtils.writeData(dafXML, daf.getDafName());
        } catch (JAXBException ex) {
           Logger.getLogger(EsperRESTWS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
