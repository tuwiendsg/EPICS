/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.ssybl;

import at.ac.tuwien.dsg.depic.dataelasticityrequirement.DataElasticityRequirement;
import at.ac.tuwien.dsg.depic.utility.MELARestWS;

import at.ac.tuwien.dsg.depic.utility.Utilities;
import at.ac.tuwien.dsg.sybl.controller.DataElasticityController;
import at.ac.tuwien.dsg.sybl.elasticstate.ElasticState;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * REST Web Service
 *
 * @author Jun
 */
@Path("elReq")
public class ElasticityRequirement {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ElasticityRequirement
     */
    public ElasticityRequirement() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.ssybl.ElasticityRequirement
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of ElasticityRequirement
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    @Produces("application/xml")
    public void putXml(String content) {
        System.out.println("SYBL recieve: " + content);
        Utilities util = new Utilities();
        DataElasticityRequirement dataElasticityRequirement = util.unMarsharlXML2DataElasticityRequirement(content);
        DataElasticityController dataElasticityController = new DataElasticityController();
        dataElasticityController.findElasticState(dataElasticityRequirement);
      
        ElasticState eState = dataElasticityController.geteState();
        System.out.println("Status of eState: " + eState.getStatus());
        String eStateXML = util.convertElasticStateToXML(eState);
        System.out.println("eStateXML: " + eStateXML);
       
        ElasticState elasticState2 = util.unMarshalXML2ElasticState(eStateXML);
        System.out.println("Status2: " + elasticState2.getStatus());
        
        MELARestWS restWS = new MELARestWS();
        restWS.sendElasticState(eStateXML);
        
       
    }
}
