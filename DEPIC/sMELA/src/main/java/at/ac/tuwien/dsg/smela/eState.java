/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.smela;

import at.ac.tuwien.dsg.depic.utility.Utilities;
import at.ac.tuwien.dsg.sybl.elasticstate.ElasticState;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.apache.commons.io.FileUtils;

/**
 * REST Web Service
 *
 * @author Jun
 */
@Path("eState")
public class eState {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of eState
     */
    public eState() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.smela.eState
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object

        Utilities util = new Utilities();
        String xmlString = util.getCurrentElasticState();

        return xmlString;
    }

    /**
     * PUT method for updating or creating an instance of eState
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    @Produces("application/xml")
    public void putXml(String content) {
        System.out.println("MELA recieve: " + content);
        Utilities util = new Utilities();
        util.saveCurrentElasticState(content);

    }
}
