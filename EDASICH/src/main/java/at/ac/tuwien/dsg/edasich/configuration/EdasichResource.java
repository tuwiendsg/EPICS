/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.ac.tuwien.dsg.edasich.configuration;

import at.ac.tuwien.dsg.edasich.entity.stream.DataAssetFunctionStreamingData;
import at.ac.tuwien.dsg.edasich.service.engine.AnalyticController;
import static at.ac.tuwien.dsg.edasich.test.test.getDAF;
import at.ac.tuwien.dsg.edasich.utils.IOUtils;
import at.ac.tuwien.dsg.edasich.utils.JAXBUtils;
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
@Path("edasich")
public class EdasichResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EdasichResource
     */
    public EdasichResource() {
    }

    /**
     * Retrieves representation of an instance of
     * at.ac.tuwien.dsg.edasich.configuration.EdasichResource
     *
     * @return an instance of java.lang.String
     */

    /**
     * PUT method for updating or creating an instance of EdasichResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("/dataassetfunction")
    @Consumes("application/xml")
    public void submitDataAssetFunction(String dafXML) {
       
            System.out.println("Recieved:" + dafXML);
            
            /*
            DataAssetFunctionStreamingData daf = JAXBUtils.unmarshal(dafXML, DataAssetFunctionStreamingData.class);

            AnalyticController controler = new AnalyticController();
            controler.submitDataAssetFunctionStreamingData("ae1", daf);
            Configuration.submitMOMConf("ae1");
            controler.startAnalyticEngine("ae1", daf.getDaFunctionID());
*/
        

    }
}
