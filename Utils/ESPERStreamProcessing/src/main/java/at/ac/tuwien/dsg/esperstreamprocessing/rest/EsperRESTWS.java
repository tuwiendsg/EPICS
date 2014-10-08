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
import at.ac.tuwien.dsg.edasich.configuration.TaskDistributionConfiguration;
import at.ac.tuwien.dsg.edasich.entity.stream.DataAssetFunctionStreamingData;

import at.ac.tuwien.dsg.esperstreamprocessing.queueclient.QueueClient;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.IOUtils;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.JAXBUtils;
import java.util.Set;
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
        String log = "DAF : " + msg;
        
        Logger.getLogger(EsperRESTWS.class.getName()).log(Level.INFO, log);
        
        
        
        
        return log;

    }
    
    @PUT
    @Path("/start")
    @Consumes(MediaType.APPLICATION_XML)
    public void startEsper(String xmlString) {
        
        try {
     
            String log = "Recieved:" + xmlString;
            Logger.getLogger(EsperRESTWS.class.getName()).log(Level.INFO, log);
            String xmlData = IOUtils.readData(xmlString);
            
            Logger.getLogger(EsperRESTWS.class.getName()).log(Level.INFO, xmlData);
            
            DataAssetFunctionStreamingData daf=null;
            daf = JAXBUtils.unmarshal(xmlData, DataAssetFunctionStreamingData.class);
      
            log = daf.getDaFunctionName();
            Logger.getLogger(EsperRESTWS.class.getName()).log(Level.INFO, xmlData);
            
            QueueClient qc = new QueueClient();
            qc.configureDataAssetFunction(daf);
            
            Thread thread = new Thread(qc, "ESPER");
            thread.start();
        } catch (JAXBException ex) {
           // Logger.getLogger(EsperRESTWS.class.getName()).log(Level.SEVERE, null, ex);
        }
    
                }


    @PUT
    @Path("/stop")
    @Consumes(MediaType.APPLICATION_XML)
    public void stopEsper() {
        Thread thread = null;

        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);

        for (int i = 0; i < threadArray.length; i++) {
            if (threadArray[i].getName().equals("ESPER")) {
                thread = threadArray[i];
            }
        }
        thread.stop();

    }

    @PUT
    @Path("/dataassetfunction")
    @Consumes(MediaType.APPLICATION_XML)
    public void submitDataAssetFunction(String dafXML) {
        try {
        String log= "Recieved:" + dafXML;
       Logger.getLogger(EsperRESTWS.class.getName()).log(Level.INFO, log);
        DataAssetFunctionStreamingData daf = JAXBUtils.unmarshal(dafXML, DataAssetFunctionStreamingData.class);
        IOUtils.writeData(dafXML, daf.getDaFunctionID());
        } catch (JAXBException ex) {
           // Logger.getLogger(EsperRESTWS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @PUT
    @Path("/momconf")
    @Consumes(MediaType.APPLICATION_XML)
    public void submitMOMConf(String xmltring) {
    
            System.out.println("Recieved: " + xmltring);       
            IOUtils.writeData(xmltring, "momconf");
          
        
    }
    
    
    @PUT
    @Path("/taskdistributionconf")
    @Consumes(MediaType.APPLICATION_XML)
    public void submitTaskDistributionConf(String xmltring) {
        try {
            TaskDistributionConfiguration obj = JAXBUtils.unmarshal(xmltring, TaskDistributionConfiguration.class);
            IOUtils.writeData(xmltring, "taskdistributionconf");
        } catch (JAXBException ex) {
            Logger.getLogger(EsperRESTWS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
