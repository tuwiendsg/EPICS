/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.esperstreamprocessing.entity;

import at.ac.tuwien.dsg.edasich.configuration.MOMConfiguration;
import at.ac.tuwien.dsg.edasich.configuration.TaskDistributionConfiguration;
import at.ac.tuwien.dsg.edasich.entity.stream.DataAssetFunctionStreamingData;
import at.ac.tuwien.dsg.edasich.utils.JAXBUtils;
import at.ac.tuwien.dsg.esperstreamprocessing.queueclient.QueueClient;
import at.ac.tuwien.dsg.esperstreamprocessing.test.test;
import at.ac.tuwien.dsg.esperstreamprocessing.utils.IOUtils;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;

/**
 * REST Web Service
 *
 * @author Jun
 */
@Path("esper")
public class EsperResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EsperResource
     */
    public EsperResource() {
    }


    @PUT
    @Path("/start")
    @Consumes("application/xml")
    public void startEsper(String xmlString) {
        
        System.out.println("Recieved:" + xmlString);
        
        String xmlData = IOUtils.readData(xmlString);
        System.out.println(""+xmlData);
         DataAssetFunctionStreamingData daf=null;
        try {
            daf = JAXBUtils.unmarshal(xmlData, DataAssetFunctionStreamingData.class);
            System.out.println("" + daf.getDaFunctionName());
        
        
        } catch (JAXBException ex) {
            
        }
        
        QueueClient qc = new QueueClient();
        qc.configureDataAssetFunction(daf);
        
        Thread thread = new Thread(qc, "ESPER");
        thread.start();
    
                }


    @PUT
    @Path("/stop")
    @Consumes("application/xml")
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
    @Consumes("application/xml")
    public void submitDataAssetFunction(String dafXML) {
        try {
            System.out.println("Recieved:" + dafXML);
            DataAssetFunctionStreamingData daf = JAXBUtils.unmarshal(dafXML, DataAssetFunctionStreamingData.class);
            IOUtils.writeData(dafXML, daf.getDaFunctionID());
            
                    } catch (JAXBException ex) {
            Logger.getLogger(EsperResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    @PUT
    @Path("/momconf")
    @Consumes("application/xml")
    public void submitMOMConf(String xmltring) {
    
            System.out.println("Recieved: " + xmltring);       
            IOUtils.writeData(xmltring, "momconf");
          
        
    }
    
    
    @PUT
    @Path("/taskdistributionconf")
    @Consumes("application/xml")
    public void submitTaskDistributionConf(String xmltring) {
        try {
            TaskDistributionConfiguration obj = JAXBUtils.unmarshal(xmltring, TaskDistributionConfiguration.class);
            IOUtils.writeData(xmltring, "taskdistributionconf");
            
                    } catch (JAXBException ex) {
            Logger.getLogger(EsperResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
