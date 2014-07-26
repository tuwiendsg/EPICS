/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package at.ac.tuwien.dsg.qualityevaluator;

import at.ac.tuwien.dsg.common.entity.DataObject;
import at.ac.tuwien.dsg.common.jaxb.DataObjectJAXB;
import at.ac.tuwien.dsg.qualityevaluator.datacompleteness.DataCompletenessMeasurement;
import at.ac.tuwien.dsg.qualityevaluator.restclient.DataObjectRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
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
@Path("QualityEvaluator")
public class QualityEvaluatorResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of QualityEvaluatorResource
     */
    public QualityEvaluatorResource() {
    }

    /**
     * Retrieves representation of an instance of at.ac.tuwien.dsg.qualityevaluator.QualityEvaluatorResource
     * @return an instance of java.lang.String
     */
    @GET
    @Consumes("application/xml")
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        
        int k=0;
        
        while (k<10) {            
            DataObjectRequest request = new DataObjectRequest();
        String dataObjectStr = request.getDataObjectString();
        DataObjectJAXB dataObjectJAXB = new DataObjectJAXB();
        DataObject dataObject = dataObjectJAXB.unmarshallingObject(dataObjectStr);
        
        DataCompletenessMeasurement measurement = new DataCompletenessMeasurement();
        double rs = measurement.evaluateDataCompleteness(dataObject);
        
        System.out.println("Measurement: " + rs);
        k++;
        }
        
        
        return "OK";
    }

    /**
     * PUT method for updating or creating an instance of QualityEvaluatorResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/xml")
    public void putXml(String content) {
    }
}
