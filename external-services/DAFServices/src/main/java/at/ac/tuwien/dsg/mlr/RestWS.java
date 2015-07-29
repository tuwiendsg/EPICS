/**
 * Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup
  E184.  This work was partially supported by the European Commission in terms
 * of the CELAR FP7 project (FP7-ICT-2011-8 #317790).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package at.ac.tuwien.dsg.mlr;



import at.ac.tuwien.dsg.mlr.util.Configuration;
import at.ac.tuwien.dsg.mlr.util.IOUtils;
import at.ac.tuwien.dsg.task.KMeans;
import at.ac.tuwien.dsg.task.MySQLOutputer;
import at.ac.tuwien.dsg.task.QueueClient;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;


/**
 * REST Web Service
 *
 * @author bolobala
 */
@Path("daf")
public class RestWS{

    @Context
    private UriInfo context;


    public RestWS() {
    }

 
    
//    @GET
//    @Path("/{param}")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getXml(@PathParam("param") String msg) {
//
//        Configuration cfg = new Configuration();
//        String configStr = msg + ": " + cfg.getConfig("DATA.ASSET.LOADER.IP");
//        return configStr;
//    }
    
    
    @GET
    @Path("momreader")
    @Produces("application/xml")
    public String startMOMReader(String momip) {
       
         String mom_ip = "128.130.172.230";
        String mom_port = "9124";
        String queue_name = "DAAS";
        
        QueueClient qc = new QueueClient(mom_ip, mom_port, queue_name);
        qc.start();
        
        System.out.println("MOM READER STARTED ... ");
       
       
        
        return "";
    }
    
    @GET
    @Path("speedestimation/{param}")
    @Produces("application/xml")
    public String startSpeedEstimation(@PathParam("param") String stopCondition) {
       
        System.out.println("KMEANS STARTED ... ");
        
        List<Integer> listOfAttibuteIndice = new ArrayList<Integer>();
        listOfAttibuteIndice.add(1);
        listOfAttibuteIndice.add(2);
        
        KMeans kMeans = new KMeans(10, 20, 0.00001, 3, listOfAttibuteIndice);
        kMeans.start();
        
        
        return "";
    }
    
    @GET
    @Path("outputer")
    @Produces("application/xml")
    public String startOutputer() {
       
        System.out.println("OUTPUTER STARTED ... ");
        
         MySQLOutputer mySQLOutputer = new MySQLOutputer();
        mySQLOutputer.start();
        
        return "outputer";
    }

  
}
