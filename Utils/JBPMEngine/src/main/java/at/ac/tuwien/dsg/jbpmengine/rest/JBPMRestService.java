package at.ac.tuwien.dsg.jbpmengine.rest;

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

//http://localhost:8080/RESTfulExample/rest/message/hello%20world
@Path("/jbpm")
public class JBPMRestService {

    @GET
    @Path("/{param}")
    @Produces(MediaType.TEXT_PLAIN)
    public String printMessage(@PathParam("param") String param) {

        String result = "DAF: " + param;
         Logger.getLogger(JBPMRestService.class.getName()).log(Level.INFO,result);
        return result;

    }

    @PUT
    @Path("/start")
    @Consumes(MediaType.APPLICATION_XML)
    public void startEsper(String xmlString) {
        
         Logger.getLogger(JBPMRestService.class.getName()).log(Level.INFO,"START" + xmlString);
      

    }

    @PUT
    @Path("/stop")
    @Consumes(MediaType.APPLICATION_XML)
    public void stopEsper(String xmlString) {

         Logger.getLogger(JBPMRestService.class.getName()).log(Level.INFO,"STOP" + xmlString);

    }

    @PUT
    @Path("/dataassetfunction")
    @Consumes(MediaType.APPLICATION_XML)
    public void submitDataAssetFunction(String dafXML) {
 
        String log = dafXML;
            Logger.getLogger(JBPMRestService.class.getName()).log(Level.INFO, log);
        
    }

}
