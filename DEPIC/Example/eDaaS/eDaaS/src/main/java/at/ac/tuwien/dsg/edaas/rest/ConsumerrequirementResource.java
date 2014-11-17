/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edaas.rest;

import at.ac.tuwien.dsg.common.entity.eda.da.DataAsset;
import at.ac.tuwien.dsg.common.utils.JAXBUtils;

import at.ac.tuwien.dsg.edaas.requirement.DataAssetRequest;
import at.ac.tuwien.dsg.edaas.requirement.service.DataAssetRequestHandler;
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
@Path("consumerrequirement")
public class ConsumerrequirementResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ConsumerrequirementResource
     */
    public ConsumerrequirementResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.edaas.ConsumerrequirementResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        //TODO return proper representation object
        return "eDaaS";
    }

    /**
     * PUT method for updating or creating an instance of ConsumerrequirementResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public String requestDataAsset(String dataAssetRequestXML) {
        
        DataAssetRequest  dataAssetRequest=null;
        try {
            dataAssetRequest = JAXBUtils.unmarshal(dataAssetRequestXML, DataAssetRequest.class);
        } catch (JAXBException ex) {
            Logger.getLogger(ConsumerrequirementResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DataAssetRequestHandler dataAssetRequestHandler = new DataAssetRequestHandler();
        DataAsset da = dataAssetRequestHandler.processDataAssetRequest(dataAssetRequest);
        String daXML="";
        try {
            daXML = JAXBUtils.marshal(da,DataAsset.class);
        } catch (JAXBException ex) {
            Logger.getLogger(ConsumerrequirementResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return daXML;
    }
}
